package APPLICATION.grafic;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import APPLICATION.connection.ConnectionManager;
import APPLICATION.controller.JornadaController;
import APPLICATION.model.GeradorPDF;
import APPLICATION.model.JornadaTrabalho;
import APPLICATION.model.ModeloJornada;
import APPLICATION.utils.Utils;

public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Utils utils = new Utils();
	private int porcentagem = 0;
	private DefaultTableModel model;
	private boolean hora70 = false;
	private boolean hora110 = false;
	private Duration horaExtra70 = Duration.ZERO;
	private Duration horaExtra110 = Duration.ZERO;
	private JornadaController jornadaController = new JornadaController();
	private JButton btnCadastrar;
	private JButton btnPesquisar;
	private JDateChooser dateChooser1, dateChooser2;
	private ModeloJornada modeloJornada;
	private static JanelaPrincipal frame;
	private int idJornada;

	private LocalDateTime startJornada;
	private LocalDateTime endJornada;
	private LocalDateTime startAlmoco;
	private LocalDateTime endAlmoco;
	private java.sql.Date datJornada;
	Duration resultado = Duration.ZERO;

	int selectedRow = 0;

	LocalDate hoje = LocalDate.now();
	LocalDate mesFinal = hoje.withDayOfMonth(26); // Data final do mês atual
	LocalDate mesComeco = mesFinal.minusMonths(1).withDayOfMonth(26);

	LocalDateTime fim = mesFinal.atStartOfDay();
	LocalDateTime comeco = mesComeco.atStartOfDay();
	private JButton btnRefresh;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new JanelaPrincipal();

					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getData() {
		return Utils.converterFormatoData(comeco);
	}

	public JanelaPrincipal(JornadaController jornadaController) {
		this.jornadaController = jornadaController;
	}

	public JanelaPrincipal() throws ParseException, SQLException {
		try {
			ConnectionManager.createDatabaseIfNotExists();

			// Faça o que precisa ser feito com a conexão...
		} catch (SQLException e) {
			System.err.println("Erro ao criar o banco de dados ou obter conexão: " + e.getMessage());
			e.printStackTrace();
		}
		setResizable(false);

		this.setFocusableWindowState(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 988, 657);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		this.setResizable(false);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {

				setTitle("Controle de Jornada por período " + Utils.converterFormatoData(comeco) + " - "
						+ Utils.converterFormatoData(fim));

				atualizaTable();
			}
		});

		model = new DefaultTableModel(new Object[] { "Cod. Registro", "Data", "Incio da Jornada", "Fim da Jornada",
				"Incio do Almoo", "Fim do Almoo", "Hora diria", "Porcentagem" }, 0) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		utils.centralizarDados(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

					selectedRow = table.getSelectedRow();

					// System.out.println("Linha selecionada: " + selectedRow);

					if (selectedRow >= 0 && selectedRow < table.getRowCount() - 7) {
						Object idObject = table.getValueAt(selectedRow, 0);
						Object idPorcentagem = table.getValueAt(selectedRow, 7);
						Object datJornadaObject = table.getValueAt(selectedRow, 1);

						if (idObject != null && datJornadaObject != null) {
							idJornada = Integer.parseInt(idObject.toString());
							String datJornadaString = datJornadaObject.toString();
							String startJornada = (String) table.getValueAt(selectedRow, 2);
							String endJornada = (String) table.getValueAt(selectedRow, 3);
							String startAlmoco = (String) table.getValueAt(selectedRow, 4);
							String endAlmoco = (String) table.getValueAt(selectedRow, 5);
							int porcentagem = Integer.parseInt(idPorcentagem.toString());

							try {
								datJornada = new java.sql.Date(dateFormat.parse(datJornadaString).getTime());
								modeloJornada = new ModeloJornada();

								if (modeloJornada == null || !modeloJornada.isVisible()) {
									// Se a instncia no existe ou est fechada, criar uma nova
									abrirModeloJornada(idJornada, datJornada, startJornada, endJornada, startAlmoco,
											endAlmoco, porcentagem);

									modeloJornada.setVisible(true);
									setBotoesEnabled(false);

								} else {
									// Se a instncia j est aberta, torne-a visvel novamente
									modeloJornada.setVisible(true);
									modeloJornada.toFront();
									setBotoesEnabled(false);
								}

							} catch (ParseException e2) {
								e2.printStackTrace();
							}
						}
					}
				}
			}
		});

		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 50, 940, 509);
		contentPane.add(scrollPane);

		ActionListener alterarListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		};

		// ActionListener para o botão Incluir na JanelaPrincipal
		ActionListener incluirListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		};

		// Adiciona um WindowListener para interceptar eventos de minimizar

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent e) {
				setExtendedState(JFrame.NORMAL); // Impede a minimização
			}
		});

		btnCadastrar = new JButton("Inserir");
		btnCadastrar.setBounds(148, 570, 134, 37);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloJornada = new ModeloJornada(JanelaPrincipal.this); // Passe a instância de JanelaPrincipal
				modeloJornada.setVisible(true);
				modeloJornada.setSize(670, 265);
				modeloJornada.setLocationRelativeTo(null);

				// Configurar os botões na instância de ModeloJornada
				modeloJornada.configurarBotaoAlterar(alterarListener);
				modeloJornada.configurarBotaoIncluir(incluirListener);
				// Desabilite os botões quando o ModeloJornada estiver aberto
				setBotoesEnabled(false);

				atualizaTable();

			}
		});

		contentPane.add(btnCadastrar);

		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(e -> {
			if (!utils.validarDatasSelecionadas(dateChooser1, dateChooser2)) {
				return;
			}

			LocalDate startDate = dateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate endDate = dateChooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			LocalDateTime start = startDate.atStartOfDay();
			LocalDateTime end = endDate.atStartOfDay();

			if (startDate.isAfter(endDate)) {
				JOptionPane.showMessageDialog(null, "A data inicial no pode ser posterior  data final.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			limparTabela();
			listar(start, end);

			LocalDate dataAtual = LocalDate.now();

			@SuppressWarnings("unused")
			boolean mesAnterior = startDate.isBefore(dataAtual)
					&& startDate.getMonthValue() == dataAtual.getMonthValue();

			setTitle("Controle de Jornada por período " + Utils.converterFormatoData(start) + " - "
					+ Utils.converterFormatoData(end));

		});

		btnPesquisar.setBounds(349, 11, 134, 31);
		contentPane.add(btnPesquisar);

		dateChooser1 = utils.createFormattedDateChooser("##/##/####");
		((JTextFieldDateEditor) dateChooser1.getDateEditor()).setEditable(false);
		dateChooser1.setPreferredSize(new Dimension(150, 30));
		dateChooser1.setBounds(10, 11, 150, 30);
		contentPane.add(new JLabel("Data Inicial:"));
		contentPane.add(dateChooser1);

		// Cria o segundo JDateChooser
		dateChooser2 = utils.createFormattedDateChooser("##/##/####");
		((JTextFieldDateEditor) dateChooser2.getDateEditor()).setEditable(false);
		dateChooser2.setPreferredSize(new Dimension(150, 30));
		dateChooser2.setBounds(180, 11, 150, 30);
		contentPane.add(new JLabel("Data Final:"));
		contentPane.add(dateChooser2);

		btnRefresh = new JButton("Atualizar");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// atualizar table
				limparTabela();

				horaExtra70 = Duration.ZERO;
				horaExtra110 = Duration.ZERO;
				limparCamposData();
				setTitle("Controle de Jornada por período " + Utils.converterFormatoData(comeco) + " - "
						+ Utils.converterFormatoData(fim));
				listar(comeco, fim);

			}
		});
		btnRefresh.setBounds(505, 11, 89, 31);
		contentPane.add(btnRefresh);

		String titulo = Utils.converterFormatoData(comeco);

		btnGerarPdf = new JButton("Gerar pdf");
		btnGerarPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Obter os dados da tabela
				List<List<Object>> dados = getDadosTabela();

				// Obter os títulos das colunas
				List<Object> titulosColunas = new ArrayList<>();
				for (int i = 0; i < model.getColumnCount(); i++) {
					titulosColunas.add(model.getColumnName(i));
				}

				// Criar uma instância de GeradorPDF e gerar o PDF
				GeradorPDF geradorPDF = new GeradorPDF();
				geradorPDF.gerarPDF(titulo, titulosColunas, dados);
			}
		});

		btnGerarPdf.setBounds(617, 11, 89, 28);
		contentPane.add(btnGerarPdf);

		// listarTodos();
	}

	private void abrirModeloJornada(int idJornada, Date datJornada, String startJornada, String endJornada,
			String startAlmoco, String endAlmoco, int porcentagem) {
		modeloJornada = new ModeloJornada(idJornada, datJornada, startJornada, endJornada, startAlmoco, endAlmoco,
				porcentagem);
		modeloJornada.setJanelaPrincipal(this);
		modeloJornada.setVisible(true);
		setBotoesEnabled(false);
	}

	public Integer getID() {
		return idJornada;
	}

	public void excluir() {
		jornadaController.excluirJornada(idJornada);
	}

	public void atualizaTable() {
		listar(comeco, fim);
	}

	int diasTrabalhado = 0;
	private JButton btnGerarPdf;

	public void listar(LocalDateTime startDate, LocalDateTime endDate) {
		limparTabela();
		List<JornadaTrabalho> jornadas = jornadaController.listarPorPeriodo(startDate, endDate);

		for (JornadaTrabalho jornada : jornadas) {
			diasTrabalhado++;
			int idJornada = jornada.getId();

			Date datJornada = jornada.getDatJornada();
			startJornada = jornada.getStartJornada().toLocalDateTime();
			endJornada = jornada.getEndJornada().toLocalDateTime();
			startAlmoco = jornada.getStartAlmoco().toLocalDateTime();
			endAlmoco = jornada.getEndAlmoco().toLocalDateTime();
			porcentagem = jornada.getPorcentagem();

			Duration totalJornada = Duration.between(startJornada, endJornada);
			Duration totalAlmoco = Duration.between(startAlmoco, endAlmoco);
			Duration tempoTrabalhado = totalJornada.minus(totalAlmoco);
			Duration horaPadrao = Duration.ofHours(8).plusMinutes(48);

			if (porcentagem == 70) {
				hora70 = true;
				resultado = tempoTrabalhado.minus(horaPadrao);
				horaExtra70 = horaExtra70.plus(resultado);
			}

			if (porcentagem == 110) {
				hora110 = true;
				resultado = tempoTrabalhado;
				horaExtra110 = horaExtra110.plus(resultado);
			}

			// Verificar se a duração é zero
			if (resultado.isZero() || resultado.isNegative()) {

				// Definir "N/A" na tabela quando a duração for zero
				model.addRow(new Object[] { idJornada, utils.converterFormatoDate(datJornada),
						utils.formatarHora(startJornada), utils.formatarHora(endJornada),
						utils.formatarHora(startAlmoco), utils.formatarHora(endAlmoco), "N/A", porcentagem });
			} else {
				// Caso contrário, formatar a duração como hora:minuto e definir na tabela
				String formattedDuration = String.format("%02d:%02d", resultado.toHours(), resultado.toMinutesPart());
				model.addRow(new Object[] { idJornada, utils.converterFormatoDate(datJornada),
						utils.formatarHora(startJornada), utils.formatarHora(endJornada),
						utils.formatarHora(startAlmoco), utils.formatarHora(endAlmoco), formattedDuration,
						porcentagem });
			}

		}

		model.addRow(new Object[] { "***************", "***************", "**************", "***************",
				"***************", "***************", "***************", "***************" });

		if (hora70) {
			model.addRow(new Object[] { "", "", "", "", "", "Hr 70%", utils.formatarDuration(horaExtra70), "" });

		}

		if (hora110) {
			model.addRow(new Object[] { "", "", "", "", "", "Hr 110%", utils.formatarDuration(horaExtra110), "" });

		} else {
			model.addRow(new Object[] { "", "", "", "", "", "Hr 110%", "00:00", "" });

		}
		Duration totalHora = Duration.ZERO;

		totalHora = horaExtra110.plus(horaExtra70);

		String msg = "";
		if (diasTrabalhado > 1) {
			msg = " dias";
		} else {
			msg = " dia";
		}

		model.addRow(new Object[] { "Dias trabalhado", diasTrabalhado + msg, "", "", "", "Total hora",
				utils.formatarDuration(totalHora), "" });
		double valorReceber70 = 22.12 * horaExtra110.toHours();
		double valorReceber110 = 28.15 * horaExtra70.toHours();
		double totalReceber = valorReceber70 + valorReceber110;

//		model.addRow(
//				new Object[] { "", "", "", "", "", "Vlr a receber 70", String.format("%.2f", valorReceber70), "" });
//		model.addRow(
//				new Object[] { "", "", "", "", "", "Vlr a receber 110", String.format("%.2f", valorReceber110), "" });
//		model.addRow(new Object[] { "", "", "", "", "", "Total a receber", String.format("%.2f", totalReceber), "" });
//		diasTrabalhado = 0;
		model.fireTableDataChanged();
	}

	public void tabelaAtualizada(List<JornadaTrabalho> jornadas) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		for (JornadaTrabalho jornada : jornadas) {

			int idJornada = jornada.getId();
			Date datJornada = jornada.getDatJornada();
			startJornada = jornada.getStartJornada().toLocalDateTime();
			endJornada = jornada.getEndJornada().toLocalDateTime();
			startAlmoco = jornada.getStartAlmoco().toLocalDateTime();
			endAlmoco = jornada.getEndAlmoco().toLocalDateTime();
			porcentagem = jornada.getPorcentagem();

			Duration totalJornada = Duration.between(startJornada, endJornada);
			Duration totalAlmoco = Duration.between(startAlmoco, endAlmoco);
			Duration tempoTrabalhado = totalJornada.minus(totalAlmoco);
			Duration horaPadrao = Duration.ofHours(8).plusMinutes(48);

			if (porcentagem == 70) {
				hora70 = true;
				resultado = tempoTrabalhado.minus(horaPadrao);
				horaExtra70 = horaExtra70.plus(resultado);
			}

			if (porcentagem == 110) {
				hora110 = true;
				resultado = tempoTrabalhado;
				horaExtra110 = horaExtra110.plus(resultado);
			}

			if (!jornadaAdicionada(jornada)) {
				modelo.addRow(new Object[] { utils.converterFormatoDate(datJornada), utils.formatarHora(startJornada),
						utils.formatarHora(endJornada), utils.formatarHora(startAlmoco), utils.formatarHora(endAlmoco),
						utils.formatarDuration(resultado), porcentagem });
			}
		}

		modelo.addRow(new Object[] { "***************", "***************", "***************", "***************",
				"***************", "***************", "*******", "***************" });

		if (hora70) {
			model.addRow(
					new Object[] { "", "", "", "", "", "Hora extra 70%", utils.formatarDuration(horaExtra70), "" });

		}

		if (hora110) {
			model.addRow(
					new Object[] { "", "", "", "", "", "Hora extra 110%", utils.formatarDuration(horaExtra110), "" });

		} else {
			modelo.addRow(new Object[] { "", "", "", "", "", "Hora extra 110%", "00:00", "" });

		}
		Duration totalHora = Duration.ZERO;

		totalHora = horaExtra110.plus(horaExtra70);

		modelo.addRow(new Object[] { "", "", "", "", "", "Total hora", utils.formatarDuration(totalHora), "" });

		modelo.fireTableDataChanged();
	}

	private boolean jornadaAdicionada(JornadaTrabalho jornada) {
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			LocalDateTime existingDatJornada = Utils.converterFormatoData((String) model.getValueAt(i, 1));
			LocalDateTime jornadaEndJornada = jornada.getEndJornada().toLocalDateTime();

			if (existingDatJornada.equals(jornadaEndJornada)) {
				return true;
			}
		}
		return false;
	}

	public void setBotoesEnabled(boolean enabled) {
		btnCadastrar.setEnabled(enabled);
		btnRefresh.setEnabled(enabled);
		btnPesquisar.setEnabled(enabled);
		dateChooser1.setEnabled(enabled);
		dateChooser2.setEnabled(enabled);
	}

	private void limparCamposData() {
		dateChooser1.setDate(null);
		dateChooser2.setDate(null);
	}

	public void limparTabela() {
		horaExtra70 = Duration.ZERO;
		horaExtra110 = Duration.ZERO;

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

	}

	public void iniciar() {
		setVisible(true);
	}

	public void listarTodos() {
		List<JornadaTrabalho> dadosDoBanco = jornadaController.listarTodasJornadas();

		for (JornadaTrabalho j : dadosDoBanco) {
			System.out.println("Dados do banco:" + j);
		}

	}

	public void updateTable() {
		// Lógica para obter dados do banco de dados (substitua isso pelo seu código
		// real)
		List<JornadaTrabalho> dadosDoBanco = jornadaController.listarTodasJornadas();

		// Cria um novo modelo de tabela
		DefaultTableModel modeloTabela = new DefaultTableModel();

		// Adiciona colunas ao modelo (substitua pelos nomes reais das colunas)
		modeloTabela.addColumn("ID");
		modeloTabela.addColumn("Data Jornada");
		modeloTabela.addColumn("Início Jornada");
		modeloTabela.addColumn("Fim Jornada");
		modeloTabela.addColumn("Início Almoço");
		modeloTabela.addColumn("Fim Almoço");
		modeloTabela.addColumn("Porcentagem");

		// Adiciona linhas ao modelo
		for (JornadaTrabalho jornada : dadosDoBanco) {
			Object[] linha = { jornada.getId(), jornada.getDatJornada(), jornada.getStartJornada(),
					jornada.getEndJornada(), jornada.getStartAlmoco(), jornada.getEndAlmoco(),
					jornada.getPorcentagem() };
			modeloTabela.addRow(linha);
		}

		// Define o novo modelo para a tabela
		table.setModel(modeloTabela);
	}

	// Método para obter os dados da tabela na classe JanelaPrincipal
	public List<List<Object>> getDadosTabela() {
		List<List<Object>> dados = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		int columnCount = model.getColumnCount();
		for (int i = 0; i < rowCount; i++) {
			List<Object> row = new ArrayList<>();
			for (int j = 0; j < columnCount; j++) {
				row.add(model.getValueAt(i, j));
			}
			dados.add(row);
		}
		return dados;
	}

}
