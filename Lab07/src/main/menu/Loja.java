package main.menu;

import main.controlador.LojaControlador;
import main.entidade.jogo.exception.JogoInvalidoException;
import main.entidade.usuario.exception.UsuarioInvalidoException;
import main.service.exception.SaldoInsuficienteException;
import main.service.exception.UsuarioInaptoException;
import util.io.Entrada;
import util.io.Saida;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe responsável pela comunicação com o usuario e exibição de menus.
 */
public class Loja {
    private static final String ADICIONAR_USUARIO_MSG = "Adicionar Usuário";
    private static final String ADICIONAR_DINHEIRO_USUARIO_MSG = "Adicionar dinheiro à conta de usuário";
    private static final String VENDER_JOGOS_USUARIO_MSG = "Vender jogo a um usuário";
    private static final String IMPRIMIR_RELATORIO_USUARIOS_MSG = "Imprimir relatório de usuários";
    private static final String USUARIO_NAO_ENCONTRADO_MSG = "Usuário não encontrado";
    private static final String UPGRADE_USUARIO_MSG = "Fazer upgrade de usuário ";
    private static final String SAIR_MSG = "Sair";
    private static final String OPCAO_INVALIDA_MSG = "Opção inválida";

    private static final String INSIRA_UMA_OPCAO_MSG = "Insira uma opção: ";
    private static final String INSIRA_NOME_MSG = "Insira o nome: ";
    private static final String INSIRA_LOGIN_MSG = "Insira o login: ";
    private static final String INSIRA_QUANTIA_MSG = "Insira quanto deseja adicionar à conta de usuário: ";
    private static final String INSIRA_NOME_JOGO_MSG = "Insira o nome do jogo: ";
    private static final String INSIRA_JOGABILIDADE_MSG = "Insira a jogabilidade do jogo (insira \"acabou\", quando terminar): ";
    private static final String INSIRA_PRECO_JOGO_MSG = "Insira o preço do jogo: ";
    private static final String INSIRA_TIPO_USUARIO_MSG = "Insira o tipo do usuário(\"Veterano\" ou \"Noob\" ): ";

    private static final String NAO_MSG = "Nao";
    private static final String SIM_MSG = "Sim";

    private static final String USUARIO_ZEROU_JOGO_MSG = "O usuário zerou o jogo? (" + SIM_MSG + "/" + NAO_MSG + ")";

    private static final String INSIRA_TIPO_JOGO_MSG = "Insira o tipo do jogo (Rpg, Luta ou Plataforma): ";
    private static final String REGISTRAR_JOGADA_MSG = "Registrar jogada de usuário";
    private static final String ACABOU_OP = "acabou";

    private static final int ADICIONAR_USUARIO_OP = 1;
    private static final int ADICIONAR_DINHEIRO_USUARIO_OP = 2;
    private static final int VENDER_JOGOS_USUARIO_OP = 3;
    private static final int IMPRIMIR_RELATORIO_USUARIOS_OP = 4;
    private static final int UPGRADE_USUARIO_OP = 5;
    private static final int REGISTRAR_JOGADA_OP = 6;
    private static final int SAIR_OP = 7;

    private static final String INSIRA_O_SCORE_MSG = "Insira o score: ";
    private static final String CENTRAL_P2_CG_MSG = "=== Central P2-CG ===";

    /**
     * Entrada da qual serão lidos os dados.
     */
    private final Entrada entrada;
    /**
     * Saida na qual serão escritos os resultados.
     */
    private final Saida saida;
    /**
     * Controller ao qual será delegada a execução das operações.
     */
    private final LojaControlador loja;

    /**
     * Construtor onde são atribuídas as dependências deste objeto.
     *
     * @param entrada - {@link Entrada} da qual serão lidos os dados.
     * @param saida   - {@link Saida} na qual serão escritos os resultados.
     * @param loja    - {@link LojaControlador} ao qual será delegada a execução das operações.
     */
    public Loja(final Entrada entrada, final Saida saida, LojaControlador loja) {
        this.entrada = entrada;
        this.saida = saida;
        this.loja = loja;
    }

    /**
     * Inicia o sistema e exibe menus ao usuário.
     */
    public void iniciaSistema() {
        int op;
        do {
            imprimeOpcoes();

            saida.escreve(SAIR_OP + " - " + SAIR_MSG);
            saida.escreve(INSIRA_UMA_OPCAO_MSG);
            op = entrada.leInteiro();

            trataOpcaoInserida(op);
        } while (op != SAIR_OP);
    }

    /**
     * Trada a opção inserida pelo usuário.
     *
     * @param op - Opção inserida pelo usuário.
     */
    private void trataOpcaoInserida(final int op) {
        switch (op) {
            case ADICIONAR_USUARIO_OP:
                adicionaUsuario();
                break;

            case ADICIONAR_DINHEIRO_USUARIO_OP:
                adicionaDinheiroUsuario();
                break;

            case VENDER_JOGOS_USUARIO_OP:
                vendeJogo();
                break;

            case IMPRIMIR_RELATORIO_USUARIOS_OP:
                imprimeRelatorioUsuario();
                break;
            case UPGRADE_USUARIO_OP:
                realizaUpgradeUsuario();
            case SAIR_OP:
                break;

            case REGISTRAR_JOGADA_OP:
                registraJogada();
                break;
            default:
                saida.escreve(OPCAO_INVALIDA_MSG);
        }
    }

    /**
     * Registra a jogada de um usuário.
     */
    private void registraJogada() {
        String login = leLogin();
        String jogo = leJogo();
        int score = leScore();
        boolean zerou = leZerou();
        try {
            loja.registraJogada(jogo, login, score, zerou);
        } catch (UsuarioInvalidoException | JogoInvalidoException e) {
            saida.escreve(e.getMessage());
        }
    }

    /**
     * @return true, se o valor lido indicar que o jogo foi zerado.
     */
    private boolean leZerou() {
        saida.escreve(USUARIO_ZEROU_JOGO_MSG);
        String resposta = entrada.leString();
        if (SIM_MSG.equalsIgnoreCase(resposta)) {
            return true;
        }
        return false;
    }

    /**
     * Le um score.
     *
     * @return - o score lido.
     */
    private int leScore() {
        saida.escreve(INSIRA_O_SCORE_MSG);
        return entrada.leInteiro();
    }

    /**
     * Reliza operações de entrada e saída para adicionar um usuário.
     */
    private void adicionaUsuario() {
        String nome = leNome();
        String login = leLogin();
        String tipo = leTipo();
        try {
            loja.adicionaUsuario(nome, login, tipo);
        } catch (UsuarioInvalidoException usuarioInvalido) {
            saida.escreve(usuarioInvalido.getMessage());
        }
    }

    private String leTipo() {
        saida.escreve(INSIRA_TIPO_USUARIO_MSG);
        return entrada.leString();
    }

    /**
     * Lê o login de um usuário.
     *
     * @return o login lido.
     */
    private String leLogin() {
        saida.escreve(INSIRA_LOGIN_MSG);
        return entrada.leString();
    }

    /**
     * Lê o nome de um usuário.
     *
     * @return o nome lido.
     */
    private String leNome() {
        saida.escreve(INSIRA_NOME_MSG);
        return entrada.leString();
    }

    /**
     * * Reliza operações de entrada e saída para adicionar dinheiro à conta de um usuário.
     */
    private void adicionaDinheiroUsuario() {
        String login = leLogin();
        double quantia = leQuantia();
        if (!loja.adicionarDinheiroUsuario(login, quantia)) {
            saida.escreve(USUARIO_NAO_ENCONTRADO_MSG);
        }
    }

    /**
     * Lê uma quantia.
     *
     * @return a quantia lida.
     */
    private double leQuantia() {
        saida.escreve(INSIRA_QUANTIA_MSG);
        return entrada.leDouble();
    }

    /**
     * Reliza operações de entrada e saída para imprimir o relatório de usuários desta loja.
     */
    private void imprimeRelatorioUsuario() {
        for (String item : loja.getRelatorioUsuarios()) {
            saida.escreve(item);
        }
    }

    /**
     * Reliza operações de entrada e saída para realizar o upgrade de um usuário.
     */
    private void realizaUpgradeUsuario() {
        String login = leLogin();
        try {
            loja.upgrade(login);
        } catch (UsuarioInvalidoException | UsuarioInaptoException e) {
            saida.escreve(e.getMessage());
        }
    }

    /**
     * Reliza operações de entrada e saída para vender um jogo a um usuário.
     */
    private void vendeJogo() {
        String login = leLogin();
        String jogo = leJogo();
        Set<String> jogabilidades = leJogabilidades();

        double preco = lePrecoJogo();
        String tipo = leTipoJogo();

        try {
            loja.vendeJogo(login, jogo, jogabilidades, preco, tipo);
        } catch (UsuarioInvalidoException | SaldoInsuficienteException | JogoInvalidoException e) {
            saida.escreve(e.getMessage());
        }
    }

    /**
     * lê o tipo de um jogo.
     *
     * @return - o tipo do jogo.
     */
    private String leTipoJogo() {
        saida.escreve(INSIRA_TIPO_JOGO_MSG);
        return entrada.leString();
    }

    /**
     * Lê o preço de um jogo.
     *
     * @return - o preço do jogo.
     */
    private double lePrecoJogo() {
        saida.escreve(INSIRA_PRECO_JOGO_MSG);
        return entrada.leDouble();
    }

    /**
     * Lê um conjunto de jogabilidades.
     *
     * @return {@link Set<String>} com as jogabilidades.
     */
    private Set<String> leJogabilidades() {
        Set<String> jogabilidades = new HashSet<>();
        String jogabilidade;
        do {
            saida.escreve(INSIRA_JOGABILIDADE_MSG);
            jogabilidade = entrada.leString();
            if (!ACABOU_OP.equalsIgnoreCase(jogabilidade)) {
                jogabilidades.add(jogabilidade);
            }
        } while (!ACABOU_OP.equalsIgnoreCase(jogabilidade));
        return jogabilidades;
    }

    /**
     * lê o nome de um jogo.
     *
     * @return o nome do jogo lido.
     */
    private String leJogo() {
        saida.escreve(INSIRA_NOME_JOGO_MSG);
        return entrada.leString();
    }

    /**
     * Imprime as opções para o usuário.
     */
    private void imprimeOpcoes() {
        saida.escreve(CENTRAL_P2_CG_MSG);
        saida.escreve(ADICIONAR_USUARIO_OP + " - "
                + ADICIONAR_USUARIO_MSG);
        saida.escreve(ADICIONAR_DINHEIRO_USUARIO_OP + " - "
                + ADICIONAR_DINHEIRO_USUARIO_MSG);
        saida.escreve(VENDER_JOGOS_USUARIO_OP + " - "
                + VENDER_JOGOS_USUARIO_MSG);
        saida.escreve(IMPRIMIR_RELATORIO_USUARIOS_OP + " - "
                + IMPRIMIR_RELATORIO_USUARIOS_MSG);
        saida.escreve(UPGRADE_USUARIO_OP + " - "
                + UPGRADE_USUARIO_MSG);
        saida.escreve(REGISTRAR_JOGADA_OP + " - "
                + REGISTRAR_JOGADA_MSG);
    }
}
