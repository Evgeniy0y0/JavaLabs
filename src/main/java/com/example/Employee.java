package com.example;
import java.util.Objects;

public class Employee {
    private static int objectCount = 0;
    private String name;
    private String position;
    private double salary;
    private String department;
    private int experienceYears;
    private EmploymentType employmentType;

    public Employee(String name, String position, double salary, String department, int experienceYears, EmploymentType employmentType) {
        setName(name);
        setPosition(position);
        setSalary(salary);
        setDepartment(department);
        setExperienceYears(experienceYears);
        setEmploymentType(employmentType);
        objectCount++;
    }

    public Employee(Employee other) {
        this.name = other.name;
        this.position = other.position;
        this.salary = other.salary;
        this.department = other.department;
        this.experienceYears = other.experienceYears;
        this.employmentType = other.employmentType;
        objectCount++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Position cannot be null or empty");
        }
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        this.department = department;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Experience years cannot be negative");
        }
        this.experienceYears = experienceYears;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        if (employmentType == null) {
            throw new IllegalArgumentException("Employment type cannot be null");
        }
        this.employmentType = employmentType;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                ", experienceYears=" + experienceYears +
                ", employmentType=" + employmentType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Double.compare(employee.salary, salary) == 0 &&
                experienceYears == employee.experienceYears &&
                Objects.equals(name, employee.name) &&
                Objects.equals(position, employee.position) &&
                Objects.equals(department, employee.department) &&
                employmentType == employee.employmentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, salary, department, experienceYears, employmentType);
    }
}