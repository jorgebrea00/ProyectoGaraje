package menus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import dao.ClienteDao;
import dao.TiposVehiculoDao;
import dao.VehiculoDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import estructuraBaseDeDatos.ImprimeTablas;
import model.Cliente;
import model.Vehiculo;

public class MenuVehiculoCliente extends Menu {

	Cliente cliente;
	VehiculoDao vdao;
	TiposVehiculoDao tvdao;
	ClienteDao cdao;
	ImprimeTablas imprime;

	public MenuVehiculoCliente(Cliente cliente, EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);
		this.cliente = cliente;
		this.vdao = new VehiculoDao(estructuraTablas, conexionAbierta);
		this.tvdao = new TiposVehiculoDao(estructuraTablas, conexionAbierta);
		this.imprime = new estructuraBaseDeDatos.ImprimeTablas(conexionAbierta);
	}

	public void ejecutaMenuCliente() throws SQLException {		
		switch (new menus.MenuWu("Mis Vehículos", "Mis Citas", "Mis datos").ejecutar()) {
		case "Mis Vehículos":			
			imprime.selectFromTablaWhereCampoEqualsValor("vehiculos", "id_cliente",
					String.valueOf(cliente.getId()));			
			switch (new menus.MenuWu("Añadir", "Borrar").ejecutar()) {
			case "Añadir":
				agregarVehiculo();
				break;
			case "Borrar":
				borrarVehiculo();
				break;
			case "Volver":
				break;
			}
			break;
		case "Mis Citas":
			// mostrar citas			
			imprime.selectFromTablaWhereCampoEqualsValor("citas", "id_cliente",
					String.valueOf(cliente.getId()));			
			switch (new menus.MenuWu("Solicitar", "Cancelar").ejecutar()) {
			case "Solicitar":
				solicitarCita();
				break;
			case "Cancelar":
				cancelarCita();
				break;
			case "Volver":
				break;
			}
			break;
		case "Mis Datos Personales":
			// mostrar datos			
			imprime.selectFromTablaWhereCampoEqualsValor("clientes", "id",
					String.valueOf(cliente.getId())); 			
			switch (new menus.MenuWu("Actualizar", "Baja cliente").ejecutar()) {
			case "Actualizar":
				actualizarDatosPersonales();
				break;
			case "Baja cliente":
				bajaCliente();
				System.exit(0);
				break;
			case "Volver":
				break;
			}
			break;
		case "Volver":
			break;
		}
	}

	public void agregarVehiculo() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Introduce matrícula: ");
		String matricula = scanner.nextLine();
		System.out.print("Introduce tipo de vehículo (COCHE/MOTO/CAMIÓN/AUTOBÚS): ");
		String tipo = scanner.nextLine().toUpperCase();
		System.out.print("Introduce marca: ");
		String marca = scanner.nextLine();
		System.out.print("Introduce modelo: ");
		String modelo = scanner.nextLine();
		System.out.print("Introduce año: ");
		int año = scanner.nextInt();
		scanner.nextLine();
		Vehiculo v = new Vehiculo(matricula, marca, modelo, año);
		v.setCliente(cliente);
		v.setTiposVehiculo(tvdao.read(tipo, "tipo"));
		vdao.insert(v);
	}

	private void borrarVehiculo() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Introduce la matrícula del vehículo que quieres borrar:");
		String matricula = scanner.nextLine();
		vdao.delete(vdao.read(matricula, "matricula").get(0));
	}

	private void actualizarDatosPersonales() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Introduce nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("Introduce primer apellido: ");
		String ap1 = scanner.nextLine();
		System.out.print("Introduce segundo apellido: ");
		String ap2 = scanner.nextLine();
		System.out.print("Introduce teléfono: ");
		int telefono = scanner.nextInt();
		cliente.setNombre(nombre);
		cliente.setApellido1(ap1);
		cliente.setApellido2(ap2);
		cliente.setTelefono(telefono);
		cdao.update(cliente);
	}

	private void solicitarCita() {
	}

	private void cancelarCita() {
	}

	private void bajaCliente() {
	}

}
