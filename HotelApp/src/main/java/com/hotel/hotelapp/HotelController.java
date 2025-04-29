package com.hotel.hotelapp;

import com.hotel.database.DBManager;
import com.hotel.tables.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HotelController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // заполнение элементов ComboBox главной формы интерфейса
        ObservableList<String> classes_temp = FXCollections.observableArrayList();
        for (RoomClass class_temp : RoomClass.values()) classes_temp.add(class_temp.getName());
        cb_classes.setItems(classes_temp);
        cb_classes.setValue(RoomClass.values()[0]);

        ObservableList<String> occupancy_temp = FXCollections.observableArrayList();
        for(int i=1; i<=5; i++) occupancy_temp.add(Integer.toString(i));
        cb_occupancy.setItems(occupancy_temp);
        cb_occupancy.setValue("1");

        // начальное заполнение таблицы данными обо всех номерах
        TableManager.showAllRooms(tableHotel);

        // вывод напоминаний для пользователя
        getStatusRooms();
    }

    // текущий используемый класс таблицы
    private static TableType tableTypeTemp;

    @FXML
    private ComboBox cb_classes;

    @FXML
    private ComboBox cb_occupancy;

    @FXML
    private TextArea ta_console;

    @FXML
    private TableView tableHotel;

    // оформление заселения в номер
    @FXML
    protected void registerRoom() {
        try
        {
            // открытие формы заселения
            FXMLLoader fxmlLoader = new FXMLLoader(HotelApplication.class.getResource("add-assignment-view.fxml"));
            Scene scene1 = new Scene(fxmlLoader.load(), 400, 320);
            Stage stage = new Stage();
            stage.setTitle("Оформление заселения");
            stage.setScene(scene1);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    // освобождение номера
    @FXML
    protected void vacateRoom() {
        // проверка на класс таблицы
        if(tableTypeTemp == TableType.GuestRoomAssignments &&
                tableHotel.getSelectionModel().getSelectedItem() != null)
        {
            // освобождение номера по его id в таблице
            GuestRoomAssignments r = (GuestRoomAssignments)tableHotel.getSelectionModel().getSelectedItem();
            DBManager.vacateNumber(tableHotel, r.getId());
            tableTypeTemp = TableType.GuestRoomAssignments;
            ta_console.setText("Комната с id=" + r.getId() + " освобождена");
            TableManager.showOccupiedRooms(tableHotel);
        }
        else
        {
            // вывод в консоли подсказки пользователю
            if(tableHotel.getSelectionModel().getSelectedItem() == null)
                ta_console.setText("Не выбрана комната");
            if(tableTypeTemp != TableType.GuestRoomAssignments)
                ta_console.setText("Не выбрана таблица 'Занятые'");
        }
    }

    // вывод в консоли напоминаний пользователю
    // о номерах, которые должны быть освобождены сегодня или завтра
    @FXML
    protected void getStatusRooms() {
        ResultSet rs = DBManager.getNowVacateRoom();
        String s1 = "";
        int i = 0;

        s1 += "Эти номера сегодня должны быть освобождены:\n";
        try {
            while (rs.next()) {
                s1 += ++i + ": id" + rs.getInt(1) + ", Номер " + rs.getInt(2) + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        s1 += "Пожалуйста, проверьте, выехали ли постояльцы.\n\n";

        s1 = i == 0 ? "" : s1;
        i = 0;

        String s2 = "";
        rs = DBManager.getTomorrowVacateRoom();
        s2 += "Постояльцы этих номеров завтра выезжают:\n";
        try {
            while (rs.next()) {
                s2 += ++i + ": id" + rs.getInt(1) + ": Номер " + rs.getInt(2) + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        s2 += "Пожалуйста, напомните им о завтрашнем выезде.\n";

        s1 = i == 0 ? s1 : s1 + s2;

        ta_console.setText(s1);
    }

    // вывод всех постояльцев в таблице
    @FXML
    protected void getAllGuests() {
        TableManager.showAllGuest(tableHotel);
        tableTypeTemp = TableType.Guests;
    }

    // вывод всех номеров в таблице
    @FXML
    protected void getAllRooms() {
        TableManager.showAllRooms(tableHotel);
        tableTypeTemp = TableType.Rooms;
    }

    // поиск свободных номеров по критериям
    @FXML
    protected void searchFreeRooms() {
        TableManager.showSearchFreeRooms(tableHotel, cb_classes.getValue().toString(), cb_occupancy.getValue().toString());
        tableTypeTemp = TableType.Rooms;

        // если по критериям не найдено,
        // то предложение об альтернативных вариантах
        if(tableHotel.getItems().isEmpty())
        {
            ta_console.setText("Номер по запросу не найден.\n");
            TableManager.showProposingRoom(tableHotel, cb_classes.getValue().toString(), Integer.parseInt(cb_occupancy.getValue().toString()));
            if(!tableHotel.getItems().isEmpty())
            {
                ta_console.setText(ta_console.getText() + "В таблице выведены предлагаемые варианты.");
            }

            // отдельно в консоли информация о номерах,
            // которые освободятся завтра
            String s = "";
            int i = 0;
            ResultSet rs = DBManager.getTomorrowVacateRoom();
            s += "Постояльцы этих номеров завтра выезжают:\n";
            try {
                while (rs.next()) {
                    s += ++i + ": id" + rs.getInt(1) + ": Номер " + rs.getInt(2) + "\n";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // вывод свободных номеров
    @FXML
    protected void getFreeRooms() {
        TableManager.showFreeRooms(tableHotel);
        tableTypeTemp = TableType.Rooms;
    }

    // вывод занятых номеров
    @FXML
    protected void getOccupiedRooms() {
        TableManager.showOccupiedRooms(tableHotel);
        tableTypeTemp = TableType.GuestRoomAssignments;
    }
}