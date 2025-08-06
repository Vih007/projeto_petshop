package model;

public class ItemVenda {
	private Produto produto;
    private int quantidade;
    private double subtotal;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.subtotal = quantidade * Double.parseDouble(produto.getPreco());
    }
    
    

    public void setProduto(Produto produto) {
		this.produto = produto;
	}



	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}



	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}



	public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getSubtotal() { return subtotal; }
}
