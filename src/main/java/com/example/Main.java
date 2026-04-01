package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Employee> employees = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "input.txt"; // константа

    public static void main(String[] args) {
        loadFromFile(FILE_NAME);

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
                    saveToFile(FILE_NAME);
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice.");
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

    private static void loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found. Starting with empty collection.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;
                Employee emp = parseEmployee(line);
                if (emp != null) {
                    employees.add(emp);
                } else {
                    System.out.println("Skipping invalid line " + lineNum + ": " + line);
                }
            }
            System.out.println("Loaded " + employees.size() + " employees from file.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Employee emp : employees) {
                writer.write(formatEmployee(emp));
                writer.newLine();
            }
            System.out.println("Saved " + employees.size() + " employees to file.");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    private static Employee parseEmployee(String line) {
        String[] parts = line.split(";");
        if (parts.length < 2) return null;

        String type = parts[0];
        try {
            switch (type) {
                case "Employee":
                    if (parts.length != 7) return null;
                    return new Employee(
                            parts[1], parts[2], Double.parseDouble(parts[3]),
                            parts[4], Integer.parseInt(parts[5]),
                            EmploymentType.valueOf(parts[6])
                    );
                case "ContractEmployee":
                    if (parts.length != 9) return null;
                    return new ContractEmployee(
                            parts[1], parts[2], Double.parseDouble(parts[3]),
                            parts[4], Integer.parseInt(parts[5]),
                            EmploymentType.valueOf(parts[6]),
                            Double.parseDouble(parts[7]), Integer.parseInt(parts[8])
                    );
                case "FullTimeEmployee":
                    if (parts.length != 9) return null;
                    return new FullTimeEmployee(
                            parts[1], parts[2], Double.parseDouble(parts[3]),
                            parts[4], Integer.parseInt(parts[5]),
                            EmploymentType.valueOf(parts[6]),
                            Double.parseDouble(parts[7]), Integer.parseInt(parts[8])
                    );
                case "Intern":
                    if (parts.length != 9) return null;
                    return new Intern(
                            parts[1], parts[2], Double.parseDouble(parts[3]),
                            parts[4], Integer.parseInt(parts[5]),
                            EmploymentType.valueOf(parts[6]),
                            parts[7], Integer.parseInt(parts[8])
                    );
                case "Manager":
                    if (parts.length != 9) return null;
                    return new Manager(
                            parts[1], parts[2], Double.parseDouble(parts[3]),
                            parts[4], Integer.parseInt(parts[5]),
                            EmploymentType.valueOf(parts[6]),
                            Integer.parseInt(parts[7]), parts[8]
                    );
                default:
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Parse error: " + e.getMessage());
            return null;
        }
    }

    private static String formatEmployee(Employee emp) {
        if (emp instanceof ContractEmployee) {
            ContractEmployee ce = (ContractEmployee) emp;
            return String.format("ContractEmployee;%s;%s;%f;%s;%d;%s;%f;%d",
                    ce.getName(), ce.getPosition(), ce.getSalary(),
                    ce.getDepartment(), ce.getExperienceYears(),
                    ce.getEmploymentType().name(),
                    ce.getHourlyRate(), ce.getHoursWorked());
        } else if (emp instanceof FullTimeEmployee) {
            FullTimeEmployee fe = (FullTimeEmployee) emp;
            return String.format("FullTimeEmployee;%s;%s;%f;%s;%d;%s;%f;%d",
                    fe.getName(), fe.getPosition(), fe.getSalary(),
                    fe.getDepartment(), fe.getExperienceYears(),
                    fe.getEmploymentType().name(),
                    fe.getBonus(), fe.getVacationDays());
        } else if (emp instanceof Intern) {
            Intern in = (Intern) emp;
            return String.format("Intern;%s;%s;%f;%s;%d;%s;%s;%d",
                    in.getName(), in.getPosition(), in.getSalary(),
                    in.getDepartment(), in.getExperienceYears(),
                    in.getEmploymentType().name(),
                    in.getUniversity(), in.getDurationMonths());
        } else if (emp instanceof Manager) {
            Manager mg = (Manager) emp;
            return String.format("Manager;%s;%s;%f;%s;%d;%s;%d;%s",
                    mg.getName(), mg.getPosition(), mg.getSalary(),
                    mg.getDepartment(), mg.getExperienceYears(),
                    mg.getEmploymentType().name(),
                    mg.getTeamSize(), mg.getManagedDepartment());
        } else {
            return String.format("Employee;%s;%s;%f;%s;%d;%s",
                    emp.getName(), emp.getPosition(), emp.getSalary(),
                    emp.getDepartment(), emp.getExperienceYears(),
                    emp.getEmploymentType().name());
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