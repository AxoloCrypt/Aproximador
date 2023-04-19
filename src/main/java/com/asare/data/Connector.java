package com.asare.data;

import com.asare.encryption.BCrypt;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Connector
{
    private final String username;
    private final String password;
    private Connection connection;
    private Statement statement;

    private final String url;
    private static ResultSet rs;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final List<Integer> materialsIds = new LinkedList<>();
    private static final List<Integer> servicesIds = new LinkedList<>();



    public Connector(){

        url = System.getenv("DB_URL");
        username = System.getenv("DB_USERNAME");
        password = System.getenv("DB_PASSWD");

    }

    /**
     * Connect to db and create a new Statement
     * This was implemented in order to use JDBC connection pool
     */
    public void openConnection(){

        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * Close the current connection and statement
     */
    public void closeConnection(){

       try{
           if(statement != null) statement.close();
           if(connection != null) connection.close();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    /**
     * Uploads new user created to the DB.
     * @param user
     * @throws SQLException
     */
    public void signUpUser(User user) throws SQLException{
        openConnection();
        String encryptPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));

        statement.executeUpdate("INSERT INTO users (name, lastname, company, email, password) VALUES ('" +
               user.getName() +"', '" + user.getLastname() + "', '" + user.getCompany() + "', '" + user.getEmail()
                + "', '" + encryptPassword + "')");
    }

    /**
    @param: String user email, String password
    &#064;returns:  boolean
    Checks if the passed parameters matches with user data in the db.
     */
    public boolean validateUser(String email, String password) throws SQLException {
        openConnection();

        rs = statement.executeQuery("SELECT password FROM users WHERE email ='" + email + "'");
        if(rs.next()){
            return BCrypt.checkpw(password, rs.getString("password"));
        }
        else
            return false;
    }

    public boolean changeUserPassword(String currentPassword, String newPassword, User user) throws SQLException {
        openConnection();

        rs = statement.executeQuery("SELECT password FROM users WHERE email ='" + user.getEmail() + "'");

        if(!rs.next())
            return false;

        if(!BCrypt.checkpw(currentPassword, rs.getString("password")))
            return false;

        String encryptedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));

        int status = statement.executeUpdate(new StringBuilder().append("UPDATE users SET password='")
                 .append(encryptedPassword)
                 .append("' WHERE email='")
                 .append(user.getEmail())
                 .append("'")
                 .toString());

        closeConnection();

        return status != 0;
    }


    /**
     &#064;parameters: String user email
    &#064;returns:  User class object
    Searches for the logged user and returns the user info
     */
    public User getUserData(String userEmail) throws SQLException{
        openConnection();
        rs = statement.executeQuery("SELECT * FROM users WHERE email = '" + userEmail + "'");
        rs.next();

        return new User(rs.getString("name"), rs.getString("lastname"),
                rs.getString("company"), userEmail, rs.getString("password"));
    }

    // Searches user saved materials
    public List<Materials> getUserMaterials(String userEmail) throws SQLException {
        openConnection();
        List<Materials> obtainedMaterials = new LinkedList<>();

        rs = statement.executeQuery(new StringBuilder().append("SELECT m.name, m.unitCost, m.description, m.amount FROM users JOIN approximations as a on a.idAprox = users.idAprox ").append("JOIN materials as  m on m.idMaterial = a.idMaterial WHERE users.email = '").append(userEmail).append("'").toString());

        while (rs.next()){

            Materials tmpMaterial = new Materials(rs.getString("name"),
                    rs.getBigDecimal("unitCost"), rs.getString("description"), Integer.parseInt(rs.getString("amount")));

            obtainedMaterials.add(tmpMaterial);

        }

        return obtainedMaterials;
    }

    /***
     * @return boolean
     * Set the is_deleted column to true in the db information of the passed record to no further usage.
     * Returns true if any row was affected, false if no changes were made.
     */
    public boolean deleteRecord(Record<?> record) throws SQLException{
        openConnection();

        int status;

        if(record instanceof Materials){
            status = statement.executeUpdate(new StringBuilder().append("DELETE FROM materials WHERE name = '").
                    append(record.getName())
                    .append("' AND unitCost = ")
                    .append(record.getUnitCost().toString())
                    .append(" AND description LIKE '")
                    .append(record.getDescription())
                    .append("'").toString());
        }
        else{
            status = statement.executeUpdate(new StringBuilder().append("DELETE FROM services WHERE name = '").append(record.getName()).append("' AND unitCost = ").append(record.getUnitCost().toString()).append(" AND description LIKE '").append(record.getDescription()).append("'").toString());
        }

        return status != 0; //executeUpdate returns 0 in case of no rows affected
    }

    // Searches user saved services
    public List<Services> getUserServices(String userEmail) throws SQLException {
        openConnection();
        List<Services> obtainedServices = new LinkedList<>();

        rs = statement.executeQuery(new StringBuilder().append("SELECT s.name, s.unitCost, s.description, s.amount FROM users JOIN approximations as a on a.idAprox = users.idAprox ").append("JOIN services as s on s.idService = a.idService WHERE users.email = '").append(userEmail).append("'").toString());

        while (rs.next()){

            Services tmpService = new Services(rs.getString("name"),
                    rs.getBigDecimal("unitCost"), rs.getString("description"), Integer.parseInt(rs.getString("amount")));

            obtainedServices.add(tmpService);

        }

        closeConnection();
        return obtainedServices;
    }

    // Searches user saved aproximations and the records
    public List<Aproximation> getUserAproximations(String userEmail) throws SQLException {
        openConnection();
        List<Aproximation> aproximations = new LinkedList<>();

        rs = statement.executeQuery(new StringBuilder().append("SELECT a.idAprox ,a.name ,a.totalCost, a.numberMaterials, a.numberServices , a.date FROM users as u ").append("JOIN approximations as a on u.idAprox = a.idAprox WHERE u.email = '").append(userEmail).append("'").toString());


        while (rs.next()){
            aproximations.add(new Aproximation(rs.getInt("idAprox"), rs.getString("name"),
                    rs.getBigDecimal("totalCost"), rs.getInt("numberMaterials"), rs.getInt("numberServices"),
                    LocalDateTime.parse(rs.getString("date"), formatter)));
        }

        rs = statement.executeQuery(new StringBuilder().append("SELECT m.name, m.unitCost, m.description, m.amount FROM materials as m JOIN approximations as a on m.idMaterial = a.idMaterial ").append("JOIN users as u on a.idAprox = u.idAprox WHERE email = '").append(userEmail).append("'").toString());


        for (int i = 0; rs.next(); i++){
            aproximations.get(i).getRecords().add(new Materials(rs.getString("name"), rs.getBigDecimal("unitCost"), rs.getString("description"), rs.getInt("amount")));
        }

        rs = statement.executeQuery(new StringBuilder().append("SELECT s.* FROM services s JOIN approximations as a on s.idService = a.idService ").append("JOIN users as u on a.idAprox = u.idAprox WHERE email = '").append(userEmail).append("'").toString());

        for (int i = 0; rs.next(); i++){
            aproximations.get(i).getRecords().add(new Services(rs.getString("name"), rs.getBigDecimal("unitCost"), rs.getString("description"), rs.getInt("amount")));
        }

        return aproximations;
    }

    // get total of rows on table aproximations
    public int getAproximationRows(){
        openConnection();
        int nRows = 0;

        try {
            rs = statement.executeQuery("SELECT COUNT(1) FROM approximations");
            rs.next();


            nRows = rs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeConnection();
        }

        return nRows;
    }

    // Saves the aproximation created by the user in db.
    public void saveAproximation(Aproximation aproximation, User user) {
        openConnection();

        if(!materialsIds.isEmpty())
            materialsIds.clear();
        if(!servicesIds.isEmpty())
            servicesIds.clear();

       try{
           rs = statement.executeQuery("SELECT COUNT(1) FROM materials"); // get n rows of the table materials
           rs.next();
           int rowsMaterials = rs.getInt(1);

           rs = statement.executeQuery("SELECT  COUNT(1) FROM services"); // get n roms of the table services
           rs.next();
           int rowsServices = rs.getInt(1);

           for(Record<?> record: aproximation.getRecords()){

               if(record instanceof Materials){

                   statement.executeUpdate(new StringBuilder().append("INSERT INTO materials (name, unitCost, description, amount) VALUES ('").append(record.getName()).append("', ").append(record.getUnitCost()).append(", '").append(record.getDescription()).append("',").append(record.getAmount()).append(")").toString());
                   rowsMaterials += 1;
                   materialsIds.add(rowsMaterials); //saves the ID of added material

               }
               else if (record instanceof Services){

                   statement.executeUpdate(new StringBuilder().append("INSERT INTO services (name, unitCost, description, amount) VALUES ('").append(record.getName()).append("', '").append(record.getUnitCost()).append("', '").append(record.getDescription()).append("',").append(record.getAmount()).append(")").toString());
                   rowsServices += 1;
                   servicesIds.add(rowsServices); // saves the ID of added service
               }

           }

           /*
           Compares if more materials or services are used in the saved aproximation
           It´s used to know how many instances of the aproximation will be in the db and prevent IndexOutOfBounds Exception
            */
           int maxRows = Math.max(materialsIds.size(), servicesIds.size());
           int minRows = Math.min(materialsIds.size(), servicesIds.size());

           boolean isMaterial = maxRows == materialsIds.size();

           for(int i = 0; i < maxRows; i++){

               if (isMaterial){
                   statement.executeUpdate(new StringBuilder()
                           .append("INSERT INTO approximations (name, totalCost, numberMaterials, numberServices, description, date, idMaterial) VALUES ('")
                           .append(aproximation.getName())
                           .append("', '").append(aproximation.getTotalCost())
                           .append("',").append(aproximation.getNumberMaterials())
                           .append(", ").append(aproximation.getNumberServices())
                           .append(", ' ', date_trunc('seconds','")
                           .append(aproximation.getDateCreation())
                           .append("'::timestamp),").append(materialsIds.get(i)).append(")").toString());
               }
               else
               {
                   statement.executeUpdate(new StringBuilder().append("INSERT INTO approximations (name, totalCost, numberMaterials, numberServices, description, date, idService) VALUES ('").append(aproximation.getName()).append("', '").append(aproximation.getTotalCost()).append("',").append(aproximation.getNumberMaterials()).append(", ").append(aproximation.getNumberServices()).append(", 'err', date_trunc('seconds','").append(aproximation.getDateCreation()).append("'::timestamp),").append(servicesIds.get(i)).append(")").toString());
               }
           }

           // Insert in the instances of the aproximation where the reference to a record is null
           for (int i = 0; i < minRows; i++){

               if(isMaterial){

                   statement.executeUpdate(new StringBuilder().append("UPDATE approximations SET idService = ").append(servicesIds.get(i)).append(" WHERE idService IS NULL AND idMaterial = ").append(materialsIds.get(i)).toString());
               }
               else {
                   statement.executeUpdate(new StringBuilder().append("UPDATE approximations SET idMaterial = ").append(materialsIds.get(i)).append(" WHERE idMaterial IS NULL AND idService = ").append(servicesIds.get(i)).toString());
               }

           }



           rs = statement.executeQuery("SELECT COUNT(1) FROM approximations"); // get n rows of the table aproximations
           rs.next();
           int rowsAproximations = rs.getInt(1);

           // Reference the first instance of the aproximation to the current user
           statement.executeUpdate(new StringBuilder().append("INSERT INTO users(name, lastname, company, email, password, idAprox) VALUES ('").append(user.getName()).append("', '").append(user.getLastname()).append("', '").append(user.getCompany()).append("', '").append(user.getEmail()).append("', '").append(user.getPassword()).append("', ").append(rowsAproximations - (maxRows - 1)).append(")").toString());

       }catch(SQLException throwables){
           throwables.printStackTrace();
       }finally {
            closeConnection();
       }

    }
}
