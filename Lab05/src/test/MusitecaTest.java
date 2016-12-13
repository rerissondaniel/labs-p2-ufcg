package test;

import main.Album;
import main.Musica;
import main.Musiteca;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MusitecaTest {

	private Album album;

	@Before
	public void setup() {
		album = new Album("Pato fu", "Música de brinquedo", 2008);
		Musica musica = new Musica("Live and let die", 5, "rock");
		album.adicionaMusica(musica);
		musica = new Musica("Música para você viver mais", 4, "rock");
		album.adicionaMusica(musica);
	}

	@Test
	public void testaAdicionaPlaylist() {
		Musiteca musiteca = new Musiteca();
		musiteca.adicionaAlbum(album);

		try {
			musiteca.adicionaPlaylist("Para dançar", null, 2);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musiteca.ALBUM_NULO_OU_VAZIO, ex.getMessage());
		}

		try {
			musiteca.adicionaPlaylist("Para dançar", "   ", 2);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musiteca.ALBUM_NULO_OU_VAZIO, ex.getMessage());
		}

		try {
			musiteca.adicionaPlaylist(null, album.getTitulo(), 1);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musiteca.PLAYLIST_NULA_OU_VAZIA,
					ex.getMessage());
		}

		try {
			musiteca.adicionaPlaylist("      ", album.getTitulo(), 2);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musiteca.PLAYLIST_NULA_OU_VAZIA,
					ex.getMessage());
		}

		try {
			musiteca.adicionaPlaylist("Para varrer a casa", "Culture of fear",
					1);
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musiteca.ALBUM_NAO_EXISTENTE, ex.getMessage());
		}
	}

	@Test
	public void testaAdicionaAlbum() {
		Musiteca musiteca = new Musiteca();
		Assert.assertTrue(musiteca.adicionaAlbum(album));

		try {
			musiteca.adicionaAlbum(null);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(ex.getMessage(), Musiteca.ALBUM_NULO_OU_VAZIO);
		}

		try {
			musiteca.adicionaAlbum(album);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(ex.getMessage(), Musiteca.ALBUM_REPETIDO);
		}
	}

	@Test
	public void testaAdicionarFavorito() {
		Musiteca musiteca = new Musiteca();
		Assert.assertTrue(musiteca.adicionaFavorito(album));

		try {
			musiteca.adicionaAlbum(null);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(ex.getMessage(), Musiteca.ALBUM_NULO_OU_VAZIO);
		}

		try {
			musiteca.adicionaFavorito(album);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(ex.getMessage(),
					Musiteca.ALBUM_REPETIDO_FAVORITOS);
		}
	}

	@Test
	public void testaGetAlbum() {
		Musiteca musiteca = new Musiteca();
		musiteca.adicionaAlbum(album);
		Assert.assertEquals(musiteca.getAlbum(album.getTitulo()), album);
	}

	@Test
	public void testaGetFavorito() {
		Musiteca musiteca = new Musiteca();
		musiteca.adicionaFavorito(album);
		Assert.assertEquals(musiteca.getFavorito(album.getTitulo()), album);
	}

	@Test
	public void testaEquals() {
		Musiteca musiteca = new Musiteca();
		musiteca.adicionaAlbum(album);
		Musiteca musiteca1 = new Musiteca();
		musiteca1.adicionaAlbum(album);

		Assert.assertEquals(musiteca, musiteca1);

		Musica musica = new Musica("Antes que seja tarde", 4, "unknown");
		musiteca.getAlbum(album.getTitulo()).adicionaMusica(musica);
		Assert.assertNotSame(musiteca, musiteca1);
	}

	@Test
	public void testaHashCode() {
		Musiteca musiteca = new Musiteca();
		musiteca.adicionaAlbum(album);
		Musiteca musiteca1 = new Musiteca();
		musiteca1.adicionaAlbum(album);

		Assert.assertEquals(musiteca.hashCode(), musiteca1.hashCode());

		Musica musica = new Musica("Antes que seja tarde", 4, "unknown");
		musiteca.getAlbum(album.getTitulo()).adicionaMusica(musica);
		Assert.assertNotSame(musiteca.hashCode(), musiteca1.hashCode());
	}
}