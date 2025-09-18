package org.irctc.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
    public static Connection getconnection(){
        try(InputStream inputStream = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.username");
            String pass = properties.getProperty("db.password");

            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database connected successfully!");
            return con;

        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
}
