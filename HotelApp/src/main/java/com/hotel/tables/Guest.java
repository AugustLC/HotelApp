package com.hotel.tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// класс таблицы постояльцев
public class Guest {
    private SimpleIntegerProperty id;
    private SimpleStringProperty surname;
    private SimpleStringProperty name;
    private SimpleStringProperty patronymic;
    private SimpleStringProperty passport;

    Guest(int id, String surname, String name, String patronymic, String passport){
        this.id = new SimpleIntegerProperty(id);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.passport = new SimpleStringProperty(passport);
    }

    public int getId(){ return id.get();}

    public String getSurname(){ return surname.get();}

    public String getName(){ return name.get();}

    public String getPatronymic(){ return patronymic.get();}

    public String getPassport(){ return passport.get();}
}
