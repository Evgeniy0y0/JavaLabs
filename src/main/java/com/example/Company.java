package com.example;

import java.util.ArrayList;
import java.util.List;

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
}