package main.factory;

import java.util.HashSet;
import java.util.Set;

import main.entidade.jogo.Jogabilidade;
import main.entidade.jogo.Jogo;
import main.entidade.jogo.exception.JogoInvalidoException;
import main.entidade.jogo.tipo.Luta;
import main.entidade.jogo.tipo.Plataforma;
import main.entidade.jogo.tipo.Rpg;

/**
 * Classe responsável pela criação de jogos.
 * 
 * @author rerissondcsm
 *
 */
public class JogoFactory {
	
	/**
     * Template para a mensagem de jogabilidade inexistente.
     */
	private static final String TEMPLATE_JOGABILIDADE_NAO_EXISTENTE = "Jogo inválido, a jogabilidade  \"%s\" não existe.";
    
    private static final String TIPO_JOGO_NAO_ENCONTRADO = "Não há o tipo de jogo indicado";
	
	private JogoFactory() {

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
    public static Jogo criaJogo(final String nomeJogo, final double preco, final String tipo,
                          final Set<String> jogabilidadesStr) throws JogoInvalidoException {
        Jogo jogo;
        
        Set<Jogabilidade> jogabilidades = new HashSet<>();

        for (String jogabilidade : jogabilidadesStr) {
            Jogabilidade jogabilidadeAux = Jogabilidade.getPorEstilo(jogabilidade);
            if (jogabilidadeAux == null) {
                throw new JogoInvalidoException(String.format(TEMPLATE_JOGABILIDADE_NAO_EXISTENTE, jogabilidade));
            }
            jogabilidades.add(jogabilidadeAux);
        }

        if (tipo.equalsIgnoreCase(Luta.REPRESENTACAO_STRING)) {
            jogo = new Luta(nomeJogo, preco, jogabilidades);
        } else if (tipo.equalsIgnoreCase(Rpg.REPRESENTACAO_STRING)) {
            jogo = new Rpg(nomeJogo, preco, jogabilidades);
        } else if (tipo.equalsIgnoreCase(Plataforma.REPRESENTACAO_STRING)) {
            jogo = new Plataforma(nomeJogo, preco, jogabilidades);
        } else {
            throw new JogoInvalidoException(TIPO_JOGO_NAO_ENCONTRADO);
        }

        return jogo;
    }
}
