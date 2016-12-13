package main;

public class Usuario {
	private String nome;
	private Musiteca musiteca;

	public Usuario(String nome) {
		super();
		this.nome = nome;
		this.musiteca = new Musiteca();
	}

	/**
	 * Adiciona uma nova playlist.
	 * 
	 * @param nomePlaylist
	 * @param nomeAlbum
	 * @param faixa
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean adicionaPlaylist(final String nomePlaylist,
			final String nomeAlbum, final int faixa)
			throws IllegalArgumentException {
		return musiteca.adicionaPlaylist(nomePlaylist, nomeAlbum, faixa);
	}

	/**
	 * Recupera um álbum pelo seu título.
	 * 
	 * @param titulo
	 * @return
	 */
	public Album getAlbum(final String titulo) {
		return musiteca.getAlbum(titulo);
	}

	/**
	 * Recupera um favorito pelo seu título.
	 * 
	 * @param titulo
	 * @return
	 */
	public Album getFavorito(final String titulo) {
		return musiteca.getFavorito(titulo);
	}

	/**
	 * Adiciona um favorito.
	 * 
	 * @param album
	 * @return
	 * @throws IllegalArgumentException
	 *             caso não seja possível adicionar {@code album} aos favoritos.
	 */
	public boolean adicionaFavorito(final Album album)
			throws IllegalArgumentException {
		return musiteca.adicionaFavorito(album);
	}

	/**
	 * Adiciona um álbum.
	 * 
	 * @param album
	 * @return {@code true}, caso o álbum tenha sido adicionado. {@code false}
	 *         caso contrário.
	 * @throws IllegalArgumentException
	 */
	public boolean adicionaAlbum(final Album album)
			throws IllegalArgumentException {
		return musiteca.adicionaAlbum(album);
	}

	public String getNome() {
		return nome;
	}
}
