module com.aproximador {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.aproximador.app to javafx.fxml;
    opens com.aproximador.logic to javafx.fxml;
    exports com.aproximador.app;
}