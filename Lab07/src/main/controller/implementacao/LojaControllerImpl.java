package main.controller.implementacao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import main.controller.LojaController;
import main.entidade.jogo.Jogo;
import main.entidade.jogo.exception.JogoInvalidoException;
import main.entidade.usuario.Usuario;
import main.entidade.usuario.exception.UsuarioInvalidoException;
import main.entidade.usuario.role.Role;
import main.entidade.usuario.role.implementacao.Noob;
import main.entidade.usuario.role.implementacao.Veterano;
import main.factory.JogoFactory;
import main.factory.UsuarioFactory;
import main.service.Formatadora;
import main.service.exception.SaldoInsuficienteException;
import main.service.exception.UsuarioInaptoException;

/**
 * Implementação de {@link LojaController}. Created by rerissondcsm on 15/02/17.
 */
public class LojaControllerImpl implements LojaController {

	/**
	 * Mapa de login para {@link Usuario} desta loja.
	 */
	private Map<String, Usuario> usuarios;

	/**
	 * {@link Formatadora} responsável por formatar adequadamente o relatório do
	 * usuário.
	 */
	private Formatadora formatadora;

	/**
	 * Construtor.
	 *
	 * @param usuarios
	 *            - {@link Map} com valores na forma <String loginUsuario,
	 *            Usuario usuario>.
	 * @param formatadora
	 *            - {@link Formatadora} para formatação de dados.
	 */
	public LojaControllerImpl(Map<String, Usuario> usuarios,
			Formatadora formatadora) {
		this.usuarios = usuarios;
		this.formatadora = formatadora;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void adicionaUsuario(final String nome, final String login,
			final String tipo) throws UsuarioInvalidoException {
		verificaExistenciaUsuario(login);

		Usuario usuario = UsuarioFactory.criaUsuario(nome, login, tipo);

		usuarios.put(login, usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean adicionarDinheiroUsuario(final String login,
			final double quantia) {
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
	public void upgrade(final String login) throws UsuarioInvalidoException,
			UsuarioInaptoException {
		Usuario usuario = usuarios.get(login);
		verificaValidadeUsuario(usuario);
		verificaUsuarioAptoUpgrade(usuario);
		usuario.setRole(new Veterano());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registraJogada(String nomeJogo, String login, int score,
			boolean zerou) throws JogoInvalidoException,
			UsuarioInvalidoException {
		Usuario usuario = usuarios.get(login);
		verificaValidadeUsuario(usuario);
		usuario.registraJogada(nomeJogo, score, zerou);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void vendeJogo(final String login, final String nomeJogo,
			final Set<String> jogabilidadesStr, final double preco,
			final String tipo) throws JogoInvalidoException,
			SaldoInsuficienteException, UsuarioInvalidoException {
		Usuario usuario = usuarios.get(login);
		verificaValidadeUsuario(usuario);

		Jogo jogo = JogoFactory.criaJogo(nomeJogo, preco, tipo,
				jogabilidadesStr);

		verificaSaldoSuficiente(usuario, jogo);
		usuario.adicionaJogo(jogo);
	}

	/**
	 * Verifica a existencia de {@code usuario} entre os usuários desta loja.
	 * 
	 * @param login
	 * @throws UsuarioInvalidoException
	 */
	private void verificaExistenciaUsuario(final String login)
			throws UsuarioInvalidoException {
		Usuario usuario = usuarios.get(login);
		if (usuario != null) {
			throw new UsuarioInvalidoException(USUARIO_EXISTENTE);
		}
	}

	/**
	 * Verifica a validade de um usuário.
	 *
	 * @param usuario
	 *            - {@link Usuario} a ser validado
	 * @throws UsuarioInvalidoException
	 *             Caso o usuário não seja válido.
	 */
	private void verificaValidadeUsuario(final Usuario usuario)
			throws UsuarioInvalidoException {
		if (usuario == null) {
			throw new UsuarioInvalidoException(USUARIO_NAO_ENCONTRADO);
		}
	}

	/**
	 * Verifica se {@code usuario} tem saldo suficiente para a compra de um
	 * jogo.
	 *
	 * @param usuario
	 *            - {@link Usuario} a ser verificado.
	 * @param jogo
	 *            - Jogo a ser verificado.
	 * @throws SaldoInsuficienteException
	 *             Caso o usuário não tenha saldo suficiente para a compra do
	 *             jogo.
	 */
	private void verificaSaldoSuficiente(final Usuario usuario, final Jogo jogo)
			throws SaldoInsuficienteException {
		if (usuario.getSaldo() < jogo.getPreco() * (1 - usuario.getDesconto())) {
			throw new SaldoInsuficienteException(SALDO_DE_USUARIO_INSUFICIENTE);
		}
	}

	/**
	 * Verifica se {@code usuario} é apto ao upgrade.
	 *
	 * @param usuario
	 *            {@link Usuario} a ser validado.
	 * @throws
	 */
	private void verificaUsuarioAptoUpgrade(Usuario usuario)
			throws UsuarioInaptoException {
		Role papel = usuario.getRole();
		if (!papel.getClass().equals(Noob.class)) {
			throw new UsuarioInaptoException(USUARIO_JA_E_VETERANO);
		} else if (usuario.getX2p() < X2P_MINIMO_VETERANO) {
			throw new UsuarioInaptoException(QUANTIDADE_X2P_INSUFICIENTE);
		}
	}
}
