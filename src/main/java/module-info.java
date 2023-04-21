module com.wordweaver {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;


    opens com.wordweaver to javafx.fxml;
    exports com.wordweaver;
    exports com.wordweaver.controller;
    opens com.wordweaver.controller to javafx.fxml;
}