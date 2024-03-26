package APPLICATION.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import APPLICATION.connection.ConnectionDB;
import APPLICATION.model.JornadaTrabalho;
import APPLICATION.utils.Utils;

public class JornadaDAO {
	Utils utils = new Utils();
	Connection conn = ConnectionDB.create();
	PreparedStatement pstm = null;
	ResultSet rs = null;
	String msg = "";

	String BANCO_JORNADA = "banco_de_horas";
	
	
	public List<JornadaTrabalho> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
		String sql = "SELECT * FROM " + BANCO_JORNADA
				+ " WHERE datJornada >= ? AND datJornada < ? ORDER BY datJornada ASC";

		List<JornadaTrabalho> lista = new ArrayList<>();

		try (Connection conn = ConnectionDB.create(); PreparedStatement pstm = conn.prepareStatement(sql)) {

			// Verifique o estado da conex�o
			if (conn == null || conn.isClosed()) {
				// Reconecte ou lide com a situa��o de conex�o fechada.
				System.out.println("Conex�o com o banco de dados n�o foi inicializada ou est� fechada.");
				return lista;
			}

			// Converte LocalDateTime para Timestamp
			pstm.setTimestamp(1, Timestamp.valueOf(dataInicio));
			pstm.setTimestamp(2, Timestamp.valueOf(dataFim));

			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					JornadaTrabalho jornada = new JornadaTrabalho();
					jornada.setId(rs.getInt("id"));
					jornada.setDatJornada(new java.sql.Date(rs.getTimestamp("datJornada").getTime()));
					jornada.setStartJornada(rs.getTimestamp("startJornada"));
					jornada.setEndJornada(rs.getTimestamp("endJornada"));
					jornada.setStartAlmoco(rs.getTimestamp("startAlmoco"));
					jornada.setEndAlmoco(rs.getTimestamp("endAlmoco"));
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
			// Verifique se a conex�o est� inicializada
			if (conn == null) {
				System.out.println("Conex�o com o banco de dados n�o foi inicializada.");
				return;
			}

			// Crie a consulta SQL para inser��o
			String sql = "INSERT INTO   " + BANCO_JORNADA
					+ " (datJornada, startJornada, endJornada, startAlmoco, endAlmoco, porcentagem) VALUES (?, ?, ?, ?, ?, ?)";

			// Crie o PreparedStatement
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setDate(1, jornadaTrabalho.getDatJornada());
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
					System.out.println("Nenhuma linha foi afetada durante a inser��o.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Falha na inser��o: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Fechamento de recursos (se necess�rio)
		}
	}

	public void excluirJornada(int id) {
		try {
			// Verifique se a conex�o est� inicializada
			if (conn == null || conn.isClosed()) {
				System.out.println("Conex�o com o banco de dados n�o foi inicializada ou est� fechada.");
				return;
			}

			// Crie a consulta SQL para exclus�o
			String sql = "DELETE FROM  " + BANCO_JORNADA + " WHERE id = ?";

			// Crie o PreparedStatement usando try-with-resources
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, id);

				int linhasAfetadas = stmt.executeUpdate();

				if (linhasAfetadas > 0) {
					msg = "Excluido com sucesso.";
					utils.messageCrud(msg);
				} else {
					System.out.println("Nenhuma linha foi afetada durante a exclus�o.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Falha na exclus�o: " + e.getMessage());
			e.printStackTrace();
		} // O bloco finally para fechamento de recursos n�o � necess�rio quando usando
			// try-with-resources
	}

	public List<JornadaTrabalho> listarTodasJornadas() {
		String sql = "SELECT * FROM " + BANCO_JORNADA + " ORDER BY datJornada ASC";

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
		String sql = "SELECT MAX(id) FROM " + BANCO_JORNADA; // Substitua "BANCO_JORNADA" pelo nome real da sua tabela
		int ultimoID = 0;

		try (Connection conn = ConnectionDB.create();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {
			if (rs.next()) {
				ultimoID = rs.getInt(1); // Obt�m o valor m�ximo da coluna "id"
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Trate a exce��o de forma adequada para o seu aplicativo
		}

		return ultimoID;
	}

	public void atualizar(JornadaTrabalho jornadaTrabalho) {
		String sql = "UPDATE " + BANCO_JORNADA
				+ " SET datJornada = ?, startJornada = ?, endJornada = ?, startAlmoco = ?, endAlmoco = ?, porcentagem = ? WHERE id = ?";

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
				String msg = "Atualiza��o bem-sucedida.";
				utils.messageCrud(msg);
			} else {
				System.out.println("Nenhuma linha foi afetada durante a atualiza��o.");
			}

		} catch (SQLException e) {
			System.out.println("Falha na atualiza��o: " + e.getMessage());
			e.printStackTrace();
		}
	}

}