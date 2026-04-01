package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void addEmployeeAddsToCompany() {
        Company company = new Company("TestCompany");
        Employee emp = new Employee("John", "Dev", 5000, "IT", 3, EmploymentType.FULL_TIME);
        company.addEmployee(emp);
        assertEquals(1, company.getEmployees().size());
        assertTrue(company.getEmployees().contains(emp));
    }

    @Test
    void addEmployeeThrowsForNull() {
        Company company = new Company("TestCompany");
        assertThrows(IllegalArgumentException.class, () -> company.addEmployee(null));
    }

    @Test
    void getEmployeesReturnsCopy() {
        Company company = new Company("TestCompany");
        Employee emp = new Employee("John", "Dev", 5000, "IT", 3, EmploymentType.FULL_TIME);
        company.addEmployee(emp);
        var list = company.getEmployees();
        list.clear(); // спроба змінити отриманий список
        assertEquals(1, company.getEmployees().size()); // оригінальний список не змінився
    }
}