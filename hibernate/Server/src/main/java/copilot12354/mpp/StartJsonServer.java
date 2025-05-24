package copilot12354.mpp;

import copilot12354.mpp.Hibernate.*;
import copilot12354.mpp.utils.AbstractServer;
import copilot12354.mpp.utils.JsonConcurrentServer;
import copilot12354.mpp.utils.NetworkingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Properties;

public class StartJsonServer
{
    private static int defaultPort = 55555;
    private static Logger logger = LogManager.getLogger(StartJsonServer.class);

    public static void main(String[] args)
    {
        Properties serverProps = new Properties();
        try
        {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/server.properties"));
            logger.info("Server properties set. {} ", serverProps);
            // serverProps.list(System.out);
        } catch (Exception e)
        {
            logger.error("Cannot find copilot12354.properties " + e);
            logger.debug("Looking for file in " + (new java.io.File(".")).getAbsolutePath());
            return;
        }

        EmployeeRepo rE = new EmployeeRepoDb();
        ClientRepo rC = new ClientRepoDb();
        FlightRepo rF = new FlightRepoDb();
        TicketRepo rT = new TicketRepoDb();
        IServices serverImplementation = new ServicesImplementation(rE, rC, rF, rT);
        int serverPort = defaultPort;

        try
        {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef)
        {
            logger.error("Wrong  Port Number" + nef.getMessage());
            logger.debug("Using default port " + defaultPort);
        }
        logger.debug("Starting server on port: " + serverPort);
        AbstractServer server = new JsonConcurrentServer(serverPort, serverImplementation);
        try
        {
            server.start();
        } catch (NetworkingException e)
        {
            logger.error("Error starting the server" + e.getMessage());
        }
    }
}
