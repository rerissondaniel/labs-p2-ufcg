package main;

import java.util.Arrays;

public class Estoque {
	private static int indicesStatus = -100;
	public static final int PRODUTO_NAO_ENCONTRADO = indicesStatus--;

	private Produto[] produtos = new Produto[1];
	private int ultimaPosicaoValida = 0;

	/**
	 * Adiciona um produto ao array.
	 * 
	 * @param produto
	 * @return true se o produto foi adicionado, false caso contrário.
	 */
	public boolean adicionaProduto(Produto produto) {
		if (produto == null)
			return false;

		// Duplica o tamanho do array caso haja apenas mais uma posição livre.
		if (ultimaPosicaoValida + 1 >= produtos.length) {
			produtos = Arrays.copyOf(produtos, (ultimaPosicaoValida + 1) * 2);
		}

		produtos[ultimaPosicaoValida] = produto;
		ultimaPosicaoValida++;
		return true;
	}

	/**
	 * Retorna o índice do produto de nome nomeProduto.
	 * 
	 * @param nomeProduto
	 * @return
	 */
	public int pesquisaProduto(String nomeProduto) {
		if (nomeProduto == null)
			return PRODUTO_NAO_ENCONTRADO;

		for (int i = 0; i < ultimaPosicaoValida; i++) {
			if (nomeProduto.equals(produtos[i].getNome())) {
				return i;
			}
		}

		return PRODUTO_NAO_ENCONTRADO;
	}

	/**
	 * 
	 * @param indice
	 * @return O produto na posição indice do array.
	 */
	public Produto getProdutoAt(int indice) {
		if (validaIndice(indice)) {
			return produtos[indice];
		}
		return null;
	}

	/**
	 * 
	 * @param indice
	 * @return true se o produto foi removido, false caso contrário.
	 */
	public boolean removeProduto(int indice) {
		if (!this.validaIndice(indice))
			return false;

		for (int i = indice; i < ultimaPosicaoValida; i++) {
			produtos[i] = produtos[i + 1];
		}
		ultimaPosicaoValida--;

		return true;
	}

	/**
	 * 
	 * @param nomeProduto
	 * @return true se o produto foi removido, false caso contrário.
	 */
	public boolean removeProduto(String nomeProduto) {
		int indice = pesquisaProduto(nomeProduto);	
		if (indice == PRODUTO_NAO_ENCONTRADO){
			return false;
		}

		return removeProduto(indice);
	}

	/**
	 * Valida indice em relação à última posição válida e ao mesmo ser menor que
	 * zero.
	 * 
	 * @param indice
	 * @return true se o índice for válido, false caso contrário.
	 */
	private boolean validaIndice(int indice) {
		return indice < ultimaPosicaoValida && indice >= 0;
	}

	/**
	 * Retorna um array de produtos que pode ser usado externamente (todas as
	 * suas posição são válidas).
	 * 
	 * @return Um array de produtos.
	 */
	public Produto[] getProdutos() {
		return Arrays.copyOf(produtos, ultimaPosicaoValida);
	}

	/**
	 * Substitui o produto que tem o mesmo nome do produto passado como
	 * argumento pelo produto que foi passado como argumento.
	 * 
	 * @return true se o produto foi atualizado. false caso contrário.
	 */
	public boolean atualizarProduto(Produto produto) {
		if (produto == null)
			return false;

		int indice = pesquisaProduto(produto.getNome());

		if (indice == PRODUTO_NAO_ENCONTRADO)
			return false;

		produtos[indice] = produto;

		return true;
	}
}