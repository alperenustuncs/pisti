package com.example.client;

import com.example.client.controller.UiController;
import com.example.client.controller.SignInUiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<UiApplication.StageReadyEvent> {
    @Value("classpath:/ui.fxml") private Resource uiResuorce;
    @Value("classpath:/signinUi.fxml") private Resource signInUiResource;
    @Value("${spring.application.ui.windowWidth}") private int windowWidth;
    @Value("${spring.application.ui.windowHeight}") private int windowHeight;
    private String applicationTitle;
    private ApplicationContext applicationContext;

    private static Stage loginStage;
    public static Stage getLoginStage() { return loginStage; }

    private Stage stage;

    private static Stage stage2;
    public static Stage getStage2() { return stage2; }

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(UiApplication.StageReadyEvent stageReadyEvent) {

        loginStage = createLoginStage(stageReadyEvent);
        loginStage.show();

        setUpStage2();
    }

    private Stage createLoginStage(UiApplication.StageReadyEvent stageReadyEvent) {
        stage = stageReadyEvent.getStage();
        try {
            FXMLLoader fxmlLoader = getFXMLLoader(signInUiResource.getURL());
            Parent parent = fxmlLoader.load();
            SignInUiController controller = fxmlLoader.getController();

            stage.setScene(new Scene(parent, windowWidth, windowHeight));
            stage.setTitle(applicationTitle);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return stage;
    }

    private void setUpStage2() {
        stage2 = new Stage(); // Create a new stage
        stage2.setTitle("Second Stage"); // Set the stage title
        stage2.setScene(new Scene(new Button("New Stage"), 800, 600));
    }

    public FXMLLoader getFXMLLoader(URL location){
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
        return fxmlLoader;
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
        stage.show();
    }
}
