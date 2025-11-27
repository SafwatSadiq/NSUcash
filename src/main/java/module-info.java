module com.mindreader007.nsucash {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires javafx.graphics;
    requires java.sql;

    opens com.mindreader007.nsucash to javafx.fxml;
    opens com.mindreader007.nsucash.controller to javafx.fxml;
    exports com.mindreader007.nsucash;
}