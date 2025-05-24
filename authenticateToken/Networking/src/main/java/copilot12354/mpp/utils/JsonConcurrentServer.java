package copilot12354.mpp.utils;

import copilot12354.mpp.IServices;
import copilot12354.mpp.jsonprotocol.ClientJsonWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.Socket;

public class JsonConcurrentServer extends AbsConcurrentServer
{
    private IServices server;
    private static Logger logger = LogManager.getLogger(JsonConcurrentServer.class);

    public JsonConcurrentServer(int port, IServices server)
    {
        super(port);
        this.server = server;
        logger.info("JsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client)
    {
        ClientJsonWorker worker = new ClientJsonWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }
}
