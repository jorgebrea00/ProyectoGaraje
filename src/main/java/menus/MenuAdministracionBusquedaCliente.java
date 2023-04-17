package menus;

import java.sql.Connection;
import java.util.List;

import dao.ClienteDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.Principal;
import mensajes.Mensajes;
import model.Cliente;

public class MenuAdministracionBusquedaCliente extends Menu {

	public MenuAdministracionBusquedaCliente(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public Cliente EjecutarMenuAdministracionBusquedaCliente() {
		Cliente cliente=new Cliente();
		int seleccionMenu;
		
		System.out.println(Mensajes.SALTOLINEA);
		seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de búsqueda de CLIENTES",new String[] {"Volver","Por número de identificación", "Por dni"}));
		switch (seleccionMenu) {
			
			case 0:
				cliente=volver(cliente);
				break;
				
			case 1:						// "Por número de identificación"
				cliente=busquedaClientePorId(cliente);
				break;

			case 2:						// "Por dni"
				cliente=busquedaClientePorDni(cliente);
				break;	
		}
		return cliente;
	}
	
	public Cliente busquedaClientePorId(Cliente cliente) {
		String id;
		
		id=Principal.introduceAtributoPorTeclado("el número de identificación del cliente",Cliente.class, "id", getEstructuraTablas());
		if (id==null) {
			cliente=null;  
		}else {
			ClienteDao clienteDao=new ClienteDao(getEstructuraTablas(), getConexionAbierta());
			cliente=clienteDao.read(Integer.parseInt(id));
			if (cliente==null) System.out.println(Mensajes.REGISTRO_NO_EXISTE_EN_BD);
		}
		return cliente;		
	}
	
	public Cliente busquedaClientePorDni(Cliente cliente) {
		String dni;
		
		dni=Principal.introduceAtributoPorTeclado("el número de dni del cliente",Cliente.class, "dni", getEstructuraTablas());
		if (dni==null) {
			cliente=null;  
		}else {
			List<Cliente> listaResultado;
			ClienteDao clienteDao=new ClienteDao(getEstructuraTablas(), getConexionAbierta());
			listaResultado=clienteDao.read(dni, Principal.nombreDelCampoAsociadoALaEntity(Cliente.class, "dni"));
			if(listaResultado.isEmpty()) {
				System.out.println(Mensajes.REGISTRO_NO_EXISTE_EN_BD);
				cliente=null; 
			}else {
				cliente=listaResultado.get(0); 	// El dni es único en la BD, por eso se lee el primer elemento de la lista.
			}
		}
		return cliente;
		
	}
	
	public Cliente volver(Cliente cliente) {
		cliente=null;
		return cliente;
	}

}
