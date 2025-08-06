package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModeloTabelaServico extends AbstractTableModel {
    private static final String[] colunas = {"IdBanho","IDCliente", "Pet", "Tipo de Pet", "Preço", "Horário", "DATA"};
    private ArrayList<Servico> servicos;

    public ModeloTabelaServico(ArrayList<Servico> servicos) {
        super();
        this.servicos = servicos;
    }

    @Override
    public int getRowCount() {
        return servicos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Servico servico = servicos.get(rowIndex);
        if(columnIndex == 0) {
             return servico.getIdBanho();
        }else if(columnIndex == 1) {
        	 return servico.getClienteID();
        }else if(columnIndex == 2) {
             return servico.getPetNome();
        }else if(columnIndex == 3) {
             return servico.getTipoPet();
        }else if(columnIndex == 4) {
        	return String.format("R$ %.2f", servico.getPreco());
        }else if(columnIndex == 5) {
        	return servico.getHorario();
        }else if(columnIndex == 6) {
             return servico.getData(); 
        }else {
             return null;
        }
    }
    

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}
