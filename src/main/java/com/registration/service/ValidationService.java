package com.registration.service;

import com.registration.configuration.DatabaseConfig;
import com.registration.models.AppUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidationService {

    DatabaseConfig databaseConfig = new DatabaseConfig();

    public boolean validate(AppUser user) throws SQLException, ClassNotFoundException {
        System.out.println("ValidationService : validate : START");
        boolean result = true;

        if(checkEmailExistence(user) || !checkPasswordRules(user)){
            result = false;
        }

        System.out.println("ValidationService : validate " + result);

        System.out.println("ValidationService : validate : END");
        return result;
    }

    private boolean checkEmailExistence(AppUser user) throws SQLException, ClassNotFoundException {
        System.out.println("ValidationService : checkEmailExistence : START");
        boolean result = false;

        Connection connection = databaseConfig.getConnection();
        String sql = "SELECT email FROM AppUser WHERE email = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, user.getEmail());
        ResultSet resultSet = stmt.executeQuery();

        result = resultSet.next();
        System.out.println("ValidationService : checkEmailExistence " + result);

        System.out.println("ValidationService : checkEmailExistence : END");
        return result;
    }

    private boolean checkPasswordRules(AppUser user) throws SQLException {
        System.out.println("ValidationService : checkPasswordRules : START");
        if(user.getPassword().length() < 8){
            System.out.println("ValidationService : checkPasswordRules : false" + "length < 8");
            return false;
        }else if (!user.getPassword().matches(".*[A-Z].*")) {
            System.out.println("ValidationService : checkPasswordRules : false" + " no uppercase");
            return false;
        }else if (!user.getPassword().matches(".*[a-z].*")) {
            System.out.println("ValidationService : checkPasswordRules : false" + " no lowercase");
            return false;
        }else if (!user.getPassword().matches(".*[0-9].*")) {
            System.out.println("ValidationService : checkPasswordRules : false" + " no digit");
            return false;
        }else{
            System.out.println("ValidationService : checkPasswordRules : true");
            System.out.println("ValidationService : checkPasswordRules : END");
            return  true;
        }
    }
}
