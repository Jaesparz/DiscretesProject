package Controller;

import Controller.RouteCalculator;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import Controller.NodeManager;
import Model.Edge;
import Model.Node;
import Model.TransportMode;

import java.util.List;
import java.util.Map;

public class MapController {
    private final Canvas mapCanvas;
    private final NodeManager nodeManager;
    private final RouteCalculator routeCalculator;

    private Node startNode = null;
    private final Image backgroundImage;

    public MapController() {
        mapCanvas = new Canvas(1600, 800);


        List<Node> predefinedNodes = List.of(
                new Node(354,67, "Canchas Sinteticas", "Para pelotear Con los Panas", "file:src/main/resources/facultad.png"),
                new Node(213,665, "FCSH", "Facultad de ingeniería", "file:src/main/resources/facultad.png"),
                new Node(306,327, "FIEC NUEVA", "Comedor principal del campus", "file:src/main/resources/comedor.png"),
                new Node(371,159, "Cancha de Futbol", "Fulbito", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png"),
                new Node(422,143, "UBP", "Unidad de Bienestar Politecnico", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png"),
                new Node(632,171, "LABS de FIMCP", "Área recreativa al aire libre", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png"),
                new Node(889,383, "Coliseo Nuevo", "Área recreativa al aire libre", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png"),
                new Node(404,61, "Coliseo Viejo", "Área recreativa al aire libre", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png"),
                new Node(279,229, "Gym ESPOL", "Área recreativa al aire libre", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png"),
                new Node(171,618, "Canchas FCSH", "Área recreativa al aire libre", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png"),
                new Node(1100,557, "FIMCM ", "Área recreativa al aire libre", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png"),
                new Node(1216,208, "FADCOM ", "facultad para DORMIR ZZZ", "C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\Fadcom.png")
        );


        nodeManager = new NodeManager(predefinedNodes);
        routeCalculator = new RouteCalculator();
        backgroundImage = new Image("C:\\Users\\Usuario\\IdeaProjects\\DiscretesMathProject\\src\\main\\resources\\mapa espol (1).jpg");




        initializeMap();
        setupMouseEvents();
    }

    private void initializeMap() {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        // MAPA
        if (!backgroundImage.isError()) {
            gc.drawImage(backgroundImage, 0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        } else {
            gc.setFill(Color.RED);
            gc.fillText("Error al cargar la imagen de fondo.", 100, 100);
        }

        // ARISTAS
        for (Edge edge : nodeManager.getEdges()) {
            edge.draw(gc, false); // Dibujar las aristas sin resaltar
        }

        // NODOS PRECARGADOS
        gc.setFill(Color.BLUE);
        for (Node node : nodeManager.getPredefinedNodes()) {
            gc.fillOval(node.getX() - 5, node.getY() - 5, 10, 10);
            gc.fillText(node.getName(), node.getX() + 10, node.getY() - 10);
        }

        // NODOS PERSONALIZADOS (NOSE COMO IMPLEMENTAR AUN XD)
        gc.setFill(Color.GREEN);
        for (Node node : nodeManager.getCustomNodes()) {
            gc.fillOval(node.getX() - 5, node.getY() - 5, 10, 10);
            gc.fillText(node.getName(), node.getX() + 10, node.getY() - 10);
        }
    }

    private void setupMouseEvents() {
        mapCanvas.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();

            Node clickedNode = nodeManager.findClosestNode(new Node(x, y, ""), nodeManager.getPredefinedNodes());
            if (clickedNode != null) {
                handleNodeClick(clickedNode);
            } else if (startNode == null) {
                handleCustomNode(x, y);
            }
        });
    }

    private void handleNodeClick(Node node) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Información del Nodo");
        alert.setHeaderText(node.getName());
        alert.setContentText(node.getDescription());

        // Agregar la imagen al cuadro de diálogo
        if (node.getImagePath() != null && !node.getImagePath().isEmpty()) {
            try {
                ImageView imageView = new ImageView(new Image(node.getImagePath()));
                imageView.setFitWidth(200); // Ajustar tamaño
                imageView.setPreserveRatio(true);
                alert.setGraphic(imageView);
            } catch (Exception e) {
                System.out.println("Error al cargar la imagen: " + e.getMessage());
            }
        }

        ButtonType startButton = new ButtonType("Establecer como inicio");
        ButtonType destinationButton = new ButtonType("Establecer como destino");
        ButtonType closeButton = ButtonType.CLOSE;

        alert.getButtonTypes().setAll(startButton, destinationButton, closeButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == startButton) {
                startNode = node;
            } else if (response == destinationButton) {
                calculateRoute(node);
            }
        });
    }

    private void handleCustomNode(double x, double y) {
        Node customNode = new Node(x, y, "Punto Personalizado");
        nodeManager.addCustomNode(customNode);
        initializeMap();
    }

    private void calculateRoute(Node endNode) {
        Map<Node, Map<Node, Double>> graph = nodeManager.buildGraph();
        validateGraph(startNode, endNode, graph);

        String result = routeCalculator.calculateRoute(graph, startNode, endNode, TransportMode.WALK);

        // MARCACION DE ARISTAS
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        List<Edge> shortestPathEdges = routeCalculator.getShortestPathEdges(startNode, endNode, graph, nodeManager.getEdges());
        for (Edge edge : shortestPathEdges) {
            edge.draw(gc, true);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
        alert.setTitle("Ruta Calculada");
        alert.showAndWait();

        startNode = null;
    }

    private void validateGraph(Node startNode, Node endNode, Map<Node, Map<Node, Double>> graph) {
        if (!graph.containsKey(startNode) || graph.get(startNode).isEmpty()) {
            throw new IllegalStateException("El nodo inicial no tiene conexiones válidas.");
        }
        if (!graph.containsKey(endNode) || graph.get(endNode).isEmpty()) {
            throw new IllegalStateException("El nodo final no tiene conexiones válidas.");
        }
    }

    public Canvas getMapCanvas() {
        return mapCanvas;
    }
}

