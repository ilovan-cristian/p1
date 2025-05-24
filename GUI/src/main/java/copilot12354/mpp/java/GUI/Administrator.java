package copilot12354.mpp.java.GUI;

import copilot12354.mpp.java.Service.ServiceAll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Administrator extends Application
{
    private ServiceAll service;

    public Administrator(ServiceAll service)
    {
        this.service = service;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("../Administrator.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new AdministratorController(service));
        Scene scene = new Scene(fxmlLoader.load());
        String css = getClass().getResource("/copilot12354/mpp/java/Administrator.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("ADMINISTRATOR");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}