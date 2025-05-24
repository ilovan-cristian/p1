package copilot12354.mpp.clientfx;

import copilot12354.mpp.IServices;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AdministratorController
{
    public IServices server;

    public AdministratorController(IServices server)
    {
        this.server = server;
    }

    void refresh()
    {

    }

    void refresh(int id)
    {

    }

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label labelEmployee;
    @FXML
    private Label labelId;
    @FXML
    private Label labelName;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPassword;


    @FXML
    public void initialize()
    {
        welcomeLabel.setText("WARNING ! LOGGED IN AS ADMINISTRATOR\nACTIONS ARE RECORDED...");

        labelEmployee.setText("EMPLOYEE DATABASE EDITOR:");
        labelId.setText("ID:");
        labelName.setText("NAME:");
        labelPassword.setText("PASSWORD:");

        refresh();
    }

    @FXML
    public void bSearch()
    {
        int id = Integer.parseInt(textFieldID.getText());
        refresh(id);
    }

    @FXML
    public void bAdd()
    {
        String name = textFieldName.getText();
        String password = textFieldPassword.getText();

        refresh();
    }

    @FXML
    public void bErase()
    {
        int id = Integer.parseInt(textFieldID.getText());

        refresh();
    }

    @FXML
    public void bModify()
    {
        int id = Integer.parseInt(textFieldID.getText());
        String name = textFieldName.getText();
        String password = textFieldPassword.getText();

        refresh(id);
    }

    @FXML
    public void bViewAll()
    {
        refresh();
    }
}