module com.hotel.hotelapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.hotel.hotelapp to javafx.fxml;
    exports com.hotel.hotelapp;
    exports com.hotel.tables;
    opens com.hotel.tables to javafx.fxml;
    exports com.hotel.database;
    opens com.hotel.database to javafx.fxml;
}