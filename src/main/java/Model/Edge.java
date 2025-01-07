package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Edge {
    private final Node startNode;
    private final Node endNode;
    private final double weight;

    public Edge(Node startNode, Node endNode, double weight) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public double getWeight() {
        return weight;
    }

    public void draw(GraphicsContext gc, boolean highlighted) {
        gc.setStroke(highlighted ? Color.RED : Color.LIGHTGRAY);
        gc.setLineWidth(highlighted ? 3 : 1);
        gc.strokeLine(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
    }
}

