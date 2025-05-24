package copilot12354.mpp.clientfx;

import copilot12354.mpp.IServices;
import copilot12354.mpp.jsonprotocol.ServicesJsonProxy;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.util.Properties;


public class Main
{
    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args)
    {
        logger.debug("main(String[] args)");
        Properties clientProps = new Properties();

        try
        {
            clientProps.load(Main.class.getResourceAsStream("/copilot12354/mpp/clientfx/client.properties"));
            logger.info("Client properties set {} ", clientProps);
            clientProps.list(System.out);
        }
        catch (IOException e)
        {
            logger.error("Cannot find client.properties " + e);
            logger.debug("Looking for client.properties in folder {}", (new java.io.File(".")).getAbsolutePath());
            throw new RuntimeException(e);
        }

        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;

        try
        {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        }
        catch (NumberFormatException ex)
        {
            logger.error("Wrong port number " + ex.getMessage());
            logger.debug("Using default port: " + defaultPort);
        }

        logger.info("Using server IP " + serverIP);
        logger.info("Using server port " + serverPort);

        IServices server = new ServicesJsonProxy(serverIP, serverPort);

        Authenticate.setServer(server);
        Authenticate.main(args);
    }
}
