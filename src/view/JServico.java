package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;

import model.Servico;
import model.ModeloTabelaServico;
import dao.Dao;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class JServico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_Pesquisa;
	private TableRowSorter<ModeloTabelaServico> rowSorter;
	private ArrayList<Servico> servicos;
	private JServico jServico;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnNewButtonVoltar;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        	System.err.println(ex);
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JServico frame = new JServico();
					frame.setLocationRelativeTo(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JServico() {
		this.jServico = this;
		Dao dao = new Dao();
		try {
			servicos = dao.listarServico();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 723, 494);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(226, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Botão para marcar um novo serviço
		JButton btnNewButton_Marcar = new JButton("Marcar");
		btnNewButton_Marcar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCadastro_Servico jCadastro = new JCadastro_Servico(null, jServico);
				jCadastro.setLocationRelativeTo(jServico);
				jCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				jCadastro.setVisible(true);
		
			}
		});
		btnNewButton_Marcar.setBounds(39, 32, 85, 27);
		contentPane.add(btnNewButton_Marcar);
		
		
		textField_Pesquisa = new JTextField();
		textField_Pesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				filtrar();
			}
		});
		
		textField_Pesquisa.setBounds(130, 32, 463, 27);
		contentPane.add(textField_Pesquisa);
		textField_Pesquisa.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 104, 621, 304);
		contentPane.add(scrollPane);
		
		ModeloTabelaServico modeloServico = new ModeloTabelaServico(servicos);
		
		table = new JTable();
		table.setModel(modeloServico);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1) {
					try {
						Servico servicoSelecinado = dao.consultarServico(modeloServico.getValueAt(table.getSelectedRow(),0).toString());
						JCadastro_Servico jCadastro = new JCadastro_Servico(servicoSelecinado, jServico);
						jCadastro.setLocationRelativeTo(jServico);
						jCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						jCadastro.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		
		rowSorter = new TableRowSorter<>(modeloServico);
		table.setRowSorter(rowSorter);
		scrollPane.setViewportView(table);
		
		btnNewButtonVoltar = new JButton("Voltar");
		btnNewButtonVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPrincipal jprincipal = new JPrincipal();
				jprincipal.setVisible(true);
				dispose();
			}
		});
		btnNewButtonVoltar.setBackground(new Color(255, 255, 255));
		btnNewButtonVoltar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButtonVoltar.setBounds(39, 422, 85, 25);
		contentPane.add(btnNewButtonVoltar);
	}

	
	private void filtrar() {
		String busca = textField_Pesquisa.getText().trim();

		if (busca.length() == 0) {
			rowSorter.setRowFilter(null);
		} else {
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + busca));
		}
	}
}