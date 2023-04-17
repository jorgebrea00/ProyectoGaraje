package menus;

import java.sql.Connection;

import estructuraBaseDeDatos.EstructuraBbdd;
import mensajes.Mensajes;
import model.Cliente;

public class MenuCliente extends Menu {
	Cliente cliente;

	public MenuCliente(Cliente cliente, EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
		this.cliente=cliente;		
	}
	
	public void ejecutaMenuCliente() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú para "+cliente.getNombre(),new String[] {"Volver","Mis datos", "Mis vehículos", "Mis citas"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Mis datos"
					ejecutaMenuDatosCliente();
					break;
				case 2:						// "Mis vehículos"	
					break;
				case 3:						// "Mis citas"	
					break;
				}
		}while (seleccionMenu!=0);	
	}
	
	public void ejecutaMenuDatosCliente() {
		MenuDatosCliente menuModifica=new MenuDatosCliente (cliente, getEstructuraTablas(), getConexionAbierta());
		menuModifica.ejecutaMenuDatosCliente();
	}
	
	
}
