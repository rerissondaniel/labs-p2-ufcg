package main;

public class Produto {
	private String nome;
	private Double precoUnitario;
	private String tipo;
	private Integer quantidade;

	public Produto(String nome, Double precoUnitario, String tipo, Integer qtdeEmEstoque) {
		this.nome = nome;
		this.precoUnitario = precoUnitario;
		this.tipo = tipo;
		this.quantidade = qtdeEmEstoque;
	}

	/**
	 * Constrói uma cópia de outro.
	 * 
	 * @param outro
	 */
	public Produto(Produto outro) {
		this.nome = outro.getNome();
		this.precoUnitario = outro.getPrecoUnitario();
		this.tipo = outro.getTipo();
		this.quantidade = outro.getQuantidade();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer qtdeEmEstoque) {
		this.quantidade = qtdeEmEstoque;
	}

	@Override
	public String toString() {
		return String.format("%s (%s). R$%.2f Restante: %d", this.nome, this.tipo, this.precoUnitario, this.quantidade);
	}
}