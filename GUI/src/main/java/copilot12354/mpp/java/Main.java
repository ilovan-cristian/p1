package copilot12354.mpp.java;

import copilot12354.mpp.java.GUI.Authenticate;
import copilot12354.mpp.java.Repository.*;
import copilot12354.mpp.java.Service.*;
import java.io.IOException;
import java.util.Properties;

public class Main
{
    public static void main(String[] args)
    {
        var database = new Properties();

        try (var reader = Main.class.getResourceAsStream("/database.in"))
        {
            database.load(reader);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        var cm = new RetryableConnectionManager(database);
        var rE = new EmployeeRepoDb(cm);
        var rC = new ClientRepoDb(cm);
        var rF = new FlightRepoDb(cm);
        var rT = new TicketRepoDb(cm, rC, rF);

        var service = new ServiceAll(rE, rC, rF, rT);
        Authenticate.setService(service);
        Authenticate.main(args);
    }
}
