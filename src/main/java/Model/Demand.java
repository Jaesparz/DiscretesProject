package Model;


public class Demand {
    private final Node location;
    private final String description;

    public Demand(Node location, String description) {
        this.location = location;
        this.description = description;
    }

    public Node getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Demanda en " + location.getName() + ": " + description;
    }
}

