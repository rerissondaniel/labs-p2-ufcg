package main;

public class Menu {

	private static final String NOVA_LINHA = System.lineSeparator();

	private static final String msgMenu = "= = = = Bem-vindo(a) ao EconomizaP2 = = = ="
			+ NOVA_LINHA
			+ "Digite a opção desejada:"
			+ NOVA_LINHA
			+ "1 - Cadastrar um Produto"
			+ NOVA_LINHA
			+ "2 - Vender um Produto"
			+ NOVA_LINHA
			+ "3 - Imprimir Balanço"
			+ NOVA_LINHA
			+ "4 - Sair"
			+ NOVA_LINHA + "Opção: ";

	private static final String msgEntradaInvalida = "Opção Inválida.";

	private Supermercado supermercado = new Supermercado();

	public static void main(String[] args) {
		new Menu().exibirMenu();
	}

	/**
	 * Exibe o menu principal da aplicação.
	 */
	public void exibirMenu() {
		Integer opcao;
		do {
			System.out.println(NOVA_LINHA);
			opcao = Entrada.leInteiro(msgMenu);
			System.out.println(NOVA_LINHA);
			switch (opcao) {
			case 1:
				menuCadastro();
				break;
			case 2:
				menuVenda();
				break;
			case 3:
				imprimeBalanco();
				break;
			case 4:
				break;
			default:
				System.out.println(msgEntradaInvalida);
			}
		} while (opcao != 4);
	}

	/**
	 * Coleta os dados do produto e o vende.
	 */
	private void menuVenda() {
		String opcao;
		System.out.println("= = = = Venda de Produtos = = = =");
		do {
			vendeProduto();
			opcao = Entrada.leString("Deseja comprar outro produto? ");
		} while (!"Nao".equals(opcao));
	}

	/**
	 * Vende ou atualiza um produto a partir do nome fornecido pelo usuário.
	 */
	private void vendeProduto() {
		String nome = Entrada
				.leString("Insira o nome do produto que será vendido: ");
		Produto produto = supermercado.getProdutoPorNome(nome);

		if (produto == null) {
			System.out.printf("==> %s nao cadastrado(a) no sistema."
					+ NOVA_LINHA, nome);
		} else {
			System.out.printf("==> " + produto.getNome() + " (" + produto.getTipo() + "). " +  "R$%.2f" + NOVA_LINHA, produto.getPrecoUnitario());
			
			
			int quantidade = Entrada.leInteiro("Digite a quantidade a ser vendida: ");
			boolean vendido = supermercado.vendeProduto(produto, quantidade);
			
			if (vendido) {
				double totalArrecadado = produto.getPrecoUnitario()
						* quantidade;
				System.out.printf("==> Total arrecadado: R$ %.2f" + NOVA_LINHA
						, totalArrecadado);
			} else {
				System.out.println("Não é possível vender pois não há "
						+ produto.getNome() + " suficiente. " + "Há apenas "
						+ produto.getQuantidade() + " produto(s) \""
						+ produto.getNome() + "\" no estoque");
			}
		}
	}

	/**
	 * Cadastra um ou mais produtos no supermercado, dependendo da vontade do
	 * usuário.
	 */
	private void menuCadastro() {
		String opcao;
		System.out.println("= = = = Cadastro de Produtos = = = =");
		do {
			cadastraOuAtualizaProduto();
			opcao = Entrada.leString("Deseja cadastrar outro Produto? ");
		} while (!"Nao".equals(opcao));
	}
	
	/**
	 * Cadastra ou atualiza um produto no estoque.
	 */
	private void cadastraOuAtualizaProduto() {
		String nome = Entrada.leString("Digite o nome do produto: ");
		
		Produto produto = supermercado.getProdutoPorNome(nome);
		
		if(produto == null){
			produto = Menu.leProduto(nome);			
		}else{
			Menu.leNovosDados(produto);
		}
	
		int status = supermercado.cadastraOuAtualizaProduto(produto);
		switch (status) {
		case Supermercado.PRODUTO_ADICIONADO:
			System.out.printf("%d produto(s) \"" + produto.getNome()
					+ "\" cadastrado(s) com sucesso." + NOVA_LINHA,
					produto.getQuantidade());
			break;

		case Supermercado.PRODUTO_ATUALIZADO:
			System.out.printf("%d produto(s) \"" + produto.getNome()
					+ "\" atualizado com sucesso." + NOVA_LINHA,
					produto.getQuantidade());
			break;
		default:
			System.out.println("Operação não realizada.");
		}
	}

	/**
	 * Imprime os produtos em estoque, o total arrecadado e o total possível de ser arrecadado com os produtos que estão em estoque.
	 */
	private void imprimeBalanco() {
		System.out.println("= = = = Impressao de Balanco = = = =");
		System.out.println("Produtos cadastrados:");
		
		Produto [] produtos = supermercado.getEstoque();
		for (int i = 0; i < produtos.length; i++) {
			System.out.println("    " + (i + 1) + ") " + produtos[i]);
		}
		
		System.out.println(NOVA_LINHA);
		
		System.out.printf("Total arrecadado em vendas: R$ %.2f" + NOVA_LINHA, supermercado.getTotalArrecadado());
		System.out.printf("Total que pode ser arrecadado: R$ %.2f" + NOVA_LINHA, supermercado.getTotalPossivelArrecadado());
		System.out.println();
	}
	
	/**
	 * Lê apenas a quantidade e o preço unitário do produto.
	 * @param produto
	 */
	public static void leNovosDados(Produto produto) {
		int novaQuantidade = Entrada
				.leInteiro("Insira a quantidade do produto: ");
		double novoPreco = Entrada
				.leDouble("Insira o novo preço unitário do produto: "
						+ NOVA_LINHA);
		produto.setQuantidade(novaQuantidade);
		produto.setPrecoUnitario(novoPreco);
	}

	/**
	 * Lê um produto da entrada padrão.
	 * 
	 * @return O produto lido.
	 */
	private static Produto leProduto(String nome) {
		Double preco = Entrada.leDouble("Digite o preço unitário do produto: ");
		String tipo = Entrada.leString("Digite o tipo do produto: ");
		Integer quantidade = Entrada
				.leInteiro("Digite a quantidade no estoque: ");

		Produto produto = new Produto(nome, preco, tipo, quantidade);
		return produto;
	}
}