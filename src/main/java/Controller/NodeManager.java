package Controller;
import Model.Edge;
import Model.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeManager {
    private final List<Node> predefinedNodes;
    private final List<Node> customNodes;
    private final List<Edge> edges;

    public NodeManager(List<Node> predefinedNodes) {
        this.predefinedNodes = predefinedNodes;
        this.customNodes = new ArrayList<>();
        this.edges = new ArrayList<>();

        // Conectar todos los nodos en un solo grafo
        connectNodesWithMST();
    }

    public List<Node> getPredefinedNodes() {
        return predefinedNodes;
    }

    public List<Node> getCustomNodes() {
        return customNodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addCustomNode(Node node) {
        customNodes.add(node);

        // Conectar el nodo personalizado al nodo más cercano
        Node closestNode = findClosestNode(node, predefinedNodes);
        if (closestNode != null) {
            double distance = calculateDistance(node, closestNode);
            addEdge(node, closestNode, distance);
        }
    }

    public void addPredefinedNode(Node node) {
        predefinedNodes.add(node);

        // Conectar el nuevo nodo al nodo más cercano
        Node closestNode = findClosestNode(node, predefinedNodes);
        if (closestNode != null && !closestNode.equals(node)) {
            double distance = calculateDistance(node, closestNode);
            addEdge(node, closestNode, distance);
        }
    }

    public void addEdge(Node startNode, Node endNode, double weight) {
        for (Edge edge : edges) {
            if ((edge.getStartNode().equals(startNode) && edge.getEndNode().equals(endNode)) ||
                    (edge.getStartNode().equals(endNode) && edge.getEndNode().equals(startNode))) {
                return; // No agregar la arista si ya existe
            }
        }
        edges.add(new Edge(startNode, endNode, weight));
    }

    Node findClosestNode(Node targetNode, List<Node> nodes) {
        Node closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Node node : nodes) {
            if (!node.equals(targetNode)) { // Evitar conectar un nodo consigo mismo
                double distance = calculateDistance(targetNode, node);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = node;
                }
            }
        }

        return closest;
    }

    private double calculateDistance(Node node1, Node node2) {
        return Math.sqrt(Math.pow(node1.getX() - node2.getX(), 2) + Math.pow(node1.getY() - node2.getY(), 2));
    }

    public Map<Node, Map<Node, Double>> buildGraph() {
        Map<Node, Map<Node, Double>> graph = new HashMap<>();
        for (Node node : predefinedNodes) {
            graph.put(node, new HashMap<>());
        }

        for (Node customNode : customNodes) {
            graph.put(customNode, new HashMap<>());
        }

        for (Edge edge : edges) {
            graph.get(edge.getStartNode()).put(edge.getEndNode(), edge.getWeight());
            graph.get(edge.getEndNode()).put(edge.getStartNode(), edge.getWeight());
        }

        return graph;
    }

    private void connectNodesWithMST() {
        List<Edge> possibleEdges = new ArrayList<>();

        // Crear todas las posibles conexiones entre nodos predefinidos
        for (int i = 0; i < predefinedNodes.size(); i++) {
            for (int j = i + 1; j < predefinedNodes.size(); j++) {
                Node node1 = predefinedNodes.get(i);
                Node node2 = predefinedNodes.get(j);
                double distance = calculateDistance(node1, node2);
                possibleEdges.add(new Edge(node1, node2, distance));
            }
        }

        // Ordenar las aristas distancia (WEIGHT/PESO)
        possibleEdges.sort((e1, e2) -> Double.compare(e1.getWeight(), e2.getWeight()));

        //  USO DEL algoritmo de Kruskal para conectar los nodos
        List<Edge> mstEdges = new ArrayList<>();
        DisjointSet<Node> disjointSet = new DisjointSet<>(predefinedNodes);

        for (Edge edge : possibleEdges) {
            Node startNode = edge.getStartNode();
            Node endNode = edge.getEndNode();

            if (!disjointSet.isConnected(startNode, endNode)) {
                disjointSet.union(startNode, endNode);
                mstEdges.add(edge);
                addEdge(startNode, endNode, edge.getWeight());
            }
        }
    }
}

