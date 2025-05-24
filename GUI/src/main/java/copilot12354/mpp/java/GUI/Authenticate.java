package copilot12354.mpp.java.GUI;

import copilot12354.mpp.java.Service.ServiceAll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Authenticate extends Application
{
    private static ServiceAll service;

    public static void setService(ServiceAll serviceInstance)
    {
        service = serviceInstance;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Authenticate.class.getResource("../Authenticate.fxml"));

        fxmlLoader.setControllerFactory(controllerClass -> new AuthenticateController(service));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        String css = getClass().getResource("/copilot12354/mpp/java/Authenticate.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("AUTHENTICATE / LOG IN");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
