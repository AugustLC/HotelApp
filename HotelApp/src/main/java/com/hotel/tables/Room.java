package com.hotel.tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// класс таблицы номеров
public class Room {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty number;
    private SimpleStringProperty type;
    private SimpleIntegerProperty occupancy;

    Room(int id, int number, String type, int occupancy){
        this.id = new SimpleIntegerProperty(id);
        this.number = new SimpleIntegerProperty(number);
        this.type = new SimpleStringProperty(type);
        this.occupancy = new SimpleIntegerProperty(occupancy);
    }

    public int getId(){ return id.get();}

    public int getNumber(){ return number.get();}

    public String getType(){ return type.get();}

    public int getOccupancy(){ return occupancy.get();}
}

