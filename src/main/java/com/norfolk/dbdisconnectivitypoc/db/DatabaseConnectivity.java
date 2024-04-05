package com.norfolk.dbdisconnectivitypoc.db;


import com.norfolk.dbdisconnectivitypoc.model.User;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseConnectivity {

    private Connection connection = null;

    //Logger logger = LoggerFactory.getLogger("db-service");

    public void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/e_portal"; //URL of database to be connected
        connection = DriverManager.getConnection(url, "root", "root");
    }

    public void disconnect() throws SQLException {

        if (connection != null) {
            connection.close();
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public List<User> getUsersDirectlyDBService() {
        String query2 = "SELECT * FROM USERS;";
        List<User> usersList = new ArrayList<>();
        try {

            this.connect();

            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(query2); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object

            while (result.next()) { //Now iterating over the ResultSet object to print the results of the query. next() returns false after all rows exhausted, else returns true
                User user = new User();
                int role = result.getInt("roleId"); //Getters extract corresponding data from column names
                String name = result.getString("name");
                String email = result.getString("email");
                //logger.info("DB Service :: Details fetched from DB");
                user.setName(name);
                user.setEmail(email);
                user.setRoleId(role);
                usersList.add(user);
              //  logger.info("DB Service :: connection details=>" + connection.toString());
            }
        } catch (SQLException e) {
           // logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
               // logger.info("DB Service :: disconnect called=>" + connection.toString());
                this.disconnect();
            } catch (Exception e) {
              //  logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return usersList;
    }
}
