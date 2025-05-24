package copilot12354.mpp.dto;

import java.io.Serializable;

public class DTOEmployee implements Serializable
{
    private Integer id;
    private String name;
    private String password;

    public DTOEmployee(Integer id)
    {
        this(id, "", "");
    }

    public DTOEmployee(Integer id, String name, String password)
    {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public String toString()
    {
        return "EmployeeDTO[" + id + ", " + name + ", " + password + "]";
    }
}
