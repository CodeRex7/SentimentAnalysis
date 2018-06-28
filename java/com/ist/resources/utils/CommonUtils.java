package com.ist.resources.utils;

import java.io.File;
import java.sql.*;

/**
 *
 * @author SOUMYAGOURAB.S
 */
public class CommonUtils {

    private static Connection conn = null;
    private static String encryptedEntities = "";
    private static String DBIPaddress = "";
    private static String usernameval = "";
    private static String passwordval = "";

    public static Connection createConnection() {
        try {
           String DatabaseURL = DBIPaddress;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(DatabaseURL,usernameval,passwordval);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private CommonUtils() {
    }
    public static void DBStrings(String DBIP,String username, String password) {
        DBIPaddress = DBIP;
        usernameval = username;
        passwordval = password;
    }

    public static Connection database_connect(String username, String password) {


        if (conn == null) {
            try {
                String DatabaseURL = "jdbc:mysql:"+DBIPaddress+"?autoReconnect=true";
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DatabaseURL, usernameval, passwordval);
                if (conn != null) {
                    System.out.println("Database connection established in constants ");
                }
            } catch (Exception e) {
                System.out.println("Cannot connect to database server");
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static Connection database_connect() {
        if (conn != null) {
            return conn;
        } else {
            conn = createConnection();
            return conn;
        }
    }
}
