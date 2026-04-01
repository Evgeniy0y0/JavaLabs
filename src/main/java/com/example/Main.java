package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Employee> employees = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            int choice = readIntInput();
            switch (choice) {
                case 1:
                    createObjectMenu();
                    break;
                case 2:
                    printAllEmployees();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2 or 3.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Create new object");
        System.out.println("2. Show all objects");
        System.out.println("3. Exit");
        System.out.print("Your choice: ");
    }

    private static void createObjectMenu() {
        while (true) {
            System.out.println("\nSelect type of object to create:");
            System.out.println("1. Employee");
            System.out.println("2. Contract Employee");
            System.out.println("3. Full-Time Employee");
            System.out.println("4. Intern");
            System.out.println("5. Manager");
            System.out.println("6. Return to main menu");
            System.out.print("Choice: ");
            int type = readIntInput();
            switch (type) {
                case 1:
                    createEmployee();
                    return;
                case 2:
                    createContractEmployee();
                    return;
                case 3:
                    createFullTimeEmployee();
                    return;
                case 4:
                    createIntern();
                    return;
                case 5:
                    createManager();
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Create base Employee");
        System.out.println("2. Create Contract Employee");
        System.out.println("3. Create Full-Time Employee");
        System.out.println("4. Show all employees");
        System.out.println("5. Exit");
        System.out.print("Your choice: ");
    }

    private static int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static void createIntern() {
        System.out.println("\n--- Create Intern ---");
        String name = readString("Name: ");
        String position = readString("Position: ");
        double salary = readDouble("Salary: ");
        String department = readString("Department: ");
        int experience = readInt("Experience (years): ");
        EmploymentType type = readEmploymentType();
        String university = readString("University: ");
        int duration = readInt("Duration (months): ");

        try {
            Intern intern = new Intern(name, position, salary, department, experience, type, university, duration);
            employees.add(intern);
            System.out.println("Intern added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Intern not created.");
        }
    }

    private static void createManager() {
        System.out.println("\n--- Create Manager ---");
        String name = readString("Name: ");
        String position = readString("Position: ");
        double salary = readDouble("Salary: ");
        String department = readString("Department: ");
        int experience = readInt("Experience (years): ");
        EmploymentType type = readEmploymentType();
        int teamSize = readInt("Team size: ");
        String managedDept = readString("Managed department: ");

        try {
            Manager manager = new Manager(name, position, salary, department, experience, type, teamSize, managedDept);
            employees.add(manager);
            System.out.println("Manager added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Manager not created.");
        }
    }

    private static void createEmployee() {
        System.out.println("\n--- Create base Employee ---");
        String name = readString("Name: ");
        String position = readString("Position: ");
        double salary = readDouble("Salary: ");
        String department = readString("Department: ");
        int experience = readInt("Experience (years): ");
        EmploymentType employmentType = readEmploymentType();

        try {
            Employee emp = new Employee(name, position, salary, department, experience, employmentType);
            employees.add(emp);
            System.out.println("Base Employee added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Employee not created.");
        }
    }

    private static void createContractEmployee() {
        System.out.println("\n--- Create Contract Employee ---");
        String name = readString("Name: ");
        String position = readString("Position: ");
        double salary = readDouble("Salary: ");
        String department = readString("Department: ");
        int experience = readInt("Experience (years): ");
        EmploymentType employmentType = readEmploymentType();
        double hourlyRate = readDouble("Hourly rate: ");
        int hoursWorked = readInt("Hours worked: ");

        try {
            ContractEmployee emp = new ContractEmployee(name, position, salary, department,
                    experience, employmentType, hourlyRate, hoursWorked);
            employees.add(emp);
            System.out.println("Contract Employee added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Employee not created.");
        }
    }

    private static void createFullTimeEmployee() {
        System.out.println("\n--- Create Full-Time Employee ---");
        String name = readString("Name: ");
        String position = readString("Position: ");
        double salary = readDouble("Salary: ");
        String department = readString("Department: ");
        int experience = readInt("Experience (years): ");
        EmploymentType employmentType = readEmploymentType();
        double bonus = readDouble("Annual bonus: ");
        int vacationDays = readInt("Vacation days: ");

        try {
            FullTimeEmployee emp = new FullTimeEmployee(name, position, salary, department,
                    experience, employmentType, bonus, vacationDays);
            employees.add(emp);
            System.out.println("Full-Time Employee added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Employee not created.");
        }
    }

    private static void printAllEmployees() {
        System.out.println("\n--- List of employees ---");
        if (employees.isEmpty()) {
            System.out.println("No employees yet.");
        } else {
            for (int i = 0; i < employees.size(); i++) {
                System.out.println((i + 1) + ". " + employees.get(i));
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please try again.");
            }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer format. Please try again.");
            }
        }
    }

    private static EmploymentType readEmploymentType() {
        while (true) {
            System.out.print("Employment type (FULL_TIME, PART_TIME, CONTRACTOR): ");
            String input = scanner.nextLine().toUpperCase();
            try {
                return EmploymentType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid employment type. Please enter one of: FULL_TIME, PART_TIME, CONTRACTOR");
            }
        }
    }
}