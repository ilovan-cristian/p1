package copilot12354.mpp.clientfx;

import copilot12354.mpp.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UserController implements Initializable, IObserver
{
    private IServices server;
    private int userId;
    private static Logger logger = LogManager.getLogger(Main.class);

    @Override
    public String toString()
    {
        return "UserController{" +
                "server=" + server +
                ", userId=" + userId +
                '}';
    }

    public UserController(IServices server, int userId)
    {
        this.server = server;
        this.userId = userId;
        System.out.println("UserController constructor");
    }

    void refresh()
    {
        logger.debug("class UserController refresh()");

        tableViewFlightsFiltered.getItems().clear();

        ArrayList<Flight> flights = server.flightFindAll().stream()
                .filter(flight -> flight.getSeats() != null && flight.getSeats() > 0)
                .collect(Collectors.toCollection(ArrayList::new));

        logger.debug("Flights: {}", flights);

        tableViewFlights.getSelectionModel().clearSelection();
        ObservableList<Flight> flightList = FXCollections.observableArrayList(flights);
        tableViewFlights.setItems(flightList);

        textFieldDestination.setText("");
        textFieldDeparture.setText("");
        textFieldFlight.setText("");
        bClear();

        logger.debug("class UserController ~refresh()");
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        logger.debug("class UserController initialize()");

        refresh();

        tableViewFlights.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleRowSelection(newValue)
        );

        tableViewFlightsFiltered.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleRowSelectionFiltered(newValue)
        );

        tableViewFlights.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.isControlDown() && event.getCode() == KeyCode.R) {
                        refresh();
                        event.consume();
                    }
                });
            }
        });
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
            logger.debug("Invalid date format.");
        }

        final String finalDestination = destination;
        final LocalDateTime finalDeparture = departure;

        if (finalDestination != null && finalDeparture != null)
        {
            ArrayList<Flight> filteredFlights = (ArrayList<Flight>) server.flightFindAll().stream()
                    .filter(flight -> flight.getDestination().equals(finalDestination) && flight.getDeparture().equals(finalDeparture))
                    .collect(Collectors.toList());
            ObservableList<Flight> flightList = FXCollections.observableArrayList(filteredFlights);
            tableViewFlightsFiltered.setItems(flightList);
        } else
        {
            logger.debug("Please enter valid search criteria.");
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
            } else
            {
                logger.debug("Please enter a valid client name.");
            }
        } else
        {
            logger.debug("size >= maximum");
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
        } catch (NumberFormatException e)
        {
            logger.debug("Invalid Flight ID");
            return;
        }

        final int flightIdFinal = flightId;
        Optional<Flight> flight = server.flightFindAll().stream()
                .filter(f -> f.getId().equals(flightIdFinal))
                .findFirst();

        if (flight.isEmpty())
            return;

        ObservableList<String> clientIds = listViewClients.getItems();

        logger.debug("flight is okay");

        for (String clientIdText : clientIds)
        {
            if (flight.get().getSeats() <= 0)
            {
                logger.debug("No seats available on flight: " + flightId);
                return;
            }

            int clientId = -1;
            try
            {
                clientId = Integer.parseInt(clientIdText);
            } catch (NumberFormatException e)
            {
                logger.debug("Invalid Client ID: " + clientIdText);
                continue;
            }

            logger.debug("client is okay");

            server.ticketAdd(clientId, flightId);
            logger.debug("flight id: " + flight.get().getId());
            logger.debug("flight destination: " + flight.get().getDestination());
            logger.debug("flight departure: " + flight.get().getDeparture());
            logger.debug("flight seats: " + flight.get().getSeats());
            logger.debug("flight location: " + flight.get().getLocation());

            server.flightUpdate(new Flight(
                    flight.get().getId(),
                    flight.get().getDestination(),
                    flight.get().getDeparture(),
                    flight.get().getSeats() - 1,
                    flight.get().getLocation()));

            logger.debug("Ticket booked for client with ID: " + clientId + " on flight: " + flightId);
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
        logger.debug("class UserController bRefresh()");
        refresh();
    }

    @FXML
    public void bClear()
    {
        textFieldSeats.setText("");
        textFieldNames.setText("");
        listViewClients.getItems().clear();
    }

    @Override
    public void ticketBooked() throws ServicesException {
        logger.debug("class UserController ticketBooked()");
        new Thread(() -> {
            try {
                // Pause for 1 second (1000 milliseconds)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Sleep interrupted", e);
            }
            Platform.runLater(this::refresh);
        }).start();
    }


}