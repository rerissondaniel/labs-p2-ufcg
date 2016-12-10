package test;

import org.junit.Assert;
import org.junit.Test;

import main.Musica;
import main.Playlist;

public class PlaylistTest{

	@Test
	public void testaPesquisaMusica() {
		Playlist playlist = new Playlist("Para Testar");
		Musica musica = new Musica("The less I know the better", 5, "Lonerism");
		playlist.adicionaMusica(musica);

		Assert.assertEquals(playlist.pesquisaMusica(musica.getTitulo()), musica);
		Assert.assertNull(playlist.pesquisaMusica("Let it happen"));
	}

	@Test
	public void testaRemoveMusica() {
		Playlist playlist = new Playlist("Para Testar");
		Musica musica = new Musica("The less I know the better", 5, "Lonerism");
		playlist.adicionaMusica(musica);

		Assert.assertTrue(playlist.removeMusica(musica));
		Assert.assertFalse(playlist.removeMusica(musica));
	}

	@Test
	public void testaRemoveMusicaTitulo() {
		Playlist playlist = new Playlist("Para Testar");
		Musica musica = new Musica("The less I know the better", 5, "Lonerism");
		playlist.adicionaMusica(musica);

		Assert.assertTrue(playlist.removeMusica("The less I know the better"));
		Assert.assertFalse(playlist.removeMusica(musica));
	}

	@Test
	public void testaAdicionaMusica() {
		Playlist playlist = new Playlist("Para testar");
		Musica musica = new Musica("The less I know the better", 5, "Lonerism");
		Assert.assertTrue(playlist.adicionaMusica(musica));

		try {
			Assert.assertFalse(playlist.adicionaMusica(musica));
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(ex.getMessage(), Playlist.MUSICA_REPETIDA);
		}

		musica = null;
		try {
			Assert.assertFalse(playlist.adicionaMusica(musica));
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(ex.getMessage(), Playlist.MUSICA_NULA);
		}
	}

	@Test
	public void testaHashCode() {
		Playlist playlist = new Playlist("Para testar");
		Musica musica = new Musica("The less I know the better", 5, "Lonerism");
		playlist.adicionaMusica(musica);

		Playlist playlist1 = new Playlist("Para testar");
		Musica musica1 = new Musica("Elephant", 5, "Lonerism");
		playlist1.adicionaMusica(musica);

		Assert.assertEquals(playlist.hashCode(), playlist1.hashCode());

		playlist.adicionaMusica(musica1);
		Assert.assertNotSame(playlist.hashCode(), playlist1.hashCode());
	}

	@Test
	public void testaEquals() {
		Playlist playlist = new Playlist("Para testar");
		Musica musica = new Musica("The less I know the better", 5, "Lonerism");
		playlist.adicionaMusica(musica);

		Playlist playlist1 = new Playlist("Para testar");
		Musica musica1 = new Musica("Elephant", 5, "Lonerism");
		playlist1.adicionaMusica(musica);

		Assert.assertEquals(playlist, playlist1);

		playlist.adicionaMusica(musica1);
		Assert.assertNotSame(playlist, playlist1);
	}
}
