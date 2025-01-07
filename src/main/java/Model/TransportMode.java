package Model;

public enum TransportMode {
    BIKE(15),   // Velocidad promedio en km/h
    BUS(25),
    CAR(50),
    WALK(5);

    private final int speed;

    TransportMode(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
