package com.example.client.controller;

import com.example.client.StageInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ForgottenPasswordController implements Initializable {

    @Value("${spring.application.apiAddress}") private String apiAddress;
    @Value("classpath:/passwordWithTokenUi.fxml") public Resource passwordTokenResource;
    @FXML
    public TextField emailBox;
    @FXML
    public Button resetButton;

    private RestTemplate restTemplate;
    @Autowired
    private StageInitializer stageInitializer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
    }

    @FXML
    public void clickResetButton(ActionEvent actionEvent) throws IOException {
        String jsonString = new JSONObject()
                .toString();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        String email = emailBox.getText();
        ResponseEntity<String> answer = restTemplate.exchange(apiAddress + "/password/request?email="+email, HttpMethod.POST, entity, String.class);
        System.out.println(answer);
        FXMLLoader fxmlLoader = stageInitializer.getFXMLLoader(passwordTokenResource.getURL());
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,800,600);
        stageInitializer.setScene(scene);
    }
}
