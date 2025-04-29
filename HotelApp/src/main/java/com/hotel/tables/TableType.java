package com.hotel.tables;

// текущий используемый класс таблицы
public enum TableType {
    Rooms ("Номера"),
    Guests ("Постояльцы"),
    GuestRoomAssignments ("Номера-Постояльцы");

    private String name;

    TableType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
