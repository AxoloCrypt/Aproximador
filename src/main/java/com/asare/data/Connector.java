package com.asare.data;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Connector
{
    private String username;
    private String password;
    private final Connection connection;
    private Statement statement;

    private final String url = "jdbc:mysql://52.53.177.118/aproximador";
    private static ResultSet rs;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



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

        rs = statement.executeQuery("SELECT m.name, m.unitCost, m.description, m.amount FROM users JOIN aproximations a on a.idAprox = users.idAprox JOIN materials m on m.idMaterial = a.idMaterial WHERE users.email = '" + userEmail + "'");

        while (rs.next()){
            obtainedMaterials.add(new Materials(rs.getString("name"),
                    rs.getBigDecimal("unitCost"), rs.getString("description"), Integer.parseInt(rs.getString("amount"))));
        }

        return obtainedMaterials;
    }

    public List<Services> getUserServices(String userEmail) throws SQLException {
        List<Services> obtainedMaterials = new LinkedList<>();

        rs = statement.executeQuery("SELECT s.name, s.unitCost, s.description, s.amount FROM users JOIN aproximations a on a.idAprox = users.idAprox JOIN services s on s.idService = a.idService WHERE users.email = '" + userEmail + "'");

        while (rs.next()){
            obtainedMaterials.add(new Services(rs.getString("name"),
                    rs.getBigDecimal("unitCost"), rs.getString("description"), Integer.parseInt(rs.getString("amount"))));
        }

        return obtainedMaterials;
    }

    public List<Aproximation> getUserAproximations(String userEmail) throws SQLException {
        List<Aproximation> aproximations = new LinkedList<>();

        rs = statement.executeQuery("SELECT a.name ,a.totalCost, a.numberMaterials, a.numberServices , a.date FROM users u JOIN aproximations a on u.idAprox = a.idAprox WHERE u.email = '" + userEmail + "'");


        while (rs.next()){
            aproximations.add(new Aproximation(rs.getString("name"),
                    rs.getBigDecimal("totalCost"), rs.getInt("numberMaterials"), rs.getInt("numberServices"),
                    LocalDateTime.parse(rs.getString("date"), formatter)));
        }

        rs = statement.executeQuery("SELECT m.name, m.unitCost, m.description, m.amount FROM materials m JOIN aproximations a on m.idMaterial = a.idMaterial JOIN users u on a.idAprox = u.idAprox WHERE email = '" + userEmail + "'");


        for (int i = 0; rs.next(); i++){
            aproximations.get(i).getRecords().add(new Materials(rs.getString("name"), rs.getBigDecimal("unitCost"), rs.getString("description"), rs.getInt("amount")));
        }

        rs = statement.executeQuery("SELECT s.* FROM services s JOIN aproximations a on s.idService = a.idService JOIN users u on a.idAprox = u.idAprox WHERE email = '" + userEmail + "'");

        for (int i = 0; rs.next(); i++){
            aproximations.get(i).getRecords().add(new Services(rs.getString("name"), rs.getBigDecimal("unitCost"), rs.getString("description"), rs.getInt("amount")));
        }

        return aproximations;
    }

    public boolean saveAproximation(Aproximation aproximation) throws SQLException {

        int succed = 0;

        for(Record<?> record: aproximation.getRecords()){

            if(record instanceof Materials){

                statement.executeUpdate("INSERT INTO materials m (m.name, m.unitCost, m.description, m.amount) VALUE ('" + record.getName() + "', '" + record.getUnitCost()
                        + "', '" + record.getDescription() + "'," + record.getAmount() + ")");

            }
            else if (record instanceof Services){

                succed = statement.executeUpdate("INSERT INTO services s (s.name, s.unitCost, s.description, s.amount) VALUE ('" + record.getName() + "', '" + record.getUnitCost()
                        + "', '" + record.getDescription() + "'," + record.getAmount() + ")");
            }

        }

        return succed == 1;
    }



}
