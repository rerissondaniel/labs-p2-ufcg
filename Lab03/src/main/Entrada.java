package main;

import java.util.Scanner;

public class Entrada {
	public static Scanner teclado = new Scanner(System.in);

	public static Integer leInteiro() {
		String numeroLido = teclado.nextLine();
		return Integer.parseInt(numeroLido);
	}
	
	public static Integer leInteiro(String prompt) {
		System.out.print(prompt);
		return leInteiro();
	}
	
	public static String leString() {
		return teclado.nextLine();
	}
	
	public static String leString(String prompt) {
		System.out.print(prompt);
		return leString();
	}

	public static Double leDouble() {
		String numeroLido = teclado.nextLine();
		return Double.parseDouble(numeroLido.replace(",", "."));
	}
	
	public static Double leDouble(String prompt) {
		System.out.print(prompt);
		return leDouble();
	}
}
