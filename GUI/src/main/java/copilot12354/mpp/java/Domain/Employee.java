package copilot12354.mpp.java.Domain;

public class Employee extends Entity<Integer> {
    private String name;
    private String password;

    public Employee() {
        super();
        this.name = null;
        this.password = null;
    }

    public Employee(Integer id, String name, String password) {
        super(id);
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name='" + name + "', password='" + password + "'}";
    }
}