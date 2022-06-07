package com.asare.data;


import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Connector
{
    private String username;
    private String password;
    private String query;
    private final Connection connection;
    private Statement statement;

    private final String url = "jdbc:mysql://52.53.177.118/aproximador";
    private static ResultSet rs;



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

    public boolean validateUser(String email, String password) throws SQLException {
        rs = statement.executeQuery("SELECT * FROM users WHERE email ='" + email + "' AND  password = '" + password +"'");

        return rs.next();
    }

    public List<Materials> getUserMaterials(String userEmail) throws SQLException {
        List<Materials> obtainedMaterials = new LinkedList<>();

        rs = statement.executeQuery("SELECT m.name, m.unitCost, m.description, m.amount FROM users JOIN aproximations a on a.idAprox = users.idAprox JOIN materials m on m.idMaterial = a.idMaterial WHERE users.name = '" + userEmail + "'");

        while (rs.next()){
            obtainedMaterials.add(new Materials(rs.getString("name"),
                    rs.getBigDecimal("unitCost"), rs.getString("description"), Integer.parseInt(rs.getString("amount"))));
        }

        return obtainedMaterials;
    }

    public List<Services> getUserServices(String userEmail) throws SQLException {
        List<Services> obtainedMaterials = new LinkedList<>();

        rs = statement.executeQuery("SELECT s.name, s.unitCost, s.description, s.amount FROM users JOIN aproximations a on a.idAprox = users.idAprox JOIN services s on s.idService = a.idService WHERE users.name = '" + userEmail + "'");

        while (rs.next()){
            obtainedMaterials.add(new Services(rs.getString("name"),
                    rs.getBigDecimal("unitCost"), rs.getString("description"), Integer.parseInt(rs.getString("amount"))));
        }

        return obtainedMaterials;
    }

    public List<Aproximation> getUserAproximations(String userEmail) throws SQLException {
        List<Aproximation> aproximations = new LinkedList<>();

        rs = statement.executeQuery("SELECT a.name ,a.totalCost, a.numberMaterials, a.numberServices ,a.description, a.date FROM users u JOIN aproximations a on users.idAprox = a.idAprox WHERE u.email = '" + userEmail + "'");


        while (rs.next()){
            aproximations.add(new Aproximation(rs.getString("name"),
                    rs.getBigDecimal("totalCost"), rs.getInt("numberMaterials"), rs.getInt("numberServices"),
                    LocalDateTime.parse(rs.getString("date"))));
        }


        return aproximations;
    }

    public String showDataFromTable(String tableName){

        StringBuilder sb = new StringBuilder();
        int index = 1;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE company = 'Cum Company'" +"  LIMIT 3;");

            while (resultSet.next()){

                for (int i = 1; i < 2; i++){
                    sb.append(resultSet.getString(i)).append("\n");
                }
                //index++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public String makeQuery(String query){
        StringBuilder sb = new StringBuilder();

        try{
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                for (int i = 1; i < 2; i++){
                    sb.append(resultSet.getString(i)).append("\n");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return sb.toString();
    }


}
