package com.example.client.controller;

import com.example.client.GameManager;
import com.example.client.StageInitializer;
import com.example.client.model.Card;
import com.example.client.model.Student;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
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
import java.util.Random;
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

    @FXML
    public void clickLoginButton() {
        StageInitializer.getLoginStage().close();

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

        Scene scene = stage2.getScene();
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DIGIT9) {
                if (e.isControlDown()) {
                    manager.setPlayerScore(151);
                    manager.makePlayerWin();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WON");
                    alert.setHeaderText("Player won!");
                    alert.setContentText("Player won by cheating! 151 points");
                    alert.showAndWait();
                    loseFocus();
                    return;
                }
            }
        });

        stage2.show();

        gameLoop(hBox, scene, manager);
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

    public void loseFocus() {
        informationLabel.requestFocus();
    }

}
