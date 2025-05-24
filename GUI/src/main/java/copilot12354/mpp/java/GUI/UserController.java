package copilot12354.mpp.java.GUI;

import copilot12354.mpp.java.Domain.Client;
import copilot12354.mpp.java.Domain.Employee;
import copilot12354.mpp.java.Domain.Flight;
import copilot12354.mpp.java.Service.ServiceAll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class UserController
{
    private ServiceAll service;
    private int userId;

    public UserController(ServiceAll service, int userId)
    {
        this.service = service;
        this.userId = userId;
    }

    void refresh()
    {
        tableViewFlights.getSelectionModel().clearSelection();

        List<Flight> flights = service.flightFindAll();
        ObservableList<Flight> flightList = FXCollections.observableArrayList(flights);
        tableViewFlights.setItems(flightList);

        textFieldDestination.setText("");
        textFieldDeparture.setText("");
        textFieldFlight.setText("");
        tableViewFlightsFiltered.getItems().clear();
        bClear();
    }

    private void handleRowSelection(Flight selectedFlight)
    {
        if (selectedFlight != null)
        {
            textFieldDestination.setText(selectedFlight.getDestination());
            textFieldDeparture.setText(String.valueOf(selectedFlight.getDeparture()));
            bSearch();
        }
    }

    private void handleRowSelectionFiltered(Flight selectedFlight)
    {
        if (selectedFlight != null)
        {
            textFieldFlight.setText(String.valueOf(selectedFlight.getId()));
        }
    }

    @FXML
    private Button buttonExit;
    @FXML
    private TableView<Flight> tableViewFlights;
    @FXML
    private TableColumn<Flight, Integer> columnID;
    @FXML
    private TableColumn<Flight, String> columnDestination;
    @FXML
    private TableColumn<Flight, LocalDateTime> columnDeparture;
    @FXML
    private TableColumn<Flight, String> columnLocation;
    @FXML
    private TableColumn<Flight, Integer> columnSeats;
    @FXML
    private TableView<Flight> tableViewFlightsFiltered;
    @FXML
    private TableColumn<Flight, Integer> columnIDFiltered;
    @FXML
    private TableColumn<Flight, String> columnDestinationFiltered;
    @FXML
    private TableColumn<Flight, LocalDateTime> columnDepartureFiltered;
    @FXML
    private TableColumn<Flight, String> columnLocationFiltered;
    @FXML
    private TableColumn<Flight, Integer> columnSeatsFiltered;
    @FXML
    private TextField textFieldFlight;
    @FXML
    private TextField textFieldDestination;
    @FXML
    private TextField textFieldDeparture;
    @FXML
    private TextField textFieldSeats;
    @FXML
    private TextField textFieldNames;
    @FXML
    private ListView listViewClients;

    @FXML
    public void initialize()
    {
        refresh();

        tableViewFlights.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleRowSelection(newValue)
        );

        tableViewFlightsFiltered.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleRowSelectionFiltered(newValue)
        );
    }

    @FXML
    public void bSearch()
    {
        String destination = textFieldDestination.getText();
        String departureText = textFieldDeparture.getText();

        LocalDateTime departure = null;
        try
        {
            departure = LocalDateTime.parse(departureText);
        } catch (Exception e)
        {
            System.out.println("Invalid date format.");
        }

        if (destination != null && departure != null)
        {
            List<Flight> filteredFlights = service.flightFindByDestinationAndDeparture(destination, departure);
            ObservableList<Flight> flightList = FXCollections.observableArrayList(filteredFlights);
            tableViewFlightsFiltered.setItems(flightList);
        }
        else
        {
            System.out.println("Please enter valid search criteria.");
        }
    }

    @FXML
    public void bAddClient()
    {
        int size = listViewClients.getItems().size();

        // int MAXIMUMINT = Integer.MAX_VALUE;
        // int MAXIMUMINT = 2147483647;
        // int MAXIMUMINT = 0x7FFFFFFF;

        int maximum = textFieldSeats.getText().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(textFieldSeats.getText());

        if (size < maximum)
        {

            String clientName = textFieldNames.getText();
            if (clientName != null && !clientName.isEmpty())
            {
                listViewClients.getItems().add(clientName);
                textFieldNames.clear();
            }
            else
            {
                System.out.println("Please enter a valid client name.");
            }
        }
        else
        {
            System.out.println("size >= maximum");
        }
    }

    @FXML
    public void bBook()
    {
        String flightIdText = textFieldFlight.getText();
        int flightId = -1;
        try
        {
            flightId = Integer.parseInt(flightIdText);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid Flight ID");
            return;
        }

        Flight flight = service.flightFindOne(flightId);
        if (flight == null)
        {
            System.out.println("Flight not found.");
            return;
        }

        ObservableList<String> clientIds = listViewClients.getItems();
        for (String clientIdText : clientIds)
        {
            int clientId = -1;
            try
            {
                clientId = Integer.parseInt(clientIdText);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid Client ID: " + clientIdText);
                continue;
            }

            Client client = service.clientFindOne(clientId);
            if (client != null)
            {
                service.ticketAdd(client.getId(), flight.getId());
                System.out.println("Ticket booked for client with ID: " + clientId + " on flight: " + flightId);
            }
            else
            {
                System.out.println("Client with ID " + clientId + " not found.");
            }
        }

        listViewClients.getItems().clear();
    }

    @FXML
    public void bExit()
    {
        Stage stage = (Stage) buttonExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void bViewTickets()
    {

    }

    @FXML
    public void bRefresh()
    {
        refresh();
    }

    @FXML
    public void bClear()
    {
        textFieldSeats.setText("");
        textFieldNames.setText("");
        listViewClients.getItems().clear();
    }
}