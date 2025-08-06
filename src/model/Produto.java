package model;

public class Produto {//"Código", "Nome", "Preço", "Quantidade", "Categoria", "Descrição"
	private String codigo;
	private String nome;
	private String preco;
	private String quantidadeEmEstoque;
	private String categoria;
	private String descricao;
	private String quantidadeMinima;
	
	
	public Produto() {
		
	}
	
	public Produto(String codigo, String nome, String preco, String quantidadeEmEstoque, String categoria,
			String descricao, String quantidadeMinima) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
		this.quantidadeEmEstoque = quantidadeEmEstoque;
		this.categoria = categoria;
		this.descricao = descricao;
		this.quantidadeMinima = quantidadeMinima;
	}




	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public String getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(String estoquemin) {
		this.quantidadeEmEstoque = estoquemin;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(String quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
	
	
	
}
