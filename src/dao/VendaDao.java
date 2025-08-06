package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Conexao;
import model.ItemVenda;
import model.Venda;

public class VendaDao {
	private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
   
	
	public int salvarVenda(Venda venda) {
        Connection connection = Conexao.getInstancia().abrirConexao();
        int vendaId = -1;

        try {
            // Inserir a venda na tabela VENDA
            String sqlVenda = "INSERT INTO VENDA (TOTAL) VALUES (?)";
            PreparedStatement stmtVenda = connection.prepareStatement(sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtVenda.setDouble(1, venda.getTotal());
            stmtVenda.executeUpdate();

            // Obter o ID gerado para a venda
            ResultSet generatedKeys = stmtVenda.getGeneratedKeys();
            if (generatedKeys.next()) {
                vendaId = generatedKeys.getInt(1);
            }

            // Inserir itens da venda
            String sqlItens = "INSERT INTO ITENS_VENDA (VENDA_ID, PRODUTO_CODIGO, QUANTIDADE, SUBTOTAL) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtItem = connection.prepareStatement(sqlItens);

            for (ItemVenda item : venda.getItens()) {
                stmtItem.setInt(1, vendaId);
                stmtItem.setString(2, item.getProduto().getCodigo());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.setDouble(4, item.getSubtotal());
                stmtItem.executeUpdate();

                // Atualizar o estoque do produto
                String sqlUpdateEstoque = "UPDATE PRODUTO SET QUANTIDADEEMESTOQUE = QUANTIDADEEMESTOQUE - ? WHERE CODIGO = ?";
                PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdateEstoque);
                stmtUpdate.setInt(1, item.getQuantidade());
                stmtUpdate.setString(2, item.getProduto().getCodigo());
                stmtUpdate.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.getInstancia().fecharConexao();
        }

        return vendaId;
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
