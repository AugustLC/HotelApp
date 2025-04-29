package com.hotel.tables;

// классы номеров, их названия и цена за день пребывания
public enum RoomClass {
    Suite ("Люкс", 1000),
    Deluxe ("Делюкс", 700),
    Standart ("Стандарт", 500),
    Econom ("Эконом", 200);

    private String name;

    private double price;

    RoomClass(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }
}
