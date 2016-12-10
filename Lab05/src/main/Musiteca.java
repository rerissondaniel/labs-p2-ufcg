package main;

import java.util.Set;

public class Musiteca {
	public static final String ALBUM_REPETIDO = "Este álbum já foi adicionado à músicoteca";

	private Set<Album> albuns;
	private Set<Album> favoritos;

	public Musiteca() {
	}

	public void adicionaAlbum(Album album) throws IllegalArgumentException {
		if (album == null) {
			throw new IllegalArgumentException(ALBUM_REPETIDO);
		}
	}
}
