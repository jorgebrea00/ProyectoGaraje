package menus;

import java.sql.Connection;

import estructuraBaseDeDatos.EstructuraBbdd;
import mensajes.Mensajes;

public class MenuAdministracionTaller extends Menu{

	public MenuAdministracionTaller(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutaMenuAdministracionTaller() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de Taller",new String[] {"Volver","Piezas", "Tarifa mano de obra"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Piezas"
					abreMenuTallerInvntarioPiezas();
					break;	
				case 2:
					abreMenuAdministracionTarifasManoObra();
				}
		}while (seleccionMenu!=0);	
	}
	
	public void abreMenuTallerInvntarioPiezas() {
		MenuTallerInventarioPiezas menuTaller=new MenuTallerInventarioPiezas(getEstructuraTablas(), getConexionAbierta());
		menuTaller.ejecutaMenuTallerInventarioPiezas();
	}
	
	public void abreMenuAdministracionTarifasManoObra() {
		MenuAdministracionTarifasManoObra menuManoObra=new MenuAdministracionTarifasManoObra(getEstructuraTablas(), getConexionAbierta());
		menuManoObra.ejecutaMenuAdministracionTarifasManoObra();
	}

}
