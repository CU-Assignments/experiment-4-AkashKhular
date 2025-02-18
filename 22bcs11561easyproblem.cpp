import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}

public class EmployeeManager {
    static ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Employee\n2. Update Employee\n3. Remove Employee\n4. Search Employee\n5. Display Employees\n6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Add Employee
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Salary: ");
                    double salary = scanner.nextDouble();
                    employees.add(new Employee(id, name, salary));
                    break;
                case 2: // Update Employee
                    System.out.print("Enter ID to update: ");
                    int updateId = scanner.nextInt();
                    for (Employee emp : employees) {
                        if (emp.id == updateId) {
                            scanner.nextLine(); // consume newline
                            System.out.print("New Name: ");
                            emp.name = scanner.nextLine();
                            System.out.print("New Salary: ");
                            emp.salary = scanner.nextDouble();
                            break;
                        }
                    }
                    break;
                case 3: // Remove Employee
                    System.out.print("Enter ID to remove: ");
                    int removeId = scanner.nextInt();
                    employees.removeIf(emp -> emp.id == removeId);
                    break;
                case 4: // Search Employee
                    System.out.print("Enter ID to search: ");
                    int searchId = scanner.nextInt();
                    employees.stream().filter(emp -> emp.id == searchId).forEach(emp -> System.out.println(emp.id + " " + emp.name + " " + emp.salary));
                    break;
                case 5: // Display Employees
                    employees.forEach(emp -> System.out.println(emp.id + " " + emp.name + " " + emp.salary));
                    break;
                case 6: // Exit
                    System.out.println("Exiting.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
