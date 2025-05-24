package copilot12354.mpp.java.GUI;

import copilot12354.mpp.java.Service.ServiceAll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class User extends Application
{
    private ServiceAll service;
    private int userId;

    public User(ServiceAll service, int userId)
    {
        this.service = service;
        this.userId = userId;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("../User.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new UserController(service, userId));
        Scene scene = new Scene(fxmlLoader.load());
        String css = getClass().getResource("/copilot12354/mpp/java/User.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("BOOK TICKETS AUTHENTICATED / LOGGED IN AS USER WITH ID: " + userId);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}