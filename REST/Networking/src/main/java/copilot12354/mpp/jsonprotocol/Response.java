package copilot12354.mpp.jsonprotocol;

import copilot12354.mpp.dto.*;

import java.util.ArrayList;

public class Response
{
    private ResponseType type;
    private String errorMessage;
    private ArrayList<DTOFlight> flights;
    private ArrayList<DTOClient> clients;

    public Response()
    {

    }

    public ResponseType getType()
    {
        return type;
    }

    public void setType(ResponseType type)
    {
        this.type = type;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public void setFlights(ArrayList<DTOFlight> flights)
    {
        this.flights = flights;
    }

    public ArrayList<DTOFlight> getFlights()
    {
        return flights;
    }

    public ArrayList<DTOClient> getClients()
    {
        return clients;
    }

    public void setClients(ArrayList<DTOClient> clients)
    {
        this.clients = clients;
    }

    @Override
    public String toString()
    {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}