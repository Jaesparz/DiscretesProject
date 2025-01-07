package Model;

public class TrafficWarning {
    private final Node from;
    private final Node to;
    private final String message;

    public TrafficWarning(Node from, Node to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Advertencia: " + message + " entre " + from.getName() + " y " + to.getName();
    }
}
