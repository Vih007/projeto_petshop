package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModeloTabelaProduto extends AbstractTableModel {
	 private static final String[] colunas = {"Código", "Nome", "Preço", "QuantEst", "Categoria", "Descrição", "QuantMin"};
	    private ArrayList<Produto> produtos;

	    public ModeloTabelaProduto(ArrayList<Produto> produtos) {
	        super();
	        this.produtos = produtos;
	    }

	    @Override
	    public int getRowCount() {
	        return produtos.size();
	    }

	    @Override
	    public int getColumnCount() {
	        return colunas.length;
	    }

	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        Produto produto = produtos.get(rowIndex);
	        if(columnIndex == 0) {
	                return produto.getCodigo();
	        }else if(columnIndex == 1) {
	                return produto.getNome();
	        }else if(columnIndex == 2) {
	                return produto.getPreco();
	        }else if(columnIndex == 3) {
	                return produto.getQuantidadeEmEstoque();
	        }else if(columnIndex == 4) {
	                return produto.getCategoria();
	        }else if(columnIndex == 5) {
	                return produto.getDescricao();
	        }else if(columnIndex == 6) {
	        	return produto.getQuantidadeMinima();
	        }else {
	                return null;
	        }
	    }

	    @Override
	    public String getColumnName(int column) {
	        return colunas[column];
	    }
	    
}