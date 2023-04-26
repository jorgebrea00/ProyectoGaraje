package main;
import java.util.Scanner;

import util.Menu;
import util.Multidao;

public class main {

	private static Scanner escaner = new Scanner(System.in);
	public static Menu menu = new Menu();
	public static Multidao dao = new Multidao();
	public static Vistas vista = new Vistas();

	public static void main(String[] args) {		
		try {
			while (true) { // ejecutar menu hasta salir
				Vistas.menuInicial();				
			}
		} catch (Exception e) {
			escaner.close();
			System.out.println("Error inesperado, cerrando programa"); // explotó
			e.printStackTrace();
		}
	}
	
	public static Scanner getEscaner() {
		return escaner;
	}
}
