package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.Conexao;
import model.Servico;
import model.Cliente;
import model.Produto;
import model.Usuario;

public class Dao {
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	
	private static String CADASTRAR_CLIENTE = " INSERT INTO CLIENTE "
			+ "(ID, NOME, CPFCNPJ, EMAIL, TELEFONE, ENDERECO)"
			+ "VALUES (NULL, ?, ?, ?, ?, ?)";

	private static String CONSULTAR_CLIENTE = " SELECT * FROM CLIENTE "
			+ " WHERE ID = ? ";
	
	private static String ALTERAR_CLIENTE = " UPDATE CLIENTE SET "
			+ " NOME = ?, CPFCNPJ = ?, EMAIL = ?, TELEFONE = ?, ENDERECO = ?"
			+ " WHERE ID = ? ";

	private static String EXCLUIR_CLIENTE = " DELETE FROM CLIENTE "
			+ " WHERE ID = ? ";
	
	private static String LISTAR_CLIENTES = " SELECT * FROM CLIENTE "
			+ " WHERE 1=1 ";
	
	private static String CONSULTAR_USUARIO = " SELECT USUARIO, SENHA "
			+ " FROM USUARIO "
			+ " WHERE USUARIO = ?"
			+ " AND SENHA = ?";
	
	
	private static String INSERIR_SERVICO = "INSERT INTO SERVICO"
			+ " (IDSERVICO, CLIENTEID, PETNOME, TIPOPET, PRECO, HORARIO, DATA)"
			+ " VALUES ( null, ?, ?, ?, ?, ?, ?)";
	
	public static String LISTAR_SERVICO = "SELECT * FROM SERVICO "
			+ " WHERE 1=1";
	
	public static String CONSULTAR_SERVICO = "SELECT * FROM SERVICO "
			+ " WHERE IDSERVICO = ?";
	
	public static String ALTERAR_SERVICO = "UPDATE SERVICO SET" 
			+ " IDSERVICO = ?, CLIENTEID = ?, PETNOME = ?, TIPOPET = ?, PRECO = ?, HORARIO = ?, DATA = ?"
			+ " WHERE IDSERVICO = ?";
	
	public static String EXCLUIR_SERVICO = " DELETE * FROM SERVICO "
			+ " WHERE IDSERVICO";
	
	public Dao() {
		
	}
	
	public void cadastrarCliente(Cliente cliente) {
		Connection connection = Conexao.getInstancia().abrirConexao();
		if (cpfJaCadastrado(cliente.getCpfCnpj())) {
	        JOptionPane.showMessageDialog(null, "CPF/CNPJ já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
		
		String query = CADASTRAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
			preparedStatement.setString(i++, cliente.getNome());
			preparedStatement.setString(i++, cliente.getCpfCnpj());
			preparedStatement.setString(i++, cliente.getEmail());
			preparedStatement.setString(i++, cliente.getTelefone());
			preparedStatement.setString(i++, cliente.getEndereco());
			
			preparedStatement.execute();
			connection.commit();
			
			JOptionPane.showMessageDialog(null, "Cliente incluído com sucesso ");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			fecharconexao();
		}
		
	}
		
	public Cliente consultarCliente(String id) throws Exception{
			Connection connection = Conexao.getInstancia().abrirConexao();
			Cliente cliente = null;
			String query = CONSULTAR_CLIENTE;
			try {
				preparedStatement = connection.prepareStatement(query);
				
				int i = 1;
				
				preparedStatement.setString(i++, id);
				
				resultSet = preparedStatement.executeQuery();
				
				connection.commit();
				while(resultSet.next()) {
					cliente = new Cliente(resultSet.getString("ID"),
							              resultSet.getString("Nome"),
							              resultSet.getString("CPFCNPJ"),
							              resultSet.getString("EMAIL"),
							              resultSet.getString("TELEFONE"),
							              resultSet.getString("ENDERECO"));
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
				fecharconexao();
			}
			if(cliente == null) {
				JOptionPane.showMessageDialog(null, "Não foi possível localizar o cliente selecionado", "", JOptionPane.WARNING_MESSAGE);
				throw new Exception("Não foi possível localizar o cliente selecionado");
			}
			return cliente;
			
		}
	
	public void alterarCliente(String id, Cliente cliente) {
		Connection connection = Conexao.getInstancia().abrirConexao();
	
		String query = ALTERAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
		
			int i = 1;
		
			preparedStatement.setString(i++, cliente.getNome());
			preparedStatement.setString(i++, cliente.getCpfCnpj());
			preparedStatement.setString(i++, cliente.getEmail());
			preparedStatement.setString(i++, cliente.getTelefone());
			preparedStatement.setString(i++, cliente.getEndereco());
			preparedStatement.setString(i++, id);
		
			preparedStatement.execute();
			connection.commit();
		
			JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso ");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharconexao();
		}
	
	}
	
	public void excluirCliente(String id) {
		Connection connection = Conexao.getInstancia().abrirConexao();
	
		String query = EXCLUIR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
		
			int i = 1;
		
			preparedStatement.setString(i++, id);
		
			preparedStatement.execute();
			connection.commit();
		
			JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso ");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharconexao();
		}
	
	}
	
	public ArrayList<Cliente> listarCliente() throws Exception{
		Connection connection = Conexao.getInstancia().abrirConexao();
		ArrayList<Cliente> clientes = new ArrayList<>();
		String query = LISTAR_CLIENTES;
		try {
			preparedStatement = connection.prepareStatement(query);
			

			resultSet = preparedStatement.executeQuery();
			
			connection.commit();
			while(resultSet.next()) {
				clientes.add( new Cliente(resultSet.getString("ID"),
						              resultSet.getString("NOME"),
						              resultSet.getString("CPFCNPJ"),
						              resultSet.getString("EMAIL"),
						              resultSet.getString("TELEFONE"),
						              resultSet.getString("ENDERECO")));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			fecharconexao();
		}
		if(clientes.size() < 0) {
			JOptionPane.showMessageDialog(null, "Não há clientes cadastrados ", "", JOptionPane.WARNING_MESSAGE);
			throw new Exception("Não há clientes cadastrados ");
		}
		return clientes;
		
	}
	
	public Usuario consultarUsuario(String nomeUsuario, String senhaCriptografada) throws Exception{
		Connection connection = Conexao.getInstancia().abrirConexao();
		Usuario usuario = null;
		String query = CONSULTAR_USUARIO;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
			preparedStatement.setString(i++, nomeUsuario);
			preparedStatement.setString(i++, senhaCriptografada);
			
			resultSet = preparedStatement.executeQuery();
			
			connection.commit();
			while(resultSet.next()) {
				usuario = new Usuario(resultSet.getInt("ID"),
						              resultSet.getString("USUARIO"),
						              resultSet.getString("SENHA"));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			fecharconexao();
		}
		if(usuario == null) {
			JOptionPane.showMessageDialog(null, "Não foi possível localizar o usuário selecionado", "", JOptionPane.WARNING_MESSAGE);
			throw new Exception("Não foi possível localizar o usuário selecionado");
		}
		return usuario;
		
	}
	
	
	public void inserirServico(Servico servico) {
		Connection connection = Conexao.getInstancia().abrirConexao();
		
		String query = INSERIR_SERVICO;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
			
			preparedStatement.setString(i++, servico.getClienteID());
			preparedStatement.setString(i++, servico.getPetNome());
			preparedStatement.setString(i++, servico.getTipoPet());
			preparedStatement.setDouble(i++, servico.getPreco());
			preparedStatement.setString(i++, servico.getHorario());
			preparedStatement.setString(i++, servico.getData());
			
			
			
			preparedStatement.execute();
			connection.commit();
			
			JOptionPane.showMessageDialog(null, "Serviço incluído com sucesso ");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			fecharconexao();
		}
		
	}
	
	public ArrayList<Servico> listarServico() throws Exception{
		Connection connection = Conexao.getInstancia().abrirConexao();
		ArrayList<Servico> servico = new ArrayList<>();
		String query = LISTAR_SERVICO;
		try {
			preparedStatement = connection.prepareStatement(query);
			

			resultSet = preparedStatement.executeQuery();
			
			connection.commit();
			while(resultSet.next()) {
				
				servico.add( new Servico(resultSet.getString("IDSERVICO"),
											resultSet.getString("CLIENTEID"),
											resultSet.getString("PETNOME"),
											resultSet.getString("TIPOPET"),
											resultSet.getDouble("PRECO"),
											resultSet.getString("HORARIO"),
											resultSet.getString("DATA")));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			fecharconexao();
		}
		if(servico.size() < 0) {
			JOptionPane.showMessageDialog(null, "Erro ao listar serviços ", "", JOptionPane.WARNING_MESSAGE);
			throw new Exception("Erro ao listar serviços ");
		}
		return servico;
		
	}
	
	public Servico consultarServico(String idServico) throws Exception {
	    Connection connection = Conexao.getInstancia().abrirConexao();
	    Servico servicoBanho = null;
	    String query = CONSULTAR_SERVICO; // 
	    
	    try {
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, idServico); // ID do banho a ser consultado

	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            servicoBanho = new Servico(
	                resultSet.getString("IDSERVICO"),
	                resultSet.getString("CLIENTEID"),
	                resultSet.getString("PETNOME"),
	                resultSet.getString("TIPOPET"),
	                resultSet.getDouble("PRECO"),
	                resultSet.getString("HORARIO"),
	                resultSet.getString("DATA"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        fecharconexao();
	    }
	    
	    return servicoBanho;
	}
	
	public void alterarServico(String idServico, Servico servicoBanho) {
	    Connection connection = Conexao.getInstancia().abrirConexao();
	    String query = ALTERAR_SERVICO; // 
	    
	    try {
	        preparedStatement = connection.prepareStatement(query);
	        
	        int i = 1;
	        
	        preparedStatement.setString(i++, idServico);
	        preparedStatement.setString(i++, servicoBanho.getClienteID());
	        preparedStatement.setString(i++, servicoBanho.getPetNome());
	        preparedStatement.setString(i++, servicoBanho.getTipoPet());
	        preparedStatement.setDouble(i++, servicoBanho.getPreco());
	        preparedStatement.setString(i++, servicoBanho.getHorario());
	        preparedStatement.setString(i++, servicoBanho.getData());
	         

	        preparedStatement.execute();
	        connection.commit();
	        
	        JOptionPane.showMessageDialog(null, "Serviço de banho alterado com sucesso");
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        fecharconexao();
	    }
	}
	
	public void excluirServico(String idServico) {
	    Connection connection = Conexao.getInstancia().abrirConexao();
	    String query = EXCLUIR_SERVICO; // 
	    
	    try {
	        preparedStatement = connection.prepareStatement(query);
	        
	        int i = 1;
	        
	        preparedStatement.setString(i++, idServico); 
	        
	        preparedStatement.execute();
	        connection.commit();
	        
	        JOptionPane.showMessageDialog(null, "Serviço de banho excluído com sucesso");
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        fecharconexao();
	    }
	}
	
	private boolean cpfJaCadastrado(String cpfCnpj) {
	    Connection connection = Conexao.getInstancia().abrirConexao();
	    String query = "SELECT COUNT(*) FROM CLIENTE WHERE CPFCNPJ = ?";
	    boolean existe = false;

	    try {
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, cpfCnpj);
	        resultSet = preparedStatement.executeQuery();
	        
	        if (resultSet.next() && resultSet.getInt(1) > 0) {
	            existe = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        fecharconexao();
	    }

	    return existe;
	}

		
	private void fecharconexao() {

			try {
				if(resultSet!=null) {
				resultSet.close();
				}
				if(preparedStatement!=null) {
					preparedStatement.close();
				}	
				Conexao.getInstancia().fecharConexao();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	
}
