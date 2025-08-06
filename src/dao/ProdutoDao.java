package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.Conexao;
import model.Cliente;
import model.Produto;

public class ProdutoDao {
	 private static PreparedStatement preparedStatement = null;
	    private static ResultSet resultSet = null;

	    private static String CADASTRAR_PRODUTO = " INSERT INTO PRODUTO (CODIGO, NOME, PRECO, QUANTIDADEEMESTOQUE, CATEGORIA, DESCRICAO, QUANTIDADEMINIMA) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    private static String CONSULTAR_PRODUTO = " SELECT * FROM PRODUTO WHERE CODIGO = ?";
	    private static String LISTAR_PRODUTO = " SELECT * FROM PRODUTO WHERE 1=1";
	    private static String ALTERAR_PRODUTO = "UPDATE PRODUTO SET NOME = ?, PRECO = ?, QUANTIDADEEMESTOQUE = ?, CATEGORIA = ?, DESCRICAO = ?, QUANTIDADEMINIMA = ? WHERE CODIGO = ?";
	    private static String EXCLUIR_PRODUTO = "DELETE FROM PRODUTO WHERE CODIGO = ?";
	    private static String ALTERAR_QUANTIDADE = "UPDATE PRODUTO SET QUANTIDADEEMESTOQUE = ? WHERE CODIGO = ?";
	    
	    public void cadastrarProduto(Produto produto) {
	        Connection connection = Conexao.getInstancia().abrirConexao();
	        try {
	            preparedStatement = connection.prepareStatement(CADASTRAR_PRODUTO);
	            preparedStatement.setString(1, produto.getCodigo());
	            preparedStatement.setString(2, produto.getNome());
	            preparedStatement.setString(3, produto.getPreco());
	            preparedStatement.setString(4, produto.getQuantidadeEmEstoque());
	            preparedStatement.setString(5, produto.getCategoria());
	            preparedStatement.setString(6, produto.getDescricao());
	            preparedStatement.setString(7, produto.getQuantidadeMinima());
	            preparedStatement.execute();
	            connection.commit();
	            JOptionPane.showMessageDialog(null, "Produto incluído com sucesso ");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            fecharConexao();
	        }
	    }

	    public Produto consultarProduto(String codigo) throws Exception {
	        Connection connection = Conexao.getInstancia().abrirConexao();
	        Produto produto = null;
	        try {
	            preparedStatement = connection.prepareStatement(CONSULTAR_PRODUTO);
	            preparedStatement.setString(1, codigo);
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()) {
	                produto = new Produto(resultSet.getString("CODIGO"),
	                                      resultSet.getString("NOME"),
	                                      resultSet.getString("PRECO"),
	                                      resultSet.getString("QUANTIDADEEMESTOQUE"),
	                                      resultSet.getString("CATEGORIA"),
	                                      resultSet.getString("DESCRICAO"),
	                                      resultSet.getString("QUANTIDADEMINIMA"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            fecharConexao();
	        }
	        if (produto == null) {
	            throw new Exception("Produto não encontrado");
	        }
	        return produto;
	    }

	    public ArrayList<Produto> listarProduto() throws Exception {
	        Connection connection = Conexao.getInstancia().abrirConexao();
	        ArrayList<Produto> produtos = new ArrayList<>();
	        try {
	            preparedStatement = connection.prepareStatement(LISTAR_PRODUTO);
	            
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()) {
	            	produtos.add( new Produto(resultSet.getString("CODIGO"),
			              resultSet.getString("NOME"),
			              resultSet.getString("PRECO"),
			              resultSet.getString("QUANTIDADEEMESTOQUE"),
			              resultSet.getString("CATEGORIA"),
			              resultSet.getString("DESCRICAO"),
			              resultSet.getString("QUANTIDADEMINIMA")));
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            fecharConexao();
	        }
	        if (produtos.size() == 0) {
	            throw new Exception("Nenhum produtO encontrado");
	        }
	        return produtos;
	    }
	    public void alterarProduto(String codigo, Produto produto) {
	        Connection connection = Conexao.getInstancia().abrirConexao();
	        try {
	            preparedStatement = connection.prepareStatement(ALTERAR_PRODUTO);
	            
	            int i = 1;
	            
				preparedStatement.setString(i++, produto.getNome());
				preparedStatement.setString(i++, produto.getPreco());
				preparedStatement.setString(i++, produto.getQuantidadeEmEstoque());
				preparedStatement.setString(i++, produto.getCategoria());
				preparedStatement.setString(i++,  produto.getDescricao());
				preparedStatement.setString(i++,  produto.getQuantidadeMinima());
				preparedStatement.setString(i++, codigo);
			
				preparedStatement.execute();
				connection.commit();
			
				JOptionPane.showMessageDialog(null, "Produto alterado com sucesso ");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				fecharConexao();
			}
	        
	        
		
		}
	    
	    public void alterarQuantidade(String codigo, Produto produto) {
	    	 Connection connection = Conexao.getInstancia().abrirConexao();
		        try {
		            preparedStatement = connection.prepareStatement(ALTERAR_QUANTIDADE);
		            
		            int i = 1;
		            
					preparedStatement.setString(i++, produto.getQuantidadeEmEstoque());
					
					preparedStatement.execute();
					connection.commit();
				
				
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					fecharConexao();
				}
	    }
	    
	    
	    public void excluirProduto(String codigo) {
	        Connection connection = Conexao.getInstancia().abrirConexao();
	        
	        try {
				preparedStatement = connection.prepareStatement(EXCLUIR_PRODUTO);
			
				int i = 1;
			
				preparedStatement.setString(i++, codigo);
			
				preparedStatement.execute();
				connection.commit();
			
				JOptionPane.showMessageDialog(null, "Produto excluído com sucesso ");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				fecharConexao();
			}
		
		}

	    private void fecharConexao() {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            Conexao.getInstancia().fecharConexao();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

}