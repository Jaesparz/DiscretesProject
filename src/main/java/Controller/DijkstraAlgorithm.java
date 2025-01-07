// Clase DijkstraAlgorithm
package Controller;

import Model.Node;

import java.util.*;

public class DijkstraAlgorithm {
    private final Map<Node, Map<Node, Double>> graph;

    public DijkstraAlgorithm(Map<Node, Map<Node, Double>> graph) {
        this.graph = graph;
    }

    public Map<Node, Double> calculateShortestPath(Node startNode) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        Set<Node> unvisitedNodes = new HashSet<>(graph.keySet());

        // Se inician Distancias
        for (Node node : graph.keySet()) {
            distances.put(node, Double.MAX_VALUE);
        }
        distances.put(startNode, 0.0);

        while (!unvisitedNodes.isEmpty()) {
            // SELECCION DEL NODO CON DISTANCIA MINIMA
            Node currentNode = getMinimumDistanceNode(unvisitedNodes, distances);
            unvisitedNodes.remove(currentNode);

            // PARA OBTENER LOS VECINOS :D
            Map<Node, Double> neighbors = graph.get(currentNode);
            if (neighbors == null || neighbors.isEmpty()) continue; // Saltar nodos sin vecinos

            // Actualizar las DISTANCIAS
            for (Map.Entry<Node, Double> neighborEntry : neighbors.entrySet()) {
                Node neighbor = neighborEntry.getKey();
                double edgeWeight = neighborEntry.getValue();

                if (unvisitedNodes.contains(neighbor)) {
                    double newDistance = distances.get(currentNode) + edgeWeight;
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previousNodes.put(neighbor, currentNode);
                    }
                }
            }
        }

        return distances;
    }

    public Map<Node, Node> getPreviousNodes(Node startNode) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        Set<Node> unvisitedNodes = new HashSet<>(graph.keySet());

        // Inicializar distancias
        for (Node node : graph.keySet()) {
            distances.put(node, Double.MAX_VALUE);
        }
        distances.put(startNode, 0.0);

        while (!unvisitedNodes.isEmpty()) {
            // Seleccionar el nodo con la distancia mínima
            Node currentNode = getMinimumDistanceNode(unvisitedNodes, distances);
            unvisitedNodes.remove(currentNode);

            // Obtener vecinos
            Map<Node, Double> neighbors = graph.get(currentNode);
            if (neighbors == null || neighbors.isEmpty()) continue; // Saltar nodos sin vecinos

            // Actualizar distancias y registrar nodos previos
            for (Map.Entry<Node, Double> neighborEntry : neighbors.entrySet()) {
                Node neighbor = neighborEntry.getKey();
                double edgeWeight = neighborEntry.getValue();

                if (unvisitedNodes.contains(neighbor)) {
                    double newDistance = distances.get(currentNode) + edgeWeight;
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previousNodes.put(neighbor, currentNode);
                    }
                }
            }
        }

        return previousNodes;
    }

    private Node getMinimumDistanceNode(Set<Node> unvisitedNodes, Map<Node, Double> distances) {
        Node minimumNode = null;
        double minimumDistance = Double.MAX_VALUE;

        for (Node node : unvisitedNodes) {
            double nodeDistance = distances.getOrDefault(node, Double.MAX_VALUE);
            if (nodeDistance < minimumDistance) {
                minimumDistance = nodeDistance;
                minimumNode = node;
            }
        }

        return minimumNode;
    }
}