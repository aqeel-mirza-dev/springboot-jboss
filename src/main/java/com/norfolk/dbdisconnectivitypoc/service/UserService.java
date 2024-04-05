package com.norfolk.dbdisconnectivitypoc.service;

import com.norfolk.dbdisconnectivitypoc.db.DatabaseConnectivity;
import com.norfolk.dbdisconnectivitypoc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    DatabaseConnectivity db;
    public User createUser()  {
        String query1 = "INSERT INTO USERS(name,email,phone,roleId,pwd) VALUES (?,?,?,?,?)";
        User user = new User();
        Connection myConn=null;
        try {

            db.connect();
            myConn = db.getConnection();
            PreparedStatement preStat = myConn.prepareStatement(query1); //PreparedStatement is a subclass of Statement that supports data substitution and can execute a statement multiple times
            preStat.setString(1, "db_issue"); //Using the setter methods to substitute values corresponding to the ?s
            preStat.setString(2, "db_issue@testmail.com");
            preStat.setString(3, "123456789");
            preStat.setInt(4, 1);
            preStat.setString(5, "12345");
            preStat.executeUpdate(); //Executing the statement using executeUpdate()
            user.setName("db_issue");
            user.setEmail("db_issue@testmail.com");
            user.setRoleId(1);
           // logger.info("user created successfully.");
        } catch (SQLException e) {
           // logger.error(e.getMessage());
            throw new RuntimeException(e);
        }finally {
            try {
                //logger.info("disconnect called"+myConn.toString());
                db.disconnect();
            } catch (Exception e) {
                //logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    return user;

    }

    public List<User> getUserFromDB(){
        return db.getUsersDirectlyDBService();
    }

    public List<User> getUsers() {
        String query2 = "SELECT * FROM USERS;";
        List<User> usersList = new ArrayList<>();
        Connection myConn=null;
        try {
            db.connect();
            myConn = db.getConnection();

            Statement statement= myConn.createStatement();
            ResultSet result = statement.executeQuery(query2); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object

            while(result.next()) { //Now iterating over the ResultSet object to print the results of the query. next() returns false after all rows exhausted, else returns true
                User user = new User();
                int role = result.getInt("roleId"); //Getters extract corresponding data from column names
                String name = result.getString("name");
                String email = result.getString("email");
               // logger.info("User Service :: Details fetched from DB");
                user.setName(name);
                user.setEmail(email);
                user.setRoleId(role);
                usersList.add(user);
               // logger.info("User Service :: connection details=>"+myConn.toString());
            }
        } catch (SQLException e) {
           // logger.error(e.getMessage());
            throw new RuntimeException(e);
        }finally {
            try {
               // logger.info("User Service::disconnect called"+myConn.toString());
                db.disconnect();
            } catch (Exception e) {
                //logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return usersList;
    }
}
