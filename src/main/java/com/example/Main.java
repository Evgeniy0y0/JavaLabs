package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input employee amount: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println("Input info about employee#" + (i + 1));
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Position: ");
            String position = scanner.nextLine();
            System.out.print("Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            Employee emp = new Employee(name, position, salary);
            employees.add(emp);
        }

        System.out.println("\nEmployee list:");
        for (Employee emp : employees) {
            System.out.println(emp);

            scanner.close();
        }
    }
}