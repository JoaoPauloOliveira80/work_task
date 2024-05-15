package APPLICATION.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import APPLICATION.controller.JornadaController;
import APPLICATION.grafic.JanelaPrincipal;
import APPLICATION.utils.PlaceholderTextField;
import APPLICATION.utils.Utils;



public class ModeloJornada extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldID;
	private JTextField txtStartJornada;
	private JTextField txtEndJornada;
	private JTextField txtStartAlmoco;
	private JTextField txtEndAlmoco;
	private JTextField txtPorcentagem;
	private JTextField txtData;
	private Utils utils = new Utils();
	private JanelaPrincipal janela;
	private JButton btnAlterar;
	private JButton btnCadastro;
	private JButton btnExcluir;
	private JornadaController jornadaController;
	private List<ActionListener> incluirListeners = new ArrayList<>();

	String msg = "";

	public void setJornadaController(JornadaController jornadaController, JanelaPrincipal janela) {
		this.jornadaController = jornadaController;
		this.janela = janela;

	}

	public ModeloJornada() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponents();
	}

	public ModeloJornada(JanelaPrincipal janela) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.janela = janela;
		initComponents();
	}

	public ModeloJornada(int idJornada, Date datJornada, String startJornada, String endJornada, String startAlmoco,
			String endAlmoco, int porcentagem) {
		initComponents();
		atualizarDados(idJornada, datJornada, startJornada, endJornada, startAlmoco, endAlmoco, porcentagem);

	}

	public void atualizarDados(int idJornada, Date datJornada, String startJornada, String endJornada,
			String startAlmoco, String endAlmoco, int porcentagem) {
		textFieldID.setText(String.valueOf(idJornada));
		txtData.setText(datJornada.toString());
		txtStartJornada.setText(startJornada);
		txtEndJornada.setText(endJornada);
		txtStartAlmoco.setText(startAlmoco);
		txtEndAlmoco.setText(endAlmoco);
		txtPorcentagem.setText(String.valueOf(porcentagem));
	}

	public void setJanelaPrincipal(JanelaPrincipal janelaPrincipal) {
		this.janela = janelaPrincipal;
	}

	private void initComponents() {
		
		setSize(670, 265);
		this.jornadaController = new JornadaController();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (janela != null) {
					janela.setBotoesEnabled(true);
					janela.setLocationRelativeTo(null);
//		            System.out.println("fechou");
				}
				dispose(); // Certifique-se de chamar dispose() após setar os botões como true
			}
		});

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtData = new PlaceholderTextField("00/00/0000");
		txtData.setBounds(144, 61, 86, 20);
		contentPane.add(txtData);

		JLabel lblNewLabel = new JLabel("Data Jornada");
		lblNewLabel.setBounds(144, 42, 90, 14);
		contentPane.add(lblNewLabel);

		textFieldID = new JTextField();
		textFieldID.setBounds(34, 62, 62, 18);
		textFieldID.setEditable(false);
		contentPane.add(textFieldID);

		txtStartJornada = new PlaceholderTextField("00:00");
		txtStartJornada.setBounds(34, 119, 90, 18);
		contentPane.add(txtStartJornada);

		JLabel label_1 = new JLabel("Início Jornada");
		label_1.setBounds(34, 91, 90, 18);
		contentPane.add(label_1);

		txtEndJornada = new PlaceholderTextField("00:00");
		txtEndJornada.setBounds(144, 119, 90, 18);
		contentPane.add(txtEndJornada);

		JLabel label_2 = new JLabel("Fim Jornada");
		label_2.setBounds(144, 91, 90, 18);
		contentPane.add(label_2);

		txtStartAlmoco = new PlaceholderTextField("");
		txtStartAlmoco.setBounds(261, 119, 95, 18);
		contentPane.add(txtStartAlmoco);

		JLabel label_3 = new JLabel("Início almoço");
		label_3.setBounds(266, 91, 90, 18);
		contentPane.add(label_3);

		txtEndAlmoco = new PlaceholderTextField("");
		txtEndAlmoco.setBounds(389, 119, 95, 18);
		contentPane.add(txtEndAlmoco);

		JLabel label_4 = new JLabel("Fim almoço");
		label_4.setBounds(389, 90, 90, 18);
		contentPane.add(label_4);

		txtPorcentagem = new JTextField();
		txtPorcentagem.setBounds(515, 119, 95, 18);
		contentPane.add(txtPorcentagem);

		JLabel label_5 = new JLabel("Porcentagem");
		label_5.setBounds(515, 90, 79, 18);
		contentPane.add(label_5);

		btnAlterar = new JButton("Atualizar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    inserirOuAtualizarJornad();
			}
		});
		btnAlterar.setBounds(34, 172, 269, 32);
		contentPane.add(btnAlterar);

	btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int id = janela.getID();
		        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o ID: " + id,
		                "Confirmação", JOptionPane.YES_NO_OPTION);
		        if (resposta == JOptionPane.YES_OPTION) {
		            excluirJornada();
		            
		        } else {
		            // O usuário cancelou a exclusão
		            msg = "Exclusão cancelada.";
		            utils.messageCrud(msg);
		        }
		    }
		});


		btnExcluir.setBounds(341, 172, 269, 32);
		contentPane.add(btnExcluir);

		JLabel lblNewLabel_1 = new JLabel("Cod. Registro");
		lblNewLabel_1.setBounds(36, 42, 79, 14);
		contentPane.add(lblNewLabel_1);
		
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = hoje.format(formatter);
		
		txtData.setText(dataFormatada);
		txtStartJornada.setText("07:00");
		txtEndJornada.setText("17:00");
		txtStartAlmoco.setText("11:00");
		txtEndAlmoco.setText("12:00");
		txtPorcentagem.setText("70");

	}
	
	public void setBotoesEnabled(boolean enabled) {
		btnExcluir.setEnabled(enabled);
		
	}

	public void configurarBotaoAlterar(ActionListener alterarListener) {
	    btnAlterar.setText("Atualizar");
	    btnAlterar.setActionCommand("Atualizar");
	    btnAlterar.removeActionListener(btnAlterar.getActionListeners()[0]);

	    btnAlterar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Chamar o método para atualizar a jornada de trabalho
	            atualizarJornada();
	            
	            // Disparar eventos para outros ouvintes, se necessário
	            for (ActionListener listener : incluirListeners) {
	                listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
	            }
	        }
	    });
	}

	public void configurarBotaoIncluir(ActionListener incluirListener) {
	    btnAlterar.setText("Incluir");
	    btnAlterar.setActionCommand("Incluir");
	    btnAlterar.removeActionListener(btnAlterar.getActionListeners()[0]); // Remover o listener anterior

	    btnAlterar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Chamar o método para inserir a nova jornada de trabalho
	            inserirJornada();
	            
	            // Habilitar os botões, etc.
	            janela.setBotoesEnabled(true);
	            
	            // Disparar eventos para outros ouvintes, se necessário
	            for (ActionListener listener : incluirListeners) {
	                listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
	            }
	        }
	    });

	    // Adicionar o novo ouvinte à lista de ouvintes
	    incluirListeners.add(incluirListener);
	}



	public void atualizarJornada() {
	    try {
	        int id = janela.getID();

	        // Verificar se o ID é válido
	        if (id > 0) {
	            // Coletar os novos dados da interface do usuário
	            
	        	Date datJornada = Utils.convertStringToSqlDate(txtData.getText());

	            Timestamp startJornada = Utils.converterStringParaTimestamp(txtStartJornada.getText());
	            Timestamp endJornada = Utils.converterStringParaTimestamp(txtEndJornada.getText());
	            Timestamp startAlmoco = Utils.converterStringParaTimestamp(txtStartAlmoco.getText());
	            Timestamp endAlmoco = Utils.converterStringParaTimestamp(txtEndAlmoco.getText());
	            int porcentagem = Integer.parseInt(txtPorcentagem.getText());

	            // Criar uma instância de JornadaTrabalho com os novos dados
	            JornadaTrabalho jornadaAtualizada = new JornadaTrabalho();
	            jornadaAtualizada.setId(id);
	            jornadaAtualizada.setDatJornada(datJornada);
	            jornadaAtualizada.setStartJornada(startJornada);
	            jornadaAtualizada.setEndJornada(endJornada);
	            jornadaAtualizada.setStartAlmoco(startAlmoco);
	            jornadaAtualizada.setEndAlmoco(endAlmoco);
	            jornadaAtualizada.setPorcentagem(porcentagem);

	            // Chamar o método atualizar da JornadaService
	            jornadaController.atualizarTabela(jornadaAtualizada);
	            dispose();
	            janela.atualizaTable();

	            if (janela != null) {
	                janela.setBotoesEnabled(true);
	                janela.setVisible(true);
	            }

	        } else {
	            // Lógica para lidar com um ID inválido, se necessário
	            JOptionPane.showInternalMessageDialog(null, "ID inválido.");
	        }
	    } catch (Exception e) {
	        // Lidar com exceções específicas ou imprimir informações de depuração
	        e.printStackTrace();
	    }
	}

	//formata data para yyyy-mm-dd
	// Função para converter a data de "dd/MM/yyyy" para java.sql.Date
    public static Date converterData(String dataOriginal) {
        SimpleDateFormat formatoOriginal = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
            java.util.Date utilDate = formatoOriginal.parse(dataOriginal); // Convertendo o texto original para um objeto java.util.Date
            return new java.sql.Date(utilDate.getTime()); // Criando um java.sql.Date a partir do java.util.Date
        } catch (ParseException e) {
            e.printStackTrace(); // Tratamento de exceção, caso ocorra um erro na conversão
            return null; // Retornando null em caso de erro
        }
    }



	public void inserirJornada() {

		if (jornadaController != null) {
			JornadaTrabalho jornada = new JornadaTrabalho();
			// Coletar dados necessrios para a jornada
	       	String converterDate = txtData.getText();			
	       	java.util.Date dat = converterData(converterDate);
	       	Date datJornada = new Date(dat.getTime());
			
			Timestamp startJornada = Utils.converterStringParaTimestamp(txtStartJornada.getText());
			Timestamp endJornada = Utils.converterStringParaTimestamp(txtEndJornada.getText());
			Timestamp startAlmoco = Utils.converterStringParaTimestamp(txtStartAlmoco.getText());
			Timestamp endAlmoco = Utils.converterStringParaTimestamp(txtEndAlmoco.getText());
			int porcentagem = Integer.parseInt(txtPorcentagem.getText());

			// Criar uma instncia de JornadaTrabalho com os dados coletados
			JornadaTrabalho novaJornada = new JornadaTrabalho(datJornada, startJornada, endJornada, startAlmoco,
					endAlmoco, porcentagem);

			// Chamar o mtodo correspondente na JornadaController
			jornadaController.inserirJornada(novaJornada);
			dispose();
			janela.limparTabela();
			janela.atualizaTable();

		} else {
			msg = "jornadaController  nulo. Certifique-se de inicializ-lo corretamente.";
            utils.messageCrud(msg);
		}
	}

	
	
	public void excluirJornada() {
	    try {
	        int id = janela.getID();

	        // Verificar se o ID é válido
	        if (id > 0) {
	            // Chamar o método excluirJornada da JornadaController
	            jornadaController.excluirJornada(id);
	            janela.limparTabela();
	            
	            janela.atualizaTable();
	            
	            if (janela != null) {
	                janela.setBotoesEnabled(true);
	                janela.setVisible(true);
	            }

	            // Lógica adicional, se necessário
	        } else {
	            msg = "ID inválido.";
	            utils.messageCrud(msg);
	        }
	    } catch (Exception e) {
	        // Lidar com exceções específicas ou imprimir informações de depuração
	        e.printStackTrace();
	    }
	}
	
	public void inserirOuAtualizarJornad() {
		int id = janela.getID();

		// Verificar se o ID é válido
		if (id > 0) {
			atualizarJornada();
		} else {
			inserirJornada();

		}
	}

}
