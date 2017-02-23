package main.entidade.usuario;

import java.util.*;

import main.entidade.jogo.Jogo;
import main.entidade.jogo.exception.JogoInvalidoException;
import main.entidade.usuario.exception.UsuarioInvalidoException;
import main.entidade.usuario.role.Role;
import util.Util;

/**
 * Classe que representa um usuário.
 *
 * @author rerissondcsm
 */
public class Usuario {
    //Constantes para mensagens de exceptions.
    private static final String O_JOGO_NAO_FOI_ENCONTRADO = "O jogo não pôde ser encontrado.";
    private static final String LOGIN_INVALIDO = "O login do usuário é inválido!";
    private static final String NOME_INVALIDO = "O nome do usuário é inválido";
    private static final String ROLE_INVALIDO = "É necessário que o usuário tenha um role inicial.";
    private static final String JOGO_JA_VENDIDO = "Jogo já vendido a este usuário";

    /**
     * Nome deste usuário.
     */
    private String nome;

    /**
     * Quantia disponível para a conta deste usuário.
     */
    private double saldo;

    /**
     * Login utilizado para acesso ao sistema. É único para cada usuário.
     */
    private String login;

    /**
     * Conjunto dos jogos comprados por este usuário.
     */
    private Map<String, Jogo> jogosComprados;

    /**
     * Quantidade de experiência deste usuário.
     */
    private int x2p;

    /**
     * Papel deste usuário.
     */
    private Role role;

    /**
     * Construtor.
     *
     * @param nome           - nome do usuário.
     * @param login          - login do usuário.
     * @param jogosComprados - jogos comprados pelo usuário.
     * @param role           - role do usuário.
     * @throws UsuarioInvalidoException Caso o usuário não seja válido
     */
    public Usuario(String nome, String login, Map<String, Jogo> jogosComprados,
                   Role role) throws UsuarioInvalidoException {
        verificaDadosUsuario(nome, login, role);
        this.nome = nome;
        this.login = login;
        this.jogosComprados = jogosComprados;
        this.role = role;
        this.x2p = role.getX2pInicial();
    }

    /**
     * Verifica os dados passados como parâmetro.
     *
     * @param nome
     * @param login
     * @param role
     * @throws UsuarioInvalidoException Caso algum dos dados seja inválido.
     */
    private void verificaDadosUsuario(String nome, String login, Role role) throws UsuarioInvalidoException {
        if (Util.ehNulaOuVazia(login)) {
            throw new UsuarioInvalidoException(LOGIN_INVALIDO);
        }
        if (Util.ehNulaOuVazia(nome)) {
            throw new UsuarioInvalidoException(NOME_INVALIDO);
        }
        if (role == null) {
            throw new UsuarioInvalidoException(ROLE_INVALIDO);
        }
    }

    /**
     * Registra uma jogada deste jogo.
     *
     * @param nomeJogo - Nome do jogo que foi jogado.
     * @param score    - Score do jogo que foi jogado.
     * @param zerou    - Indica se o jogo foi zerado.
     */
    public void registraJogada(final String nomeJogo, final int score, final boolean zerou) throws JogoInvalidoException {
        Jogo jogo = jogosComprados.get(nomeJogo);
        verificaValidadeJogo(jogo);
        this.x2p += jogo.registraJogada(score, zerou);
    }

    /**
     * Verifica a validade de um jogo.
     *
     * @param jogo
     * @throws JogoInvalidoException Caso o jogo seja inválido.
     */
    private void verificaValidadeJogo(Jogo jogo) throws JogoInvalidoException {
        if (jogo == null) {
            throw new JogoInvalidoException(O_JOGO_NAO_FOI_ENCONTRADO);
        }
    }

    /**
     * Adiciona um jogo ao jogos comprados por este usuário.
     * Assume que esta é uma venda válida, isto é, este usuário pode comprar {@code jogo}, independentemente de
     * seu saldo ficar negativo ou não.
     *
     * @param jogo - {@link Jogo} a ser adicionado.
     */
    public void adicionaJogo(final Jogo jogo) throws JogoInvalidoException {
        Jogo aux = jogosComprados.get(jogo.getNome());
        if (aux != null) {
            throw new JogoInvalidoException(JOGO_JA_VENDIDO);
        }
        this.saldo -= jogo.getPreco() - (jogo.getPreco() * role.getDesconto());
        jogosComprados.put(jogo.getNome(), jogo);
        this.x2p += role.getx2pCompra(jogo.getPreco());
    }

    /**
     * @return os jogos comprados por este usuário.
     */
    public Collection<Jogo> getJogosComprados() {
        return jogosComprados.values();
    }

    public double getDesconto() {
        return role.getDesconto();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public int getX2p() {
        return x2p;
    }

    public double getSaldo() {
        return saldo;
    }

    public void adicionaSaldo(final double saldo) {
        this.saldo += saldo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Double.compare(usuario.saldo, saldo) == 0 &&
                x2p == usuario.x2p &&
                Objects.equals(nome, usuario.nome) &&
                Objects.equals(login, usuario.login) &&
                Objects.equals(jogosComprados, usuario.jogosComprados) &&
                Objects.equals(role, usuario.role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, saldo, login, jogosComprados, x2p, role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", saldo=" + saldo +
                ", login='" + login + '\'' +
                ", jogosComprados=" + jogosComprados +
                ", x2p=" + x2p +
                ", role=" + role +
                '}';
    }
}
