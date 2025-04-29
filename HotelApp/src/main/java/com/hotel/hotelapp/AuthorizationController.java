package com.hotel.hotelapp;

import com.hotel.database.DBManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationController {
    @FXML
    private TextField tf_login;

    @FXML
    private TextField tf_password;

    @FXML
    private void authorization()
    {
        String login = tf_login.getText();
        String password = tf_password.getText();

        // проверка логина и пароля на наличие недопустимых символов
        Pattern pattern = Pattern.compile("[~#@*+%{}<>;,/\\[\\]|\"\'\\_^]");
        Matcher matcher1 = pattern.matcher(login);
        Matcher matcher2 = pattern.matcher(password);

        if (matcher1.find() || matcher2.find())
        {
            showAuthError("Недопустимые символы в логине или пароле");
            return;
        }

        // взятие пароля по логину из базы данных
        ResultSet rs = DBManager.getAuthData(login);

        try {
            // проверка на наличие введенного логина
            if (rs.next()) {
                // проверка пароля на несоответствие с паролем из базы данных
                if (!rs.getString(1).equals(password))
                {
                    showAuthError("Неверный логин или пароль");
                    return;
                }
                // если соответствует
                else
                {
                    try {
                        // открытие главной формы приложения
                        FXMLLoader fxmlLoader = new FXMLLoader(HotelApplication.class.getResource("hotel-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                        Stage stage = new Stage();
                        stage.setTitle("Отель");
                        stage.setScene(scene);
                        stage.show();

                        // закрытие формы авторизации
                        Stage stExit = (Stage) this.tf_login.getScene().getWindow();
                        stExit.close();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else
            {
                showAuthError("Неверный логин или пароль");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAuthError(String error)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information MessageBox");
        alert.setContentText(error);
        alert.show();
    }
}
