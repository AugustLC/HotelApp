package com.hotel.database;

import javafx.scene.control.TableView;

import java.sql.ResultSet;
import java.time.LocalDate;

// класс для работы с базой данных
public class DBManager {
    public static void vacateNumber(TableView tableView, int id)
    {
        DBAccess.executePreparedQuery("DELETE FROM GuestRoomAssignments " +
                "WHERE id=" + id + ";");
    }

    public static ResultSet addGuest(String surname, String name, String patronymic, String passport)
    {
        DBAccess.executePreparedQuery("INSERT INTO `Guest` (`surname`, `name`, `patronymic`, `passport`) " +
                "VALUES ('" + surname + "', '" + name + "', '" + patronymic + "', '" + passport + "');");
        DBAccess.executeQuery("SELECT id FROM Guest " +
                "WHERE passport = '" + passport + "';");
        return DBAccess.getResultSet();
    }

    public static void addAssignment(int id_guest, int id_room, String date_start, String date_finish)
    {
        DBAccess.executePreparedQuery("INSERT INTO `GuestRoomAssignments` (`id_room`, `id_guest`, `date_start`, `date_finish`) " +
                "VALUES ('" + id_guest + "', '" + id_room + "', '" + date_start + "', '" + date_finish + "');");
    }

    public static ResultSet getNowVacateRoom()
    {
        DBAccess.executeQuery("SELECT Room.id, Room.number, GuestRoomAssignments.date_finish " +
                "FROM Room " +
                "INNER JOIN GuestRoomAssignments ON Room.id = GuestRoomAssignments.id_room " +
                "WHERE GuestRoomAssignments.date_finish = '" + LocalDate.now().toString() + "';");
        return DBAccess.getResultSet();
    }

    public static ResultSet getTomorrowVacateRoom()
    {
        DBAccess.executeQuery("SELECT Room.id, Room.number, GuestRoomAssignments.date_finish " +
                "FROM Room " +
                "INNER JOIN GuestRoomAssignments ON Room.id = GuestRoomAssignments.id_room " +
                "WHERE GuestRoomAssignments.date_finish = '" + LocalDate.now().plusDays(1).toString() + "';");
        return DBAccess.getResultSet();
    }

    public static ResultSet showAllGuest()
    {
        DBAccess.executeQuery("SELECT * FROM Guest");
        return DBAccess.getResultSet();
    }

    public static ResultSet showAllRooms()
    {
        DBAccess.executeQuery("SELECT * FROM Room");
        return DBAccess.getResultSet();
    }

    public static ResultSet showFreeRooms()
    {
        DBAccess.executeQuery("SELECT * FROM Room " +
                "LEFT OUTER JOIN GuestRoomAssignments ON Room.id = GuestRoomAssignments.id_room " +
                "WHERE GuestRoomAssignments.id_room IS null;");
        return DBAccess.getResultSet();
    }

    public static ResultSet showOccupiedRooms()
    {
        DBAccess.executeQuery("SELECT GuestRoomAssignments.id, Room.number, Room.type, Guest.surname, Guest.name, Guest.patronymic, GuestRoomAssignments.date_start, GuestRoomAssignments.date_finish " +
                "FROM Room " +
                "INNER JOIN GuestRoomAssignments ON Room.id = GuestRoomAssignments.id_room " +
                "INNER JOIN Guest ON GuestRoomAssignments.id_guest = Guest.id;");
        return DBAccess.getResultSet();
    }

    public static ResultSet showSearchFreeRooms(String type, String occupancy)
    {
        DBAccess.executeQuery("SELECT * FROM Room " +
                "LEFT OUTER JOIN GuestRoomAssignments ON Room.id = GuestRoomAssignments.id_room " +
                "WHERE GuestRoomAssignments.id_room IS null " +
                "AND Room.type = '" + type + "' " +
                "AND Room.occupancy = " + occupancy + ";");
        return DBAccess.getResultSet();
    }

    public static ResultSet showProposingRoom(String type, int occupancy)
    {
        String type2 = type;
        switch (type)
        {
            case "Эконом":
                type2 = "Стандарт";
                break;
            case "Стандарт":
                type2 = "Эконом";
                break;
            case "Делюкс":
                type2 = "Люкс";
                break;
            case "Люкс":
                type2 = "Делюкс";
                break;
        }
        DBAccess.executeQuery("SELECT * FROM Room " +
                "LEFT OUTER JOIN GuestRoomAssignments ON Room.id = GuestRoomAssignments.id_room " +
                "WHERE GuestRoomAssignments.id_room IS null " +
                "AND (occupancy = " + (occupancy+1) + " OR occupancy = " + occupancy + " OR occupancy = " + (occupancy-1) + ") " +
                "AND (type = '" + type + "' OR type = '" + type2 + "');");
        return DBAccess.getResultSet();
    }

    public static ResultSet getAuthData(String login)
    {
        DBAccess.executeQuery("SELECT password FROM `auth_data` WHERE login = '" + login + "';");
        return DBAccess.getResultSet();
    }
}
