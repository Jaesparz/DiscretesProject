package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import Model.Demand;
import Model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DemandGenerator {
    private final List<Node> locations;
    private final List<Demand> activeDemands = new ArrayList<>();
    private final Random random = new Random();

    public DemandGenerator(List<Node> locations) {
        this.locations = locations;
    }

    public void startGenerating() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            Node randomLocation = locations.get(random.nextInt(locations.size()));
            Demand newDemand = new Demand(randomLocation, "Nueva demanda en " + randomLocation);
            activeDemands.add(newDemand);

            System.out.println("Demanda generada: " + newDemand.getDescription());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public List<Demand> getActiveDemands() {
        return activeDemands;
    }
}

