package com.example;

public class FullTimeEmployee extends Employee {
    private double bonus;
    private int vacationDays;

    public FullTimeEmployee(String name, String position, double salary, String department,
                            int experienceYears, EmploymentType employmentType,
                            double bonus, int vacationDays) {
        super(name, position, salary, department, experienceYears, employmentType);
        setBonus(bonus);
        setVacationDays(vacationDays);
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Bonus cannot be negative");
        }
        this.bonus = bonus;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        if (vacationDays < 0) {
            throw new IllegalArgumentException("Vacation days cannot be negative");
        }
        this.vacationDays = vacationDays;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", bonus=" + bonus +
                ", vacationDays=" + vacationDays +
                " (Full-Time)";
    }
}