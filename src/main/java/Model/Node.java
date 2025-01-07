package Model;

public class Node {
    private final double x;
    private final double y;
    private final String name;
    private final String description;
    private final String imagePath;

    // Constructor completo para nodos predefinidos
    public Node(double x, double y, String name, String description, String imagePath) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Constructor simplificado para nodos personalizados
    public Node(double x, double y, String name) {
        this(x, y, name, "", ""); // Sin descripción ni imagen
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return name + " (" + x + ", " + y + ")";
    }
}

