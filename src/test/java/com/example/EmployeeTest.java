package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void constructorShouldThrowExceptionForInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("", "Manager", 5000, "IT", 5, EmploymentType.FULL_TIME);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Manager", -100, "IT", 5, EmploymentType.FULL_TIME);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Manager", 5000, "", 5, EmploymentType.FULL_TIME);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Manager", 5000, "IT", -1, EmploymentType.FULL_TIME);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Manager", 5000, "IT", 5, null);
        });
    }

    @Test
    void setterShouldThrowExceptionForInvalidValue() {
        Employee emp = new Employee("John", "Manager", 5000, "IT", 5, EmploymentType.FULL_TIME);

        assertThrows(IllegalArgumentException.class, () -> emp.setName(null));
        assertThrows(IllegalArgumentException.class, () -> emp.setSalary(-10));
        assertThrows(IllegalArgumentException.class, () -> emp.setDepartment("   "));
        assertThrows(IllegalArgumentException.class, () -> emp.setExperienceYears(-5));
        assertThrows(IllegalArgumentException.class, () -> emp.setEmploymentType(null));
    }

    // 4. Перевірка, що сеттер для enum кидає виняток для null
    @Test
    void setEmploymentTypeThrowsForNull() {
        Employee emp = new Employee("John", "Manager", 5000, "IT", 5, EmploymentType.FULL_TIME);
        assertThrows(IllegalArgumentException.class, () -> emp.setEmploymentType(null));
    }

}