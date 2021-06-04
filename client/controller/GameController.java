package com.example.client.controller;

import com.example.client.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
public class GameController implements Initializable {
    private static final String EMPTY_STRING = "";
    private AtomicBoolean isTableChanged;
    private RestTemplate restTemplate;
    @Value("${spring.application.apiAddress}") private String apiAddress;

    public ImageView metuLogo;
    @Value("classpath:/static/metu.jpg") public Resource metuLogoResource;
    @FXML public VBox generalLayout;
    @FXML public Label informationLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Image image = new Image(metuLogoResource.getInputStream());
            metuLogo = new ImageView(image);
            metuLogo.setPreserveRatio(true);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    public void loseFocus() {
        informationLabel.requestFocus();
    }

}
