package ro.mpp2025;

import ro.mpp2025.Repository.*;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        var dbProperties = new Properties();
        try (var reader = Main.class.getResourceAsStream("/db-props.txt")) {
            dbProperties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var url = dbProperties.getProperty("url", "");
        var cm = new RetryableConnectionManager(url, dbProperties);

        EmployeeRepo er = new EmployeeRepoDb(cm);
        ClientRepo cr = new ClientRepoDb(cm);
        FlightRepo fr = new FlightRepoDb(cm);
        TicketRepo tr = new TicketRepoDb(cm, cr, fr);

        cr.findAll().forEach(System.out::println);
    }
}
