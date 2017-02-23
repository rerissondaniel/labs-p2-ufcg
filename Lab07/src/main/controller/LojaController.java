package main.controller;

import main.entidade.jogo.exception.JogoInvalidoException;
import main.entidade.usuario.Usuario;
import main.entidade.usuario.exception.UsuarioInvalidoException;
import main.service.exception.SaldoInsuficienteException;
import main.service.exception.UsuarioInaptoException;

import java.util.List;
import java.util.Set;

/**
 * Interface responsável pelas operações da loja.
 */
public interface LojaController {

    /**
     * Constantes recomendadas para a implementação desta interface.
     */

    String USUARIO_EXISTENTE = "Login já cadastrado";
    String SALDO_DE_USUARIO_INSUFICIENTE = "Saldo do usuário insuficiente";
    String USUARIO_NAO_ENCONTRADO = "O usuário não pode ser encontrado.";
    String USUARIO_JA_E_VETERANO = "Usuário já é veterano.";
    String QUANTIDADE_X2P_INSUFICIENTE = "Usuário não possui quantidade suficiente de x2p";
    int X2P_MINIMO_VETERANO = 1000;

    /**
     * Adiciona um {@link Usuario} à loja.
     *
     * @param nome  - Nome do usuário.
     * @param login - Login do usuário.
     * @throws UsuarioInvalidoException Caso o usuário seja inválido.
     */
    void adicionaUsuario(final String nome, final String login, final String tipo)
            throws UsuarioInvalidoException;

    /**
     * @param login   - Login do usuário.
     * @param quantia - Quantia a ser adicionada à conta.
     * @return {@code true}, caso a operação seja realizada.
     */
    boolean adicionarDinheiroUsuario(final String login, final double quantia);


    /**
     * @return {@link List<String>} com as tuplas do relatório de usuários desta loja.
     */
    List<String> getRelatorioUsuarios();


    /**
     * Vende um jogo a um usuário.
     *
     * @param login            - login do usuário.
     * @param nomeJogo         - Nome do jogo a ser vendido.
     * @param jogabilidadesStr - String com a jogabilidade do jogo.
     * @param preco            - preço do jogo.
     * @param tipo             - tipo do jogo.
     * @throws JogoInvalidoException      Caso o jogo seja inválido.
     * @throws SaldoInsuficienteException Caso o saldo do usuário seja insuficiente
     * @throws UsuarioInvalidoException   Caso o usuário seja inválido.
     */
    void vendeJogo(final String login, final String nomeJogo, final Set<String> jogabilidadesStr,
                   final double preco, final String tipo)
            throws JogoInvalidoException, SaldoInsuficienteException, UsuarioInvalidoException;


    /**
     * Realiza o upgrade de um usuário.
     *
     * @param login - login do usuário;
     * @throws UsuarioInvalidoException Caso o login do usuário seja inválido
     * @throws UsuarioInaptoException   caso o usuário seja inapto a upgrade.
     */
    void upgrade(final String login) throws UsuarioInvalidoException, UsuarioInaptoException;

    /**
     * Registra que o {@link Usuario} que possui {@code login} como login jogou determinado jogo.
     *
     * @param login    - login do usuario.
     * @param nomeJogo - jogo que foi jogado.
     * @param score    - Valor do score do usuario.
     * @param zerou    - Indica se o usuário zerou o jogo.
     * @throws JogoInvalidoException    - Caso o jogo seja inválido.
     * @throws UsuarioInvalidoException - Caso o usuário seja inválido.
     */
    void registraJogada(final String nomeJogo, final String login, final int score, final boolean zerou) throws JogoInvalidoException, UsuarioInvalidoException;
}
