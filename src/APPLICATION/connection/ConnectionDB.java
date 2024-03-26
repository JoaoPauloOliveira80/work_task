package APPLICATION.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

    static Connection conn;
    static String nameBD = "jornadadiaria";

    public static Connection create() {
        try {
            // Carregar o driver
            Class.forName("com.mysql.jdbc.Driver");

            // Criar a conexão
            String user = "root";
            String password = "vertrigo";
            String url = "jdbc:mysql://localhost:3306/" + nameBD + "?autoReconnect=true&useSSL=false";

            try {
                conn = DriverManager.getConnection(url, user, password);
               // System.out.println("Connected to Database: " + nameBD);
                return conn;
            } catch (SQLException e) {
                throw new RuntimeException("Cannot connect to database", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
    
    private  void createDatabase() throws SQLException {
        // Verifica se o banco de dados já existe
        if (databaseExists()) {
            System.out.println("Database " + nameBD + " already exists");
            return; // Sai do método sem tentar criar o banco de dados novamente
        }

        Statement stmt = conn.createStatement();
        String sql = "CREATE DATABASE " + nameBD;
        stmt.executeUpdate(sql);
        System.out.println("Database " + nameBD + " created successfully");
        stmt.close();
    }

    private  boolean databaseExists() throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = metaData.getCatalogs();
        while (resultSet.next()) {
            String databaseName = resultSet.getString(1);
            if (databaseName.equals(nameBD)) {
                resultSet.close();
                return true;
            }
        }
        resultSet.close();
        return false;
    }
}
