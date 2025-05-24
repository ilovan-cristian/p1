package copilot12354.mpp;

public class Client extends Entity<Integer> {
    private String name;

    public Client() {
        super();
        this.name = null;
    }

    public Client(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + "'}";
    }
}