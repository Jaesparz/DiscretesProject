package Controller;

import Controller.DijkstraAlgorithm;
import Model.Edge;
import Model.Node;
import Model.TransportMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteCalculator {
    public String calculateRoute(Map<Node, Map<Node, Double>> graph, Node startNode, Node endNode, TransportMode transportMode) {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        Map<Node, Double> shortestDistances = dijkstra.calculateShortestPath(startNode);

        double distance = shortestDistances.getOrDefault(endNode, Double.MAX_VALUE);
        if (distance == Double.MAX_VALUE) {
            return "No hay ruta válida hacia el nodo destino.";
        }

        double time = distance / transportMode.getSpeed();

        return "Distancia: " + String.format("%.2f", distance) + " unidades\n" +
                "Tiempo estimado: " + String.format("%.2f", time) + " minutos";
    }

    public List<Edge> getShortestPathEdges(Node startNode, Node endNode, Map<Node, Map<Node, Double>> graph, List<Edge> edges) {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        Map<Node, Node> previousNodes = dijkstra.getPreviousNodes(startNode);

        List<Edge> pathEdges = new ArrayList<>();
        Node currentNode = endNode;

        while (previousNodes.containsKey(currentNode)) {
            Node previousNode = previousNodes.get(currentNode);


            for (Edge edge : edges) {
                if ((edge.getStartNode().equals(previousNode) && edge.getEndNode().equals(currentNode)) ||
                        (edge.getStartNode().equals(currentNode) && edge.getEndNode().equals(previousNode))) {
                    pathEdges.add(edge);
                    break;
                }
            }

            currentNode = previousNode;
        }

        return pathEdges;
    }
}
