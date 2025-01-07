package Application;

import Controller.MapController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MapaInteractivoEspol extends Application {



        @Override
        public void start(Stage primaryStage) {
            try {

                MapController mapController = new MapController();


                StackPane root = new StackPane(mapController.getMapCanvas());


                Scene scene = new Scene(root, 1600, 800);


                primaryStage.setTitle("Mapa Interactivo ESPOL");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (Exception e) {
                System.out.println("Error en la inicialización de la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {

            launch(args);
        }
    }


