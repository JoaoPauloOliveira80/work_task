package APPLICATION.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConnectionDB {

	 static Connection conn;
	    static String nameBD = "banco-hora-tupi";

	    public static Connection create() {
	        final int MAX_ATTEMPTS = 3;
	        int attempt = 0;

	        while (attempt < MAX_ATTEMPTS) {
	            try {
	                // Carregar o driver
	                Class.forName("com.mysql.jdbc.Driver");

	                // Criar a conexão
	                String user = "root";
	                String password = "vertrigo";
	                String url = "jdbc:mysql://localhost:3306/" + nameBD + "?autoReconnect=true&useSSL=false";

	                conn = DriverManager.getConnection(url, user, password);
	                // Se a conexão for bem-sucedida, retorna a conexão
	                return conn;
	            } catch (ClassNotFoundException e) {
	                System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
	                throw new RuntimeException("JDBC Driver not found", e);
	            } catch (SQLException e) {
	                System.err.println("Failed to connect to database: " + e.getMessage());
	                attempt++;
	                if (attempt < MAX_ATTEMPTS) {
	                    System.out.println("Retrying connection... Attempt " + attempt);
	                    try {
	                        Thread.sleep(5000); // Espera 5 segundos antes de tentar reconectar
	                    } catch (InterruptedException ex) {
	                        Thread.currentThread().interrupt();
	                    }
	                } else {
	                    // Mostrar mensagem de aviso ao usuário
	                    JOptionPane.showMessageDialog(null, "Servidor de banco de dados não está disponível. Por favor, verifique a conexão com o servidor.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
	                    return null;
	                }
	            }
	        }
	        return null;
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
