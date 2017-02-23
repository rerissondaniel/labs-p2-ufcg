package main.factory;

import java.util.HashMap;

import main.entidade.usuario.Usuario;
import main.entidade.usuario.exception.UsuarioInvalidoException;
import main.entidade.usuario.role.implementacao.Noob;
import main.entidade.usuario.role.implementacao.Veterano;

/**
 * Classe responsável pela criação de usuários.
 * 
 * @author rerissondcsm
 *
 */
public class UsuarioFactory {
	private UsuarioFactory() {

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
    public static Usuario criaUsuario(String nome, String login, String tipo) throws UsuarioInvalidoException {
    	Usuario usuario;
        if (Noob.REPRESENTACAO_STRING.equalsIgnoreCase(tipo)) {
            usuario = new Usuario(nome, login, new HashMap<>(), new Noob());
        } else if (Veterano.REPRESENTACAO_STRING.equalsIgnoreCase(tipo)) {
            usuario = new Usuario(nome, login, new HashMap<>(), new Veterano());
        } else {
            usuario = new Usuario(nome, login, new HashMap<>(), null);
        }
        return usuario;
    }
}
