module com.example.dartcounterapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.dartcounterapp to javafx.fxml;
    exports com.example.dartcounterapp;
}