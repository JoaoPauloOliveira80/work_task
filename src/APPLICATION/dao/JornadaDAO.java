package APPLICATION.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import APPLICATION.connection.ConnectionDB;
import APPLICATION.grafic.JanelaPrincipal;
import APPLICATION.model.JornadaTrabalho;
import APPLICATION.utils.Utils;

public class JornadaDAO {
	private Utils utils = new Utils();
	private Connection conn = ConnectionDB.create();
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	private String msg = "";
	private JanelaPrincipal principal;
	String table = "jornada";
	
	
	

//	public List<JornadaTrabalho> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
//		
//		 
//		String sql = "SELECT * FROM " + table
//				+ " WHERE dat_jornada >= ? AND dat_jornada <= ? ORDER BY dat_jornada ASC";
//
//		List<JornadaTrabalho> lista = new ArrayList<>();
//
//		try (Connection conn = ConnectionDB.create(); PreparedStatement pstm = conn.prepareStatement(sql)) {
//
//			// Verifique o estado da conexão
//			if (conn == null || conn.isClosed()) {
//				// Reconecte ou lide com a situação de conexão fechada.
//				System.out.println("Conexão com o banco de dados não foi inicializada ou está fechada.");
//				return lista;
//			}
//
//			// Converte LocalDateTime para Timestamp
//			pstm.setTimestamp(1, Timestamp.valueOf(dataInicio));
//			pstm.setTimestamp(2, Timestamp.valueOf(dataFim));
//
//			try (ResultSet rs = pstm.executeQuery()) {
//				while (rs.next()) {
//					JornadaTrabalho jornada = new JornadaTrabalho();
//					jornada.setId(rs.getInt("id"));
//					jornada.setDatJornada(new java.sql.Date(rs.getTimestamp("dat_jornada").getTime()));
//					jornada.setStartJornada(rs.getTimestamp("start_Jornada"));
//					jornada.setEndJornada(rs.getTimestamp("end_Jornada"));
//					jornada.setStartAlmoco(rs.getTimestamp("start_Almoco"));
//					jornada.setEndAlmoco(rs.getTimestamp("end_Almoco"));
//					jornada.setPorcentagem(rs.getInt("porcentagem"));
//
//					lista.add(jornada);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return lista;
//	}

	public List<JornadaTrabalho> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
		String sql = "SELECT * FROM " + table + " WHERE dat_jornada >= ? AND dat_jornada <= ? ORDER BY dat_jornada ASC";

		List<JornadaTrabalho> lista = new ArrayList<>();

		try (Connection conn = ConnectionDB.create(); PreparedStatement pstm = conn.prepareStatement(sql)) {

			// Verifique o estado da conexão
			if (conn == null || conn.isClosed()) {
				// Reconecte ou lide com a situação de conexão fechada.
				System.out.println("Conexão com o banco de dados não foi inicializada ou está fechada.");
				return lista;
			}

			// Converte LocalDateTime para Timestamp
			Timestamp inicio = Timestamp.valueOf(dataInicio);
			Timestamp fim = Timestamp.valueOf(dataFim);

			// Define os parâmetros da consulta
			pstm.setTimestamp(1, inicio);
			pstm.setTimestamp(2, fim);

			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					JornadaTrabalho jornada = new JornadaTrabalho();
					jornada.setId(rs.getInt("id"));
					jornada.setDatJornada(rs.getDate("dat_jornada"));
					jornada.setStartJornada(rs.getTimestamp("start_Jornada"));
					jornada.setEndJornada(rs.getTimestamp("end_Jornada"));
					jornada.setStartAlmoco(rs.getTimestamp("start_Almoco"));
					jornada.setEndAlmoco(rs.getTimestamp("end_Almoco"));
					jornada.setPorcentagem(rs.getInt("porcentagem"));

					lista.add(jornada);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public void inserirJornada(JornadaTrabalho jornadaTrabalho) {
		try {
			// Verifique se a conexão está inicializada
			if (conn == null) {
				System.out.println("Conexão com o banco de dados não foi inicializada.");
				return;
			}

			// Crie a consulta SQL para inserção
			String sql = "INSERT INTO " + table
					+ " (dat_jornada, start_Jornada, end_Jornada, start_Almoco, end_Almoco, porcentagem) VALUES (?, ?, ?, ?, ?, ?)";

			// Crie o PreparedStatement
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				// Converta a data da jornada para java.sql.Date, se necessário
				Date datJornada = new Date(jornadaTrabalho.getDatJornada().getTime());

				stmt.setDate(1, datJornada);
				stmt.setTimestamp(2, jornadaTrabalho.getStartJornada());
				stmt.setTimestamp(3, jornadaTrabalho.getEndJornada());
				stmt.setTimestamp(4, jornadaTrabalho.getStartAlmoco());
				stmt.setTimestamp(5, jornadaTrabalho.getEndAlmoco());
				stmt.setInt(6, jornadaTrabalho.getPorcentagem());

				int linhasAfetadas = stmt.executeUpdate();

				if (linhasAfetadas > 0) {
					msg = "Salvo com sucesso.";
					utils.messageCrud(msg);
				} else {
					System.out.println("Nenhuma linha foi afetada durante a inserção.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Falha na inserção: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Fechamento de recursos (se necessário)
		}
	}

//	public void inserirJornada(JornadaTrabalho jornadaTrabalho) {
//
//		try {
//			// Verifique se a conexão está inicializada
//			if (conn == null) {
//				System.out.println("Conexão com o banco de dados não foi inicializada.");
//				return;
//			}
//
//			// Crie a consulta SQL para inserção
//			String sql = "INSERT INTO   " + BANCO_JORNADA
//					+ " (datJornada, start_Jornada, end_Jornada, start_Almoco, end_Almoco, porcentagem) VALUES (?, ?, ?, ?, ?, ?)";
//
//			// Crie o PreparedStatement
//			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//				stmt.setDate(1, jornadaTrabalho.getDatJornada());
//				stmt.setTimestamp(2, jornadaTrabalho.getStartJornada());
//				stmt.setTimestamp(3, jornadaTrabalho.getEndJornada());
//				stmt.setTimestamp(4, jornadaTrabalho.getStartAlmoco());
//				stmt.setTimestamp(5, jornadaTrabalho.getEndAlmoco());
//				stmt.setInt(6, jornadaTrabalho.getPorcentagem());
//
//				int linhasAfetadas = stmt.executeUpdate();
//
//				if (linhasAfetadas > 0) {
//					msg = "Salvo com sucesso.";
//					utils.messageCrud(msg);
//				} else {
//					System.out.println("Nenhuma linha foi afetada durante a inserção.");
//				}
//			}
//		} catch (SQLException e) {
//			System.out.println("Falha na inserção: " + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			// Fechamento de recursos (se necessário)
//		}
//	}

	public void excluirJornada(int id) {
		try {
			// Verifique se a conexão está inicializada
			if (conn == null || conn.isClosed()) {
				System.out.println("Conexão com o banco de dados não foi inicializada ou está fechada.");
				return;
			}

			// Crie a consulta SQL para exclusão
			String sql = "DELETE FROM  " + table + " WHERE id = ?";

			// Crie o PreparedStatement usando try-with-resources
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, id);

				int linhasAfetadas = stmt.executeUpdate();

				if (linhasAfetadas > 0) {
					msg = "Excluido com sucesso.";
					utils.messageCrud(msg);
				} else {
					System.out.println("Nenhuma linha foi afetada durante a exclusão.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Falha na exclusão: " + e.getMessage());
			e.printStackTrace();
		} // O bloco finally para fechamento de recursos não é necessário quando usando
			// try-with-resources
	}

	public List<JornadaTrabalho> listarTodasJornadas() {
		String sql = "SELECT * FROM " + table + " ORDER BY datJornada ASC";

		List<JornadaTrabalho> lista = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConnectionDB.create();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			while (rs.next()) {
				JornadaTrabalho jornada = new JornadaTrabalho();
				// jornada.setId(rs.getInt("id"));
				jornada.setDatJornada(new java.sql.Date(rs.getTimestamp("datJornada").getTime()));
				jornada.setStartJornada(rs.getTimestamp("startJornada"));
				jornada.setEndJornada(rs.getTimestamp("endJornada"));
				jornada.setStartAlmoco(rs.getTimestamp("startAlmoco"));
				jornada.setEndAlmoco(rs.getTimestamp("endAlmoco"));
				jornada.setPorcentagem(rs.getInt("porcentagem"));

				lista.add(jornada);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Fechamento de recursos (ResultSet, PreparedStatement, Connection) omitido por
			// brevidade.
		}

		return lista;
	}

	public int obterUltimoID() {
		String sql = "SELECT MAX(id) FROM " + table; // Substitua "BANCO_JORNADA" pelo nome real da sua tabela
		int ultimoID = 0;

		try (Connection conn = ConnectionDB.create();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {
			if (rs.next()) {
				ultimoID = rs.getInt(1); // Obtém o valor máximo da coluna "id"
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Trate a exceção de forma adequada para o seu aplicativo
		}

		return ultimoID;
	}

	public void atualizar(JornadaTrabalho jornadaTrabalho) {
		String sql = "UPDATE " + table
				+ " SET dat_jornada = ?, start_Jornada = ?, end_Jornada = ?, start_Almoco = ?, end_Almoco = ?, porcentagem = ? WHERE id = ?";

		try (Connection conn = ConnectionDB.create(); PreparedStatement pstm = conn.prepareStatement(sql)) {

			// Convert java.util.Date to java.sql.Date
			java.sql.Date datJornadaSql = new java.sql.Date(jornadaTrabalho.getDatJornada().getTime());

			pstm.setDate(1, datJornadaSql);
			pstm.setTimestamp(2, jornadaTrabalho.getStartJornada());
			pstm.setTimestamp(3, jornadaTrabalho.getEndJornada());
			pstm.setTimestamp(4, jornadaTrabalho.getStartAlmoco());
			pstm.setTimestamp(5, jornadaTrabalho.getEndAlmoco());
			pstm.setInt(6, jornadaTrabalho.getPorcentagem());
			pstm.setInt(7, jornadaTrabalho.getId());

			int linhasAfetadas = pstm.executeUpdate();

			if (linhasAfetadas > 0) {
				String msg = "Atualização bem-sucedida.";
				utils.messageCrud(msg);
			} else {
				System.out.println("Nenhuma linha foi afetada durante a atualização.");
			}

		} catch (SQLException e) {
			System.out.println("Falha na atualização: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
