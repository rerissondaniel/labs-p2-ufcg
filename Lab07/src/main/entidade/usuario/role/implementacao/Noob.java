package main.entidade.usuario.role.implementacao;

import main.entidade.jogo.Jogo;
import main.entidade.usuario.role.Role;

/**
 * Classe que representa o role do usuário noob.
 *
 * @author rerissondcsm
 */
public class Noob implements Role {

    private static final int X2P_INICIAL_NOOB = 0;
    private static final double DESCONTO_NOOB = 0.10;

    public static final String REPRESENTACAO_STRING = "Noob";

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDesconto() {
        return DESCONTO_NOOB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getX2pInicial() {
        return X2P_INICIAL_NOOB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Noob";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getx2pCompra(double precoJogo) {
        return (int) precoJogo * 10;
    }
}
