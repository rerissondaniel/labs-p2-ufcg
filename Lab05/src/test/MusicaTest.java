package test;

import org.junit.Assert;
import org.junit.Test;

import main.Musica;

public class MusicaTest{

	@SuppressWarnings("unused")
	@Test
	public void testaCriacaoNulls() {
		// Teste com o título nulo.
		try {
			Musica musica = new Musica(null, 9, "Rock");
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musica.MUSICA_INVALIDA, ex.getMessage());
		}
		// Teste com genero nulo.
		try {
			Musica musica = new Musica("Another brick in the wall", 2, null);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musica.MUSICA_INVALIDA, ex.getMessage());
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void testaCriacaoVazios() {
		// Teste com o título vazio.
		try {
			Musica musica = new Musica("", 9, "Sertanejo");
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musica.MUSICA_INVALIDA, ex.getMessage());
		}
		// Teste com genero vazio.
		try {
			Musica musica = new Musica("Borboletas", 2, "");
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals(Musica.MUSICA_INVALIDA, ex.getMessage());
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void testaCriacaoDuracao(){
		//Teste com a ducação negativa.
		try{
			Musica musica = new Musica("Clara", -4, "MPB");
			Assert.fail();
		}catch(IllegalArgumentException ex){
			Assert.assertEquals(ex.getMessage(), Musica.MUSICA_INVALIDA);
		}
	}
	
	@Test
	public void testaHashCode() {
		Musica musica = new Musica("Todos os olhos", 1, "tropicalia");
		Musica musica1 = new Musica("O mundo é um moinho", 2, "mpb");
		
		Assert.assertNotSame(musica.hashCode(), musica1.hashCode());
		
		musica1 = new Musica("Todos os olhos", 1, "tropicalia");
		Assert.assertEquals(musica.hashCode(), musica1.hashCode());
	}

	@Test
	public void testaEquals() {
		Musica musica = new Musica("Dogs", 16,"rock");
		Musica musica1 = new Musica("Pigs", 10, "rock");
		
		Assert.assertNotSame(musica, musica1);
		
		musica1 = new Musica("Dogs", 16, "rock");
		Assert.assertEquals(musica, musica1);
	}
}