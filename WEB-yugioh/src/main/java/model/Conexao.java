package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/yugioh";
    private static final String USER = "root";
    private static final String PASS = "ryansophia";

    // CORREÇÃO: registra o driver JDBC explicitamente na classe.
    // Sem isso, em alguns containers (Tomcat) aparece
    // "No suitable driver found for jdbc:mysql://..." e a página /list quebra.
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                    "Driver MySQL não encontrado no classpath: " + e.getMessage());
        }
    }

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