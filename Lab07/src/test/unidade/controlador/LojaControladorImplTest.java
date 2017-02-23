package test.unidade.controlador;

import main.controller.LojaController;
import main.controller.implementacao.LojaControllerImpl;
import main.entidade.jogo.Jogo;
import main.entidade.jogo.exception.JogoInvalidoException;
import main.entidade.jogo.tipo.Luta;
import main.entidade.usuario.exception.UsuarioInvalidoException;
import main.service.exception.SaldoInsuficienteException;
import main.service.exception.UsuarioInaptoException;

import org.junit.Assert;
import org.junit.Test;

import test.util.TestUtils;

import java.util.HashSet;

/**
 * Testes para {@link LojaControllerImpl}
 * Created by rerissondcsm on 17/02/17.
 */
public class LojaControladorImplTest {

    private LojaController controlador;

    @Test
    public void testaAdicionaUsuario() throws UsuarioInvalidoException {
        controlador = new LojaControllerImpl(TestUtils.getMapaUsuarios(), TestUtils.getFormatadora());
        String login = "marcos";
        String nome = "Marcos";
        controlador.adicionaUsuario(nome, login, "Noob");
    }

    @Test
    public void testaAdicionarDinheiroUsuario() throws JogoInvalidoException, SaldoInsuficienteException, UsuarioInvalidoException {
        controlador = new LojaControllerImpl(TestUtils.getMapaUsuarios(), TestUtils.getFormatadora());
        Assert.assertTrue(controlador.adicionarDinheiroUsuario("mauro", 25.00));
        Jogo tekken = TestUtils.getJogos().get("Tekken");
        controlador.vendeJogo("mauro", tekken.getNome(), new HashSet<>(), tekken.getPreco(), "luta");
    }

    @Test(expected = SaldoInsuficienteException.class)
    public void testaVendeJogoSaldoInsuficiente() throws JogoInvalidoException, SaldoInsuficienteException, UsuarioInvalidoException {
        controlador = new LojaControllerImpl(TestUtils.getMapaUsuarios(), TestUtils.getFormatadora());
        Assert.assertTrue(controlador.adicionarDinheiroUsuario("mauro", 25.00));
        Jogo tekken = TestUtils.getJogos().get("Tekken");
        controlador.vendeJogo("mauro", tekken.getNome(), new HashSet<>(), tekken.getPreco(), "luta");
        Jogo finalFantasy = TestUtils.getJogos().get("Final Fantasy X");
        controlador.vendeJogo("mauro", finalFantasy.getNome(), new HashSet<>(), finalFantasy.getPreco(), "luta");
    }

    @Test(expected = JogoInvalidoException.class)
    public void testaVendeJogorepetido() throws JogoInvalidoException, SaldoInsuficienteException, UsuarioInvalidoException {
        controlador = new LojaControllerImpl(TestUtils.getMapaUsuarios(), TestUtils.getFormatadora());
        Jogo mkUltimate = TestUtils.getJogos().get("MK ultimate");
        controlador.adicionarDinheiroUsuario("jose", 100000);
        controlador.vendeJogo("jose", mkUltimate.getNome(), new HashSet<>(), mkUltimate.getPreco(), Luta.REPRESENTACAO_STRING);
        controlador.vendeJogo("jose", mkUltimate.getNome(), new HashSet<>(), mkUltimate.getPreco(), Luta.REPRESENTACAO_STRING);
    }

    @Test
    public void testaVendeJogo() throws JogoInvalidoException, SaldoInsuficienteException, UsuarioInvalidoException {
        controlador = new LojaControllerImpl(TestUtils.getMapaUsuarios(), TestUtils.getFormatadora());
        Assert.assertTrue(controlador.adicionarDinheiroUsuario("mauro", 40.00));
        Jogo tekken = TestUtils.getJogos().get("Tekken");
        controlador.vendeJogo("mauro", tekken.getNome(), new HashSet<>(), tekken.getPreco(), "luta");
        Jogo finalFantasy = TestUtils.getJogos().get("Final Fantasy X");
        controlador.vendeJogo("mauro", finalFantasy.getNome(), new HashSet<>(), finalFantasy.getPreco(), "luta");
    }

    @Test
    public void testaUpgradeValido() throws UsuarioInvalidoException, UsuarioInaptoException, JogoInvalidoException, SaldoInsuficienteException {
        controlador = new LojaControllerImpl(TestUtils.getMapaUsuarios(), TestUtils.getFormatadora());
        Jogo mkUltimate = TestUtils.getJogos().get("MK ultimate");
        controlador.adicionarDinheiroUsuario("jose", 100000);
        controlador.vendeJogo("jose", mkUltimate.getNome(), new HashSet<>(), mkUltimate.getPreco(), Luta.REPRESENTACAO_STRING);

        controlador.registraJogada("MK ultimate", "jose", 100001, false);
        controlador.registraJogada("MK ultimate", "jose", 100002, false);
        controlador.registraJogada("MK ultimate", "jose", 100003, false);
        controlador.registraJogada("MK ultimate", "jose", 100004, false);
        controlador.registraJogada("MK ultimate", "jose", 100005, false);
        controlador.registraJogada("MK ultimate", "jose", 100006, false);
        controlador.registraJogada("MK ultimate", "jose", 100007, false);

        controlador.upgrade("jose");
    }

    @Test(expected = UsuarioInaptoException.class)
    public void testaUpgradeInvalido() throws UsuarioInvalidoException, UsuarioInaptoException {
        controlador = new LojaControllerImpl(TestUtils.getMapaUsuarios(), TestUtils.getFormatadora());
        controlador.upgrade("mauro");
    }
}
