module com.app.tcrs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;

    opens com.app.tcrs to javafx.fxml;
    exports com.app.tcrs;
    exports com.app.tcrs.model;
    exports com.app.tcrs.dataio;
    exports com.app.tcrs.database;
}