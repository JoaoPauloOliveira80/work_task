package APPLICATION.controller;

import java.time.LocalDateTime;
import java.util.List;

import APPLICATION.model.JornadaTrabalho;
import APPLICATION.service.JornadaService;

public class JornadaController {

    private JornadaService jornadaService;
   
    public JornadaController() { 
        this.jornadaService = new JornadaService();
    }


    public List<JornadaTrabalho> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return jornadaService.listarPorPeriodo(dataInicio, dataFim);
    }
    
    
    public List<JornadaTrabalho> listarTodasJornadas() {
        return jornadaService.listarTodasJornadas();
    }

    
    public void inserirJornada(JornadaTrabalho jornadaTrabalho) {
    	jornadaService.inserirJornada(jornadaTrabalho);
    }
    
    public void excluirJornada(int id) {
        // Adicione lógica de validação ou regras de negócios, se necessário
    	jornadaService.excluirJornada(id);
    }
    
    public void atualizarTabela(JornadaTrabalho jornadaTrabalho) {
        jornadaService.atualizar(jornadaTrabalho);
    }
    
    public Integer obterUltimoID() {
    	return jornadaService.obterUltimoID();
	}

}
