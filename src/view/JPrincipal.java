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


import model.Cliente;
import model.ModeloTabela;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;



import dao.Dao;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class JPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldBusca;
	private ArrayList<Cliente> clientes;
	private JTable table;
	private JPrincipal jPrincipal;
	private TableRowSorter<ModeloTabela> rowSorter;
	private JButton btnNewButton_1;
	private JServico jservico;

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
					JPrincipal frame = new JPrincipal();
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
	public JPrincipal() {
		
		this.jPrincipal = this;
		Dao dao = new Dao();
		try {
			clientes = dao.listarCliente();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 746, 479);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(226, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCadastro jCadastro = new JCadastro(null, jPrincipal);
				jCadastro.setLocationRelativeTo(jCadastro);
				jCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				jCadastro.setVisible(true);
			}
		});
		btnNewButton.setBounds(29, 39, 100, 23);
		contentPane.add(btnNewButton);
		
		textFieldBusca = new JTextField();
		textFieldBusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				filtrar();
			}

		
		});
		textFieldBusca.setBounds(139, 39, 418, 22);
		contentPane.add(textFieldBusca);
		textFieldBusca.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 88, 665, 291);
		contentPane.add(scrollPane);
		
		ModeloTabela modeloTabela = new ModeloTabela(clientes);
		
		
		table = new JTable();
		table.setBackground(new Color(141, 222, 206));
		table.setModel(modeloTabela);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1) {
					try {
						Cliente clienteSelecionado = dao.consultarCliente(modeloTabela.getValueAt(table.getSelectedRow(),0).toString());
						JCadastro jCadastro = new JCadastro(clienteSelecionado, jPrincipal);
						jCadastro.setLocationRelativeTo(jCadastro);
						jCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						jCadastro.setVisible(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		rowSorter = new TableRowSorter<>(modeloTabela);
		table.setRowSorter(rowSorter);
		scrollPane.setViewportView(table);
		
		btnNewButton_1 = new JButton("Marcar Serviço Banho_Tosa");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JServico jservico = new JServico();
				jservico.setLocationRelativeTo(jservico);
				jservico.setVisible(true);
				}
			
		});
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBounds(517, 396, 177, 25);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_produto = new JButton("Produtos");
		btnNewButton_produto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				venda_produtos jvenda_produto = new venda_produtos();
				jvenda_produto.setLocationRelativeTo(jvenda_produto);
				jvenda_produto.setVisible(true);
				
			}
		});
		btnNewButton_produto.setBackground(new Color(255, 255, 255));
		btnNewButton_produto.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton_produto.setBounds(422, 396, 85, 25);
		contentPane.add(btnNewButton_produto);
		
		
	}
	
	private void filtrar() {
		String busca = textFieldBusca.getText().trim();
		
		if(busca.length()==0) {
			rowSorter.setRowFilter(null);
		}else {
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+busca));
		}
		
	}
}