package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Armazena os álbuns, os álbuns favoritos e as playlists de um usuário.
 * 
 * @author rerissondcsm
 *
 */
public class Musiteca {

	// Constantes utilizadas para as mensagens de exceção.
	public static final String ALBUM_REPETIDO = "Este álbum já foi adicionado à músicoteca";
	public static final String ALBUM_REPETIDO_FAVORITOS = "Este álbum já foi adicionado aos favoritos";
	public static final String ALBUM_NAO_EXISTENTE = "Álbum não pertence ao perfil especificado";
	public static final String PLAYLIST_NULA_OU_VAZIA = "Playlist nula ou vazia";
	public static final String ALBUM_NULO_OU_VAZIO = "Álbum nulo ou vazio";

	private Set<Album> albuns;
	private Set<Album> favoritos;
	private Map<String, Playlist> playlists;

	/**
	 * Inicializa as coleções.
	 */
	public Musiteca() {
		this.albuns = new HashSet<Album>();
		this.favoritos = new HashSet<Album>();
		this.playlists = new HashMap<String, Playlist>();
	}

	/**
	 * Adiciona a faixa de número {@code faixa} do álbum {@code nomeAlbum} à
	 * {@link Playlist} de nome {@code nomePlaylist}. Caso a playlist não
	 * exista, ela é criada e a música adicionada.
	 * 
	 * @param nomePlaylist
	 *            - Nome da {@link Playlist} a ser adicionada.
	 * @param nomeAlbum
	 *            - Nome do {@link Album} do qual será extraída a faixa.
	 * @param faixa
	 *            - Número da faixa do {@link Album} que será adicionada.
	 * @return {@code true}, caso a playlist tenha sido adicionada ou
	 *         {@code false} caso contrário.
	 * @throws IllegalArgumentException
	 *             Em caso de {@code nomePlaylist} ser {@code null} ou vazia,
	 *             {@code nomeAlbum} ser {@code null} ou vazio e no caso do
	 *             álbum não pertencer ao usuário.
	 * 
	 */
	public boolean adicionaPlaylist(final String nomePlaylist,
			final String nomeAlbum, final int faixa)
			throws IllegalArgumentException {
		Playlist playlist;
		if (nomeAlbum == null || "".equals(nomeAlbum.trim())) {
			throw new IllegalArgumentException(ALBUM_NULO_OU_VAZIO);
		}

		if (nomePlaylist == null || "".equals(nomePlaylist.trim())) {
			throw new IllegalArgumentException(PLAYLIST_NULA_OU_VAZIA);
		}

		Album album = getAlbum(nomeAlbum);
		if (album == null) {
			throw new IllegalArgumentException(ALBUM_NAO_EXISTENTE);
		}

		if (playlists.containsKey(nomePlaylist)) {
			playlist = playlists.get(nomePlaylist);
		} else {
			playlist = new Playlist(nomePlaylist);
			playlists.put(nomePlaylist, playlist);
		}

		return playlist.adicionaMusica(album.getFaixa(faixa));

	}

	/**
	 * Adiciona um álbum à musiteca.
	 * 
	 * @param album
	 *            - {@link Album} a ser adicionado a esta musiteca.
	 * @return {@code true}, caso o {@code album} tenha sido adicionado ou
	 *         {@code false} caso contrário.
	 * @throws IllegalArgumentException
	 *             , caso o {@code album} seja nulo ou já tenha sido adicionado
	 *             a esta musiteca.
	 */
	public boolean adicionaAlbum(final Album album)
			throws IllegalArgumentException {
		if (album == null) {
			throw new IllegalArgumentException(ALBUM_NULO_OU_VAZIO);
		}
		if (albuns.contains(album)) {
			throw new IllegalArgumentException(ALBUM_REPETIDO);
		}
		return albuns.add(album);
	}

	/**
	 * Adiciona um álbum favorito à musiteca.
	 * 
	 * @param album
	 *            - {@link Album} favorito a ser adicionado a esta musiteca.
	 * @return {@code true}, caso o {@code album} tenha sido adicionado ou
	 *         {@code false} caso contrário.
	 * @throws IllegalArgumentException
	 *             , caso {@code album} seja nulo ou já tenha sido adicionado a
	 *             esta musiteca.
	 */
	public boolean adicionaFavorito(final Album album) {
		if (album == null) {
			throw new IllegalArgumentException(ALBUM_NULO_OU_VAZIO);
		}
		if (favoritos.contains(album)) {
			throw new IllegalArgumentException(ALBUM_REPETIDO_FAVORITOS);
		}
		return favoritos.add(album);
	}

	/**
	 * Retorna um álbum favorito que tem {@code titulo} como título.
	 * 
	 * @param titulo
	 *            - título do álbum favorito que será recuperado.
	 * @return o álbum recuperado, ou {@code null} caso ele não exista.
	 */
	public Album getFavorito(final String titulo) {
		return getAlbum(titulo, favoritos);
	}

	/**
	 * Retorna um álbum que tem {@code titulo} como título.
	 * 
	 * @param titulo
	 *            - título do álbum que será recuperado.
	 * @return o álbum recuperado, ou {@code null} caso ele não exista.
	 */
	public Album getAlbum(final String titulo) {
		return getAlbum(titulo, albuns);
	}

	/**
	 * Retorna um álbum que tem {@code titulo} como título.
	 * 
	 * @param titulo
	 *            - título do álbum que será recuperado.
	 * @param colecao
	 *            - Set<Album>
	 * @return o álbum recuperado, ou {@code null} caso ele não exista.
	 */
	private Album getAlbum(final String titulo, final Set<Album> colecao) {
		if (titulo == null || "".equals(titulo.trim())) {
			return null;
		}

		for (Album album : colecao) {
			if (album.getTitulo().equals(titulo)) {
				return album;
			}
		}

		return null;
	}

	/**
	 * Imprime os álbuns e os favoritos ordenados de acordo com o critério de
	 * comparação de {@link Album}.
	 */
	public void sortedAlbuns() {
		imprimeAlbunsOrdenadosAno(albuns);
		imprimeAlbunsOrdenadosAno(favoritos);
	}

	/**
	 * Imprime os álbuns e os favoritos ordenados de acordo com o nome do
	 * artista do {@link Album}.
	 */
	public void imprimeAlbunsOrdenadosArtista() {
		Comparator<Album> comparatorArtista = new Comparator<Album>() {
			public int compare(Album a1, Album a2) {
				return a1.getArtista().compareTo(a2.getArtista());
			}
		};
		imprimeAlbunsOrdenados(albuns, comparatorArtista);
		imprimeAlbunsOrdenados(favoritos, comparatorArtista);
	}

	/**
	 * Imprime os álbuns e os favoritos ordenados de acordo com a duração do
	 * {@link Album}.
	 */
	public void imprimeAlbunsOrdenadosDuracao() {
		Comparator<Album> comparatorDuracao = new Comparator<Album>() {
			public int compare(Album a1, Album a2) {
				return a1.getDuracaoTotal() - a2.getDuracaoTotal();
			}
		};
		imprimeAlbunsOrdenados(albuns, comparatorDuracao);
		imprimeAlbunsOrdenados(favoritos, comparatorDuracao);
	}

	/**
	 * Imprime os álbuns e os favoritos ordenados de acordo com a quantidade de
	 * faixas do {@link Album}.
	 */
	public void imprimeAlbunsOrdenadosQtde() {
		Comparator<Album> comparatorQtde = new Comparator<Album>() {
			public int compare(Album a1, Album a2) {
				return a1.quantidadeFaixas() - a2.quantidadeFaixas();
			}
		};
		imprimeAlbunsOrdenados(albuns, comparatorQtde);
		imprimeAlbunsOrdenados(favoritos, comparatorQtde);
	}

	/**
	 * Imprime os álbuns do {@code albumSet} ordenados de acordo com o
	 * {@link Comparator}.
	 * 
	 * @param albunsSet
	 *            - Set de álbuns a serem impressos.
	 * @param comparator
	 *            - Comparator usado para a ordenação.
	 */
	public void imprimeAlbunsOrdenados(final Set<Album> albunsSet,
			Comparator<Album> comparator) {
		List<Album> albunsList = new ArrayList<Album>(albunsSet);
		Collections.sort(albunsList, comparator);
		for (Album album : albunsList) {
			System.out.println(album);
		}
	}

	/**
	 * Imprime os álbuns do {@code albumSet} ordenados de acordo com o critério
	 * de ordenação de {@link Album}.
	 * 
	 * @param albunsSet
	 *            - Set de álbuns a serem impressos.
	 * @param comparator
	 *            - Comparator usado para a ordenação.
	 */
	public void imprimeAlbunsOrdenadosAno(final Set<Album> albunsSet) {
		List<Album> albunsList = new ArrayList<Album>(albunsSet);
		Collections.sort(albunsList);
		for (Album album : albunsList) {
			System.out.println(album);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albuns == null) ? 0 : albuns.hashCode());
		result = prime * result
				+ ((favoritos == null) ? 0 : favoritos.hashCode());
		result = prime * result
				+ ((playlists == null) ? 0 : playlists.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Musiteca other = (Musiteca) obj;
		if (albuns == null) {
			if (other.albuns != null)
				return false;
		} else if (!albuns.equals(other.albuns))
			return false;
		if (favoritos == null) {
			if (other.favoritos != null)
				return false;
		} else if (!favoritos.equals(other.favoritos))
			return false;
		if (playlists == null) {
			if (other.playlists != null)
				return false;
		} else if (!playlists.equals(other.playlists))
			return false;
		return true;
	}
}
