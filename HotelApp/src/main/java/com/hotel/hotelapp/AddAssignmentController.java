package com.hotel.hotelapp;

import com.hotel.database.DBManager;
import com.hotel.tables.Guest;
import com.hotel.tables.Room;
import com.hotel.tables.RoomClass;
import com.hotel.tables.TableManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class AddAssignmentController implements Initializable {

    private static Room roomTemp = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // настройка элементов формы
        rbG = new ToggleGroup();
        rb_1.setToggleGroup(rbG);
        rb_2.setToggleGroup(rbG);

        rb_1.setSelected(true);

        tv_guests.setDisable(true);

        // заполнение таблиц
        TableManager.showAllGuest(tv_guests);
        TableManager.showFreeRooms(tv_rooms);
    }

    @FXML
    private RadioButton rb_1;

    @FXML
    private RadioButton rb_2;

    @FXML
    private ToggleGroup rbG;

    @FXML
    private TableView tv_guests;

    @FXML
    private TableView tv_rooms;

    @FXML
    private TextField tf_1;

    @FXML
    private TextField tf_2;

    @FXML
    private TextField tf_3;

    @FXML
    private TextField tf_4;

    @FXML
    private Label lbl;

    @FXML
    private DatePicker dp_start;

    @FXML
    private DatePicker dp_finish;

    @FXML
    private Button buttonRegistration;

    // выбор RadioButton для добавления постояльца
    @FXML
    protected void onClickedRB_1() {
        tv_guests.setDisable(true);
        tf_1.setDisable(false);
        tf_2.setDisable(false);
        tf_3.setDisable(false);
        tf_4.setDisable(false);
    }

    // выбор RadioButton для выбора постояльца из таблицы
    @FXML
    protected void onClickedRB_2() {
        tv_guests.setDisable(false);
        tf_1.setDisable(true);
        tf_2.setDisable(true);
        tf_3.setDisable(true);
        tf_4.setDisable(true);
    }

    // расчёт стоимости проживания по времени и стоимости номера
    @FXML
    protected void selectDate() {
        // проверка на выбор всех элементов для расчёта
        if(dp_start.getValue() == null ||
                dp_finish.getValue() == null ||
                tv_rooms.getSelectionModel().getSelectedItem() == null)
            return;

        // проверка на корректную дату
        if(dp_start.getValue().until(dp_finish.getValue(), ChronoUnit.DAYS) <= 0 ||
                LocalDate.now().until(dp_start.getValue(), ChronoUnit.DAYS) < 0)
        {
            lbl.setText("Некорректная дата.");
            return;
        }

        // расчёт стоимости проживания и вывод его на форму
        roomTemp = (Room)tv_rooms.getSelectionModel().getSelectedItem();
        double price = 0;
        for (RoomClass class_temp : RoomClass.values())
            if(roomTemp.getType().equals(class_temp.getName()))
                price = class_temp.getPrice();
        lbl.setText("Цена: " +
                price * dp_start.getValue().until(dp_finish.getValue(), ChronoUnit.DAYS));
    }

    // оформление
    @FXML
    protected void registration() {
        // проверка на выбор всех необходимых элементов
        if(dp_start.getValue() == null ||
                dp_finish.getValue() == null ||
                tv_rooms.getSelectionModel().getSelectedItem() == null ||
                (rb_2.isSelected() && tv_guests.getSelectionModel().getSelectedItem() == null)) {
            lbl.setText("Не все поля выбраны/заполнены");
            return;
        }

        // проверка на корректную дату
        if(dp_start.getValue().until(dp_finish.getValue(), ChronoUnit.DAYS) <= 0 ||
                LocalDate.now().until(dp_start.getValue(), ChronoUnit.DAYS) < 0)
        {
            lbl.setText("Некорректная дата.");
            return;
        }

        // вывод сообщения
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information MessageBox");
        alert.setContentText("Добавлено!");
        alert.show();

        int id = 0;

        // добавление нового постояльца,
        // если выбран первый RadioButton
        if(rb_1.isSelected())
        {
            try {
                ResultSet rs = DBManager.addGuest(tf_1.getText(), tf_2.getText(), tf_3.getText(), tf_4.getText());
                rs.next();
                id = rs.getInt(1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // иначе выбор постояльца по его id из таблицы
        else
        {
            Guest guestTemp = (Guest)tv_guests.getSelectionModel().getSelectedItem();
            id = guestTemp.getId();
        }

        // добавление информации о заселении
        DBManager.addAssignment(roomTemp.getId(), id, dp_start.getValue().toString(), dp_finish.getValue().toString());

        // закрытие формы регистрации
        Stage stage = (Stage)this.buttonRegistration.getScene().getWindow();
        stage.close();
    }
}
