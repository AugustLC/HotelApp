package com.hotel.tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// класс таблицы соединенных таблиц Guest и занятых из Room
public class GuestRoomAssignments {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty roomNumber;
    private SimpleStringProperty roomType;
    private SimpleStringProperty surname;
    private SimpleStringProperty name;
    private SimpleStringProperty patronymic;
    private SimpleStringProperty dateStart;
    private SimpleStringProperty dateFinish;

    GuestRoomAssignments(int id, int roomNumber, String roomType, String surname, String name, String patronymic, String dateStart, String dateFinish){
        this.id = new SimpleIntegerProperty(id);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.roomType = new SimpleStringProperty(roomType);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.dateStart = new SimpleStringProperty(dateStart);
        this.dateFinish = new SimpleStringProperty(dateFinish);
    }

    public int getId(){ return id.get();}

    public int getRoomNumber(){ return roomNumber.get();}

    public String getRoomType(){ return roomType.get();}

    public String getSurname(){ return surname.get();}

    public String getName(){ return name.get();}

    public String getPatronymic(){ return patronymic.get();}

    public String getDateStart(){ return dateStart.get();}

    public String getDateFinish(){ return dateFinish.get();}
}
