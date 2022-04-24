package com.aproximador.logic;


import java.sql.*;

public class Connector
{
    private String username;
    private String password;
    private String query;
    private final Connection connection;
    private Statement statement;

    private final String url = "jdbc:mysql://52.53.177.118/test_connection";



    public Connector(String username, String password){
        this.username = username;
        this.password = password;

        try {
            connection = DriverManager.getConnection(url, this.username, this.password);
            statement = connection.createStatement();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public String showDataFromTable(String tableName){

        StringBuilder sb = new StringBuilder();
        int index = 1;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            while (resultSet.next()){

                for (int i = 1; i < 3; i++){
                    sb.append(resultSet.getString(i)).append(": ");
                }
                //index++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

}
