package view;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.ProdutoDao;
import dao.VendaDao;
import model.ItemVenda;
import model.ModeloTabelaProduto;
import model.Produto;
import model.Venda;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.Font;

public class JCompra extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldNome;
	private JTextField textField_Quantidade;
	private JTextField textField_Preco;
	private JTextField textField_Subtotal;
	private ArrayList<Produto> produto;
	private JTextField textFieldBusca;
	private TableRowSorter<ModeloTabelaProduto> rowSorter;
	private ArrayList<ItemVenda> itensVenda = new ArrayList<>();
	private venda_produtos jvenda_produtos;
	private JTable table_1;
	private JCompra jcompra;
	
	
	private void atualizarTotal() {
        double total = 0;
        for (ItemVenda item : itensVenda) {
            total += item.getSubtotal();
        }
        textField_Subtotal.setText(String.format("%.2f", total));
    }
	

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
					JCompra frame = new JCompra();
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
	public JCompra() {
		 ProdutoDao produtoDao = new ProdutoDao();
	        try {
	            produto = produtoDao.listarProduto();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        ModeloTabelaProduto modeloProduto = new ModeloTabelaProduto(produto);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 879, 533);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(226, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		JPanel panel = new JPanel();
		panel.setBackground(new Color(141, 222, 206));
		panel.setBounds(581, 165, 260, 257);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel.setBounds(15, 19, 52, 13);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Quantidade");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel_1.setBackground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(154, 19, 67, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Preço");
		lblNewLabel_2.setBackground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(15, 68, 45, 13);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("SubTotal");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(143, 68, 67, 13);
		panel.add(lblNewLabel_3);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(15, 33, 125, 25);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		textField_Quantidade = new JTextField();
		textField_Quantidade.setBounds(154, 33, 96, 25);
		panel.add(textField_Quantidade);
		textField_Quantidade.setColumns(10);
		
		textField_Preco = new JTextField();
		textField_Preco.setBounds(15, 82, 107, 25);
		panel.add(textField_Preco);
		textField_Preco.setColumns(10);
		
		textField_Subtotal = new JTextField();
		textField_Subtotal.setBounds(139, 82, 111, 25);
		panel.add(textField_Subtotal);
		textField_Subtotal.setColumns(10);
		
		JButton btnNewButtonFinalizar = new JButton("Finalizar compra");
		btnNewButtonFinalizar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButtonFinalizar.setBackground(new Color(255, 255, 255));
		btnNewButtonFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (itensVenda.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Nenhum item foi adicionado à compra!");
		        } else {
		            // Aqui você pode colocar a lógica de finalizar a compra
		            StringBuilder resumo = new StringBuilder("Resumo da Compra:\n");
		            double totalCompra = 0;

		            for (ItemVenda item : itensVenda) {
		                resumo.append(item.getProduto().getNome())
		                      .append(" - Quantidade: ")
		                      .append(item.getQuantidade())
		                      .append(" - Subtotal: R$")
		                      .append(item.getSubtotal())
		                      .append("\n");
		                totalCompra += item.getSubtotal();
		            }
		            
		            Venda venda = new Venda();
		            venda.setItens(itensVenda);
		            
		            String texto = textField_Subtotal.getText().replace(",", ".");
		            double valor = Double.parseDouble(texto);

		  
		            VendaDao vendaDao = new VendaDao();
		            int vendaId = vendaDao.salvarVenda(venda);

		            resumo.append("\nTotal da Compra: R$").append(totalCompra);
		            
		            if (vendaId > 0) {
		                JOptionPane.showMessageDialog(null, "Venda finalizada com sucesso!");
		                JOptionPane.showMessageDialog(null, resumo.toString(), "Resumo da Compra", JOptionPane.INFORMATION_MESSAGE);
		                salvarFatura();
		                itensVenda.clear();
		                atualizarTotal();
		                
		                dispose();
		                
		                venda_produtos venda_produto = new venda_produtos();
		                verificarEstoqueAposCompra();
		                venda_produto.setVisible(true);
		               
		              
		            } else {
		                JOptionPane.showMessageDialog(null, "Erro ao finalizar venda.");
		            }

		            // Exibe um resumo da compra
		            
		            
		            
		            // Salva a fatura
		              // Chama o método salvarFatura para salvar a compra em um arquivo
		        }
		    }
		});
		btnNewButtonFinalizar.setBounds(139, 206, 111, 25);
		panel.add(btnNewButtonFinalizar);
		
	
		
		JButton btnNewButton_Remover = new JButton("Remover");
		btnNewButton_Remover.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton_Remover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nomeProdutoStr = textFieldNome.getText().trim(); // Nome do produto
		        String quantidadeRemoverStr = textField_Quantidade.getText().trim(); // Quantidade
		        
		        if (nomeProdutoStr.isEmpty() || quantidadeRemoverStr.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do produto e a quantidade a remover.", "Erro", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        try {
		            int quantidadeRemover = Integer.parseInt(quantidadeRemoverStr);
		            
		            if (quantidadeRemover <= 0) {
		                JOptionPane.showMessageDialog(null, "A quantidade a remover deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
		                return;
		            }
		            
		            Iterator<ItemVenda> iterator = itensVenda.iterator();
		            boolean itemEncontrado = false;
		            
		            while (iterator.hasNext()) {
		                ItemVenda item = iterator.next();
		                if (item.getProduto().getNome().equalsIgnoreCase(nomeProdutoStr)) { // Busca pelo nome
		                    itemEncontrado = true;
		                    
		                    if (quantidadeRemover >= item.getQuantidade()) {
		                        iterator.remove(); // Remove o item da lista de forma segura
		                    } else {
		                        item.setQuantidade(item.getQuantidade() - quantidadeRemover);
		                    }
		                    
		                    atualizarTotal();
		                    JOptionPane.showMessageDialog(null, "Quantidade removida com sucesso!");
		                    return;
		                }
		            }
		            
		            if (!itemEncontrado) {
		                JOptionPane.showMessageDialog(null, "Produto não encontrado no carrinho.", "Erro", JOptionPane.ERROR_MESSAGE);
		            }
		            
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Quantidade inválida. Digite um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		
		btnNewButton_Remover.setBounds(165, 138, 85, 21);
		panel.add(btnNewButton_Remover);
		
		JButton btnNewButton_exibir = new JButton("Exibir Carrinho");
		btnNewButton_exibir.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton_exibir.setBackground(new Color(255, 255, 255));
		btnNewButton_exibir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (itensVenda.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Carrinho vazio!");
		        } else {
		            // Cria um StringBuilder para armazenar a lista de itens do carrinho
		            StringBuilder carrinhoDetails = new StringBuilder("Itens no Carrinho:\n\n");
		            for (ItemVenda item : itensVenda) {
		                Produto produto = item.getProduto();

		                // Adiciona os detalhes de cada item do carrinho
		                carrinhoDetails.append("Código: " + produto.getCodigo() + "\n")
		                               .append("Nome: " + produto.getNome() + "\n")
		                               .append("Preço: R$" + produto.getPreco() + "\n")
		                               .append("Quantidade: " + item.getQuantidade() + "\n")
		                               .append("Categoria: " + produto.getCategoria() + "\n")
		                               .append("Descrição: " + produto.getDescricao() + "\n")
		                               .append("-----------------------------------\n");
		            }

		            // Exibe a lista de itens no carrinho
		            JOptionPane.showMessageDialog(null, carrinhoDetails.toString(), "Detalhes do Carrinho", JOptionPane.INFORMATION_MESSAGE);
		        }
		    }
		});
			
		
		btnNewButton_exibir.setBounds(15, 206, 107, 25);
		panel.add(btnNewButton_exibir);
		
		textFieldBusca = new JTextField();
		textFieldBusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrarProdutos(modeloProduto);
			}
		});
		textFieldBusca.setBounds(27, 68, 400, 25);
		contentPane.add(textFieldBusca);
		textFieldBusca.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 117, 539, 307);
		contentPane.add(scrollPane);
		
		table_1 = new JTable();
		table_1.setModel(modeloProduto);
		table_1.setBackground(new Color(141, 222, 206));
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) { // Verifica se foi um clique simples
		            int selectedRow = table_1.getSelectedRow();
		            if (selectedRow != -1) {
		                Produto produtoSelecionado = produto.get(selectedRow);
		                String quantidadeStr = JOptionPane.showInputDialog("Quantidade:");
		                if (quantidadeStr != null && !quantidadeStr.isEmpty()) {
		                    try {
		                        int quantidade = Integer.parseInt(quantidadeStr);
		                        int estoqueDisponivel = Integer.parseInt(produtoSelecionado.getQuantidadeEmEstoque());
		                        
		                        // Verifica se a quantidade desejada não ultrapassa o estoque
		                        if (quantidade <= estoqueDisponivel) {
		                            ItemVenda item = new ItemVenda(produtoSelecionado, quantidade);
		                            itensVenda.add(item);
		                           
		                            
		                            // Atualiza o carrinho
		                            textField_Quantidade.setText(String.valueOf(quantidade));
		                            textFieldNome.setText(produtoSelecionado.getNome());
		                            textField_Preco.setText(produtoSelecionado.getPreco().toString());
		                            atualizarTotal(); // Atualiza o total
		                            
		                            modeloProduto.fireTableDataChanged();
		                            // Chama o método para verificar o estoque após a inserção
		                            verificarEstoque(produtoSelecionado);
		                            
		                        } else {
		                            JOptionPane.showMessageDialog(null, "Estoque insuficiente!");
		                        }
		                    } catch (NumberFormatException ex) {
		                        JOptionPane.showMessageDialog(null, "Quantidade inválida!");
		                    }
		                }
		            }
		        }
		    }
		});
		scrollPane.setViewportView(table_1);
		rowSorter = new TableRowSorter<>(modeloProduto);
		table_1.setRowSorter(rowSorter);
		
			
		table_1.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		
		JLabel lblNewLabel_4 = new JLabel("Pesquise o produto");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_4.setBounds(27, 50, 120, 13);
		contentPane.add(lblNewLabel_4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(50, 156, 156));
		panel_1.setBounds(581, 117, 260, 38);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Carrinho de Compras");
		lblNewLabel_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_5.setBackground(new Color(64, 128, 128));
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel_5.setBounds(25, 10, 225, 23);
		panel_1.add(lblNewLabel_5);
		}
	
	
	private void filtrarProdutos(ModeloTabelaProduto modeloProduto) {
	    String textoBusca = textFieldBusca.getText().trim();
	    TableRowSorter<ModeloTabelaProduto> sorter = new TableRowSorter<>(modeloProduto);
	    table_1.setRowSorter(sorter);
	    if (textoBusca.length() == 0) {
	        sorter.setRowFilter(null); // Remove o filtro se não houver texto de busca
	    } else {
	        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusca)); // Aplica o filtro
	    }
	}
	
	public void salvarFatura() {
        if (itensVenda.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum item foi adicionado à compra!");
        } else {
            // Definir nome do arquivo com base na data e hora para garantir nomes únicos
            String nomeArquivo = "Fatura_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
            File arquivo = new File(nomeArquivo);

            // Criar um StringBuilder para armazenar o conteúdo da fatura
            StringBuilder fatura = new StringBuilder();
            fatura.append("Fatura de Compra\n");
            fatura.append("Data: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n");
            fatura.append("====================================\n");

            double totalCompra = 0;

            // Adiciona cada item à fatura
            for (ItemVenda item : itensVenda) {
                fatura.append(item.getProduto().getNome())
                      .append(" - Quantidade: ")
                      .append(item.getQuantidade())
                      .append(" - Subtotal: R$")
                      .append(item.getSubtotal())
                      .append("\n");
                totalCompra += item.getSubtotal();
            }

            fatura.append("====================================\n");
            fatura.append("Total da Compra: R$").append(totalCompra).append("\n");
            fatura.append("====================================\n");
            fatura.append("Obrigado pela sua compra!\n");

            // Escreve a fatura no arquivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                writer.write(fatura.toString());
                JOptionPane.showMessageDialog(null, "Fatura salva em: " + arquivo.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar a fatura: " + e.getMessage());
            }
            }
        }
        
	public void verificarEstoque(Produto produtoSelecionado) {
	    try {
	        int quantidadeEmEstoque = Integer.parseInt(produtoSelecionado.getQuantidadeEmEstoque());
	        int quantidadeMinima = Integer.parseInt(produtoSelecionado.getQuantidadeMinima());

	        // Verifica se o estoque está abaixo da quantidade mínima
	        if (quantidadeEmEstoque <= quantidadeMinima) {
	            JOptionPane.showMessageDialog(null, 
	                "Alerta: A quantidade em estoque está abaixo da quantidade mínima permitida!", 
	                "Alerta de Estoque", 
	                JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao ler os dados de estoque. Verifique os valores inseridos.", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}
	public void verificarEstoqueAposCompra() {
	    for (ItemVenda item : itensVenda) {
	        Produto produto = item.getProduto();
	        int quantidadeVendida = item.getQuantidade();
	        
	        // Verifica o estoque atual do produto
	        int estoqueAtual = Integer.parseInt(produto.getQuantidadeEmEstoque());
	        
	        // Calcula o novo estoque após a venda
	        int novoEstoque = estoqueAtual - quantidadeVendida;
	        
	        // Verifica se o novo estoque é menor ou igual ao estoque mínimo
	        if (novoEstoque <= Integer.parseInt(produto.getQuantidadeMinima())) {
	            // Exibe um alerta caso o estoque seja insuficiente
	            JOptionPane.showMessageDialog(null, 
	                "Atenção: O estoque do produto " + produto.getNome() + " está baixo! Estoque restante: " + novoEstoque,
	                "Aviso de Estoque", JOptionPane.WARNING_MESSAGE);
	        }
	        
	        // Atualiza o estoque do produto no banco de dados (opcional, caso você tenha persistência)
	        // ProdutoDAO.atualizarEstoque(produto.getId(), novoEstoque);
	    }
	}
		public void finalizarCompra() {
	    // Seu código de finalização de compra, como gerar a fatura e limpar o carrinho
	    
	    // Após finalizar a compra, verifica o estoque
			verificarEstoqueAposCompra();
	    
	    // Alerta de sucesso na compra
	    JOptionPane.showMessageDialog(null, "Compra finalizada com sucesso!");
	}  
		
		
        
}
