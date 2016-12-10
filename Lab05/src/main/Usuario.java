package main;

public class Usuario {
	private String nome;
	private Musiteca musiteca;

	public Usuario(String nome) {
		super();
		this.nome = nome;
		this.musiteca = new Musiteca();
	}

	public boolean adicionaPlaylist(final String nomePlaylist, final String nomeAlbum, final int faixa)
			throws IllegalArgumentException {
		return musiteca.adicionaPlaylist(nomePlaylist, nomeAlbum, faixa);
	}

	public Album getAlbum(final String titulo){
		return musiteca.getAlbum(titulo);
	}
	
	public Album getFavorito(final String titulo){
		return musiteca.getFavorito(titulo);
	}
	
	public boolean adicionaFavorito(final Album album) throws IllegalArgumentException{
		return musiteca.adicionaFavorito(album);
	}

	public boolean adicionaAlbum(final Album album) throws IllegalArgumentException{
		return musiteca.adicionaAlbum(album);
	}
	
	public String getNome(){
		return nome;
	}
}
