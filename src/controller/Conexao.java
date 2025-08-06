package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Cliente;
import model.Produto;

public class Conexao {
	private static Conexao instancia;
	private static String DRIVER = "org.sqlite.JDBC";
	private static String BD = "jdbc:sqlite:resource/bdclientes.db";
	private static Connection conexao;
	
	private Conexao() {
		
	}
	
	private static Connection conectar() throws SQLException {
        // Configuração do banco de dados
        String url = "jdbc:sqlite:caminho_do_banco_de_dados"; // Altere o caminho conforme necessário
        return DriverManager.getConnection(url);
    }
	
	public static Conexao getInstancia() {
		if(instancia == null) {
			instancia = new Conexao();
		}
		return instancia;
	}
	
	public Connection abrirConexao() {
		try {
			Class.forName(DRIVER);
		    conexao = DriverManager.getConnection(BD);
			conexao.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Erro ao conectar com banco de dados!"+e.getMessage());
		}
		return conexao;
	}
	
	public void fecharConexao() {
		try {
			if(conexao!=null && !conexao.isClosed()) {
				conexao.close();
			}
		} catch (SQLException e) {
			System.out.println("Erro ao fechar a conexão!"+e.getMessage());
		} finally {
			conexao = null;
		}
	}
	


}

