/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.*;

/**
 *
 * @author Nham Ngo
 */
public class DataProvider {

    static Connection conn = null;
    private static DataProvider instance;

    public static DataProvider getInstance() {
        if (instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    private DataProvider() {
        String sever = "DESKTOP-0PQ4U60\\SQLEXPRESS";
        String user = "sa";
        String password = "sa";
        String db = "QL_NHAHANG";
        int port = 1433;
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName(sever);
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setPortNumber(port);
        ds.setEncrypt(false);
        try {
            conn = ds.getConnection();
            System.out.println("kết nối thành công");
            System.out.println(conn.getCatalog());
            ResultSet rs = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("kết nối không thành công");
        }
    }

    
}
