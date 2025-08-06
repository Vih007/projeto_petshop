package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.Dao;
import dao.ProdutoDao;
import dao.VendaDao;
import model.Cliente;
import model.ItemVenda;
import model.ModeloTabelaProduto;
import model.Produto;
import model.Venda;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;

public class venda_produtos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldBusca;
	private JTable table;
	private ArrayList<Produto> produto;
	private JScrollPane scrollPane;
	private venda_produtos jvenda_produtos;
	private TableRowSorter<ModeloTabelaProduto> rowSorter;
	private ArrayList<ItemVenda> itensVenda = new ArrayList<>();
	private JButton btnNewButton_Compra;
	private JCompra jcompra;
	private JButton btnNewButton_Voltar;
	
	

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
					venda_produtos frame = new venda_produtos();
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
	public venda_produtos() {
		this.jvenda_produtos= this;
		ProdutoDao produtodao = new ProdutoDao();
		try {
			produto = produtodao.listarProduto();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 494);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(226, 255, 255));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCadastro_Produtos jprodutos = new JCadastro_Produtos(null, jvenda_produtos);
				jprodutos.setLocationRelativeTo(jprodutos);
				jprodutos.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				jprodutos.setVisible(true);

			}
		});
		btnNewButton.setBounds(23, 31, 85, 30);
		contentPane.add(btnNewButton);
		
		textFieldBusca = new JTextField();
		textFieldBusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				filtrar();
			}
		});
		textFieldBusca.setBounds(124, 32, 367, 30);
		contentPane.add(textFieldBusca);
		textFieldBusca.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 103, 660, 286);
		contentPane.add(scrollPane);
		
		ModeloTabelaProduto modeloProduto = new ModeloTabelaProduto(produto);
		
		table = new JTable();
		table.setModel(modeloProduto);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
				    try {
				        Produto produtoSelecionado = produtodao.consultarProduto(modeloProduto.getValueAt(table.getSelectedRow(), 0).toString());
				        JCadastro_Produtos jproduto = new JCadastro_Produtos(produtoSelecionado, jvenda_produtos);
				        jproduto.setLocationRelativeTo(jproduto);
				        jproduto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				        jproduto.setVisible(true);
				    } catch (Exception e1) {
				        e1.printStackTrace();
				    }
				}
			}

				    
		});
		
		rowSorter = new TableRowSorter<>(modeloProduto);
		table.setRowSorter(rowSorter);;
		scrollPane.setViewportView(table);
		
		btnNewButton_Compra = new JButton("Realizar Compra");
		btnNewButton_Compra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JCompra jcompra = new JCompra();
				jcompra.setLocationRelativeTo(jcompra);
				jcompra.setVisible(true);
				
			}
		});
		btnNewButton_Compra.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton_Compra.setBounds(560, 415, 123, 25);
		contentPane.add(btnNewButton_Compra);
		
		btnNewButton_Voltar = new JButton("Voltar");
		btnNewButton_Voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPrincipal jprincipal = new JPrincipal();
				jprincipal.setVisible(true);
				dispose();
			}
		});
		btnNewButton_Voltar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton_Voltar.setBackground(new Color(255, 255, 255));
		btnNewButton_Voltar.setBounds(23, 415, 85, 25);
		contentPane.add(btnNewButton_Voltar);
		
		
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
	
