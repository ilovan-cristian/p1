package copilot12354.mpp.java.GUI;

import copilot12354.mpp.java.Domain.Employee;
import copilot12354.mpp.java.Service.ServiceAll;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthenticateController
{
    public ServiceAll service;

    public AuthenticateController(ServiceAll service)
    {
        this.service = service;
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
        try
        {
            if (textFieldUsername.getText().equals("a"))
            {
                if (passwordField.getText().equals("p"))
                {
                    textFieldUsername.setText("");
                    textFieldPassword.setText("");
                    passwordField.setText("");
                    Administrator newWindow = new Administrator(service);
                    Stage stage = new Stage();
                    newWindow.start(stage);
                    stage.setWidth(1000);
                    stage.setHeight(500);
                    labelWait.setText("AUTHENTICATED / LOGGED IN");
                }
                else
                {
                    labelWait.setText("TRYING TO AUTHENTICATE / LOG IN AS ADMINISTRATOR; PASSWORD NOT WORKING...");
                }
            }
            else
            {
                int employeeId = Integer.parseInt(textFieldUsername.getText());
                Employee employee = service.employeeFindOne(employeeId);

                if (employee == null)
                {
                    labelWait.setText("ID NOT FOUND IN DATABASE: " + employeeId);
                }
                else if (employee.getPassword().equals(passwordField.getText()))
                {
                    textFieldUsername.setText("");
                    textFieldPassword.setText("");
                    passwordField.setText("");
                    User newWindow = new User(service, employeeId);
                    Stage stage = new Stage();
                    newWindow.start(stage);
                    stage.setWidth(1000);
                    stage.setHeight(500);
                    labelWait.setText("AUTHENTICATED / LOGGED IN");
                }
                else
                {
                    labelWait.setText("PASSWORD NOT WORKING...");
                }
            }
        }
        catch (NumberFormatException e)
        {
            labelWait.setText("AUTHENTICATE / LOG IN USING ID");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            labelWait.setText("ERROR");
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

    @FXML
    protected void bCLI()
    {
        labelWait.setText("ONLY GUI FOR THIS VERSION");
    }
}