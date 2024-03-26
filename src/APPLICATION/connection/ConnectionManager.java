package APPLICATION.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import APPLICATION.utils.Utils;

public class ConnectionManager {
	private static Utils utils = new Utils(); 
	
	private static final String USER = "root";
	private static final String PASSWORD = "vertrigo";
	private static final String DATABASE_NAME = "celular";
	private static final String TABLE_NAME = "pra_sempre_bb";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useSSL=false";
	private static String msg = "";
	private static final String LOGIN_TABLE_NAME = "login";

	public static void createDatabaseIfNotExists() throws SQLException {
		if (!databaseExists()) {
			createDatabase();
			createTableIfNotExists();
		} else {
			msg = "O banco de dados já existe. Não é necessário criar novamente.";
			utils.messageCrud(msg);
			createTableIfNotExists();
		}
	}

	public static void createTableIfNotExists() throws SQLException {
		if (!tableExists()) {
			createTable();
		} else {
			msg = "A tabela já existe. Não é necessário criar novamente.";
			utils.messageCrud(msg);
			
		}
	}
	
	public static void createLoginIfNotExists() throws SQLException {
		if(!LoginExists()) {
			LoginExists();
		}else {
			msg = "A tabela "+LOGIN_TABLE_NAME+" já existe. Não é necessário criar novamente.";
			utils.messageCrud(msg);
		}
	}

	private static boolean databaseExists() throws SQLException {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", USER, PASSWORD)) {
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet resultSet = metaData.getCatalogs();
			while (resultSet.next()) {
				String dbName = resultSet.getString(1);
				if (dbName.equals(DATABASE_NAME)) {
					return true;
				}
			}
			return false;
		}
	}

	private static boolean tableExists() throws SQLException {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, TABLE_NAME, null);
			return resultSet.next();
		}
	}
	
	private static boolean LoginExists() throws SQLException {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, LOGIN_TABLE_NAME, null);
			return resultSet.next();
		}
	}

	private static void createDatabase() throws SQLException {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", USER, PASSWORD)) {
			String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
			try (Statement stmt = conn.createStatement()) {
				stmt.executeUpdate(sql);
				
				msg = "Banco de dados criado com sucesso.";
				utils.messageCrud(msg);
			}
		}
	}
	
	public static void createTable() throws SQLException {
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
	        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
	                   + "id INT AUTO_INCREMENT PRIMARY KEY,"
	                   + "datJornada DATE,"
	                   + "startJornada TIME,"
	                   + "endJornada TIME,"
	                   + "startAlmoco TIME,"
	                   + "endAlmoco TIME,"
	                   + "porcentagem INT"
	                   + ")";
	        try (Statement stmt = conn.createStatement()) {
	            stmt.executeUpdate(sql);
	            
	            msg = "Tabela criada com sucesso.";
				utils.messageCrud(msg);
				
	            
	        }
	    }
	}


	

	public static void createLoginTable() throws SQLException {
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
	        String sql = "CREATE TABLE IF NOT EXISTS " + LOGIN_TABLE_NAME + " ("
	                   + "id INT AUTO_INCREMENT PRIMARY KEY,"
	                   + "nome VARCHAR(255),"
	                   + "username VARCHAR(255),"
	                   + "senha VARCHAR(255)"
	                   + ")";
	        try (Statement stmt = conn.createStatement()) {
	            stmt.executeUpdate(sql);
	            
	            msg = "Tabela de login criada com sucesso.";
				utils.messageCrud(msg);
	        }
	    }
	}

}
