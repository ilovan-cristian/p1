package copilot12354.mpp.jsonprotocol;

import copilot12354.mpp.dto.*;

public class Request
{
    private RequestType type;
    private DTOEmployee dtoEmployee;
    private DTOTicket dtoTicket;
    private DTOFlight dtoFlight;

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
        return dtoEmployee;
    }

    public void setEmployee(DTOEmployee DTOemployee)
    {
        this.dtoEmployee = DTOemployee;
    }

    public DTOTicket getTicket()
    {
        return dtoTicket;
    }

    public void setTicket(DTOTicket DTOticket)
    {
        this.dtoTicket = DTOticket;
    }

    public DTOFlight getFlight()
    {
        return dtoFlight;
    }

    public void setFlight(DTOFlight DTOFlight)
    {
        this.dtoFlight = DTOFlight;
    }

    @Override
    public String toString()
    {
        return "Request{" +
                "type=" + type +
                ", employee=" + dtoEmployee +
                '}';
    }
}