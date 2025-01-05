package dto;

public class ClientName implements java.io.Serializable {
    private String name;

    public ClientName() {
    }

    public ClientName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
