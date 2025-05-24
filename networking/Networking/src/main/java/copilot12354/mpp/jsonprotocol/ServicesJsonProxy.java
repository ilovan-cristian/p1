package copilot12354.mpp.jsonprotocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.google.gson.Gson;
import copilot12354.mpp.*;
import copilot12354.mpp.dto.DTOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServicesJsonProxy implements IServices
{
    private String host;
    private int port;
    private IObserver client;
    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    private static Logger logger = LogManager.getLogger(ServicesJsonProxy.class);

    public ServicesJsonProxy(String host, int port)
    {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    private void closeConnection()
    {
        finished = true;
        try
        {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e)
        {
            logger.error(e);
            logger.error(e.getStackTrace());
        }

    }

    private void sendRequest(Request request) throws ServicesException
    {
        String reqLine = gsonFormatter.toJson(request);
        try
        {
            output.println(reqLine);
            output.flush();
        } catch (Exception e)
        {
            throw new ServicesException("Error sending object " + e);
        }

    }

    private Response readResponse() throws ServicesException
    {
        Response response = null;
        try
        {
            response = qresponses.take();

        } catch (InterruptedException e)
        {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
        return response;
    }

    private void initializeConnection() throws ServicesException
    {
        try
        {
            gsonFormatter = new Gson();
            connection = new Socket(host, port);
            output = new PrintWriter(connection.getOutputStream());
            output.flush();
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished = false;
            startReader();
        } catch (IOException e)
        {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    private void startReader()
    {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response)
    {
        logger.debug("class ServicesJsonProxy handleUpdate()");
        if (response.getType() == ResponseType.REFRESH)
        {
            try
            {
                client.ticketBooked();
                logger.debug("class ServicesJsonProxy handleUpdate() - REFRESH OK");
            }
            catch (Exception e)
            {
                logger.debug("class ServicesJsonProxy handleUpdate() - REFRESH ERROR!!!");
                logger.error(e);
            }
        }
    }

    private boolean isUpdate(Response response)
    {
        return response.getType() == ResponseType.REFRESH;
    }

    private class ReaderThread implements Runnable
    {
        public void run()
        {
            while (!finished)
            {
                try
                {
                    String responseLine = input.readLine();
                    logger.debug("response received {}", responseLine);
                    Response response = gsonFormatter.fromJson(responseLine, Response.class);
                    if (isUpdate(response))
                    {
                        handleUpdate(response);
                    } else
                    {

                        try
                        {
                            qresponses.put(response);
                        } catch (InterruptedException e)
                        {
                            logger.error(e);
                            logger.error(e.getStackTrace());
                        }
                    }
                } catch (IOException e)
                {
                    logger.error("Reading error " + e);
                }
            }
        }
    }

    public void setClient(IObserver client)
    {
        this.client = client;
    }

    @Override
    public void login(Employee employee, IObserver client) throws ServicesException
    {
        initializeConnection();
        Request req = JsonProtocolUtils.newLoginRequest(employee);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK)
        {
            this.client = client;
            logger.debug("class ServicesJsonProxy login() - TRYING");
            if (this.client != null)
            {
                logger.debug(this.client);
                logger.debug("class ServicesJsonProxy login() - OK");
            }
            return;
        }
        if (response.getType() == ResponseType.ERROR)
        {
            String err = response.getErrorMessage();
            closeConnection();
            throw new ServicesException(err);
        }
    }

    @Override
    public ArrayList<Flight> flightFindAll()
    {
        Request req = JsonProtocolUtils.newGetFlightsRequest();
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR)
        {
            String err = response.getErrorMessage();
            throw new ServicesException(err);
        }

        return DTOUtils.getFromDTO(response.getFlights());
    }

    @Override
    public ArrayList<Client> clientFindAll()
    {
        Request req = JsonProtocolUtils.newGetClientsRequest();
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR)
        {
            String err = response.getErrorMessage();
            throw new ServicesException(err);
        }

        return DTOUtils.getClientsFromDTO(response.getClients());
    }

    @Override
    public void ticketAdd(Ticket ticket)
    {
        Request req = JsonProtocolUtils.newAddTicketRequest(ticket);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR)
        {
            String err = response.getErrorMessage();
            throw new ServicesException(err);
        }
    }

    @Override
    public void flightUpdate(Flight flight)
    {
        Request req = JsonProtocolUtils.newUpdateFlightRequest(flight);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR)
        {
            String err = response.getErrorMessage();
            throw new ServicesException(err);
        }
    }
}