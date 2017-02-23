package main;

import main.controller.implementacao.LojaControllerImpl;
import main.service.implementacao.FormatadoraCentralP2Cg;
import util.io.implementacao.Console;
import util.io.implementacao.Teclado;
import main.menu.Loja;

import java.util.HashMap;

/**
 * Classe que inicia o sistema.
 * Created by rerissondcsm on 15/02/17.
 */
public class Main {

    public static void main(String[] args) {
        iniciaLoja();
    }

    /**
     * Injeta as depêndencias de {@link Loja} e inicia o sistema.
     */
    private static void iniciaLoja() {
        Loja loja = new Loja(new Teclado(), new Console(),
                new LojaControllerImpl(new HashMap<>(), new FormatadoraCentralP2Cg()));

        loja.iniciaSistema();
    }

}
