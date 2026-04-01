package com.example;

public class ContractEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public ContractEmployee(String name, String position, double salary, String department,
                            int experienceYears, EmploymentType employmentType,
                            double hourlyRate, int hoursWorked) {
        super(name, position, salary, department, experienceYears, employmentType);
        setHourlyRate(hourlyRate);
        setHoursWorked(hoursWorked);
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate <= 0) {
            throw new IllegalArgumentException("Hourly rate must be positive");
        }
        this.hourlyRate = hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative");
        }
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", hourlyRate=" + hourlyRate +
                ", hoursWorked=" + hoursWorked +
                " (Contract)";
    }
}