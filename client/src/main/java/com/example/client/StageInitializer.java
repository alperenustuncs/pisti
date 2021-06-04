package com.example.client;

import com.example.client.controller.SignInUiController;
import com.example.client.controller.UiController;
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

@Component
public class StageInitializer implements ApplicationListener<UiApplication.StageReadyEvent> {
    @Value("classpath:/ui.fxml") private Resource uiResuorce;
    @Value("classpath:/signinUi.fxml") private Resource signInUiResource;
    @Value("${spring.application.ui.windowWidth}") private int windowWidth;
    @Value("${spring.application.ui.windowHeight}") private int windowHeight;
    private String applicationTitle;
    private ApplicationContext applicationContext;




    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(UiApplication.StageReadyEvent stageReadyEvent) {

        createLoginStage(stageReadyEvent).show();

    }

    private Stage createLoginStage(UiApplication.StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(signInUiResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            SignInUiController controller = fxmlLoader.getController();

//            stage.addEventFilter(WindowEvent.WINDOW_SHOWN, windowEvent -> {
//                controller.loseFocus();
//                controller.metuLogo.setFitWidth(controller.table.getWidth());
//                controller.metuLogo.setFitHeight(controller.table.getHeight());
//                controller.table.setPlaceholder(controller.metuLogo);
//            });

            stage.setScene(new Scene(parent, windowWidth, windowHeight));
            stage.setTitle(applicationTitle);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return stage;
    }
}
