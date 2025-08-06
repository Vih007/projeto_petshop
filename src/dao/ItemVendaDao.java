package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.Conexao;
import model.ItemVenda;
import model.Produto;
import model.Venda;

public class ItemVendaDao {
private Connection connection;
private static PreparedStatement preparedStatement = null;
private static ResultSet resultSet = null;
    
    public boolean inserir(Venda venda) {
    	try {
            connection.setAutoCommit(false);

            String insertVendaSql = "INSERT INTO venda (data_venda, id, total) VALUES (?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(insertVendaSql, Statement.RETURN_GENERATED_KEYS)) {
                pst.setDate(1, new java.sql.Date(venda.getData().getTime()));
                pst.setInt(2, Integer.parseInt(venda.getCliente().getId()));
                pst.setDouble(3, venda.getTotal());  // ✅ Corrigido o índice (era 1, deveria ser 3)
                pst.executeUpdate();

                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int vendaId = rs.getInt(1);

                    String insertItemVendaSql = "INSERT INTO itens_venda (id_venda, id_produto, quantidade) VALUES (?, ?, ?)";
                    try (PreparedStatement pstItem = connection.prepareStatement(insertItemVendaSql)) {
                        for (ItemVenda item : venda.getItens()) {
                            pstItem.setInt(1, vendaId);
                            pstItem.setInt(2, Integer.parseInt(item.getProduto().getCodigo())); // ✅ Corrigido (estava pst em vez de pstItem)
                            pstItem.setInt(3, item.getQuantidade());
                            pstItem.executeUpdate();

                            String updateProdutoSql = "UPDATE produto SET quantidade = quantidade - ? WHERE id_produto = ?";
                            try (PreparedStatement pstProduto = connection.prepareStatement(updateProdutoSql)) {
                                pstProduto.setInt(1, item.getQuantidade());
                                pstProduto.setInt(2, Integer.parseInt(item.getProduto().getCodigo())); // ✅ Corrigido (estava pst em vez de pstProduto)
                                pstProduto.executeUpdate();
                            }
                        }
                    }
                    connection.commit();
                    return true;  // ✅ Agora permitido
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;  // ✅ Agora permitido
    }
    }