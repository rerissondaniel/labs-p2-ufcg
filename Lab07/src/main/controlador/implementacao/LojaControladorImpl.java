package main.controlador.implementacao;

import main.controlador.LojaControlador;
import main.entidade.jogo.Jogabilidade;
import main.entidade.jogo.Jogo;
import main.entidade.jogo.exception.JogoInvalidoException;
import main.entidade.jogo.tipo.Luta;
import main.entidade.jogo.tipo.Plataforma;
import main.entidade.jogo.tipo.Rpg;
import main.entidade.usuario.Usuario;
import main.entidade.usuario.exception.UsuarioInvalidoException;
import main.entidade.usuario.role.Role;
import main.entidade.usuario.role.implementacao.Noob;
import main.entidade.usuario.role.implementacao.Veterano;
import main.service.Formatadora;
import main.service.exception.SaldoInsuficienteException;
import main.service.exception.UsuarioInaptoException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementação de {@link LojaControlador}.
 * Created by rerissondcsm on 15/02/17.
 */
public class LojaControladorImpl implements LojaControlador {

    /**
     * Mapa de login para {@link Usuario} desta loja.
     */
    private Map<String, Usuario> usuarios;

    /**
     * {@link Formatadora} responsável por formatar adequadamente o relatório do usuário.
     */
    private Formatadora formatadora;


    /**
     * Construtor.
     *
     * @param usuarios    - {@link Map} com valores na forma <String loginUsuario, Usuario usuario>.
     * @param formatadora - {@link Formatadora} para formatação de dados.
     */
    public LojaControladorImpl(Map<String, Usuario> usuarios, Formatadora formatadora) {
        this.usuarios = usuarios;
        this.formatadora = formatadora;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void adicionaUsuario(final String nome, final String login, final String tipo)
            throws UsuarioInvalidoException {
        Usuario usuario = criaUsuario(nome, login, tipo);
        usuarios.put(login, usuario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean adicionarDinheiroUsuario(final String login, final double quantia) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            return false;
        }
        usuario.adicionaSaldo(quantia);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getRelatorioUsuarios() {
        return this.formatadora.formataDadosUsuario(usuarios.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upgrade(final String login) throws UsuarioInvalidoException, UsuarioInaptoException {
        Usuario usuario = usuarios.get(login);
        verificaValidadeUsuario(usuario);
        verificaUsuarioAptoUpgrade(usuario);
        usuario.setRole(new Veterano());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registraJogada(String nomeJogo, String login, int score, boolean zerou) throws JogoInvalidoException, UsuarioInvalidoException {
        Usuario usuario = usuarios.get(login);
        verificaValidadeUsuario(usuario);
        usuario.registraJogada(nomeJogo, score, zerou);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vendeJogo(final String login, final String nomeJogo, final Set<String> jogabilidadesStr,
                          final double preco, final String tipo)
            throws JogoInvalidoException, SaldoInsuficienteException, UsuarioInvalidoException {
        Usuario usuario = usuarios.get(login);
        verificaValidadeUsuario(usuario);

        Set<Jogabilidade> jogabilidades = new HashSet<>();

        for (String jogabilidade : jogabilidadesStr) {
            Jogabilidade jogabilidadeAux = Jogabilidade.getPorEstilo(jogabilidade);
            if (jogabilidadeAux == null) {
                throw new JogoInvalidoException(String.format(TEMPLATE_JOGABILIDADE_NAO_EXISTENTE, jogabilidade));
            }
            jogabilidades.add(jogabilidadeAux);
        }

        Jogo jogo = criaJogo(nomeJogo, preco, tipo, jogabilidades);
        verificaSaldoSuficiente(usuario, jogo);
        usuario.adicionaJogo(jogo);
    }

    /**
     * Verifica a validade de um usuário.
     *
     * @param usuario - {@link Usuario} a ser validado
     * @throws UsuarioInvalidoException Caso o usuário não seja válido.
     */
    private void verificaValidadeUsuario(final Usuario usuario) throws UsuarioInvalidoException {
        if (usuario == null) {
            throw new UsuarioInvalidoException(USUARIO_NAO_ENCONTRADO);
        }
    }

    /**
     * Verifica se {@code usuario} tem saldo suficiente para a compra de um jogo.
     *
     * @param usuario - {@link Usuario} a ser verificado.
     * @param jogo    - Jogo a ser verificado.
     * @throws SaldoInsuficienteException Caso o usuário não tenha saldo suficiente para a compra do jogo.
     */
    private void verificaSaldoSuficiente(final Usuario usuario, final Jogo jogo) throws SaldoInsuficienteException {
        if (usuario.getSaldo() < jogo.getPreco() * (1 - usuario.getDesconto())) {
            throw new SaldoInsuficienteException(SALDO_DE_USUARIO_INSUFICIENTE);
        }
    }

    /**
     * Verifica se {@code usuario} é apto ao upgrade.
     *
     * @param usuario {@link Usuario} a ser validado.
     * @throws
     */
    private void verificaUsuarioAptoUpgrade(Usuario usuario) throws UsuarioInaptoException {
        Role papel = usuario.getRole();
        if (!papel.getClass().equals(Noob.class)) {
            throw new UsuarioInaptoException(USUARIO_JA_E_VETERANO);
        }else if(usuario.getX2p() < X2P_MINIMO_VETERANO){
            throw new UsuarioInaptoException(QUANTIDADE_X2P_INSUFICIENTE);
        }
    }

    /**
     * Cria um usuario a partir dos parâmetros.
     *
     * @param nome
     * @param login
     * @param tipo
     * @return
     * @throws UsuarioInvalidoException Caso algum dos dados seja inválido.
     */
    private Usuario criaUsuario(String nome, String login, String tipo) throws UsuarioInvalidoException {
        Usuario usuario = usuarios.get(login);
        if (usuario != null) {
            throw new UsuarioInvalidoException(USUARIO_EXISTENTE);
        }
        if (Noob.REPRESENTACAO_STRING.equalsIgnoreCase(tipo)) {
            usuario = new Usuario(nome, login, new HashMap<>(), new Noob());
        } else if (Veterano.REPRESENTACAO_STRING.equalsIgnoreCase(tipo)) {
            usuario = new Usuario(nome, login, new HashMap<>(), new Veterano());
        } else {
            usuario = new Usuario(nome, login, new HashMap<>(), null);
        }
        return usuario;
    }

    /**
     * Cria um jogo a partir dos atributos passados como parâmetro.
     *
     * @param nomeJogo
     * @param preco
     * @param tipo
     * @param jogabilidade
     * @return
     * @throws JogoInvalidoException - Caso o tipo do jogo não exista no sistema.
     */
    private Jogo criaJogo(final String nomeJogo, final double preco, final String tipo,
                          final Set<Jogabilidade> jogabilidade) throws JogoInvalidoException {
        Jogo jogo;

        if (tipo.equalsIgnoreCase(Luta.REPRESENTACAO_STRING)) {
            jogo = new Luta(nomeJogo, preco, jogabilidade);
        } else if (tipo.equalsIgnoreCase(Rpg.REPRESENTACAO_STRING)) {
            jogo = new Rpg(nomeJogo, preco, jogabilidade);
        } else if (tipo.equalsIgnoreCase(Plataforma.REPRESENTACAO_STRING)) {
            jogo = new Plataforma(nomeJogo, preco, jogabilidade);
        } else {
            throw new JogoInvalidoException(TIPO_JOGO_NAO_ENCONTRADO);
        }

        return jogo;
    }
}
