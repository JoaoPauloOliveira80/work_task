package APPLICATION.service;

import java.time.LocalDateTime;
import java.util.List;

import APPLICATION.dao.JornadaDAO;
import APPLICATION.model.JornadaTrabalho;

public class JornadaService {

	private JornadaDAO jornadaDAO;

	public JornadaService() {
		this.jornadaDAO = new JornadaDAO();
	}

	public List<JornadaTrabalho> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
		return jornadaDAO.listarPorPeriodo(dataInicio, dataFim);
	}

	public List<JornadaTrabalho> listarTodasJornadas() {
		return jornadaDAO.listarTodasJornadas();
	}

	public void inserirJornada(JornadaTrabalho jornadaTrabalho) {
		jornadaDAO.inserirJornada(jornadaTrabalho);
	}

	public void excluirJornada(int id) {
		// Adicione lógica de validação ou regras de negócios, se necessário
		jornadaDAO.excluirJornada(id);
	}

	public void atualizar(JornadaTrabalho jornadaTrabalho) {
		jornadaDAO.atualizar(jornadaTrabalho);
	}

	public Integer obterUltimoID() {
		return jornadaDAO.obterUltimoID();
	}
}
