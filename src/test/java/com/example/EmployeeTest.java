package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void constructorShouldThrowExceptionForInvalidData() {
        // Порожнє ім'я
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("", "Manager", 5000, "IT", 5);
        });

        // Від'ємна зарплата
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Manager", -100, "IT", 5);
        });

        // Порожній відділ
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Manager", 5000, "", 5);
        });

        // Від'ємний досвід
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Manager", 5000, "IT", -1);
        });
    }

    @Test
    void setterShouldThrowExceptionForInvalidValue() {
        Employee emp = new Employee("John", "Manager", 5000, "IT", 5);

        // Сетер імені з null
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setName(null);
        });

        // Сетер зарплати з від'ємним значенням
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setSalary(-10);
        });

        // Сетер відділу з порожнім рядком
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setDepartment("   ");
        });

        // Сетер досвіду з від'ємним числом
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setExperienceYears(-5);
        });
    }
}