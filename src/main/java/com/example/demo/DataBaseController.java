package com.example.demo;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseController {
    private static String PASSWORD = "3361";
    private static String USER = "postgres";
    private static String URL = "jdbc:postgresql://localhost:5432/dots_php";



    public String getDots(int count,int leftLimit,int rightLimit) {

        ArrayList<int[]> list = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String SQLcommand = "SELECT * FROM dots \n" +
                "            WHERE y <= sin(x/120)*20 + 600 AND y >= sin(x/100)*50 + 200 AND x >= " + leftLimit + " AND x <= " + rightLimit + "\n" +
                "            LIMIT 100 OFFSET " + count*100;

        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(SQLcommand);
            while (result.next()) {
                int[] xy = {result.getInt("x"), result.getInt("y")};
                list.add(xy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String jsonlist = new Gson().toJson(list);


        return jsonlist;
    }
}
