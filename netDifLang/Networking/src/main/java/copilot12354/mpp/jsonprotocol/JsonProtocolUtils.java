package copilot12354.mpp.jsonprotocol;

import copilot12354.mpp.Client;
import copilot12354.mpp.Employee;
import copilot12354.mpp.Flight;
import copilot12354.mpp.Ticket;
import copilot12354.mpp.dto.DTOUtils;
import java.util.ArrayList;

public class JsonProtocolUtils
{
    public static Request newLoginRequest(Employee employee)
    {
        Request request = new Request();
        request.setType(RequestType.LOGIN);
        request.setEmployee(DTOUtils.getDTO(employee));
        return request;
    }

    public static Request newGetFlightsRequest()
    {
        Request request = new Request();
        request.setType(RequestType.GETALLFLIGHTS);
        return request;
    }

    public static Request newGetClientsRequest()
    {
        Request request = new Request();
        request.setType(RequestType.GET_CLIENTS);
        return request;
    }

    public static Request newAddTicketRequest(Integer clientId, Integer flightId)
    {
        Request request = new Request();
        request.setType(RequestType.ADDTICKET);
        request.setTicket(DTOUtils.getDTO(clientId, flightId));
        return request;
    }

    public static Request newUpdateFlightRequest(Flight flight)
    {
        Request request = new Request();
        request.setType(RequestType.UPDATEFLIGHT);
        request.setFlight(DTOUtils.getDTO(flight));
        return request;
    }

    public static Response newOkResponse()
    {
        Response response = new Response();
        response.setType(ResponseType.OK);
        return response;
    }

    public static Response newErrorResponse(String errorMessage)
    {
        Response resp = new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static Response newGetFlightsResponse(ArrayList<Flight> flightList)
    {
        Response response = new Response();
        response.setType(ResponseType.OK);
        response.setFlights(DTOUtils.getDTO(flightList));
        return response;
    }

    public static Response newGetClientsResponse(ArrayList<Client> clientList)
    {
        Response response = new Response();
        response.setType(ResponseType.OK);
        response.setClients(DTOUtils.getClientsDTO(clientList));
        return response;
    }

    public static Response newRefreshResponse()
    {
        Response response = new Response();
        response.setType(ResponseType.REFRESH);
        return response;
    }
}
