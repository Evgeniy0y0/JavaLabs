package com.example;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private List<Employee> employees;

    public Company(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee emp) {
        if (emp == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        employees.add(emp);
    }

    public void removeEmployee(Employee emp) {
        employees.remove(emp);
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }
}