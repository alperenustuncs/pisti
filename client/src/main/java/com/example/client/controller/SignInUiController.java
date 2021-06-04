package com.example.client.controller;

import com.example.client.StageInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SignInUiController implements Initializable {
    @FXML private TextField emailBox;
    @FXML private PasswordField passwordBox;
    @FXML private Button signInButton;
    @FXML private Button signUpButton;
    @FXML private Hyperlink forgotPasswordLink;
    @Value("Menu") private String applicationTitle;
    @Value("classpath:/menuUi.fxml") private Resource menuResource;
    private int windowWidth = 800;
    private int windowHeight = 600;
    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void clickSignInButton(ActionEvent event){

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(menuResource.getURL());

            //fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            System.out.println("hello televole");
            MenuUiController controller = fxmlLoader.getController();

//            stage.addEventFilter(WindowEvent.WINDOW_SHOWN, windowEvent -> {
//                controller.loseFocus();
//                controller.metuLogo.setFitWidth(controller.table.getWidth());
//                controller.metuLogo.setFitHeight(controller.table.getHeight());
//                controller.table.setPlaceholder(controller.metuLogo);
//            });

            stage.setScene(new Scene(parent, windowWidth, windowHeight));
            stage.setTitle(applicationTitle);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @FXML
    public void clickSignUpButton(){

    }

    @FXML
    public void clickForgotPasswordLink(){

    }

}
