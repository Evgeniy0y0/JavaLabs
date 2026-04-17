package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Company {
    private String name;
    private List<EmployeeRecord> items;

    public Company(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addEmployee(Employee emp, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        for (EmployeeRecord record : items) {
            if (record.getEmployee().equals(emp)) {
                record.addQuantity(quantity);
                return;
            }
        }
        items.add(new EmployeeRecord(emp, quantity));
    }

    public List<Employee> searchByName(String fragment) {
        List<Employee> results = new ArrayList<>();
        for (EmployeeRecord record : items) {
            if (record.getEmployee().getName().toLowerCase().contains(fragment.toLowerCase())) {
                results.add(record.getEmployee());
            }
        }
        return results;
    }

    public List<Employee> searchByDepartment(String dept) {
        List<Employee> results = new ArrayList<>();
        for (EmployeeRecord record : items) {
            if (record.getEmployee().getDepartment().equalsIgnoreCase(dept)) {
                results.add(record.getEmployee());
            }
        }
        return results;
    }

    public List<Employee> searchByMinSalary(double minSalary) {
        List<Employee> results = new ArrayList<>();
        for (EmployeeRecord record : items) {
            if (record.getEmployee().getSalary() >= minSalary) {
                results.add(record.getEmployee());
            }
        }
        return results;
    }

    public Employee findEmployeeByUuid(UUID uuid) {
        for (EmployeeRecord record : items) {
            if (record.getEmployee().getUuid().equals(uuid)) {
                return record.getEmployee();
            }
        }
        return null;
    }

    public List<EmployeeRecord> getAllRecords() {
        return new ArrayList<>(items);
    }

    public void printAllEmployees() {
        if (items.isEmpty()) {
            System.out.println("No employees yet.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                EmployeeRecord record = items.get(i);
                System.out.println((i + 1) + ". " + record.getEmployee() + " x" + record.getQuantity());
            }
        }
    }

    public boolean update(Employee oldEmployee, Employee newEmployee) {
        for (int i = 0; i < items.size(); i++) {
            EmployeeRecord record = items.get(i);
            if (record.getEmployee().equals(oldEmployee)) {
                int quantity = record.getQuantity();
                items.set(i, new EmployeeRecord(newEmployee, quantity));
                return true;
            }
        }
        return false;
    }

    public boolean delete(Employee employee) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getEmployee().equals(employee)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public static class EmployeeRecord {
        private Employee employee;
        private int quantity;

        public EmployeeRecord(Employee employee, int quantity) {
            this.employee = employee;
            this.quantity = quantity;
        }

        public Employee getEmployee() { return employee; }
        public int getQuantity() { return quantity; }
        public void addQuantity(int delta) { this.quantity += delta; }
    }

    public List<EmployeeRecord> getSortedRecords(Comparator<Employee> comparator) {
        List<EmployeeRecord> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparing(EmployeeRecord::getEmployee, comparator));
        return sorted;
    }

    public void printSortedEmployees(Comparator<Employee> comparator, String criteriaName) {
        List<EmployeeRecord> sorted = getSortedRecords(comparator);
        if (sorted.isEmpty()) {
            System.out.println("No employees yet.");
        } else {
            System.out.println("\n--- Sorted employees by " + criteriaName + " ---");
            for (int i = 0; i < sorted.size(); i++) {
                EmployeeRecord record = sorted.get(i);
                System.out.println((i + 1) + ". " + record.getEmployee() + " x" + record.getQuantity());
            }
        }
    }
}