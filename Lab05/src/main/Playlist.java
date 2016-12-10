package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Playlist {
	public static final String MUSICA_NULA = "Música não pode ser nula";
	public static final String MUSICA_REPETIDA = "Música já está adicionada nesta playlist";
	
	private String nome;
	private List<Musica> musicas;
	
	public Playlist(final String nome) {
		this.nome = nome;
		this.musicas = new ArrayList<Musica>();
	}

	public Musica pesquisaMusica(final String titulo){
		for(Musica musica : musicas){
			if(musica.getTitulo().equals(titulo)){
				return musica;
			}
		}
		
		return null;
	}
	
	public boolean removeMusica(final String titulo){
		for(Iterator<Musica> i = musicas.iterator(); i.hasNext(); ){
			Musica musica = i.next();
			if(musica.getTitulo().equals(titulo)){
				i.remove();
				return true;
			}
		}
		return false;
	}
	
	public boolean removeMusica(final Musica musica){
		return removeMusica(musica.getTitulo());
	}
	
	public boolean adicionaMusica(final Musica musica) throws IllegalArgumentException{
		if(musica == null){
			throw new IllegalArgumentException(MUSICA_NULA);
		}
		if(musicas.contains(musica)){
			throw new IllegalArgumentException(MUSICA_REPETIDA);
		}
		return musicas.add(musica);
	}
	
	public String getNome(){
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((musicas == null) ? 0 : musicas.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Playlist other = (Playlist) obj;
		if (musicas == null) {
			if (other.musicas != null)
				return false;
		} else if (!musicas.equals(other.musicas))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
}
