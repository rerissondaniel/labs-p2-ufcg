package main;

import java.util.ArrayList;
import java.util.List;

public class Album implements Comparable<Album> {
	public static final String ARTISTA_INVALIDO = "Artista do album nao pode ser nulo ou vazio.";
	public static final String TITULO_INVALIDO = "Titulo do album nao pode ser nulo ou vazio.";
	public static final String ANO_INVALIDO = "Ano de lancamento do album nao pode ser inferior a 1900.";
	public static final String MUSICA_REPETIDA = "Há outra música identica neste álbum";

	private List<Musica> musicas;
	private String artista;
	private String titulo;
	private int ano;

	public Album(final String artista, final String titulo, final int ano) {
		if (artista == null || "".equals(artista.trim())) {
			throw new IllegalArgumentException(ARTISTA_INVALIDO);
		} else if (titulo == null || "".equals(titulo.trim())) {
			throw new IllegalArgumentException(TITULO_INVALIDO);
		} else if (ano < 1900) {
			throw new IllegalArgumentException(ANO_INVALIDO);
		}

		musicas = new ArrayList<Musica>();
		this.artista = artista;
		this.titulo = titulo;
		this.ano = ano;
	}

	public boolean adicionaMusica(final Musica musica) {
		if (musica == null || musicas.contains(musica)) {
			throw new IllegalArgumentException(MUSICA_REPETIDA);
		}
		return musicas.add(musica);
	}

	public Musica getMusica(final String titulo) {
		if ("".equals(titulo) || titulo == null) {
			return null;
		}

		for (Musica musica : musicas) {
			if (musica.getTitulo().equals(titulo)) {
				return musica;
			}
		}

		return null;
	}

	public int getDuracaoTotal() {
		int duracaoTotal = 0;
		for (Musica musica : musicas) {
			duracaoTotal += musica.getDuracao();
		}

		return duracaoTotal;
	}

	public boolean contemMusica(final String titulo) {
		return !(getMusica(titulo) == null);
	}

	public boolean removeMusica(final int indice) {
		try {
			musicas.remove(indice - 1);
			return true;
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public Musica getFaixa(final int faixa) {
		try {
			return musicas.get(faixa - 1);
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	public int getQuantidadeFaixas() {
		return musicas.size();
	}

	public String getArtista() {
		return artista;
	}

	public String getTitulo() {
		return titulo;
	}

	public int getAno() {
		return ano;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artista == null) ? 0 : artista.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (artista == null) {
			if (other.artista != null)
				return false;
		} else if (!artista.equals(other.artista))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public int compareTo(Album o) {
		return getAno() - o.getAno();
	}
}
