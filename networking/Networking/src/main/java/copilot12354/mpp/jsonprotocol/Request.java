package copilot12354.mpp.jsonprotocol;

import copilot12354.mpp.dto.*;

public class Request
{
    private RequestType type;
    private DTOEmployee DTOemployee;
    private DTOTicket DTOticket;
    private DTOFlight DTOFlight;

    public Request()
    {

    }

    public RequestType getType()
    {
        return type;
    }

    public void setType(RequestType type)
    {
        this.type = type;
    }

    public DTOEmployee getEmployee()
    {
        return DTOemployee;
    }

    public void setEmployee(DTOEmployee DTOemployee)
    {
        this.DTOemployee = DTOemployee;
    }

    public DTOTicket getTicket()
    {
        return DTOticket;
    }

    public void setTicket(DTOTicket DTOticket)
    {
        this.DTOticket = DTOticket;
    }

    public DTOFlight getFlight()
    {
        return DTOFlight;
    }

    public void setFlight(DTOFlight DTOFlight)
    {
        this.DTOFlight = DTOFlight;
    }

    @Override
    public String toString()
    {
        return "Request{" +
                "type=" + type +
                ", employee=" + DTOemployee +
                '}';
    }
}