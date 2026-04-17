package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeExceptionTest {

    @Test
    void shouldThrowInvalidEmployeeDataExceptionForInvalidName() {
        assertThrows(InvalidEmployeeDataException.class, () -> {
            new ContractEmployee("", "Dev", 1000, "IT", 2, EmploymentType.FULL_TIME, 20.0, 160);
        });
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingNonExisting() {
        Company company = new Company("Test");
        Employee emp = new ContractEmployee("Test", "Dev", 1000, "IT", 2, EmploymentType.FULL_TIME, 20.0, 160);
        assertThrows(ObjectNotFoundException.class, () -> {
            company.delete(emp);
        });
    }
}