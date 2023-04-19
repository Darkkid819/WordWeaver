module com.wordweaver {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.wordweaver to javafx.fxml;
    exports com.wordweaver;
}