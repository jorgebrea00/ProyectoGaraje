package menus;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuWu {

	private ArrayList<String> opciones = new ArrayList<>();
	private Scanner escaner = new Scanner(System.in);

	// private static Menu menuDimensionFigura = new Menu(new
	// ArrayList<>(Arrays.asList("Figuras 2D", "Figuras 3D")));

	public String ejecutar() {

		System.out.println();
		for (int i = 1; i <= opciones.size(); i++) {
			System.out.println(i + " - " + opciones.get(i - 1));
		}
		System.out.println("0 - Volver");
		System.out.println();
		System.out.println("Elige opción para continuar...");
		int eleccion = escaner.nextInt();
		while (eleccion > opciones.size() || eleccion < 0) {
			System.out.println("Opción incorrecta, elige de nuevo.");
			eleccion = escaner.nextInt();
		}
//		System.out.println("^^^^^^");
//		System.out.println(opciones.get(eleccion));
//		System.out.println("^^^^^^");
		if (eleccion == 0) {
			return "Volver";
		} else {
			return opciones.get(eleccion - 1).toString();
		}
	}

	public MenuWu(ArrayList<String> opciones) {
		super();
		this.opciones = opciones;
	}

	public MenuWu(String o1, String o2) {
		super();
		this.opciones.add(o1);
		this.opciones.add(o2);
	}

	public MenuWu(String o1, String o2, String o3) {
		super();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
	}

	public MenuWu(String o1, String o2, String o3, String o4) {
		super();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		this.opciones.add(o4);
	}

	public MenuWu(String o1, String o2, String o3, String o4, String o5) {
		super();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		this.opciones.add(o4);
		this.opciones.add(o5);
	}

	public MenuWu(String o1, String o2, String o3, String o4, String o5, String o6) {
		super();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		this.opciones.add(o4);
		this.opciones.add(o5);
		this.opciones.add(o6);
	}

	public MenuWu(String o1, String o2, String o3, String o4, String o5, String o6, String o7) {
		super();
		this.opciones.add(o1);
		this.opciones.add(o2);
		this.opciones.add(o3);
		this.opciones.add(o4);
		this.opciones.add(o5);
		this.opciones.add(o6);
		this.opciones.add(o7);
	}

}
