package main;

public class Supermercado {
	private ColecaoDinamicaDeProdutos estoque = new ColecaoDinamicaDeProdutos();
	private ColecaoDinamicaDeProdutos vendidos = new ColecaoDinamicaDeProdutos();

	public boolean cadastraProduto(Produto produto) {
		return estoque.adicionaProduto(produto);
	}

	/**
	 * Vende o produto que tem nomeProduto como nome. Caso o produto não tenha
	 * mais unidades no estoque, ele é removido de lá.
	 * 
	 * @param nomeProduto
	 *            Nome do produto que será vendido.
	 */
	public boolean vendeProduto(String nomeProduto) {

		int indice = estoque.pesquisaProduto(nomeProduto);
		Produto produto = estoque.getProdutoAt(indice);

		if (produto != null && produto.getQuantidade() > 0) {
			Integer quantidade = produto.getQuantidade();
			produto.setQuantidade(quantidade - 1);
			atualizaVendidos(produto);
			
			//Caso o produto não tenha mais unidades, será removido do estoque.
			if (produto.getQuantidade() == 0) {
				estoque.removeProduto(indice);
			}
			
			return true;
		}
		return false;
	}

	/**
	 * Atualiza a lista de produtos vendidos.
	 */
	private void atualizaVendidos(Produto produto) {
		int indice = vendidos.pesquisaProduto(produto.getNome());

		if (indice == ColecaoDinamicaDeProdutos.PRODUTO_NAO_ENCONTRADO) {
			Produto copia = new Produto(produto);
			vendidos.adicionaProduto(copia);
			copia.setQuantidade(1);// Há pelo menos uma unidade que foi vendida.
		} else {
			// Caso o produto já tenha sido vendido, apenas atualizamos sua
			// quantidade.
			Produto produtoVendido = vendidos.getProdutoAt(indice);
			produtoVendido.setQuantidade(produtoVendido.getQuantidade() + 1);
		}
	}

	/**
	 * 
	 * @return Um array de produtos correspondente ao estoque do supermercado.
	 */
	public Produto[] getEstoque() {
		return estoque.getProdutos();
	}

	/**
	 * 
	 * @return Um array de produtos correspondente aos produtos vendidos pelo
	 *         supermercado.
	 */
	public Produto[] getVendidos() {
		return vendidos.getProdutos();
	}
}
