package model;

import java.util.ArrayList;
import java.util.Date;

public class Venda {
	private int id;
    private Date data;
    private double total;
    private Cliente cliente;
    private ArrayList<ItemVenda> itens; // Apenas uma lista de itens

    public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Venda() {
        this.itens = new ArrayList<>();
    }
	

    public Venda(int id, Date data, double total, Cliente cliente, ArrayList<ItemVenda> itens) {
		super();
		this.id = id;
		this.data = data;
		this.total = total;
		this.cliente = cliente;
		this.itens = itens;
	}

	public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    // Métodos para manipular os itens da venda
    public void setItens(ArrayList<ItemVenda> itens) {
        this.itens = itens;
    }

    public ArrayList<ItemVenda> getItens() {
        return itens;
    }

    // Adiciona um item à venda
    public void adicionarItem(ItemVenda item) {
        this.itens.add(item);
    }

    // Remove um item da venda
    public void removerItem(ItemVenda item) {
        this.itens.remove(item);
    }
}

