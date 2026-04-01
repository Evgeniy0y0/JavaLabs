package com.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Company company = new Company("MyCompany");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readIntInput();
            switch (choice) {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    printAllEmployees();
                    break;
                case 3:
                    copyEmployee();
                    break;
                case 4:
                    showTotalObjects();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-5.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Create new employee");
        System.out.println("2. Show all employees");
        System.out.println("3. Create copy of existing employee");
        System.out.println("4. Show total objects created (static counter)");
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

    private static void createEmployee() {
        System.out.println("\n--- Create new employee ---");
        String name = readString("Name: ");
        String position = readString("Position: ");
        double salary = readDouble("Salary: ");
        String department = readString("Department: ");
        int experience = readInt("Experience (years): ");
        EmploymentType employmentType = readEmploymentType();

        try {
            Employee emp = new Employee(name, position, salary, department, experience, employmentType);
            company.addEmployee(emp);
            System.out.println("Employee added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Employee not created. Please try again.");
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

    private static void copyEmployee() {
        if (company.getEmployees().isEmpty()) {
            System.out.println("No employees available to copy. Create an employee first.");
            return;
        }

        printAllEmployees();
        int index = readInt("Enter the number of the employee to copy: ") - 1;

        try {
            Employee original = company.getEmployees().get(index);
            Employee copy = new Employee(original);
            company.addEmployee(copy);
            System.out.println("Employee copied successfully!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index. No employee copied.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error while copying: " + e.getMessage());
        }
    }

    private static void showTotalObjects() {
        System.out.println("\nTotal Employee objects created: " + Employee.getObjectCount());
    }

    private static void printAllEmployees() {
        System.out.println("\n--- List of employees ---");
        if (company.getEmployees().isEmpty()) {
            System.out.println("No employees yet.");
        } else {
            for (int i = 0; i < company.getEmployees().size(); i++) {
                System.out.println((i + 1) + ". " + company.getEmployees().get(i));
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
}