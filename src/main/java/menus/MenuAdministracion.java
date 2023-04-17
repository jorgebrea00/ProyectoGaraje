package menus;

import java.sql.Connection;
import estructuraBaseDeDatos.EstructuraBbdd;
import mensajes.Mensajes;

public class MenuAdministracion extends Menu {
	
	public MenuAdministracion(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutaMenuAdministracion() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de Administración",new String[] {"Volver","Clientes","Empleados", "Administración", "Taller"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Clientes"
					abreMenuClientes();
					break;
				
				case 2:						// "Empleados"
					abreMenuEmpleado();
					break;
					
				case 3:						// "Administración"
					abreMenuAdministracionGeneral();
					break;
				
				case 4:						// "Taller"
					break;	
				
				}
		}while (seleccionMenu!=0);	
	}
	
	public void abreMenuClientes() {
		MenuAdministracionCliente menuCliente=new MenuAdministracionCliente(getEstructuraTablas(), getConexionAbierta());
		menuCliente.ejecutaMenuAdministracionCliente();
	}
	
	public void abreMenuEmpleado(){
		MenuAdministracionEmpleado menuEmpleado=new MenuAdministracionEmpleado(getEstructuraTablas(), getConexionAbierta());
		menuEmpleado.ejecutaMenuAdministracionEmpleado();
		
	}
	
	public void abreMenuAdministracionGeneral() {
		MenuAdministracionGeneral menuAdmin=new MenuAdministracionGeneral(getEstructuraTablas(), getConexionAbierta());
		menuAdmin.ejecutaMenuAdministracionGeneral();		
	}

}
