module com.example.discretesmathsproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.discretesmathsproject to javafx.fxml;
    exports com.example.discretesmathsproject;
}