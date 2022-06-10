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
    private static final List<Integer> materialsIds = new LinkedList<>();
    private static final List<Integer> servicesIds = new LinkedList<>();



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

    public boolean saveAproximation(Aproximation aproximation, User user) {

        if(!materialsIds.isEmpty())
            materialsIds.clear();
        if(!servicesIds.isEmpty())
            servicesIds.clear();

       try{
           rs = statement.executeQuery("SELECT COUNT(1) FROM materials"); // get n rows of the table materials
           rs.next();
           int rowsMaterials = rs.getInt(1);

           System.out.println(rowsMaterials);

           rs = statement.executeQuery("SELECT  COUNT(1) FROM services"); // get n roms of the table services
           rs.next();
           int rowsServices = rs.getInt(1);

           System.out.println(rowsServices);


           for(Record<?> record: aproximation.getRecords()){

               if(record instanceof Materials){

                   statement.executeUpdate("INSERT INTO materials (name, unitCost, description, amount) VALUES ('" + record.getName() + "', " + record.getUnitCost()
                           + ", '" + record.getDescription() + "'," + record.getAmount() + ")");
                   rowsMaterials += 1;
                   materialsIds.add(rowsMaterials);

               }
               else if (record instanceof Services){

                   statement.executeUpdate("INSERT INTO services (name, unitCost, description, amount) VALUES ('" + record.getName() + "', '" + record.getUnitCost()
                           + "', '" + record.getDescription() + "'," + record.getAmount() + ")");
                   rowsServices += 1;
                   servicesIds.add(rowsServices);
               }

           }

           int maxRows = Math.max(materialsIds.size(), servicesIds.size());
           int minRows = Math.min(materialsIds.size(), servicesIds.size());

           System.out.println(maxRows);
           System.out.println(minRows);

           boolean isMaterial = maxRows == materialsIds.size();

           for(int i = 0; i < maxRows; i++){

               System.out.println(i);

               if (isMaterial){
                   statement.executeUpdate("INSERT INTO aproximations (name, totalCost, numberMaterials, numberServices, description, date, idMaterial) VALUES ('" +
                           aproximation.getName() +"', '" + aproximation.getTotalCost() + "'," + aproximation.getNumberMaterials() + ", "
                           + aproximation.getNumberServices() +", 'err', '"+aproximation.getDateCreation() + "'," + materialsIds.get(i)+ ")");
               }
               else
               {
                   statement.executeUpdate("INSERT INTO aproximations (name, totalCost, numberMaterials, numberServices, description, date, idService) VALUES ('" +
                           aproximation.getName() +"', '" + aproximation.getTotalCost() + "'," + aproximation.getNumberMaterials() + ", "
                           + aproximation.getNumberServices() +", 'err', '"+aproximation.getDateCreation() + "'," + servicesIds.get(i)+ ")");
               }
           }

           for (int i = 0; i < minRows; i++){

               if(isMaterial){

                   statement.executeUpdate("UPDATE aproximations SET idService = " + servicesIds.get(i) + " WHERE idService IS NULL AND idMaterial = " + materialsIds.get(i));
               }
               else {
                   statement.executeUpdate("UPDATE aproximations SET idMaterial = " + materialsIds.get(i) + " WHERE idMaterial IS NULL AND idService = " + servicesIds.get(i));
               }

           }



           rs = statement.executeQuery("SELECT COUNT(1) FROM aproximations"); // get n rows of the table aproximations
           rs.next();
           int rowsAproximations = rs.getInt(1);

          // rowsAproximations += 1;

           statement.executeUpdate("INSERT INTO users(name, lastname, company, email, password, idAprox) VALUES ('" +
                   user.getName() + "', '" + user.getLastname() + "', '" + user.getCompany() + "', '" + user.getEmail() +
                   "', '" + user.getPassword() + "', " + (rowsAproximations - (maxRows - 1)) + ")");

       }catch(SQLException throwables){
           throwables.printStackTrace();
           return false;
       }

        return true;
    }
}
