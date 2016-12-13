package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um álbum no contexto do sp2fy.
 * 
 * @author rerissondcsm
 *
 */
public class Album implements Comparable<Album> {

	// Constantes utilizadas para mensagens de exceção.
	public static final String ARTISTA_INVALIDO = "Artista do album nao pode ser nulo ou vazio.";
	public static final String TITULO_INVALIDO = "Titulo do album nao pode ser nulo ou vazio.";
	public static final String ANO_INVALIDO = "Ano de lancamento do album nao pode ser inferior a 1900.";
	public static final String MUSICA_REPETIDA = "Há outra música identica neste álbum";

	private List<Musica> musicas;
	private String artista;
	private String titulo;
	private int ano;

	/**
	 * Inicializa as coleções e faz as verificações necessárias para que este
	 * álbum seja válido.
	 * 
	 * @param artista
	 *            - artista do álbum.
	 * @param titulo
	 *            - titulo do álbum.
	 * @param ano
	 *            - ano do álbum.
	 * @throws IllegalArgumentException
	 *             no caso de {@code artista} ser nulo ou vazio, {@code titulo}
	 *             ser nulo ou vazio. ou no caso de o ano ser inferior a 1900.
	 */
	public Album(final String artista, final String titulo, final int ano)
			throws IllegalArgumentException {
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

	/**
	 * Adiciona {@code musica} a esta playlist.
	 * 
	 * @param musica
	 *            - {@link Musica} a ser adicionada.
	 * @return {@code true}, caso a música tenha sido adicionada ou
	 *         {@code false}, caso contrário.
	 */
	public boolean adicionaMusica(final Musica musica) {
		if (musica == null) {
			return false;
		}
		return musicas.add(musica);
	}

	/**
	 * Retorna uma música do álbum cujo título é {@code titulo}.
	 * 
	 * @param titulo
	 *            - Título da música a ser recuperada.
	 * @return a música cujo título é {@code titulo}, ou null, caso contrário.
	 */
	public Musica getMusica(final String titulo) {
		if (titulo == null || "".equals(titulo.trim())) {
			return null;
		}

		for (Musica musica : musicas) {
			if (musica.getTitulo().equals(titulo)) {
				return musica;
			}
		}

		return null;
	}

	/**
	 * Retorna a duração total do álbum.
	 * 
	 * @return A duração total do álbum (i. e. a soma da duração de suas
	 *         faixas).
	 */
	public int getDuracaoTotal() {
		int duracaoTotal = 0;
		for (Musica musica : musicas) {
			duracaoTotal += musica.getDuracao();
		}

		return duracaoTotal;
	}

	/**
	 * 
	 * @param titulo
	 * @return {@code true}, caso a música esteja contida neste álbum ou @{code
	 *         false}, caso contrário.
	 */
	public boolean contemMusica(final String titulo) {
		return !(getMusica(titulo) == null);
	}

	/**
	 * Remove a faixa de {@code indice} do álbum.
	 * 
	 * @param indice
	 *            - índice da música a ser removida.
	 * @return {@code true}, caso a música tenha sido removida ou {@code false},
	 *         caso contrário.
	 */
	public boolean removeMusica(final int indice) {
		try {
			musicas.remove(indice - 1);
			return true;
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	/**
	 * Retorna uma faixa do álbum.
	 * 
	 * @param faixa
	 *            - faixa a ser recuperada.
	 * @return a música, caso exista ou @{code null} caso contrário.
	 */
	public Musica getFaixa(final int faixa) {
		try {
			return musicas.get(faixa - 1);
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	/**
	 * Retorna a quantidade de faixas do álbum,
	 * 
	 * @return a quaitdade de faixas do álbum.
	 */
	public int quantidadeFaixas() {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artista == null) ? 0 : artista.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
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
