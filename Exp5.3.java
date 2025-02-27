Menu-based Java application that allows you to add employee details, display all employees, and exit. The employee details will be stored in a file, and the program will
read the file to display the stored employee information.

Steps:
1. Create an Employee class with fields like name, id, designation, and salary.
2. Create a menu with three options:
Add an Employee
Display All Employees
Exit
3. Store Employee Data in a File: Serialize the employee objects and store them in a file.
4. Read Employee Data from the File: Deserialize the employee objects from the file and display the details.
5. Handle Exceptions: Handle file I/O exceptions.



---Implementation
  
Employee Class: This class contains details like name, id, designation, and salary. It implements Serializable to allow serialization of Employee objects.
addEmployee(): This method takes input from the user for an employee's details, creates an Employee object, and saves it to a file using ObjectOutputStream.
saveEmployeeToFile(): This method appends employee details to a file. The file is opened in append mode (true parameter in FileOutputStream).
displayAllEmployees(): This method reads all employee objects from the file and prints their details.
readEmployeesFromFile(): This method reads the employee objects from the file using ObjectInputStream and stores them in a list. 
The loop continues until the end of the file is reached (IOFException).

CODE:
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int id;
    private String designation;
    private double salary;

    public Employee(String name, int id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee ID: " + id + ", Name: " + name + ", Designation: " + designation + ", Salary: " + salary;
    }
}

public class EmployeeManagement {
    private static final String FILE_NAME = "employees.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Add an Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    displayAllEmployees();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void addEmployee(Scanner scanner) {
        System.out.print("Employee Name: ");
        String name = scanner.nextLine();
        System.out.print("Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Designation: ");
        String designation = scanner.nextLine();
        System.out.print("Salary: ");
        double salary = scanner.nextDouble();

        Employee employee = new Employee(name, id, designation, salary);
        saveEmployeeToFile(employee);
        System.out.println("Employee added successfully!");
    }

    private static void saveEmployeeToFile(Employee employee) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME, true))) {
            oos.writeObject(employee);
        } catch (IOException e) {
            System.out.println("Error saving employee to file: " + e.getMessage());
        }
    }

    private static void displayAllEmployees() {
        List<Employee> employees = readEmployeesFromFile();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private static List<Employee> readEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            while (true) {
                Employee employee = (Employee) ois.readObject();
                employees.add(employee);
            }
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading employees from file: " + e.getMessage());
        }
        return employees;
    }
}


Test Cases:

Test Case 1: Add a new employee and display all employees.
Steps: Select option 1 to add a new employee, then select option 2 to display all employees.
Input:
Employee Name: John Doe
Employee ID: 101
Designation: Software Engineer
Salary: 50000
  
Expected Output:
Employee added successfully!
Employee ID: 101, Name: John Doe, Designation: Software Engineer, Salary: 50000.0

Test Case 2: Try adding multiple employees and display all of them.
Steps: Add multiple employees (using option 1) and then display all employees (using option 2).
Expected Output:
Employee added successfully!
Employee ID: 101, Name: John Doe, Designation: Software Engineer, Salary: 50000.0
Employee added successfully!
Employee ID: 102, Name: Jane Smith, Designation: Manager, Salary: 75000.0
