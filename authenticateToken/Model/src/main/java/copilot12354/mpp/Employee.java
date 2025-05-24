package copilot12354.mpp;

import jakarta.persistence.*;

@Entity
@Table(name = "Employee")
public class Employee extends EntityBase<Integer>
{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    public Employee()
    {
        super();
        this.name = null;
        this.password = null;
    }

    public Employee(Integer id, String name, String password)
    {
        this.id = id; // inherited from Base
        this.name = name;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "Employee{" + "id=" + id + ", name='" + name + "', password='" + password + "'}";
    }
}
