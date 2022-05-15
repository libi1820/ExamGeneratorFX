module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens Application to javafx.graphics,javafx.fxml;
    exports model;
}