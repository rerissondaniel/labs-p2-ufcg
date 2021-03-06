package main;

public class Musica {
	public static final String MUSICA_INVALIDA = "Música inválida";
	private String titulo;
	private int duracao;
	private String genero;

	/**
	 * Atribui os valores dos parâmetros aos atributos e checa a validade da
	 * música.
	 * 
	 * @param titulo
	 * @param duracao
	 * @param genero
	 * @throws IllegalArgumentException
	 *             caso a música seja inválida.
	 */
	public Musica(String titulo, int duracao, String genero)
			throws IllegalArgumentException {
		if (titulo == null || "".equals(titulo.trim()) || genero == null
				|| "".equals(genero.trim()) || duracao < 0) {
			throw new IllegalArgumentException(MUSICA_INVALIDA);
		}
		this.titulo = titulo;
		this.duracao = duracao;
		this.genero = genero;
	}

	public String getTitulo() {
		return titulo;
	}

	public int getDuracao() {
		return duracao;
	}

	public String getGenero() {
		return genero;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duracao;
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Musica other = (Musica) obj;
		if (duracao != other.duracao)
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}
}
