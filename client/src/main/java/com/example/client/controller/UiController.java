package com.example.client.controller;

import com.example.client.StageInitializer;
import com.example.client.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class UiController implements Initializable {
    private static final String EMPTY_STRING = "";
    private AtomicBoolean isTableChanged;
    private RestTemplate restTemplate;
    @Value("${spring.application.apiAddress}") private String apiAddress;

    public ImageView metuLogo;
    @Value("classpath:/static/metu.jpg") public Resource metuLogoResource;
    @FXML public VBox generalLayout;
    @FXML public HBox fieldsLayout;
    @FXML public HBox buttonsLayout;
    @FXML public TableView<Student> table;
    @FXML public TableColumn<Student, Integer> idColumn;
    @FXML public TableColumn<Student, String> nameColumn;
    @FXML public TableColumn<Student, String> surnameColumn;
    @FXML public TableColumn<Student, String> departmentColumn;
    @FXML public TableColumn<Student, Integer> yearColumn;
    @FXML public TableColumn<Student, Double> gpaColumn;
    @FXML public TextField idField;
    @FXML public TextField nameField;
    @FXML public TextField surnameField;
    @FXML public TextField departmentField;
    @FXML public TextField yearField;
    @FXML public TextField gpaField;
    @FXML public Label informationLabel;
    @FXML public Button addButton;
    @FXML public Button updateButton;
    @FXML public Button deleteButton;
    @FXML public Button findButton;
    @FXML public Button showAllButton;

    @FXML public HBox mButtonsLayout;
    @FXML public Button loginButton;

//    @Value("classpath:/ui.fxml") private Resource uiResuorce;
//    @Value("${spring.application.ui.windowWidth}") private int windowWidth;
//    @Value("${spring.application.ui.windowHeight}") private int windowHeight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
        isTableChanged = new AtomicBoolean(true);

        generalLayout.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            table.getSelectionModel().clearSelection();
            informationLabel.requestFocus();
        });

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getSelectionModel().selectedItemProperty().addListener(((obs, oldSelection, newSelection) -> {
            if(newSelection == null) setFieldsEmpty();
            else{
                if(table.getSelectionModel().getSelectedItems().size() > 1) setFieldsEmpty();
                else setFieldsStudent(newSelection);
            }
        }));

        int columnNumber = 6;
        idColumn.prefWidthProperty().bind(table.widthProperty().divide(columnNumber));
        nameColumn.prefWidthProperty().bind(table.widthProperty().divide(columnNumber));
        surnameColumn.prefWidthProperty().bind(table.widthProperty().divide(columnNumber));
        departmentColumn.prefWidthProperty().bind(table.widthProperty().divide(columnNumber));
        yearColumn.prefWidthProperty().bind(table.widthProperty().divide(columnNumber));
        gpaColumn.prefWidthProperty().bind(table.widthProperty().divide(columnNumber));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        gpaColumn.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        try {
            Image image = new Image(metuLogoResource.getInputStream());
            metuLogo = new ImageView(image);
            metuLogo.setPreserveRatio(true);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @FXML
    public void clickLoginButton() {
        //StageInitializer.menuStage.show();
    }

    @FXML
    public void clickAddButton() {
        String id = idField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String department = departmentField.getText();
        String year = yearField.getText();
        String gpa = gpaField.getText();

        if(id.isEmpty() || name.isEmpty() || surname.isEmpty() || department.isEmpty() ||
                year.isEmpty() || gpa.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Add Student");
            alert.setContentText("Some fields are missing.");
            alert.showAndWait();
            loseFocus();
            return;
        }

        if(!areFieldsProper(id, name, surname, department, year, gpa))return;

        String jsonString = new JSONObject()
                .put("id", Integer.parseInt(id))
                .put("name", name)
                .put("surname", surname)
                .put("department", department)
                .put("year", Integer.parseInt(year))
                .put("gpa", Double.parseDouble(gpa)).toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        restTemplate.exchange(apiAddress + "/add", HttpMethod.POST, entity, String.class);

        isTableChanged.set(true);
        showAllButton.fire();
    }

    @FXML
    public void clickUpdateButton() {
        ObservableList<Student> selectedStudents = table.getSelectionModel().getSelectedItems();
        if (selectedStudents.size() == 0) loseFocus();
        else {
            if (selectedStudents.size() > 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Update Student");
                alert.setContentText("Choose one student for update.");
                alert.showAndWait();
                loseFocus();
                return;
            }

            if (!areFieldsProper(EMPTY_STRING, nameField.getText(), surnameField.getText(), departmentField.getText(),
                    yearField.getText(), gpaField.getText()))return;

            Student student = selectedStudents.get(0);
            String jsonString = new JSONObject()
                    .put("id", student.getId())
                    .put("name", nameField.getText())
                    .put("surname", surnameField.getText())
                    .put("department", departmentField.getText())
                    .put("year", Integer.parseInt(yearField.getText()))
                    .put("gpa", Double.parseDouble(gpaField.getText())).toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            restTemplate.exchange(apiAddress + "/update", HttpMethod.POST, entity, String.class);

            isTableChanged.set(true);
            showAllButton.fire();
        }
    }

    @FXML
    public void clickDeleteButton() {
        ObservableList<Student> selectedStudents = table.getSelectionModel().getSelectedItems();
        if (selectedStudents.size() == 0) loseFocus();
        else{
            JSONArray json = new JSONArray();
            for(Student student : selectedStudents) json.put(student.getId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(json.toString(), headers);
            restTemplate.exchange(apiAddress + "/delete", HttpMethod.DELETE, entity, String.class);

            isTableChanged.set(true);
            showAllButton.fire();
        }
    }

    @FXML
    public void clickFindButton() {
        JSONObject json = new JSONObject();

        if(idField.getText().isEmpty() && nameField.getText().isEmpty() && surnameField.getText().isEmpty() &&
                departmentField.getText().isEmpty() && yearField.getText().isEmpty() && gpaField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Find Student");
            alert.setContentText("Specify criteria for Find.");
            alert.showAndWait();
            loseFocus();
            return;
        }

        if(!areFieldsProper(idField.getText(), nameField.getText(), surnameField.getText(), departmentField.getText(),
                yearField.getText(), gpaField.getText())) return;

        if(!idField.getText().isEmpty())json.put("id", Integer.parseInt(idField.getText()));
        if(!nameField.getText().isEmpty())json.put("name", nameField.getText());
        if(!surnameField.getText().isEmpty())json.put("surname", surnameField.getText());
        if(!departmentField.getText().isEmpty())json.put("department", departmentField.getText());
        if(!yearField.getText().isEmpty())json.put("year", Integer.parseInt(yearField.getText()));
        if(!gpaField.getText().isEmpty())json.put("gpa", Double.parseDouble(gpaField.getText()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json.toString(), headers);
        ResponseEntity<List<Student>> response = restTemplate.exchange(apiAddress + "/find",
                HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});

        table.setItems(FXCollections.observableList(response.getBody()));
        isTableChanged.set(true);
        setFieldsEmpty();
        loseFocus();
    }

    @FXML
    public void clickShowAllButton() {
        if (isTableChanged.get()) {
            ResponseEntity<List<Student>> response = restTemplate.exchange(apiAddress + "/getAll", HttpMethod.GET,
                    null, new ParameterizedTypeReference<>() {});
            table.setItems(FXCollections.observableList(response.getBody()));
            isTableChanged.set(false);
            setFieldsEmpty();
            if(response.getBody().size() == 0) informationLabel.setText(EMPTY_STRING);
            else informationLabel.setText("Number of Registered Students: " + table.getItems().size());
        }
        loseFocus();
    }

    public void loseFocus() {
        informationLabel.requestFocus();
    }

    private void setFieldsEmpty() {
        idField.clear();
        nameField.clear();
        surnameField.clear();
        departmentField.clear();
        yearField.clear();
        gpaField.clear();
    }

    private void setFieldsStudent(Student student) {
        idField.setText(Integer.toString(student.getId()));
        nameField.setText(student.getName());
        surnameField.setText(student.getSurname());
        departmentField.setText(student.getDepartment());
        yearField.setText(Integer.toString(student.getYear()));
        gpaField.setText(Double.toString(student.getGpa()));
    }

    private boolean areFieldsProper(String id, String name, String surname,
                                    String department, String year, String gpa){
        if(!id.isEmpty()) {
            if (id.length() != 7) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Add Student");
                alert.setContentText("Student ID is a 7-digit number.");
                alert.showAndWait();
                loseFocus();
                return false;
            } else {
                try {
                    Integer.parseInt(id);
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Add Student");
                    alert.setContentText("ID must be an integer.");
                    alert.showAndWait();
                    loseFocus();
                    return false;
                }
            }
        }

        if(!year.isEmpty()) {
            try {
                Integer.parseInt(year);
            }catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Add Student");
                alert.setContentText("Year must be an integer.");
                alert.showAndWait();
                loseFocus();
                return false;
            }
        }

        if(!name.isEmpty() && !name.chars().allMatch(Character::isLetter)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Add Student");
            alert.setContentText("Only letters are allowed for Name.");
            alert.showAndWait();
            loseFocus();
            return false;
        }

        if(!surname.isEmpty() && !surname.chars().allMatch(Character::isLetter)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Add Student");
            alert.setContentText("Only letters are allowed for Surname.");
            alert.showAndWait();
            loseFocus();
            return false;
        }

        if(!department.isEmpty() && !department.chars().allMatch(Character::isLetter)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Add Student");
            alert.setContentText("Only letters are allowed for Department.");
            alert.showAndWait();
            loseFocus();
            return false;
        }

        if (!gpa.isEmpty()) {
            try {
                Double.parseDouble(gpa);
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Add Student");
                alert.setContentText("GPA must be a floating-point number.");
                alert.showAndWait();
                loseFocus();
                return false;
            }
        }
        return true;
    }
}
