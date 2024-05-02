package APPLICATION.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import APPLICATION.utils.Utils;

public class JornadaTrabalho {

	private int id;
	private Date datJornada;
	private Timestamp startJornada;
	private Timestamp endJornada;
	private Timestamp startAlmoco;
	private Timestamp endAlmoco;
	private int porcentagem;
	private Utils utils = new Utils();

	// Construtor

	public JornadaTrabalho() {
		// Construtor padrão
	}

	public JornadaTrabalho(int id, Date datJornada, Timestamp startJornada, Timestamp endJornada, Timestamp startAlmoco,
			Timestamp endAlmoco, int porcentagem) {
		super();
		this.id = id;
		this.datJornada = datJornada;
		this.startJornada = startJornada;
		this.endJornada = endJornada;
		this.startAlmoco = startAlmoco;
		this.endAlmoco = endAlmoco;
		this.porcentagem = porcentagem;

	}

	public JornadaTrabalho(Date datJornada, Timestamp startJornada, Timestamp endJornada, Timestamp startAlmoco,
			Timestamp endAlmoco, int porcentagem) {

		this.datJornada = datJornada;
		this.startJornada = startJornada;
		this.endJornada = endJornada;
		this.startAlmoco = startAlmoco;
		this.endAlmoco = endAlmoco;
		this.porcentagem = porcentagem;
	}

	public void setDatJornadaFromDateString(String datJornadaString) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.datJornada = new Date(dateFormat.parse(datJornadaString).getTime());
	}

	public void setTimestampFromTimeString(String timeString, Timestamp targetTimestamp) throws ParseException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		targetTimestamp.setTime(timeFormat.parse(timeString).getTime());
	}

	
	public void setDatJornadaFromString(String datJornadaString) throws ParseException {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    java.util.Date utilDate = dateFormat.parse(datJornadaString);
	    this.datJornada = new Date(utilDate.getTime());
	}

	public Timestamp getStartJornada() {
		return startJornada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatJornada() {
		return datJornada;
	}

	public void setDatJornada(Date datJornada) {
		this.datJornada = datJornada;
	}

	public void setStartJornada(Timestamp startJornada) {
		this.startJornada = startJornada;
	}

	public Timestamp getEndJornada() {
		return endJornada;
	}

	public void setEndJornada(Timestamp endJornada) {
		this.endJornada = endJornada;
	}

	public Timestamp getStartAlmoco() {
		return startAlmoco;
	}

	public void setStartAlmoco(Timestamp startAlmoco) {
		this.startAlmoco = startAlmoco;
	}

	public Timestamp getEndAlmoco() {
		return endAlmoco;
	}

	public void setEndAlmoco(Timestamp endAlmoco) {
		this.endAlmoco = endAlmoco;
	}

	public int getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(int porcentagem) {
		this.porcentagem = porcentagem;
	}

	@Override
	public String toString() {
		return "id=" + id + "\ndatJornada=" + datJornada + "\nstartJornada=" + startJornada + "\nendJornada="
				+ endJornada + "\nstartAlmoco=" + startAlmoco + "\nendAlmoco=" + endAlmoco + "\nporcentagem="
				+ porcentagem;
	}

}
