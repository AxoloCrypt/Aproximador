module com.asare {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.asare.app to javafx.fxml;
    opens com.asare.controllers to javafx.fxml;
    exports com.asare.app;
}