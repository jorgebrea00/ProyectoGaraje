package util;

import java.util.ArrayList;

import main.main;

public class Menu {

	private ArrayList<String> opciones = new ArrayList<>();
	private int ANCHO_MENU = 50;
	public static String titulo = "";

	public String imprimir() {		
		int espaciosTitulo = ANCHO_MENU - titulo.length();
		System.out.println();
		System.out.println(rellenarConCaracter(ANCHO_MENU, "*"));
		System.out.println("* " + rellenarConCaracter((espaciosTitulo - 4) / 2, " ") + titulo
				+ rellenarConCaracter((espaciosTitulo - 4) / 2, " ") + " *");
		System.out.println(rellenarConCaracter(ANCHO_MENU, "*"));
		System.out.println("*" + rellenarConCaracter(ANCHO_MENU - 2, " ") + "*");

		for (int i = 1; i <= opciones.size(); i++) {
			String opcion = opciones.get(i - 1);
			int espacios = ANCHO_MENU - opcion.length();
			System.out.println("* " + i + " - " + opcion + rellenarConCaracter(espacios - 8, " ") + " *");
		}

		System.out.println("* 0 - Volver" + rellenarConCaracter(ANCHO_MENU - 13, " ") + "*");
		System.out.println("*" + rellenarConCaracter(ANCHO_MENU - 2, " ") + "*");
		System.out.println("* Elige opción para continuar..." + rellenarConCaracter(ANCHO_MENU - 33, " ") + "*");
		System.out.println("*" + rellenarConCaracter(ANCHO_MENU - 2, " ") + "*");
		System.out.println(rellenarConCaracter(ANCHO_MENU, "*"));

		int eleccion = main.getEscaner().nextInt();
		while (eleccion > opciones.size() || eleccion < 0) {
			System.out.println("Opción incorrecta, elige de nuevo.");
			eleccion = main.getEscaner().nextInt();
		}

		if (eleccion == 0) {
			main.getEscaner().nextLine(); // Consumir el carácter de nueva línea en el búfer del escáner
		
			return "Volver";
		} else {
			main.getEscaner().nextLine(); 			
			return opciones.get(eleccion - 1).toString();
		}
	}

	private String rellenarConCaracter(int cantidad, String caracter) {
		StringBuilder espacios = new StringBuilder();
		for (int i = 0; i < cantidad; i++) {
			espacios.append(caracter);
		}
		return espacios.toString();
	}
	
	public String exe(String o1) {
		this.opciones.clear();
		this.opciones.add(o1);
		return this.imprimir();
	}

	public String exe(String o1, String o2) {
		this.opciones.clear();
		this.opciones.add(o1);
		this.opciones.add(o2);
		return this.imprimir();
	}

	public String exe(String o1, String o2, String o3) {
		this.opciones.clear();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		return this.imprimir();
	}

	public String exe(String o1, String o2, String o3, String o4) {
		this.opciones.clear();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		this.opciones.add(o4);
		return this.imprimir();
	}

	public String exe(String o1, String o2, String o3, String o4, String o5) {
		this.opciones.clear();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		this.opciones.add(o4);
		this.opciones.add(o5);
		return this.imprimir();
	}

	public String exe(String o1, String o2, String o3, String o4, String o5, String o6) {
		this.opciones.clear();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		this.opciones.add(o4);
		this.opciones.add(o5);
		this.opciones.add(o6);
		return this.imprimir();
	}

	public String exe(String o1, String o2, String o3, String o4, String o5, String o6, String o7) {
		this.opciones.clear();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		this.opciones.add(o4);
		this.opciones.add(o5);
		this.opciones.add(o6);
		this.opciones.add(o7);
		return this.imprimir();
	}

	public Menu setTitulo(String t) {
		titulo = t;
		return this;
	}

	public Menu() {
		super();
	}
}
