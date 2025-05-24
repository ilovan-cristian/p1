package copilot12354.mpp.dto;

import copilot12354.mpp.Client;
import copilot12354.mpp.Flight;

import java.io.Serializable;

public class DTOTicket implements Serializable
{
    private Integer id;
    private DTOClient client;
    private DTOFlight flight;

    public DTOTicket(Integer id, DTOClient client, DTOFlight flight)
    {
        this.id = id;
        this.client = client;
        this.flight = flight;
    }

    public Integer getId()
    {
        return id;
    }

    public DTOClient getClient()
    {
        return client;
    }

    public DTOFlight getFlight()
    {
        return flight;
    }

    @Override
    public String toString()
    {
        return "DTOTicket{" +
                "id=" + id +
                ", client=" + client +
                ", flight=" + flight +
                '}';
    }
}
