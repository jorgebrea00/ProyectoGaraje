package util;

import main.Vistas;
import main.main;
import model.Autenticacion;
import model.Cliente;

public class Formularios {

	public static void registroClienteYAutenticacion() {
		Cliente cliente = solicitarDatosCreacionCliente();
		String pass = solicitarNuevaPass();
		Autenticacion auth = new Autenticacion(main.dao.getClientedao().insert(cliente), cliente.getEmail(), pass);
		main.dao.getAutenticaciondao().insert(auth);
		System.out.println();
		System.out.println("¡Registro completado!");
		System.out.println();
	}

	public static boolean ingresoCliente() {
		String correo = "";
		String pass = "";
		int intentos = 0;
		boolean loginOk = false;
		do {
			System.out.println();
			System.out.println("Introduce tus datos de acceso:");
			System.out.println();
			System.out.println("Correo:");
			correo = main.getEscaner().nextLine();
			System.out.println();
			System.out.println("Contraseña:");
			pass = main.getEscaner().nextLine();
			loginOk = main.dao.getAutenticaciondao().validarCredenciales(correo, pass); // validar credenciales
			if (!loginOk) {
				System.out.println("Usuario o contraseña no válido");
				intentos++;
			}
			if (intentos > 2) {
				// login fallido, mandamos a menu principal
				return false;
			}
		} while (!loginOk);
		
		
		// Login Ok
		main.setClienteLogeado(main.dao.getClientedao().read(correo)); // Asignar cliente logeado
		return true;
	}

	public static void salir() {
		String respuesta = "";
		do {
			System.out.println("¿Seguro que quieres salir?(S/N)");
			respuesta = main.getEscaner().nextLine();
			if (respuesta == "N") {
				Vistas.menuInicial();
			}
		} while (!respuesta.equals("Y") && !respuesta.equals("N"));
		System.out.println("Hasta la vista, baby.");
		main.getEscaner().close();
		System.exit(0);
	}

	public static Cliente solicitarDatosCreacionCliente() {
		Cliente c = new Cliente();
		System.out.println("Por favor, introduce tus datos:");
		System.out.println();
		System.out.println("Dni:");
		c.setDni(main.getEscaner().nextLine());
		System.out.println("Email:");
		c.setEmail(main.getEscaner().nextLine());
		System.out.println("Nombre:");
		c.setNombre(main.getEscaner().nextLine());
		System.out.println("Apellido:");
		c.setApellido(main.getEscaner().nextLine());
		System.out.println("Teléfono:");
		c.setTelefono(main.getEscaner().nextInt());
		main.getEscaner().nextLine(); // Consumir el carácter de nueva línea en el búfer del escáner
		return c;
	}

	public static String solicitarNuevaPass() {
		String p1 = "";
		String p2 = "";
		Autenticacion a = new Autenticacion();
		do {
			System.out.println("Introduce tu contraseña");
			if (main.getEscaner().hasNextLine()) { // Verificar si hay una línea disponible
				p1 = main.getEscaner().nextLine();
			} else {
				break;
			}
			System.out.println("Repite la contraseña");
			if (main.getEscaner().hasNextLine()) { // Verificar si hay una línea disponible
				p2 = main.getEscaner().nextLine();
			} else {
				break;
			}
			if (!p1.equals(p2)) {
				System.out.println("¡Las contraseñas no coinciden!");
				System.out.println();
			}
		} while (!p1.equals(p2));
		return p1;
	}

	public static void actualizarDatosCliente() {
		Cliente c = solicitarDatosCreacionCliente();
		c.setId(main.getClienteLogeado().getId());
		main.dao.getClientedao().update(c);
		main.setClienteLogeado(c);
	}

	public static void registroVehiculo() {
		System.out.println("PENDIENTE");
		
	}

	public static void eliminarVehiculo() {
		System.out.println("PENDIENTE");		
	}
}
