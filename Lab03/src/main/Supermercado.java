package main;

public class Supermercado {
	
	/**
	 * Constantes que indicam o status das operações realizadas.
	 */
	public static final int OPERACAO_NAO_REALIZADA = 0;
	public static final int PRODUTO_ADICIONADO = 1;
	public static final int PRODUTO_ATUALIZADO = 2;

	private Estoque estoque = new Estoque();
	private double totalArrecadado;
	
	/**
	 * Cadastra ou atualiza <i>produto</i> no estoque.
	 * @param produto a ser atualizado ou cadastrado.
	 * @return Um valor indicando se o produto foi atualizado, cadastrado ou se não foi possível realizar nenhuma operação.
	 */
	public int cadastraOuAtualizaProduto(Produto produto) {
		int status = OPERACAO_NAO_REALIZADA;
		//Caso o produto não exista e sua adição seja bem sucedida.
		if (estoque.pesquisaProduto(produto.getNome()) == Estoque.PRODUTO_NAO_ENCONTRADO &&
				estoque.adicionaProduto(produto)) {
				status = PRODUTO_ADICIONADO;
		} else if (estoque.atualizarProduto(produto)) {
			status = PRODUTO_ATUALIZADO;
		}
		return status;
	}
	
	/**
	 * Pesquisa por um produto que tenha <i>nome</i> como nome.
	 * @param nome
	 * @return Produto que tem <i>nome</i> como valor de nome, null, caso contrário.
	 */
	public Produto getProdutoPorNome(String nomeProduto){
		int indice = estoque.pesquisaProduto(nomeProduto);
		return estoque.getProdutoAt(indice);
	}

	/**
	 * Vende o produto que tem nomeProduto como nome. Caso o produto não tenha
	 * mais unidades no estoque, ele é removido de lá.
	 * 
	 * @param nomeProduto
	 *            Nome do produto que será vendido.
	 */
	public boolean vendeProduto(Produto produto, int quantidade) {
		if (produto != null && produto.getQuantidade() >= quantidade) {
			Integer quantidadeProd = produto.getQuantidade();
			
			produto.setQuantidade(quantidadeProd - quantidade);
			totalArrecadado += produto.getPrecoUnitario() * quantidade;
			
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return Um array de produtos correspondente ao estoque do supermercado.
	 */
	public Produto[] getEstoque() {
		return estoque.getProdutos();
	}

	public double getTotalArrecadado() {
		return totalArrecadado;
	}

	public double getTotalPossivelArrecadado() {
		return somaPrecos(getEstoque());
	}
	
	/**
	 * Soma os preços de <i>produtos</i>.
	 * @param produtos
	 * @return
	 */
	private double somaPrecos(Produto [] produtos){
		double total = 0.0;
		for(Produto produto : getEstoque()){
			total += produto.getPrecoUnitario() * produto.getQuantidade();
		}
		return total;
	}
}