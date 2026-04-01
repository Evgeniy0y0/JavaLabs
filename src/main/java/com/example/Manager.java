package com.example;

public class Manager extends Employee {
    private int teamSize;
    private String managedDepartment;

    public Manager(String name, String position, double salary, String department,
                   int experienceYears, EmploymentType employmentType,
                   int teamSize, String managedDepartment) {
        super(name, position, salary, department, experienceYears, employmentType);
        setTeamSize(teamSize);
        setManagedDepartment(managedDepartment);
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        if (teamSize < 0) {
            throw new IllegalArgumentException("Team size cannot be negative");
        }
        this.teamSize = teamSize;
    }

    public String getManagedDepartment() {
        return managedDepartment;
    }

    public void setManagedDepartment(String managedDepartment) {
        if (managedDepartment == null || managedDepartment.trim().isEmpty()) {
            throw new IllegalArgumentException("Mganaged department cannot be null or empty");
        }
        this.managedDepartment = managedDepartment;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", teamSize=" + teamSize +
                ", managedDepartment='" + managedDepartment + '\'' +
                " (Manager)";
    }
}