package copilot12354.mpp;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IServices
{
    void login(Employee employee, IObserver client) throws ServicesException;
    ArrayList<Flight> flightFindAll();
    ArrayList<Client> clientFindAll();
    void ticketAdd(Integer clientId, Integer flightId);
    void flightUpdate(Flight flight);
    void setClient(IObserver client);
}
