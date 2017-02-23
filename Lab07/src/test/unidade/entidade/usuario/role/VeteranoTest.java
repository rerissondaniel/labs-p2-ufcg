package test.unidade.entidade.usuario.role;

import main.entidade.jogo.Jogo;
import main.entidade.jogo.exception.JogoInvalidoException;
import main.entidade.usuario.Usuario;
import main.entidade.usuario.exception.UsuarioInvalidoException;
import main.entidade.usuario.role.implementacao.Veterano;
import org.junit.Assert;
import org.junit.Test;
import test.util.TestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Testes para {@link Veterano}
 * Created by rerisson on 18/02/17.
 */
public class VeteranoTest {

    @Test
    public void testaCriacaoUsuarioVeteranoValido() throws UsuarioInvalidoException {
        Usuario usuario = new Usuario("Joao", "joao", new HashMap<>(), new Veterano());
        Assert.assertEquals(1000, usuario.getX2p());
    }

    @Test
    public void testaAdicionaJogoVeterano() throws UsuarioInvalidoException, JogoInvalidoException {
        Usuario usuario = new Usuario("Joao", "joao", new HashMap<>(), new Veterano());
        Map<String, Jogo> jogos = TestUtils.getJogos();

        Jogo finalFantasyX = jogos.get("Tekken");
        double saldoAnteriorff = usuario.getSaldo();

        double saldoNovo = finalFantasyX.getPreco() - finalFantasyX.getPreco() * usuario.getDesconto();
        int x2pNovo = (int) (usuario.getX2p() + (15 * finalFantasyX.getPreco()));

        usuario.adicionaJogo(finalFantasyX);

        Assert.assertEquals(x2pNovo, usuario.getX2p());
        Assert.assertEquals(saldoAnteriorff, usuario.getSaldo() +
                saldoNovo, 0.005);
    }
}
