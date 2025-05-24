package copilot12354.mpp.java.GUI;

import copilot12354.mpp.java.Domain.Employee;
import copilot12354.mpp.java.Service.ServiceAll;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.List;

public class AdministratorController
{
    private ServiceAll service;

    public AdministratorController(ServiceAll service)
    {
        this.service = service;
    }

    void refresh()
    {
        List<Employee> employees = service.employeeFindAll();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList(employees);
        tableViewEmployees.setItems(employeeList);
    }

    void refresh(int id)
    {
        List<Employee> employees = new ArrayList<>();
        employees.add(service.employeeFindOne(id));
        ObservableList<Employee> employeeList = FXCollections.observableArrayList(employees);
        tableViewEmployees.setItems(employeeList);
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
    private TableView<Employee> tableViewEmployees;
    @FXML
    private TableColumn<Employee, Integer> columnID;
    @FXML
    private TableColumn<Employee, String> columnName;
    @FXML
    private TableColumn<Employee, String> columnPassword;


    @FXML
    public void initialize()
    {
        welcomeLabel.setText("WARNING ! LOGGED IN AS ADMINISTRATOR\nACTIONS ARE RECORDED...");

        labelEmployee.setText("EMPLOYEE DATABASE EDITOR:");
        labelId.setText("ID:");
        labelName.setText("NAME:");
        labelPassword.setText("PASSWORD:");

        columnID.setCellValueFactory(cellData -> {
            Employee employee = cellData.getValue();
            return new SimpleIntegerProperty(employee.getId()).asObject();
        });

        columnName.setCellValueFactory(cellData -> {
            Employee employee = cellData.getValue();
            return new SimpleStringProperty(employee.getName());
        });

        columnPassword.setCellValueFactory(cellData -> {
            Employee employee = cellData.getValue();
            return new SimpleStringProperty(employee.getPassword());
        });

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

        service.employeeAdd(name, password);

        refresh();
    }

    @FXML
    public void bErase()
    {
        int id = Integer.parseInt(textFieldID.getText());
        service.employeeDelete(id);
        refresh();
    }

    @FXML
    public void bModify()
    {
        int id = Integer.parseInt(textFieldID.getText());
        String name = textFieldName.getText();
        String password = textFieldPassword.getText();

        service.employeeUpdate(id, name, password);

        Employee modifiedEmployee = service.employeeFindOne(id);
        ObservableList<Employee> currentItems = tableViewEmployees.getItems();
        for (int i = 0; i < currentItems.size(); i++)
        {
            Employee employee = currentItems.get(i);
            if (employee.getId() == id)
            {
                currentItems.set(i, modifiedEmployee);
                break;
            }
        }

        refresh(id);
    }

    @FXML
    public void bViewAll()
    {
        refresh();
    }
}