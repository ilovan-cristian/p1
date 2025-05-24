package copilot12354.mpp;

public class Ticket extends Entity<Integer>
{
    private Flight flight;
    private Client client;

    public Ticket()
    {
        super();
        this.flight = null;
        this.client = null;
    }

    public Ticket(Integer id, Client client, Flight flight)
    {
        super(id);
        this.client = client;
        this.flight = flight;
    }

    public Flight getFlight()
    {
        return flight;
    }

    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    @Override
    public String toString()
    {
        return "Ticket{" + "id=" + id + ", flight=" + flight + ", client=" + client + "}";
    }
}
