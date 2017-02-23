package main.entidade.usuario.role.implementacao;

import main.entidade.jogo.Jogo;
import main.entidade.usuario.role.Role;

/**
 * Classe que representa o Role do usuário veterano.
 *
 * @author rerissondcsm
 */
public class Veterano implements Role {

    private static final double DESCONTO_VETERANO = 0.20;
    private static final int X2P_INICIAL_VETERANO = 1000;
    public static final String REPRESENTACAO_STRING = "Veterano";

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDesconto() {
        return DESCONTO_VETERANO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getX2pInicial() {
        return X2P_INICIAL_VETERANO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Veterano";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getx2pCompra(double precoJogo) {
        return (int) precoJogo * 15;
    }
}
