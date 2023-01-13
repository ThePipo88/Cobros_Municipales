module org.una.municipalidad {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires com.jfoenix;
    requires lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires jasperreports;
    requires java.desktop;
    requires poi;
    requires poi.ooxml;
    requires java.sql;


    opens org.una.municipalidad.controller to javafx.fxml;
    exports org.una.municipalidad;
    exports org.una.municipalidad.controller;
    exports org.una.municipalidad.dto to com.fasterxml.jackson.databind;
    opens org.una.municipalidad.data to javafx.base;
}
