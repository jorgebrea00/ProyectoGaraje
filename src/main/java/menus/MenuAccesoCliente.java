package menus;

import java.sql.Connection;

<<<<<<< HEAD
import cita.MenuCita;
=======
>>>>>>> eb016fbe99444c8b38b894266884b510d30d4717
import estructuraBaseDeDatos.EstructuraBbdd;
import mensajes.Mensajes;
import model.Cliente;

public class MenuAccesoCliente extends Menu {
	Cliente cliente;

	public MenuAccesoCliente(Cliente cliente, EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);
		this.cliente = cliente;
	}

	public void ejecutaMenuAccesoCliente() {
		int seleccionMenu;

		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu = opcionSeleccionadaDelMenu(cadenaMenu("Menú para " + cliente.getNombre(),
					new String[] { "Volver", "Mis datos", "Mis vehículos", "Mis citas" }));
			switch (seleccionMenu) {
			case 0: // Volver
				break;
			case 1: // "Mis datos"
				ejecutaMenuDatosCliente();
				break;
			case 2: // "Mis vehículos"
				ejecutaMenuVehiculosCliente();
				break;
			case 3: // "Mis citas"
<<<<<<< HEAD
				MenuCita.darMenu();
=======
>>>>>>> eb016fbe99444c8b38b894266884b510d30d4717
				break;
			}
		} while (seleccionMenu != 0);
	}

	public void ejecutaMenuDatosCliente() {
		MenuDatosCliente menuModifica = new MenuDatosCliente(cliente, getEstructuraTablas(), getConexionAbierta());
		menuModifica.ejecutaMenuDatosCliente();
	}

	public void ejecutaMenuVehiculosCliente() {
		MenuVehiculoCliente menuCliente = new MenuVehiculoCliente(cliente, getEstructuraTablas(), getConexionAbierta());
		try {
<<<<<<< HEAD
			menuCliente.ejecutaMenuCliente();
=======
			menuCliente.ejecutaMenuVehiculoCliente();
>>>>>>> eb016fbe99444c8b38b894266884b510d30d4717
		} catch (Exception e) {
			System.out.println(Mensajes.NUMERO_OPCION_INEXISTENTE);
		}

	}

}
