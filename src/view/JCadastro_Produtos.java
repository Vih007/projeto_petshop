package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import dao.ProdutoDao;
import model.Cliente;
import model.Produto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.List;

public class JCadastro_Produtos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_NomeProduto;
	private JTextField textField_Preco;
	private JTextField textField_Categoria;
	private JTextField textField_Quantidade;
	private JTextField textField_Descricao;
	private JTextField textFieldQuantMin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCadastro_Produtos frame = new JCadastro_Produtos(null, null);
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
	public JCadastro_Produtos(Produto produtoSelecionado, venda_produtos jvenda_produtos) {
		ProdutoDao produtodao = new ProdutoDao();
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 370);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(226, 255, 255));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_NomeProduto = new JLabel("Nome_Produto");
		lblNewLabel_NomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_NomeProduto.setBounds(24, 39, 90, 13);
		contentPane.add(lblNewLabel_NomeProduto);
		
		textField_NomeProduto = new JTextField();
		textField_NomeProduto.setBounds(24, 62, 231, 30);
		contentPane.add(textField_NomeProduto);
		textField_NomeProduto.setColumns(10);
		
		JLabel lblNewLabelPreco = new JLabel("Preço");
		lblNewLabelPreco.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabelPreco.setBounds(304, 39, 45, 13);
		contentPane.add(lblNewLabelPreco);
		
		textField_Preco = new JTextField();
		textField_Preco.setBounds(301, 62, 118, 30);
		contentPane.add(textField_Preco);
		textField_Preco.setColumns(10);
		
		
		JLabel lblNewLabel_Categoria = new JLabel("Categoria");
		lblNewLabel_Categoria.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_Categoria.setBounds(196, 107, 80, 13);
		contentPane.add(lblNewLabel_Categoria);
		
		textField_Categoria = new JTextField();
		textField_Categoria.setBounds(196, 130, 223, 30);
		contentPane.add(textField_Categoria);
		textField_Categoria.setColumns(10);
		
		JLabel lblNewLabel_Quantidade = new JLabel("Quantidade");
		lblNewLabel_Quantidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_Quantidade.setBounds(24, 110, 80, 13);
		contentPane.add(lblNewLabel_Quantidade);
		
		textField_Quantidade = new JTextField();
		textField_Quantidade.setBounds(24, 130, 143, 30);
		contentPane.add(textField_Quantidade);
		textField_Quantidade.setColumns(10);
		
		JLabel lblNewLabelDescricao = new JLabel("Descrição");
		lblNewLabelDescricao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabelDescricao.setBounds(24, 189, 90, 13);
		contentPane.add(lblNewLabelDescricao);
		
		textField_Descricao = new JTextField();
		textField_Descricao.setBounds(24, 212, 252, 30);
		contentPane.add(textField_Descricao);
		textField_Descricao.setColumns(10);
		
		JButton btnNewButton_Incluir = new JButton(produtoSelecionado==null?"Incluir":"Alterar");
		btnNewButton_Incluir.setBackground(new Color(255, 255, 255));
		btnNewButton_Incluir.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton_Incluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produto produto = new Produto(null, textField_NomeProduto.getText(),
				        textField_Preco.getText(), textField_Quantidade.getText(),
				        textField_Categoria.getText(), textField_Descricao.getText(), textFieldQuantMin.getText());
				
				if (produtoSelecionado == null) {
				    if (!"".equalsIgnoreCase(textField_Preco.getText()) && !"".equalsIgnoreCase(textField_NomeProduto.getText())) {
				        produtodao.cadastrarProduto(produto); // Aqui, passamos o novo objeto 'produto'
				        abrirTelaVenda(jvenda_produtos);
				    } else {
				        JOptionPane.showMessageDialog(null, "Confira os campos Codigo e Nome_Produto");
				    }
				} else {
				    if (!"".equalsIgnoreCase(textField_Preco.getText()) && !"".equalsIgnoreCase(textField_NomeProduto.getText())) {
				        produtodao.alterarProduto(produtoSelecionado.getCodigo(), produto);
				        abrirTelaVenda(jvenda_produtos);
				    } else {
				        JOptionPane.showMessageDialog(null, "Confira os campos Codigo e Nome_Produto");
				    }
				}
			}
		});
		
		btnNewButton_Incluir.setBounds(329, 285, 90, 25);
		contentPane.add(btnNewButton_Incluir);
		
		JButton btnNewButtonExcluir = new JButton("Excluir");
		btnNewButtonExcluir.setBackground(new Color(255, 0, 0));
		btnNewButtonExcluir.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButtonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				produtodao.excluirProduto(produtoSelecionado.getCodigo());
				abrirTelaVenda(jvenda_produtos);
			}
		});
		btnNewButtonExcluir.setBounds(24, 285, 90, 25);
		btnNewButtonExcluir.setVisible(false);
		contentPane.add(btnNewButtonExcluir);
		
		JLabel lblNewLabelQuantMin = new JLabel("Quantidade Minima");
		lblNewLabelQuantMin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabelQuantMin.setBounds(301, 189, 118, 13);
		contentPane.add(lblNewLabelQuantMin);
		
		textFieldQuantMin = new JTextField();
		textFieldQuantMin.setBounds(304, 214, 115, 25);
		contentPane.add(textFieldQuantMin);
		textFieldQuantMin.setColumns(10);
		
		if(produtoSelecionado!=null){
			preencherCampos(produtoSelecionado);
			btnNewButtonExcluir.setVisible(true);
		}
	}
	private void abrirTelaVenda(venda_produtos jvenda_produtos) {
		jvenda_produtos.dispose();
		dispose();
		jvenda_produtos = new venda_produtos();
		jvenda_produtos.setLocationRelativeTo(jvenda_produtos);
		jvenda_produtos.setVisible(true);
	}
	private void preencherCampos(Produto produtoSelecionado) {
	    textField_NomeProduto.setText(produtoSelecionado.getNome());
	    textField_Preco.setText(produtoSelecionado.getPreco());
	    textField_Quantidade.setText(produtoSelecionado.getQuantidadeEmEstoque()); // Converte int para String
	    textField_Categoria.setText(produtoSelecionado.getCategoria());
	    textField_Descricao.setText(produtoSelecionado.getDescricao());
	    textFieldQuantMin.setText(produtoSelecionado.getQuantidadeMinima());
	}
}

