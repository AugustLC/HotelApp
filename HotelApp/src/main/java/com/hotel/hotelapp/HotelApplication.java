package com.hotel.hotelapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HotelApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // открытие формы авторизации
        FXMLLoader fxmlLoader = new FXMLLoader(HotelApplication.class.getResource("authorization-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 250, 180);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}