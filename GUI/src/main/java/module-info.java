module copilot12354.mpp.java {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.apache.logging.log4j;

    opens copilot12354.mpp.java to javafx.fxml;
    exports copilot12354.mpp.java;
    exports copilot12354.mpp.java.GUI;
    opens copilot12354.mpp.java.GUI to javafx.fxml;
}