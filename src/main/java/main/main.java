package main;
import java.util.Scanner;

import model.Cliente;
import model.Sesion;
import util.Controlador;
import util.Menu;
import util.Multidao;

public class main {

	public static Scanner escaner = new Scanner(System.in);
	public static Menu menu = new Menu();
	public static Multidao dao = new Multidao();
	public static Vistas vista = new Vistas();
	public static Sesion sesion = new Sesion();
	public static Cliente clienteLogeado;
	public static Controlador controlador = new Controlador();

	public static Controlador getControlador() {
		return controlador;
	}

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
	
	public static Sesion getSesion() {
		return sesion;
	}
	
	public static Cliente getClienteLogeado() {
		return clienteLogeado;
	}

	public static void setClienteLogeado(Cliente clienteLogeado) {
		main.clienteLogeado = clienteLogeado;
	}

	public static void setSesion(Sesion sesion) {
		main.sesion = sesion;
	}

	
}
