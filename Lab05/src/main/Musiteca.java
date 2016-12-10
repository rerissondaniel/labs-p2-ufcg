package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Musiteca {
	public static final String ALBUM_REPETIDO = "Este álbum já foi adicionado à músicoteca";
	public static final String ALBUM_REPETIDO_FAVORITOS = "Este álbum já foi adicionado aos favoritos";
	public static final String ALBUM_NAO_EXISTENTE = "Álbum não pertence ao perfil especificado";
	public static final String PLAYLIST_NULA_OU_VAZIA = "Playlist nula ou vazia";
	public static final String ALBUM_NULO_OU_VAZIO = "Álbum nulo ou vazio";

	private Set<Album> albuns;
	private Set<Album> favoritos;
	private Map<String, Playlist> playlists;

	public Musiteca() {
		this.albuns = new HashSet<Album>();
		this.favoritos = new HashSet<Album>();
		this.playlists = new HashMap<String, Playlist>();
	}

	public boolean adicionaPlaylist(final String nomePlaylist, final String nomeAlbum, final int faixa)
			throws IllegalArgumentException {
		Playlist playlist;
		if (nomeAlbum == null || "".equals(nomeAlbum)) {
			throw new IllegalArgumentException(ALBUM_NULO_OU_VAZIO);
		}

		if (nomePlaylist == null || "".equals(nomePlaylist)) {
			throw new IllegalArgumentException(PLAYLIST_NULA_OU_VAZIA);
		}

		if (playlists.containsKey(nomePlaylist)) {
			playlist = playlists.get(nomePlaylist);
		} else {
			playlist = new Playlist(nomePlaylist);
			playlists.put(nomePlaylist, playlist);
		}

		Album album = getAlbum(nomeAlbum);
		if (album == null) {
			throw new IllegalArgumentException(ALBUM_NAO_EXISTENTE);
		}

		return playlist.adicionaMusica(album.getFaixa(faixa));

	}

	public boolean adicionaAlbum(final Album album) throws IllegalArgumentException {
		if (album == null) {
			throw new IllegalArgumentException(ALBUM_NULO_OU_VAZIO);
		}
		if(albuns.contains(album)){
			throw new IllegalArgumentException(ALBUM_REPETIDO);
		}
		return albuns.add(album);
	}

	public boolean adicionaFavorito(final Album album) {
		if (album == null) {
			throw new IllegalArgumentException(ALBUM_NULO_OU_VAZIO);
		}
		if(favoritos.contains(album)){
			throw new IllegalArgumentException(ALBUM_REPETIDO_FAVORITOS);
		}
		return favoritos.add(album);
	}

	public Album getFavorito(final String titulo) {
		return getAlbum(titulo, favoritos);
	}

	public Album getAlbum(final String titulo) {
		return getAlbum(titulo, albuns);
	}

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

	public void sortedAlbuns(){
		imprimeAlbunsOrdenadosAno(albuns);
		imprimeAlbunsOrdenadosAno(favoritos);
	}
	
	public void imprimeAlbunsOrdenadosArtista(){
		Comparator<Album> comparatorArtista = new Comparator<Album>(){
			public int compare(Album a1, Album a2){
				return a1.getArtista().compareTo(a2.getArtista());
			}
		};
		imprimeAlbunsOrdenados(albuns, comparatorArtista);
		imprimeAlbunsOrdenados(favoritos, comparatorArtista);
	}
	
	public void imprimeAlbunsOrdenadosDuracao(){
		Comparator<Album> comparatorDuracao = new Comparator<Album>(){
			public int compare(Album a1, Album a2){
				return a1.getDuracaoTotal() - a2.getDuracaoTotal();
			}
		};
		imprimeAlbunsOrdenados(albuns, comparatorDuracao);
		imprimeAlbunsOrdenados(favoritos, comparatorDuracao);
	}
	
	public void imprimeAlbunsOrdenadosQtde(){
		Comparator<Album> comparatorQtde = new Comparator<Album>(){
			public int compare(Album a1, Album a2){
				return a1.getQuantidadeFaixas() - a2.getQuantidadeFaixas();
			}
		};
		imprimeAlbunsOrdenados(albuns, comparatorQtde);
		imprimeAlbunsOrdenados(favoritos, comparatorQtde);
	}
	
	@SuppressWarnings("unchecked")
	public void imprimeAlbunsOrdenados(final Set<Album> albunsSet, Comparator comparator){
		List<Album> albunsList = new ArrayList<Album>(albunsSet);
		Collections.sort(albunsList, comparator);
		for(Album album : albunsList){
			System.out.println(album);
		}
	}
	
	public void imprimeAlbunsOrdenadosAno(final Set<Album> albunsSet){
		List<Album> albunsList = new ArrayList<Album>(albunsSet);
		Collections.sort(albunsList);
		for(Album album : albunsList){
			System.out.println(album);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albuns == null) ? 0 : albuns.hashCode());
		result = prime * result + ((favoritos == null) ? 0 : favoritos.hashCode());
		result = prime * result + ((playlists == null) ? 0 : playlists.hashCode());
		return result;
	}

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
