package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/yugioh";
    private static final String USER = "root";
    private static final String PASS = "ryansophia";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        try (Connection c = getConnection()) {
            System.out.println("Conexão OK: " + (c != null && !c.isClosed()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}