package APPLICATION.model;

import java.time.LocalDateTime;

public class Jornada {
	private int id;
	private LocalDateTime startJornada;
	private LocalDateTime endJornada;
	private LocalDateTime startAlmoco;
	private LocalDateTime endAlmoco;
	private int porcentagem;

	public Jornada() {
		// TODO Auto-generated constructor stub
	}

	public Jornada(int id,LocalDateTime startJornada, LocalDateTime endJornada, LocalDateTime startRefeicao,
			LocalDateTime endRefeicao, int porcentagem) {
		this.id = id;
		this.startJornada = startJornada;
		this.endJornada = endJornada;
		this.startAlmoco = startRefeicao;
		this.endAlmoco = endRefeicao;
		this.porcentagem = porcentagem;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStartJornada() {
		return startJornada;
	}

	public void setStartJornada(LocalDateTime startJornada) {
		this.startJornada = startJornada;
	}

	public LocalDateTime getEndJornada() {
		return endJornada;
	}

	public void setEndJornada(LocalDateTime endJornada) {
		this.endJornada = endJornada;
	}

	public LocalDateTime getStartRefeicao() {
		return startAlmoco;
	}

	public void setStartRefeicao(LocalDateTime startRefeicao) {
		this.startAlmoco = startRefeicao;
	}

	public LocalDateTime getEndRefeicao() {
		return endAlmoco;
	}

	public void setEndRefeicao(LocalDateTime endRefeicao) {
		this.endAlmoco = endRefeicao;
	}

	public int getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(int porcentagem) {
		this.porcentagem = porcentagem;
	}

	@Override
	public String toString() {
		return "Entrada = " + startJornada + 
				"\nSaída =" + endJornada + 
				"\nEntrada almoço = " + startAlmoco +
				"\nRetorno do  almoço = " + endAlmoco + 
				"\nporcentagem = " + porcentagem + "\n";
		
	}

	
	
	
}