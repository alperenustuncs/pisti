package com.example.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
//@Scope("prototype")
public class MenuUiController implements Initializable {

    @FXML public Button playButton;
    @FXML public Button pastScoresButton;
    @FXML public Button highScoresButton;
    @FXML public VBox generalLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clickPlayButton(ActionEvent actionEvent) {

    }

    public void clickPastScoresButton(ActionEvent actionEvent) {
    }

    public void clickHighScoresButton(ActionEvent actionEvent) {
    }
}
