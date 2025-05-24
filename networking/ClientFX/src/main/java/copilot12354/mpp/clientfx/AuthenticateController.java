package copilot12354.mpp.clientfx;

import copilot12354.mpp.Employee;
import copilot12354.mpp.IServices;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticateController
{
    private IServices server;
    private static Logger logger = LogManager.getLogger(AuthenticateController.class);

    public AuthenticateController(IServices server)
    {
        this.server = server;
    }

    @FXML
    private Label labelWait;
    @FXML
    private Label labelAbout;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox checkBoxPassword;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldUsername;

    @FXML
    public void initialize()
    {
        labelAbout.setText("ADMINISTRATORS AND AGENCY EMPLOYEES ONLY\n");
        textFieldPassword.setVisible(false);
        textFieldPassword.setManaged(false);
    }

    @FXML
    protected void bAuthenticate()
    {
        labelWait.setText("AUTHENTICATING... / LOGGING IN...");

        int employeeId = Integer.parseInt(textFieldUsername.getText());
        String password = passwordField.getText();

        // FOR SECURITY REASONS CLEAR THE PASSWORD INPUT
        textFieldPassword.setText("");
        passwordField.setText("");

        logger.debug("TRYING TO AUTHENTICATE / LOG IN:\n");
        logger.debug("ID: " + employeeId);
        logger.debug("PASSWORD: " + password);

        try
        {
            Employee employee = new Employee(employeeId, "", password);

            server.login(employee, null);

            User newWindow = new User(server, employeeId);
            Stage stage = new Stage();
            newWindow.start(stage);
            stage.setWidth(1000);
            stage.setHeight(500);
            server.setClient(newWindow.getController());
        }
        catch (Exception e)
        {
            labelWait.setText("AUTHENTICATE / LOG IN FAILED...");
        }
    }

    @FXML
    protected void bAbout()
    {
        labelWait.setText("ONLY ADMINISTRATORS HAVE ACCESS TO MAKE AGENCY EMPLOYEE ACCOUNTS");
    }

    @FXML
    protected void bPassword()
    {
        if (checkBoxPassword.isSelected())
        {
            textFieldPassword.setText(passwordField.getText());
            passwordField.setVisible(false);
            textFieldPassword.setVisible(true);
            passwordField.setManaged(false);
            textFieldPassword.setManaged(true);
        }
        else
        {
            passwordField.setText(textFieldPassword.getText());
            textFieldPassword.setVisible(false);
            passwordField.setVisible(true);
            textFieldPassword.setManaged(false);
            passwordField.setManaged(true);
        }
    }
}