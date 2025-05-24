package copilot12354.mpp.dto;

import java.io.Serializable;

public class DTOClient implements Serializable
{
    private Integer id;
    private String name;

    public DTOClient(Integer id)
    {
        this(id, "");
    }

    public DTOClient(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "ClientDTO[" + id + ", " + name + "]";
    }
}
