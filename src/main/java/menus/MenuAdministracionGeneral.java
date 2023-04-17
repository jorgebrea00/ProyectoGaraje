package menus;

import java.sql.Connection;

import estructuraBaseDeDatos.EstructuraBbdd;
import mensajes.Mensajes;

public class MenuAdministracionGeneral extends Menu{
	

	public MenuAdministracionGeneral(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}

	public void ejecutaMenuAdministracionGeneral() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de Administración general",new String[] {"Volver","Iva","Métodos de pago", "Facturacion"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Iva"
					abreMenuAdministracionIva();
					break;
				
				case 2:						// "Métodos de pago"
					abreMenuAdministracionMetodosPago();
					break;
				case 3:						// "Facturacion"
					
					break;
				
				}
		}while (seleccionMenu!=0);	
		
	}
	
	public void abreMenuAdministracionIva() {
		MenuAdministracionIva menuIva=new MenuAdministracionIva(getEstructuraTablas(),getConexionAbierta());
		menuIva.ejecutaMenuAdministracionIva();
	}
	
	public void abreMenuAdministracionMetodosPago() {
		MenuAdministracionMetodosPago menuMetodoPago=new MenuAdministracionMetodosPago(getEstructuraTablas(),getConexionAbierta());
		menuMetodoPago.ejecutaMenuAdministracionMetodosPago();
	}
}
