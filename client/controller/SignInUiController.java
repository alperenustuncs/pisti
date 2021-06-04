package com.example.client.controller;

import com.example.client.GameManager;
import com.example.client.StageInitializer;
import com.example.client.model.Card;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
//@Scope("prototype")
public class SignInUiController implements Initializable {
    @FXML public Pane generalLayout;
    @FXML public TextField usernameBox;
    @FXML public PasswordField passwordBox;
    @FXML public Button signInButton;
    @FXML public Button signUpButton;
    @FXML public Hyperlink forgotPasswordLink;
    @Value("Menu") public String applicationTitle;
    @Value("classpath:/menuUi.fxml") public Resource menuResource;
    @Value("classpath:/forgottenPassword.fxml") public Resource forgotPasswordResource;
    @Value("classpath:/signUpUi.fxml") public Resource signupResource;
    @Value("${spring.application.apiAddress}") private String apiAddress;
    @Autowired
    private StageInitializer stageInitializer;
    @Autowired
    private ApplicationContext context;
    private RestTemplate restTemplate;

    //private int windowWidth = 800;
    //private int windowHeight = 600;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
    }

    @FXML
    public void clickSignInButton() throws IOException {
        String jsonString = new JSONObject()
                .put("username", usernameBox.getText())
                .put("password", passwordBox.getText()).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> answer = restTemplate.exchange(apiAddress + "/api/player/login", HttpMethod.POST, entity, String.class);
        System.out.println(answer);
        //set player

        FXMLLoader fxmlLoader = stageInitializer.getFXMLLoader(menuResource.getURL());
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,800,600);
        stageInitializer.setScene(scene);

        Stage stage2 = StageInitializer.getStage2();
        //HBox centerHBox = new HBox();
        //centerHBox.paddingProperty().set(new Insets(200, 0, 0, 100));
        HBox aiBox = new HBox();
        aiBox.setSpacing(80);
        aiBox.paddingProperty().set(new Insets(100, 0, 0, 100));

        HBox hBox = new HBox();
        hBox.setSpacing(80);
        hBox.paddingProperty().set(new Insets(400, 0, 0, 100));

        GameManager manager = new GameManager(Card.initCards(hBox), hBox, aiBox);
        BorderPane pane = new BorderPane();
        pane.setMaxSize(800, 600);
        //pane.getChildren().add(aiBox);
        //pane.getChildren().add(hBox);
        pane.setTop(hBox);
        stage2.setScene(new Scene(new StackPane(hBox), 800, 600));

        Scene _scene = stage2.getScene();
        _scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DIGIT9) {
                if (e.isControlDown()) {
                    manager.setPlayerScore(151);
                    manager.makePlayerWin();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WON");
                    alert.setHeaderText("Player won!");
                    alert.setContentText("Player won by cheating! 151 points");
                    alert.showAndWait();
                    return;
                }
            }
        });

        stage2.show();

        gameLoop(hBox, _scene, manager);


    }

    private void gameLoop(HBox hBox, Scene scene, GameManager manager) {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (manager.getPlayerCards().size() == 0 && manager.getAiCards().size() == 0) {
                    if (manager.getAllCards().size() > 0) {
                        manager.spread4CardsToPlayers();
                        for (int i = 0; i < 4; i++) {
                            hBox.getChildren().add(manager.getPlayerCards().get(i).getButton());
                        }
                    }

                    System.out.println("FINISHED");
                }
            }
        }.start();
    }

    @FXML
    public void clickSignUpButton() throws IOException {
        FXMLLoader fxmlLoader = stageInitializer.getFXMLLoader(signupResource.getURL());
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,800,600);
        stageInitializer.setScene(scene);
    }

    @FXML
    public void clickForgotPasswordLink() throws IOException {
        FXMLLoader fxmlLoader = stageInitializer.getFXMLLoader(forgotPasswordResource.getURL());
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,800,600);
        stageInitializer.setScene(scene);
    }



}
