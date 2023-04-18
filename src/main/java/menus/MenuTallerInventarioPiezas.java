package menus;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.InventariosPiezaDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.ListadoEntity;
import main.Principal;
import mensajes.Mensajes;
import model.InventariosPieza;

public class MenuTallerInventarioPiezas extends Menu{

	public MenuTallerInventarioPiezas(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutaMenuTallerInventarioPiezas() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de inventario de piezas",new String[] {"Volver","Consulta","Listar", "Alta", "Modificación", "Baja"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Consulta"
					consultaPiezas();
					break;
					
				case 2:						// "Listar"
					listadoPiezas();
					break;	
				
				case 3:						// "Alta"
					altaPieza();
					break;
					
				case 4:						// "Modificación"
					modificacionPieza();
					break;
				
				case 5:						// "Baja"
					bajaPieza();
					break;	
				
				}
		}while (seleccionMenu!=0);	
	}

	public void altaPieza() {
		InventariosPieza pieza;
	
		pieza=introducePieza();
		if (pieza!=null) {
			grabaPiezaEnBD(pieza);			
		}
	}
	
	public InventariosPieza introducePieza() {
		InventariosPieza pieza=new InventariosPieza();
		
		muestraTituloCentrado("Proceso de alta de piezas");
		pieza.setNombre(introduceNombrePieza());
		if(!compruebaExisteNombrePieza(pieza.getNombre())) { 
			introduceOtrosDatosDeLaPieza(pieza );
		}else {
			pieza=null;
			System.out.println(Mensajes.REGISTRO_YA_EXISTE_EN_BD);
		}
		return pieza;
	}
	
	public InventariosPieza introduceOtrosDatosDeLaPieza(InventariosPieza pieza ) {
		pieza.setDescripcion(Principal.introduceAtributoPorTeclado("la descripción",InventariosPieza.class, "descripcion", getEstructuraTablas()));
		pieza.setStockTotal(Integer.parseInt(Principal.introduceAtributoPorTeclado("el stock",InventariosPieza.class, "stockTotal", getEstructuraTablas())));
		pieza.setPrecioVenta(Float.parseFloat(Principal.introduceAtributoPorTeclado("el precio",InventariosPieza.class, "precioVenta", getEstructuraTablas())));
		return pieza;
	}
	
	public String introduceNombrePieza() {
		String nombrePieza;

			nombrePieza=Principal.introduceAtributoPorTeclado("el nombre",InventariosPieza.class, "nombre", getEstructuraTablas());
//		if (compruebaExisteNombrePieza(nombrePieza)) {
//			System.out.println(Mensajes.REGISTRO_YA_EXISTE_EN_BD);
//			nombrePieza=null;
//		}
		return nombrePieza;
	}
	
	public boolean compruebaExisteNombrePieza(String nombrePieza) {
		boolean existePieza=false;

		if (buscaNombrePiezaEnBD(nombrePieza)!=null) existePieza=true;
		return existePieza;		 
	 	}
	
	public InventariosPieza buscaNombrePiezaEnBD(String nombrePieza) {
		InventariosPieza pieza;
		InventariosPiezaDao piezaDao=new InventariosPiezaDao(getEstructuraTablas(),getConexionAbierta());
		
		pieza=null;
		List<InventariosPieza> piezas=piezaDao.read(nombrePieza, Principal.nombreDelCampoAsociadoALaEntity(InventariosPieza.class, "nombre"));
		if (!piezas.isEmpty()) pieza=piezas.get(0);				// No pueden existir dos piezas con el mismo nombre
		return pieza;
	}
	
	public void grabaPiezaEnBD(InventariosPieza pieza) {
		InventariosPiezaDao inventariosPiezaDao=new InventariosPiezaDao(getEstructuraTablas(), getConexionAbierta());
		
		if(inventariosPiezaDao.insert(pieza)>0) {
			System.out.println(Mensajes.CAMBIO_GUARDADO_OK);
		}
		
	}
	
	public void consultaPiezas() {
		InventariosPieza pieza;
		List <InventariosPieza> resultadoConsulta=new ArrayList<>();
		String nombrePieza;
		
		muestraTituloCentrado("Consulta de piezas");	
		nombrePieza=introduceNombrePieza();
		if (nombrePieza!=null) {
			pieza=buscaNombrePiezaEnBD(nombrePieza);
			if (pieza!=null) {
				resultadoConsulta.add(pieza);
				boolean mostrarIndices=true;
				String tituloListado="* * Piezas buscadas * *";
				ListadoEntity listadoEmpleados=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(InventariosPieza.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
				listadoEmpleados.listarDatosTablas();					
			}
		}
		
	}
	
	public void listadoPiezas() {
		List <InventariosPieza> resultadoConsulta;
		InventariosPiezaDao piezasDao=new InventariosPiezaDao(getEstructuraTablas(), getConexionAbierta());
		
		resultadoConsulta=piezasDao.getAll();
		boolean mostrarIndices=true;
		String tituloListado="* * Listado de piezas * *";
		ListadoEntity listadoEmpleados=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(InventariosPieza.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoEmpleados.listarDatosTablas();		
	}
	
	public void modificacionPieza() {
		InventariosPieza pieza;
		String nombrePieza;
		
		muestraTituloCentrado("Modificación de piezas");	
		nombrePieza=introduceNombrePieza();
		if (nombrePieza!=null) {
			pieza=buscaNombrePiezaEnBD(nombrePieza);
			if (pieza!=null) {
				InventariosPieza nuevaPieza=new InventariosPieza();
				nuevaPieza=introduceOtrosDatosDeLaPieza(nuevaPieza);
				if (confirmaGuardarCambios()) {
					nuevaPieza.setId(pieza.getId());
					nuevaPieza.setNombre(pieza.getNombre());
					InventariosPiezaDao piezaDao=new InventariosPiezaDao(getEstructuraTablas(), getConexionAbierta());
					if(piezaDao.update(nuevaPieza)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK);					
				}			
			}
		}
	}
	
	public void bajaPieza() {
		InventariosPieza pieza;
		String nombrePieza;
		
		muestraTituloCentrado("Baja de piezas");	
		nombrePieza=introduceNombrePieza();
		if (nombrePieza!=null) {
			pieza=buscaNombrePiezaEnBD(nombrePieza);
			if (pieza!=null) {
				System.out.println("Se eliminará de la base de datos la pieza "+pieza.getNombre()+" con descripción "+pieza.getDescripcion());
				if (confirmaGuardarCambios()) {
					InventariosPiezaDao piezaDao=new InventariosPiezaDao(getEstructuraTablas(), getConexionAbierta());
					if(piezaDao.delete(pieza)>0) System.out.println(Mensajes.REGISTRO_BORRADO_OK);					
				}
								
			}
		}
	}
	
	
}
