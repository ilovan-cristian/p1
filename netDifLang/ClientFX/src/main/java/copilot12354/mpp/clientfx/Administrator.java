package copilot12354.mpp.clientfx;

import copilot12354.mpp.IServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Administrator extends Application
{
    private static IServices server;

    public Administrator(IServices server)
    {
        this.server = server;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("/copilot12354/mpp/clientfx/Administrator.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new AdministratorController(server));
        Scene scene = new Scene(fxmlLoader.load());
        String css = getClass().getResource("/copilot12354/mpp/clientfx/Administrator.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("ADMINISTRATOR");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}