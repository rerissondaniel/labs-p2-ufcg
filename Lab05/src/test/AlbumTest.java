package test;

import main.Album;
import main.Musica;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AlbumTest {

	private Album mariliaMendDVD;

	@Before
	public void setUp() throws Exception {
		mariliaMendDVD = new Album("Marilia Mendonca", "Marilia Mendonca- Audio DVD", 2015);
	}
	
	@Test
	public void testConstrutorWithException() {

		// Test Artista Vazio/Null
		try {
			Album album = new Album("  ", "AoVivo", 2012);
			Assert.fail("Lancamento de Exception com Artista vazio");

		} catch (Exception e) {
			Assert.assertEquals("Artista do album nao pode ser nulo ou vazio.", e.getMessage());
		}
		
		try {
			Album album1 = new Album(null, "AoVivo", 2012);
			Assert.fail("Lancamento de Exception com Artista null");

		} catch (Exception e) {
			Assert.assertEquals("Artista do album nao pode ser nulo ou vazio.", e.getMessage());

		}

		// Test Titulo Vazio/Null
		try {
			Album album = new Album("Sia", "", 2015);
			Assert.fail("Lancamento de Exception com Titulo Vazio");

		} catch (Exception e) {
			Assert.assertEquals("Titulo do album nao pode ser nulo ou vazio.", e.getMessage());

		}
		try {
			Album album2 = new Album("Marilia Mendonca", null, 2015);
			Assert.fail("Lancamento de Exception com Titulo null");

		} catch (Exception e) {
			Assert.assertEquals("Titulo do album nao pode ser nulo ou vazio.", e.getMessage());

		}

		// Test Ano de Lancamento Inferior ao esperado/Nulo

		try {
			Album album = new Album("Sia", "This is Acting", 1899);
			Assert.fail("Exception com Ano de Lancamento Inferior ao esperado");

		} catch (Exception e) {
			Assert.assertEquals("Ano de lancamento do album nao pode ser inferior a 1900.", e.getMessage());

		}
		try {
			Album album1 = new Album("Marilia Mendonca", "Marilia Mendonca - Audio DVD", 0);
			Assert.fail("Exception com ano de Lancamento nulo");

		} catch (Exception e) {
			Assert.assertEquals("Ano de lancamento do album nao pode ser inferior a 1900.", e.getMessage());
		}
	}

	@Test
	public void testAddFaixa() throws Exception {
		Musica infiel = new Musica("Infiel", 3, "Sertanejo");
		Musica comoFazComEla = new Musica("Como faz com ela", 3, "Sertanejo");
		Musica aloPorteiro = new Musica("Alo porteiro", 5, "Sertanejo");
		Musica comoFazComElaPop = new Musica("Como faz com ela", 3, "Pop");

		//casos corretos de adicao
		Assert.assertTrue(mariliaMendDVD.adicionaMusica(infiel));
		//adicionar musicas tidas como iguais.
		Assert.assertTrue(mariliaMendDVD.adicionaMusica(comoFazComEla));
		Assert.assertTrue(mariliaMendDVD.adicionaMusica(comoFazComElaPop));

		Assert.assertTrue(mariliaMendDVD.contemMusica("Infiel"));
		Assert.assertTrue(mariliaMendDVD.contemMusica("Como faz com ela"));
		Assert.assertFalse(mariliaMendDVD.contemMusica("Alo porteiro"));

		//casos invalidos de adicao
		Assert.assertFalse(mariliaMendDVD.adicionaMusica(null));
	}

	@Test
	public void testRemoveFaixa() throws Exception {
		Musica aloPorteiro = new Musica("Alo porteiro", 5, "Sertanejo");

		mariliaMendDVD.adicionaMusica(aloPorteiro);		
		Assert.assertTrue(mariliaMendDVD.contemMusica("Alo porteiro"));

		mariliaMendDVD.removeMusica(1);
		Assert.assertFalse(mariliaMendDVD.contemMusica("Alo porteiro"));

	}

	@Test
	public void testNumeroDeFaixas() throws Exception {
		Musica sentimentoLouco = new Musica("Sentimento Louco", 2, "Sertanejo");
		mariliaMendDVD.adicionaMusica(sentimentoLouco);
		Assert.assertEquals(1, mariliaMendDVD.quantidadeFaixas());
	}

	@Test
	public void testGetMusica() throws Exception {
		Musica oQueFalta = new Musica("O que falta em voce sou eu", 4, "Sertanejo");
		mariliaMendDVD.adicionaMusica(oQueFalta);
		Assert.assertEquals(oQueFalta, mariliaMendDVD.getMusica("O que falta em voce sou eu"));
	}

	@Test
	public void testDuracaoTotal() throws Exception {
		Musica folgado = new Musica("Folgado", 3, "Sertanejo");
		Musica esseCaraAquiDoLado = new Musica("Esse cara aqui do lado", 5, "Sertanejo");

		mariliaMendDVD.adicionaMusica(folgado);
		mariliaMendDVD.adicionaMusica(esseCaraAquiDoLado);

		Assert.assertEquals(8, mariliaMendDVD.getDuracaoTotal());
	}

	@Test
	public void testEquals() throws Exception {
		Musica meuCupidoEhGari = new Musica("Meu cupido eh gari", 5, "Sertanejo");
		mariliaMendDVD.adicionaMusica(meuCupidoEhGari);

		Album mariliaMendDVD2 = new Album("Marilia Mendonca", "Marilia Mendonca- Audio DVD", 2015);
		Assert.assertTrue(mariliaMendDVD.equals(mariliaMendDVD2));
	}
	
	@Test
	public void testNotEquals() throws Exception {
		Musica meuCupidoEhGari = new Musica("Meu cupido eh gari", 5, "Sertanejo");
		mariliaMendDVD.adicionaMusica(meuCupidoEhGari);

		Album forroPerfil = new Album("Perfil - Marilia Mendonca", "Marilia Mendoca", 2015);
		forroPerfil.adicionaMusica(meuCupidoEhGari);
		
		Assert.assertFalse(mariliaMendDVD.equals(forroPerfil));
	}
}