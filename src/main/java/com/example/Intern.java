package com.example;

public class Intern extends Employee {
    private String university;
    private int durationMonths;

    public Intern(String name, String position, double salary, String department,
                  int experienceYears, EmploymentType employmentType,
                  String university, int durationMonths) {
        super(name, position, salary, department, experienceYears, employmentType);
        setUniversity(university);
        setDurationMonths(durationMonths);
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        if (university == null || university.trim().isEmpty()) {
            throw new IllegalArgumentException("University cannot be null or empty");
        }
        this.university = university;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        if (durationMonths <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        this.durationMonths = durationMonths;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", university='" + university + '\'' +
                ", durationMonths=" + durationMonths +
                " (Intern)";
    }
}