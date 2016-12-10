package test;

import org.junit.Test;

import junit.framework.TestCase;
import main.Musica;

public class MusicaTest extends TestCase {

	@Test
	public void testaCriacaoNulls() {
		// Teste com o título nulo.
		try {
			Musica musica = new Musica(null, 9, "Rock");
			fail();
		} catch (IllegalArgumentException ex) {
			assertEquals(Musica.MUSICA_INVALIDA, ex.getMessage());
		}
		// Teste com genero nulo.
		try {
			Musica musica = new Musica("Another brick in the wall", 2, null);
			fail();
		} catch (IllegalArgumentException ex) {
			assertEquals(Musica.MUSICA_INVALIDA, ex.getMessage());
		}
	}

	@Test
	public void testaCriacaoVazios() {
		// Teste com o título vazio.
		try {
			Musica musica = new Musica("", 9, "Sertanejo");
			fail();
		} catch (IllegalArgumentException ex) {
			assertEquals(Musica.MUSICA_INVALIDA, ex.getMessage());
		}
		// Teste com genero vazio.
		try {
			Musica musica = new Musica("Borboletas", 2, "");
			fail();
		} catch (IllegalArgumentException ex) {
			assertEquals(Musica.MUSICA_INVALIDA, ex.getMessage());
		}
	}

	@Test
	public void testaCriacaoDuracao(){
		//Teste com a ducação negativa.
		try{
			Musica musica = new Musica("Clara", -4, "MPB");
			fail();
		}catch(IllegalArgumentException ex){
			assertEquals(ex.getMessage(), Musica.MUSICA_INVALIDA);
		}
	}
	
	@Test
	public void testaHashCode() {
		Musica musica = new Musica("Todos os olhos", 1, "tropicalia");
		Musica musica1 = new Musica("O mundo é um moinho", 2, "mpb");
		
		assertNotSame(musica.hashCode(), musica1.hashCode());
		
		musica1 = new Musica("Todos os olhos", 1, "tropicalia");
		assertEquals(musica.hashCode(), musica1.hashCode());
	}

	@Test
	public void testaEquals() {
		Musica musica = new Musica("Dogs", 16,"rock");
		Musica musica1 = new Musica("Pigs", 10, "rock");
		
		assertNotSame(musica, musica1);
		
		musica1 = new Musica("Dogs", 16, "rock");
		assertEquals(musica, musica1);
	}
}