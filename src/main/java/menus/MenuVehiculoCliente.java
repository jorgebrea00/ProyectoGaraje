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

	public void ejecutaMenuVehiculoCliente() throws SQLException {

		// Mostrar vehiculos del cliente
		imprime.selectFromTablaWhereCampoEqualsValor("vehiculos", "id_cliente", String.valueOf(cliente.getId()));
		switch (new MenuWu("Añadir", "Borrar").ejecutar()) {
		case "Añadir":
			agregarVehiculo();
			break;
		case "Borrar":
			borrarVehiculo();
			break;
		case "Volver":
			break;
		}
	}

	public void agregarVehiculo() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Introduce la matrícula: ");
		String matricula = scanner.nextLine();
		while (!(vdao.read(matricula, "matricula")).isEmpty()) {
			System.out.println("Error, la matrícula ya exite en nuestro sistema.");
			System.out.print("Introduce la matrícula: ");
			matricula = scanner.nextLine();
		}

		System.out.print("Introduce tipo de vehículo (COCHE/MOTO/CAMIÓN/AUTOBÚS): ");
		String tipo = scanner.nextLine().toUpperCase();

		while (!tipo.equalsIgnoreCase("COCHE") && !tipo.equalsIgnoreCase("MOTO") && !tipo.equalsIgnoreCase("CAMIÓN")
				&& !tipo.equalsIgnoreCase("AUTOBÚS")) {
			System.out.print("Opción no válida, inténtalo de nuevo (COCHE/MOTO/CAMIÓN/AUTOBÚS): ");
			tipo = scanner.nextLine().toUpperCase();
		}

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

}
