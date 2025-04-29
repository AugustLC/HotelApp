package com.hotel.tables;

import com.hotel.database.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;

// менеджер таблиц
public class TableManager {

    // добавление колонки в TableView
    private static <T> void addTableColumn(T objectExample, TableView tableView, String displayName, String fieldName)
    {
        TableColumn<T, String> nameColumn = new TableColumn<T, String>(displayName);
        nameColumn.setCellValueFactory(new PropertyValueFactory<T, String>(fieldName));
        tableView.getColumns().add(nameColumn);
    }

    // изменение TableView под таблицу класса Room и её заполнение
    private static void changeTableViewToRoomsTable(TableView tableView, ResultSet rs)
    {
        tableView.getColumns().clear();

        Room e = new Room(1, 1, "", 1);
        addTableColumn(e, tableView, "Id", "id");
        addTableColumn(e, tableView, "Номер", "number");
        addTableColumn(e, tableView, "Класс", "type");
        addTableColumn(e, tableView, "Вместимость", "occupancy");

        ObservableList<Room> rooms = FXCollections.observableArrayList();

        try{
            while(rs.next()) {
                rooms.add(new Room(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4)));
            }
            tableView.setItems(rooms);
        }
        catch(Exception ex){
            System.out.println("Result output failed. " + ex);
        }
    }

    // изменение TableView под таблицу класса Guest и её заполнение
    private static void changeTableViewToGuestsTable(TableView tableView, ResultSet rs)
    {
        tableView.getColumns().clear();

        Guest e = new Guest(1, "", "", "", "");
        addTableColumn(e, tableView, "Id", "id");
        addTableColumn(e, tableView, "Фамилия", "surname");
        addTableColumn(e, tableView, "Имя", "name");
        addTableColumn(e, tableView, "Отчество", "patronymic");
        addTableColumn(e, tableView, "Паспорт", "passport");

        ObservableList<Guest> guests = FXCollections.observableArrayList();

        try{
            while(rs.next()) {
                guests.add(new Guest(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            tableView.setItems(guests);
        }
        catch(Exception ex){
            System.out.println("Result output failed. " + ex);
        }
    }

    // изменение TableView под таблицу класса GuestRoomAssignments и её заполнение
    private static void changeTableViewToGuestRoomAssignmentsTable(TableView tableView, ResultSet rs)
    {
        tableView.getColumns().clear();

        GuestRoomAssignments e = new GuestRoomAssignments(1, 1, "", "", "", "", "", "");
        addTableColumn(e, tableView, "Id", "id");
        addTableColumn(e, tableView, "Номер", "roomNumber");
        addTableColumn(e, tableView, "Класс", "roomType");
        addTableColumn(e, tableView, "Фамилия", "surname");
        addTableColumn(e, tableView, "Имя", "name");
        addTableColumn(e, tableView, "Отчество", "patronymic");
        addTableColumn(e, tableView, "Дата въезда", "dateStart");
        addTableColumn(e, tableView, "Дата выезда", "dateFinish");

        ObservableList<GuestRoomAssignments> rooms = FXCollections.observableArrayList();

        try{
            while(rs.next()) {
                rooms.add(new GuestRoomAssignments(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7).toString(), rs.getDate(8).toString()));
            }
            tableView.setItems(rooms);
        }
        catch(Exception ex){
            System.out.println("Result output failed. " + ex);
        }
    }

    // отобразить всех постояльцев
    public static void showAllGuest(TableView tableView)
    {
        changeTableViewToGuestsTable(tableView, DBManager.showAllGuest());
    }

    // отобразить все номера
    public static void showAllRooms(TableView tableView)
    {
        changeTableViewToRoomsTable(tableView, DBManager.showAllRooms());
    }

    // отобразить все свободные номера
    public static void showFreeRooms(TableView tableView)
    {
        changeTableViewToRoomsTable(tableView, DBManager.showFreeRooms());
    }

    // отобразить все занятые номера
    public static void showOccupiedRooms(TableView tableView)
    {
        changeTableViewToGuestRoomAssignmentsTable(tableView, DBManager.showOccupiedRooms());
    }

    // отобразить все свободные номера, попадающие под критерии
    public static void showSearchFreeRooms(TableView tableView, String type, String occupancy)
    {
        changeTableViewToRoomsTable(tableView, DBManager.showSearchFreeRooms(type, occupancy));
    }

    // отобразить все свободные номера в качестве альтернативы,
    // если свободных, попадающих под критерии, не нашлось
    public static void showProposingRoom(TableView tableView,  String type, int occupancy)
    {
        changeTableViewToRoomsTable(tableView, DBManager.showProposingRoom(type, occupancy));
    }
}
