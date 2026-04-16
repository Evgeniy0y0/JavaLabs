package com.example.gui;

import com.example.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class MainPane extends BorderPane {
    private Company company;
    private ListView<String> listView;
    private TextArea detailsArea;

    public MainPane() {
        company = new Company("MyCompany");
        createUI();
    }

    private void createUI() {
        VBox addBox = new VBox(10);
        addBox.setPadding(new Insets(10));
        addBox.setStyle("-fx-border-color: gray;");

        ChoiceBox<String> typeChoice = new ChoiceBox<>();
        typeChoice.getItems().addAll("ContractEmployee", "FullTimeEmployee", "Intern", "Manager");
        typeChoice.setValue("ContractEmployee");

        TextField nameField = new TextField(); nameField.setPromptText("Name");
        TextField positionField = new TextField(); positionField.setPromptText("Position");
        TextField salaryField = new TextField(); salaryField.setPromptText("Salary");
        TextField deptField = new TextField(); deptField.setPromptText("Department");
        TextField expField = new TextField(); expField.setPromptText("Experience years");
        ChoiceBox<String> empTypeChoice = new ChoiceBox<>();
        empTypeChoice.getItems().addAll("FULL_TIME", "PART_TIME", "CONTRACTOR");
        empTypeChoice.setValue("FULL_TIME");

        TextField extra1 = new TextField(); extra1.setPromptText("Extra field 1");
        TextField extra2 = new TextField(); extra2.setPromptText("Extra field 2");

        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String position = positionField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String dept = deptField.getText();
                int exp = Integer.parseInt(expField.getText());
                EmploymentType empType = EmploymentType.valueOf(empTypeChoice.getValue());
                String type = typeChoice.getValue();
                Employee emp = null;
                if (type.equals("ContractEmployee")) {
                    double rate = Double.parseDouble(extra1.getText());
                    int hours = Integer.parseInt(extra2.getText());
                    emp = new ContractEmployee(name, position, salary, dept, exp, empType, rate, hours);
                } else if (type.equals("FullTimeEmployee")) {
                    double bonus = Double.parseDouble(extra1.getText());
                    int vac = Integer.parseInt(extra2.getText());
                    emp = new FullTimeEmployee(name, position, salary, dept, exp, empType, bonus, vac);
                }
                if (emp != null) {
                    company.addEmployee(emp, 1);
                    refreshList();
                    clearFields(nameField, positionField, salaryField, deptField, expField, extra1, extra2);
                }
            } catch (Exception ex) {
                showAlert("Error", "Invalid input: " + ex.getMessage());
            }
        });

        addBox.getChildren().addAll(
                new Label("Add new employee:"), typeChoice,
                nameField, positionField, salaryField, deptField, expField, empTypeChoice,
                new Label("Extra field 1 (depends on type)"), extra1,
                new Label("Extra field 2"), extra2,
                addButton
        );

        listView = new ListView<>();
        listView.setPrefHeight(200);
        refreshList();

        listView.setSelectionModel(null);
        listView.setFocusTraversable(false);

        listView.setCellFactory(lv -> new ListCell<String>() {
            private final Text text = new Text();
            {
                text.setStyle("-fx-font-family: monospace;");
                text.setMouseTransparent(false);
            }

            private final TextField textField = new TextField();
            {
                textField.setEditable(false);
                textField.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: transparent; " +
                                "-fx-font-family: monospace;"
                );
                textField.setFocusTraversable(false);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    textField.setText(item);
                    setGraphic(textField);
                    setText(null);
                }
            }
        });

        VBox searchBox = new VBox(10);
        searchBox.setPadding(new Insets(10));
        TextField uuidField = new TextField();
        uuidField.setPromptText("Enter UUID");
        Button searchButton = new Button("Find");
        detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setPrefHeight(150);
        searchButton.setOnAction(e -> {
            String uuidStr = uuidField.getText().trim();
            try {
                java.util.UUID uuid = java.util.UUID.fromString(uuidStr);
                Employee found = company.findEmployeeByUuid(uuid);
                if (found == null) {
                    detailsArea.setText("Employee not found.");
                } else {
                    detailsArea.setText(found.toString());
                }
            } catch (IllegalArgumentException ex) {
                detailsArea.setText("Invalid UUID format.");
            }
        });
        searchBox.getChildren().addAll(new Label("Search by UUID"), uuidField, searchButton, new Label("Details:"), detailsArea);

        setLeft(addBox);
        setCenter(listView);
        setRight(searchBox);
    }

    private void refreshList() {
        listView.getItems().clear();
        for (var record : company.getAllRecords()) {
            Employee emp = record.getEmployee();
            String shortInfo = emp.getName() + " | " + emp.getUuid().toString();
            listView.getItems().add(shortInfo);
        }
    }

    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}