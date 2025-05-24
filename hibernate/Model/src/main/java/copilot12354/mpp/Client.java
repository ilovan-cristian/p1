package copilot12354.mpp;

import jakarta.persistence.*;

@Entity
@Table(name = "Client")
public class Client extends EntityBase<Integer>
{
    @Column(name = "name", nullable = false)
    private String name;

    public Client()
    {
        this.name = null;
    }

    public Client(Integer id, String name)
    {
        this.id = id; // inherited from Base
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Client{" + "id=" + id + ", name='" + name + "'}";
    }
}
