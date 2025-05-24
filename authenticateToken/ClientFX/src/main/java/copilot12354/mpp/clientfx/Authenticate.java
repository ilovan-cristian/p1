package copilot12354.mpp.clientfx;

import copilot12354.mpp.IServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Authenticate extends Application
{
    private static IServices server;

    public static void setServer(IServices serverInstance)
    {
        server = serverInstance;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Authenticate.class.getResource("/copilot12354/mpp/clientfx/Authenticate.fxml"));

        fxmlLoader.setControllerFactory(controllerClass -> new AuthenticateController(server));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        String css = getClass().getResource("/copilot12354/mpp/clientfx/Authenticate.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("AUTHENTICATE / LOG IN");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
