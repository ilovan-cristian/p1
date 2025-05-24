package copilot12354.mpp.dto;

import java.io.Serializable;

public class DTOTicket implements Serializable
{
    public Integer clientId;
    public Integer flightId;

    public DTOTicket(Integer clientId, Integer flightId)
    {
        this.clientId = clientId;
        this.flightId = flightId;
    }

    public Integer getClientId()
    {
        return clientId;
    }

    public void setClientId(Integer clientId)
    {
        this.clientId = clientId;
    }

    public Integer getFlightId()
    {
        return flightId;
    }

    public void setFlightId(Integer flightId)
    {
        this.flightId = flightId;
    }

    @Override
    public String toString()
    {
        return "DTOTicket{" +
                "clientId=" + clientId +
                ", flightId=" + flightId +
                '}';
    }
}
