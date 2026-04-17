package com.example;

import java.io.*;
import java.util.*;

public class Main {
    private static Company company;
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        loadFromFile(FILE_NAME);
        if (company == null) {
            company = new Company("Default Company");
        }

        while (true) {
            printMainMenu();
            int choice = readIntInput();
            switch (choice) {
                case 1:
                    searchMenu();
                    break;
                case 2:
                    createObjectMenu();
                    break;
                case 3:
                    company.printAllEmployees();
                    break;
                case 4:
                    sortMenu();
                    break;
                case 5:
                    updateObject(); break;
                case 6:
                    deleteObject(); break;
                case 7:
                    saveToFile(FILE_NAME); System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Search objects");
        System.out.println("2. Create new object");
        System.out.println("3. Show all objects");
        System.out.println("4. Sort and show objects");
        System.out.println("5. Update an object");
        System.out.println("6. Delete an object");
        System.out.println("7. Exit");
        System.out.print("Your choice: ");
    }

    private static void searchMenu() {
        while (true) {
            System.out.println("\n=== Search Menu ===");
            System.out.println("1. Search by name (contains)");
            System.out.println("2. Search by department (exact match)");
            System.out.println("3. Search by minimum salary");
            System.out.println("4. Search by UUID");
            System.out.println("5. Return to main menu");
            System.out.print("Choice: ");
            int choice = readIntInput();
            switch (choice) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    searchByDepartment();
                    break;
                case 3:
                    searchByMinSalary();
                    break;
                case 4:
                    searchByUuid();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void sortMenu() {
        while (true) {
            System.out.println("\n=== Sort Criteria ===");
            System.out.println("1. By name (ascending)");
            System.out.println("2. By salary (ascending)");
            System.out.println("3. By experience years (descending)");
            System.out.println("4. Return to main menu");
            System.out.print("Choice: ");
            int choice = readIntInput();
            switch (choice) {
                case 1:
                    Comparator<Employee> byName = (e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName());
                    company.printSortedEmployees(byName, "name");
                    break;
                case 2:
                    Comparator<Employee> bySalary = (e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary());
                    company.printSortedEmployees(bySalary, "salary (low to high)");
                    break;
                case 3:
                    Comparator<Employee> byExperienceDesc = (e1, e2) -> Integer.compare(e2.getExperienceYears(), e1.getExperienceYears());
                    company.printSortedEmployees(byExperienceDesc, "experience (high to low)");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void updateObject() {
        if (company.getAllRecords().isEmpty()) {
            System.out.println("No employees to update.");
            return;
        }
        company.printAllEmployees();
        int index = readInt("Enter the number of the employee to update: ") - 1;
        if (index < 0 || index >= company.getAllRecords().size()) {
            System.out.println("Invalid index.");
            return;
        }
        Employee oldEmp = company.getAllRecords().get(index).getEmployee();
        System.out.println("Selected: " + oldEmp.getName());

        System.out.println("What do you want to change?");
        System.out.println("1. Name");
        System.out.println("2. Position");
        System.out.println("3. Salary");
        System.out.println("4. Department");
        System.out.println("5. Experience years");
        System.out.println("6. Employment type");
        int attr = readIntInput();
        Employee newEmp = null;
        try {
            newEmp = cloneEmployee(oldEmp);
            switch (attr) {
                case 1:
                    String newName = readString("New name: ");
                    newEmp.setName(newName);
                    break;
                case 2:
                    String newPos = readString("New position: ");
                    newEmp.setPosition(newPos);
                    break;
                case 3:
                    double newSalary = readDouble("New salary: ");
                    newEmp.setSalary(newSalary);
                    break;
                case 4:
                    String newDept = readString("New department: ");
                    newEmp.setDepartment(newDept);
                    break;
                case 5:
                    int newExp = readInt("New experience (years): ");
                    newEmp.setExperienceYears(newExp);
                    break;
                case 6:
                    EmploymentType newType = readEmploymentType();
                    newEmp.setEmploymentType(newType);
                    break;
                default:
                    System.out.println("Invalid attribute.");
                    return;
            }
            if (company.update(oldEmp, newEmp)) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Update failed (employee not found).");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Employee cloneEmployee(Employee original) {
        if (original instanceof ContractEmployee) {
            ContractEmployee ce = (ContractEmployee) original;
            return new ContractEmployee(ce.getName(), ce.getPosition(), ce.getSalary(),
                    ce.getDepartment(), ce.getExperienceYears(), ce.getEmploymentType(),
                    ce.getHourlyRate(), ce.getHoursWorked());
        } else if (original instanceof FullTimeEmployee) {
            FullTimeEmployee fe = (FullTimeEmployee) original;
            return new FullTimeEmployee(fe.getName(), fe.getPosition(), fe.getSalary(),
                    fe.getDepartment(), fe.getExperienceYears(), fe.getEmploymentType(),
                    fe.getBonus(), fe.getVacationDays());
        } else if (original instanceof Intern) {
            Intern in = (Intern) original;
            return new Intern(in.getName(), in.getPosition(), in.getSalary(),
                    in.getDepartment(), in.getExperienceYears(), in.getEmploymentType(),
                    in.getUniversity(), in.getDurationMonths());
        } else if (original instanceof Manager) {
            Manager mg = (Manager) original;
            return new Manager(mg.getName(), mg.getPosition(), mg.getSalary(),
                    mg.getDepartment(), mg.getExperienceYears(), mg.getEmploymentType(),
                    mg.getTeamSize(), mg.getManagedDepartment());
        } else {
            throw new IllegalArgumentException("Unknown employee type");
        }
    }

    private static void deleteObject() {
        if (company.getAllRecords().isEmpty()) {
            System.out.println("No employees to delete.");
            return;
        }
        company.printAllEmployees();
        int index = readInt("Enter the number of the employee to delete: ") - 1;
        if (index < 0 || index >= company.getAllRecords().size()) {
            System.out.println("Invalid index.");
            return;
        }
        Employee emp = company.getAllRecords().get(index).getEmployee();
        System.out.print("Are you sure you want to delete " + emp.getName() + "? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("y")) {
            if (company.delete(emp)) {
                System.out.println("Employee deleted.");
            } else {
                System.out.println("Delete failed.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void searchByName() {
        System.out.print("Enter name fragment: ");
        String fragment = scanner.nextLine().trim();
        if (fragment.isEmpty()) {
            System.out.println("Search fragment cannot be empty.");
            return;
        }
        List<Employee> results = company.searchByName(fragment);
        printSearchResults(results);
    }

    private static void searchByDepartment() {
        System.out.print("Enter department name: ");
        String dept = scanner.nextLine().trim();
        if (dept.isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }
        List<Employee> results = company.searchByDepartment(dept);
        printSearchResults(results);
    }

    private static void searchByMinSalary() {
        System.out.print("Enter minimum salary: ");
        double minSalary = readDoubleInput();
        List<Employee> results = company.searchByMinSalary(minSalary);
        printSearchResults(results);
    }

    private static void searchByUuid() {
        System.out.print("Enter UUID (e.g., 123e4567-e89b-12d3-a456-426614174000): ");
        String input = scanner.nextLine().trim();
        try {
            UUID uuid = UUID.fromString(input);
            Employee emp = company.findEmployeeByUuid(uuid);
            if (emp == null) {
                System.out.println("Employee with UUID " + uuid + " not found.");
            } else {
                System.out.println("Found: " + emp); // виведе всі поля
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Please try again.");
        }
    }

    private static void printSearchResults(List<Employee> results) {
        if (results.isEmpty()) {
            System.out.println("No employees match the search criteria.");
        } else {
            System.out.println("\nFound " + results.size() + " employee(s):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }

    private static void createObjectMenu() {
        while (true) {
            System.out.println("\nSelect type of object to create:");
            System.out.println("1. Contract Employee");
            System.out.println("2. Full-Time Employee");
            System.out.println("3. Intern");
            System.out.println("4. Manager");
            System.out.println("5. Return to main menu");
            System.out.print("Choice: ");
            int type = readIntInput();
            switch (type) {
                case 1: createContractEmployee(); return;
                case 2: createFullTimeEmployee(); return;
                case 3: createIntern(); return;
                case 4: createManager(); return;
                case 5: return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Create employee functions
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
            company.addEmployee(emp, 1);
            System.out.println("Contract Employee added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
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
            company.addEmployee(emp, 1);
            System.out.println("Full-Time Employee added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
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
            company.addEmployee(intern, 1);
            System.out.println("Intern added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
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
            company.addEmployee(manager, 1);
            System.out.println("Manager added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // File functions

    private static void loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found. Starting with empty collection.");
            company = new Company("Default Company");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String firstLine = reader.readLine();
            if (firstLine == null || !firstLine.startsWith("Company;")) {
                System.out.println("Invalid file format. Starting with empty company.");
                company = new Company("Default Company");
                return;
            }
            String companyName = firstLine.split(";")[1];
            company = new Company(companyName);

            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;
                EmployeeWithQuantity eq = parseEmployeeWithQuantity(line);
                if (eq != null) {
                    company.addEmployee(eq.employee, eq.quantity);
                } else {
                    System.out.println("Skipping invalid line " + lineNum + ": " + line);
                }
            }
            System.out.println("Loaded " + company.getAllRecords().size() + " employee types from file.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            company = new Company("Default Company");
        }
    }

    private static void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Company;" + company.getName());
            writer.newLine();
            for (var record : company.getAllRecords()) {
                Employee emp = record.getEmployee();
                int qty = record.getQuantity();
                String baseLine = formatEmployee(emp);
                writer.write(baseLine + ";" + qty);
                writer.newLine();
            }
            System.out.println("Saved " + company.getAllRecords().size() + " employee types to file.");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Parsing helpers

    private static class EmployeeWithQuantity {
        Employee employee;
        int quantity;
        EmployeeWithQuantity(Employee emp, int q) { employee = emp; quantity = q; }
    }

    private static EmployeeWithQuantity parseEmployeeWithQuantity(String line) {
        String[] parts = line.split(";");
        if (parts.length < 8) return null;
        int quantity;
        try {
            quantity = Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return null;
        }
        String[] empParts = new String[parts.length - 1];
        System.arraycopy(parts, 0, empParts, 0, parts.length - 1);
        String empLine = String.join(";", empParts);
        Employee emp = parseEmployee(empLine);
        if (emp == null) return null;
        return new EmployeeWithQuantity(emp, quantity);
    }

    private static Employee parseEmployee(String line) {
        String[] parts = line.split(";");
        if (parts.length < 2) return null;
        String type = parts[0];
        try {
            switch (type) {
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

    private static int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static double readDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter a valid number: ");
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