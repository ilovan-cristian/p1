package copilot12354.mpp.clientfx;

import copilot12354.mpp.IObserver;
import copilot12354.mpp.IServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class User extends Application
{
    private IServices server;
    private int userId;
    private UserController controller;

    public User(IServices server, int userId)
    {
        this.server = server;
        this.userId = userId;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("/copilot12354/mpp/clientfx/User.fxml"));
        controller = new UserController(server, userId);
        fxmlLoader.setControllerFactory(controllerClass -> controller);
        Scene scene = new Scene(fxmlLoader.load());
        String css = getClass().getResource("/copilot12354/mpp/clientfx/User.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("BOOK TICKETS AUTHENTICATED / LOGGED IN AS USER WITH ID: " + userId);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }

    public UserController getController()
    {
        return controller;
    }
}