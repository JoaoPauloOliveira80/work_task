package APPLICATION.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
//import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import APPLICATION.grafic.JanelaPrincipal;

public class Utils {
	private JanelaPrincipal janela;

	public Utils(JanelaPrincipal janela) throws ParseException, SQLException {
		this.janela = new JanelaPrincipal();
	}

	public Utils() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Duration calcularTotalHora(Duration duracaoJornada, Duration duracaoAlmoco) {
		Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);

		if (duracaoAlmoco == null || duracaoAlmoco.equals(Duration.ZERO)) {
			return duracaoJornada;
		} else {
			Duration jornada = duracaoJornada.minus(duracaoAlmoco).minus(tempoPadrao);
			return jornada.isNegative() ? Duration.ZERO : jornada;
		}
	}

	public JDateChooser createFormattedDateChooser(String mask) {
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd/MM/yyyy");

		JFormattedTextField formattedTextField = createMaskedTextField(mask);

		dateChooser.addPropertyChangeListener("date", evt -> {
			if ("date".equals(evt.getPropertyName())) {
				Object newValue = evt.getNewValue();
				if (newValue instanceof java.util.Date) {
					formattedTextField.setValue(new SimpleDateFormat("dd/MM/yyyy").format(newValue));
				}
			}
		});

		JPanel panel = new JPanel();
		panel.add(formattedTextField);
		panel.add(dateChooser);

		return dateChooser;
	}

	public static Timestamp converterStringParaTimestamp(String dataHora) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
			Date date = formato.parse(dataHora);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace(); // Trate a exceção conforme necessário
			return null;
		}
	}

	public boolean validarDatasSelecionadas(JDateChooser dateChooser1, JDateChooser dateChooser2) {
		if (dateChooser1.getDate() == null || dateChooser2.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Por favor, selecione ambas as datas.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private JFormattedTextField createMaskedTextField(String mask) {
		MaskFormatter maskFormatter = null;
		try {
			maskFormatter = new MaskFormatter(mask);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JFormattedTextField(maskFormatter);
	}

	// FORMATADOR DE DATA
	public static String formatarIntervalo(LocalDate inicio, LocalDate fim) {
		// Formatando as datas
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String inicioFormatado = inicio.format(formatter);
		String fimFormatado = fim.format(formatter);

		// Construindo o intervalo
		String intervalo = inicioFormatado + " - " + fimFormatado;

		return intervalo;
	}

	public String formatDate(java.sql.Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	public static String formatarLocalDate(LocalDate data, String padrao) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(padrao);
		return data.format(formatter);
	}

//	public String formatarHora(LocalDateTime dateTime) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//		return dateTime.format(formatter);
//	}
	
	public  String formatarHora(LocalDateTime dateTime) {
	    if (dateTime != null) {
	        // Formate a data e hora
	        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
	    } else {
	        // Retorne uma string vazia ou outra string padrão, dependendo do que for apropriado para sua aplicação
	        return "";
	    }
	}

	// FORMATADOR DE HORA
	public String formatTime(Timestamp time) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		return formatter.format(time);
	}

	// Centralizar os dados na table
	public void centralizarDados(JTable table) {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
	}

	// formata hora e minutos
	public String formatDurationToHoursAndMinutes(Duration duration) {
		long totalMinutes = duration.toMinutes();
		long hours = totalMinutes / 60;
		long minutes = totalMinutes % 60;
		return String.format("%02d:%02d", hours, minutes);
	}

	// Formata data
	public String formatarData(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return dateTime.format(formatter);
	}

	public Duration arredondarHora(Duration originalDuration) {
		// Define your lunch break start and end times
		LocalTime lunchBreakStart = LocalTime.of(12, 0);
		LocalTime lunchBreakEnd = LocalTime.of(13, 10);

		// Get the start and end times of the original duration
		LocalTime start = LocalTime.MIDNIGHT.plus(originalDuration);
		LocalTime end = start.plus(originalDuration);

		// Check if the duration overlaps with the lunch break
		if (start.isBefore(lunchBreakEnd) && end.isAfter(lunchBreakStart)) {
			// Adjust the start time if it's before the lunch break
			if (start.isBefore(lunchBreakStart)) {
				start = lunchBreakStart;
			}

			// Adjust the end time if it's after the lunch break
			if (end.isAfter(lunchBreakEnd)) {
				end = lunchBreakEnd;
			}

			// Calculate the adjusted duration
			return Duration.between(LocalTime.MIDNIGHT, start).plus(Duration.between(start, end));
		}

		// If there's no overlap, return the original duration
		return originalDuration;
	}

	public String formatarDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.minusHours(hours).toMinutes();

		return String.format("%02d:%02d", hours, minutes);
	}

	public String converterFormatoDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date);
	}

	public static String formatarData() {
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = hoje.format(formatter);

		return dataFormatada;
	}

	public String converterFormatoData(LocalDate data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data.format(formatter);
	}

	public static String converterFormatoData(LocalDateTime data) {
		DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data.format(formatoSaida);
	}

	public static LocalDateTime converterFormatoData(String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(data, formatter).atStartOfDay();
	}

	public LocalTime converterFormatoHora(String hora) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		try {
			return LocalTime.parse(hora, formatter);
		} catch (DateTimeParseException e) {
			throw new ParseException(e.getMessage(), e.getErrorIndex());
		}
	}

	// MESSAGEM DE EXCLUSAO

	public void mostrarMensagemExclusaoBemSucedida() {
		JOptionPane optionPane = new JOptionPane("Exclusão bem-sucedida", JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
		JDialog dialog = optionPane.createDialog("Sucesso");

		// Configurar um Timer para fechar o diálogo após 3 segundos
		Timer timer = new Timer(3000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false); // Executar apenas uma vez
		timer.start();

		dialog.setVisible(true);
	}

	public void messageCrud(String msg) {
		JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
				new Object[] {}, null);
		JDialog dialog = optionPane.createDialog("Sucesso");

		// Configurar um Timer para fechar o diálogo após 3 segundos
		Timer timer = new Timer(2500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false); // Executar apenas uma vez
		timer.start();

		dialog.setVisible(true);
	}

	public static java.sql.Date convertStringToSqlDate(String dateString) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = dateFormat.parse(dateString);
		return new java.sql.Date(utilDate.getTime());
	}

	public void updateJornada(String msg) {
		JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
				new Object[] {}, null);
		JDialog dialog = optionPane.createDialog("Sucesso");

		// Configurar um Timer para fechar o diálogo após 3 segundos
		Timer timer = new Timer(3000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false); // Executar apenas uma vez
		timer.start();

		dialog.setVisible(true);
	}

	public LocalDateTime converteTimestampToLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}

	// margem no campo
	public void adicionarPaddingAoDateChooser(JDateChooser dateChooser, int padding) {
		// Cria uma borda vazia com o tamanho do padding desejado
		EmptyBorder border = new EmptyBorder(padding, padding, padding, padding);
		// Define a borda no JDateChooser
		dateChooser.setBorder(BorderFactory.createCompoundBorder(dateChooser.getBorder(), border));
	}
}
