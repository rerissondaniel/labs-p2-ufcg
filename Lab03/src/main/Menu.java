package main;

public class Menu {

	private static final String NOVA_LINHA = System.lineSeparator();

	private static final String msgMenu = "= = = = Bem-vindo(a) ao EconomizaP2 = = = =" + NOVA_LINHA
			+ "Digite a opção desejada:" + NOVA_LINHA + "1 - Cadastrar um Produto" + NOVA_LINHA
			+ "2 - Vender um Produto" + NOVA_LINHA + "3 - Imprimir Balanço" + NOVA_LINHA
			+ "4 - Sair" + NOVA_LINHA
			+ "Opção: ";
	

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
			opcao = Entrada.leInteiro(msgMenu);
			switch (opcao) {
			case 1:
				cadastraProduto();
				break;
			case 2:
				vendeProduto();
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
	private void vendeProduto() {
		String nome = Entrada.leString("Insira o nome do produto que será vendido: ");
		if (supermercado.vendeProduto(nome)) {
			System.out.println("Produto vendido.");
		} else {
			System.out.println("O produto não pôde ser vendido, pois não existem unidades suficientes em Estoque.");
		}
	}

	/**
	 * Cadastra um ou mais produtos no supermercado, dependendo da vontade do usuário.
	 */
	private void cadastraProduto() {
		String opcao;
		System.out.println("= = = = Cadastro de Produtos = = = =");
		do {
			Produto produto = Menu.leProduto();
			if (supermercado.cadastraProduto(produto)) {
				System.out.printf("%d produto(s) \"" + produto.getNome() + "\" cadastrado(s) com sucesso."
						+ NOVA_LINHA, produto.getQuantidade());
			} else {
				System.out.println("O produto não pôde ser cadastrado :(");
			}
			opcao = Entrada.leString("Deseja cadastrar outro Produto?" + NOVA_LINHA);
		} while (!"Nao".equals(opcao));
	}

	/**
	 * Imprime os produtos em estoque e os produtos vendidos.
	 */
	public void imprimeBalanco() {
		System.out.println("= = = = Vendidos = = = =");
		for (Produto produto : supermercado.getVendidos()) {
			System.out.println(produto);
		}

		System.out.println(NOVA_LINHA + "= = = = Estoque = = = =");

		for (Produto produto : supermercado.getEstoque()) {
			System.out.println(produto);
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Lê um produto da entrada padrão.
	 * 
	 * @return O produto lido.
	 */
	public static Produto leProduto() {
		String nome = Entrada.leString("Digite o nome do produto: ");
		Double preco = Entrada.leDouble("Digite o preço unitário do produto: ");
		String tipo = Entrada.leString("Digite o tipo do produto: ");
		Integer quantidade = Entrada.leInteiro("Digite a quantidade no estoque: ");

		Produto produto = new Produto(nome, preco, tipo, quantidade);
		return produto;
	}
}
