package com.hotel.database;

import java.sql.*;

// класс для подключения к базе данных и запросов к ней
public class DBAccess {
    // данные для подключения
    private static String user_name = "root";
    private static String password = "";
    private static String url = "mysql://localhost/hotel";

    // объекты подключения базы данных
    private static Statement stmt = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;
    private static Connection conn;

    static
    {
        try{
            //подключение к базе данных
            conn = DriverManager.getConnection("jdbc:" + url + "?" +
                            "user='" + user_name + "'&password=" + password);

            stmt = conn.createStatement();
        }
        catch(Exception ex){
            System.out.println("Не удалось подключиться к базе данных.");
            System.out.println(ex);
        }
    }

    // запрос к базе данных
    public static boolean executeQuery(String query)
    {
        try{
            rs = stmt.executeQuery(query);
            return true;
        }
        catch(Exception ex){
            System.out.println("Query failed.");
            System.out.println(ex);
            return false;
        }
    }

    // запрос на изменение данных в таблице базы данных
    public static boolean executePreparedQuery(String query)
    {
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println("Query failed.");
            System.out.println(ex);
            return false;
        }
    }

    // получение результата запроса
    public static ResultSet getResultSet()
    {
        return rs;
    }
}
