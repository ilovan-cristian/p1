package copilot12354.mpp;

import jakarta.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket extends EntityBase<Integer>
{

    @ManyToOne(fetch = FetchType.LAZY) // Many tickets can be associated with one flight
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY) // Many tickets can be associated with one client
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Ticket() {
        super();
        this.flight = null;
        this.client = null;
    }

    public Ticket(Integer id, Client client, Flight flight) {
        this.id = id; // inherited from Base
        this.flight = flight;
        this.client = client;
    }

    public Integer getId() {
        return id; // inherited from Base
    }

    public void setId(Integer id) {
        this.id = id; // inherited from Base
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", flight=" + flight + ", client=" + client + "}";
    }
}
