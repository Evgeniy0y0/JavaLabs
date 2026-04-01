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

    // 1. Перевірка статичного лічильника
    @Test
    void staticCounterIncrementsOnCreation() {
        int before = Employee.getObjectCount();
        Employee emp = new Employee("Test", "Dev", 1000, "IT", 2, EmploymentType.FULL_TIME);
        assertEquals(before + 1, Employee.getObjectCount());
    }

    @Test
    void staticCounterIncrementsOnCopy() {
        int before = Employee.getObjectCount();
        Employee original = new Employee("Original", "Dev", 2000, "HR", 3, EmploymentType.PART_TIME);
        Employee copy = new Employee(original);
        assertEquals(before + 2, Employee.getObjectCount()); // оригінал + копія
    }

    // 2. Перевірка конструктора копіювання
    @Test
    void copyConstructorCopiesAllFields() {
        Employee original = new Employee("John Doe", "Senior Developer", 75000, "Engineering", 8, EmploymentType.FULL_TIME);
        Employee copy = new Employee(original);

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getPosition(), copy.getPosition());
        assertEquals(original.getSalary(), copy.getSalary());
        assertEquals(original.getDepartment(), copy.getDepartment());
        assertEquals(original.getExperienceYears(), copy.getExperienceYears());
        assertEquals(original.getEmploymentType(), copy.getEmploymentType());

        // Перевіряємо, що це різні об'єкти
        assertNotSame(original, copy);
    }

    // 3. Перевірка валідації при копіюванні (в конструкторі копіювання не повинно бути винятків)
    @Test
    void copyConstructorDoesNotThrowForValidObject() {
        Employee original = new Employee("Valid", "Manager", 3000, "Sales", 2, EmploymentType.CONTRACTOR);
        assertDoesNotThrow(() -> new Employee(original));
    }

    // 4. Перевірка, що сеттер для enum кидає виняток для null
    @Test
    void setEmploymentTypeThrowsForNull() {
        Employee emp = new Employee("John", "Manager", 5000, "IT", 5, EmploymentType.FULL_TIME);
        assertThrows(IllegalArgumentException.class, () -> emp.setEmploymentType(null));
    }

    // 5. Перевірка, що метод getObjectCount повертає коректне значення
    @Test
    void getObjectCountReturnsCorrectValue() {
        int initial = Employee.getObjectCount();
        Employee e1 = new Employee("A", "Dev", 1000, "IT", 1, EmploymentType.FULL_TIME);
        Employee e2 = new Employee("B", "QA", 1100, "IT", 2, EmploymentType.PART_TIME);
        assertEquals(initial + 2, Employee.getObjectCount());
    }
}