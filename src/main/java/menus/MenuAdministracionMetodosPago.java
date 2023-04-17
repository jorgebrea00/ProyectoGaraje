package menus;

import java.sql.Connection;
import java.util.List;

import dao.MetodosPagoDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.ListadoEntity;
import main.Principal;
import mensajes.Mensajes;
import model.MetodosPago;

public class MenuAdministracionMetodosPago extends Menu {

	public MenuAdministracionMetodosPago(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutaMenuAdministracionMetodosPago() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de Administración METODOS DE PAGO",new String[] {"Volver","Listar", "Alta", "Eliminar"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Listar"
					listadoMetodosPago();
					break;
				
				case 2:						// "Alta"
					altaMetodoPago();					
					break;
//				case 3:						// "Modificar"		
//					modificarMetodPago();
//					break;
					
				case 3: 					// "Eliminar"
					borrarMetodoPago();
					break;
				
				}
		}while (seleccionMenu!=0);	
	}
	
	public void altaMetodoPago() {
		String metodoPago;
		MetodosPago metodosPago=new MetodosPago();
	
		muestraTituloCentrado("Proceso de alta de tipos de METODOS DE PAGO");
		metodoPago=introduceMetodoPago();
		if (metodoPago!=null && !compruebaExisteMetodoPagoBD(metodoPago)) {
			metodosPago.setDescripcion(metodoPago);
			grabaMetodoPagoEnBD(metodosPago);			
		}else if(compruebaExisteMetodoPagoBD(metodoPago)) System.out.println(Mensajes.REGISTRO_YA_EXISTE_EN_BD);
	}
	
	public String introduceMetodoPago(){
		String metodoPago=null;
		
		metodoPago=Principal.introduceAtributoPorTeclado("el método de pago (efectivo o tarjeta)",MetodosPago.class, "descripcion", getEstructuraTablas()).toLowerCase();
		if (metodoPago.equals("e") || metodoPago.equals("efectivo")) {
			metodoPago="efectivo"; 
		}else if (metodoPago.equals("t") || metodoPago.equals("tarjeta")) {
			metodoPago="tarjeta";
		}else {
			System.out.println(Mensajes.DATO_INTRODUCIDO_ERRONEO);
			metodoPago=null;
		}
		
		return metodoPago;
	}
	
	public boolean compruebaExisteMetodoPagoBD(String metodoPago) {
		boolean existeMetodoPago=false;
		
		if (buscaMetodoPagoEnBD(metodoPago)!=null) existeMetodoPago=true;
		return existeMetodoPago;		
	}

	public MetodosPago buscaMetodoPagoEnBD(String metodoPagoIntroducido) {
		MetodosPago metodoPago;
		MetodosPagoDao metodoPagoDao=new MetodosPagoDao(getEstructuraTablas(),getConexionAbierta());
		
		metodoPago=null;
		List<MetodosPago> metodosPago=metodoPagoDao.read(metodoPagoIntroducido,Principal.nombreDelCampoAsociadoALaEntity(MetodosPago.class, "descripcion"));
		if (!metodosPago.isEmpty()) metodoPago=metodosPago.get(0); // No pueden existir dos tipos de IVA iguales
		return metodoPago;
	}
	
	public void grabaMetodoPagoEnBD(MetodosPago metodoPago) {
		MetodosPagoDao metodoPagoDao=new MetodosPagoDao(getEstructuraTablas(), getConexionAbierta());
		
		if(metodoPagoDao.insert(metodoPago)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK);
	}
	
	public void listadoMetodosPago() {
		List<MetodosPago> resultadoConsulta;
		MetodosPagoDao metodoPagoDao=new MetodosPagoDao(getEstructuraTablas(), getConexionAbierta());
		
		resultadoConsulta=metodoPagoDao.getAll();
		boolean mostrarIndices=true;
		String tituloListado="* * Listado de metodos de pago * *";
		ListadoEntity listadoEmpleados=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(MetodosPago.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoEmpleados.listarDatosTablas();	
	}
	
	public void modificarMetodPago() {
		MetodosPago metodoPago;
		String metodoPagoBuscado;
		String nuevoMetodoPago;
		
		
		
		muestraTituloCentrado("Modificación de metodos de pago");	
		metodoPagoBuscado=introduceMetodoPago();
		if(metodoPagoBuscado!=null) {
			metodoPago=buscaMetodoPagoEnBD(metodoPagoBuscado);
			if(metodoPago!=null) {
				System.out.println("- NUEVO TIPO METODO DE PAGO -");
				nuevoMetodoPago=introduceMetodoPago();
				if (confirmaGuardarCambios()) {
					metodoPago.setDescripcion(nuevoMetodoPago);
					MetodosPagoDao metodoPagoDao=new MetodosPagoDao(getEstructuraTablas(), getConexionAbierta());
					if(metodoPagoDao.update(metodoPago)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK);	
				}
			}
		}
	}
	
	public void borrarMetodoPago() {
		MetodosPago metodosPago;
		String metodoPagoIntroducido;
		muestraTituloCentrado("Baja de metodos de pago");	
		metodoPagoIntroducido=introduceMetodoPago();
		if(metodoPagoIntroducido!=null) {
			metodosPago=buscaMetodoPagoEnBD(metodoPagoIntroducido);
			if(metodosPago!=null) {
				if (confirmaGuardarCambios()) {
					MetodosPagoDao metodoPagoDao=new MetodosPagoDao(getEstructuraTablas(), getConexionAbierta());
					if(metodoPagoDao.delete(metodosPago)>0) System.out.println(Mensajes.REGISTRO_BORRADO_OK);
				}
			}
		}
	}
	
}
