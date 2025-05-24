package copilot12354.mpp.jsonprotocol;

import copilot12354.mpp.*;
import copilot12354.mpp.dto.DTOEmployee;
import copilot12354.mpp.dto.DTOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;

public class ClientJsonWorker implements Runnable, IObserver
{
    private IServices server;
    private Socket connection;
    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;
    private static Logger logger = LogManager.getLogger(ClientJsonWorker.class);

    public ClientJsonWorker(IServices server, Socket connection)
    {
        this.server = server;
        this.connection = connection;
        gsonFormatter = new Gson();
        try
        {
            output = new PrintWriter(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected = true;
        }
        catch (IOException e)
        {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    public void run()
    {
        while (connected)
        {
            try
            {
                String requestLine = input.readLine();
                logger.debug("class ClientJsonWorker run() requestLine " + requestLine);
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if (response != null)
                {
                    sendResponse(response);
                }
            } catch (IOException e)
            {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
        try
        {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e)
        {
            logger.error("Error " + e);
        }
    }

    private static Response okResponse = JsonProtocolUtils.newOkResponse();

    private Response handleRequest(Request request)
    {
        Response response = null;
        logger.debug("class ClientJsonWorker handleRequest(Request request) request " + request);
        if (request.getType() == RequestType.LOGIN)
        {
            logger.debug("Login request ...{}" + request.getEmployee());
            DTOEmployee eDTO = request.getEmployee();
            Employee employee = DTOUtils.getFromDTO(eDTO);
            try
            {
                server.login(employee, this);
                return okResponse;
            }
            catch (ServicesException e)
            {
                connected = false;
                return JsonProtocolUtils.newErrorResponse(e.getMessage());
            }
        }
        else if (request.getType() == RequestType.GETALLFLIGHTS)
        {
            return JsonProtocolUtils.newGetFlightsResponse(server.flightFindAll());
        }
        else if (request.getType() == RequestType.GET_CLIENTS)
        {
            return JsonProtocolUtils.newGetClientsResponse(server.clientFindAll());
        }
        else if (request.getType() == RequestType.ADDTICKET)
        {
            try
            {
                server.ticketAdd(request.getTicket().clientId, request.getTicket().flightId);
                return okResponse;
            }
            catch (ServicesException e)
            {
                return JsonProtocolUtils.newErrorResponse(e.getMessage());
            }
        }
        else if (request.getType() == RequestType.UPDATEFLIGHT)
        {
            try
            {
                server.flightUpdate(DTOUtils.getFromDTO(request.getFlight()));
                return okResponse;
            }
            catch (ServicesException e)
            {
                return JsonProtocolUtils.newErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException
    {
        String responseLine = gsonFormatter.toJson(response);
        logger.debug("sending response " + responseLine);
        synchronized (output)
        {
            output.println(responseLine);
            output.flush();
        }
    }

    @Override
    public void ticketBooked() throws ServicesException
    {
        logger.debug("class ClientJsonWorker ticketBooked()");
        Response response = JsonProtocolUtils.newRefreshResponse();
        try
        {
            sendResponse(response);
            logger.debug("class ClientJsonWorker ticketBooked() response sent");
        }
        catch (IOException e)
        {
            throw new ServicesException("Sending error: " + e);
        }
    }
}
