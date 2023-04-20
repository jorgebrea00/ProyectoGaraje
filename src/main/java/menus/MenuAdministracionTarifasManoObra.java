package menus;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.IvaDao;
import dao.TarifasManoObraDao;
import dao.TiposVehiculoDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.ListadoEntity;
import main.Principal;
import mensajes.Mensajes;
import model.Iva;
import model.TarifasManoObra;
import model.TiposVehiculo;

public class MenuAdministracionTarifasManoObra extends Menu{
	

	public MenuAdministracionTarifasManoObra(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutaMenuAdministracionTarifasManoObra() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de Administración TARIFAS MANO DE OBRA",new String[] {"Volver","Listar", "Alta", "modificar","Eliminar"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Listar"
					listadoTarifasManoObra();
					break;
				
				case 2:						// "Alta"
					altaTarifaManoObra();					
					break;
				case 3:						// "Modificar"		
					modificarTarifaManoObra();
					break;
					
				case 4: 					// "Eliminar"
					borrarTarifaManoObra();
					break;
				
				}
		}while (seleccionMenu!=0);	
	}
	
	public void listadoTarifasManoObra() {
		List<TarifasManoObra> resultadoConsulta;
		TarifasManoObraDao tarifaDao=new TarifasManoObraDao(getEstructuraTablas(), getConexionAbierta());
		
		resultadoConsulta=tarifaDao.getAll();
		boolean mostrarIndices=true;
		String tituloListado="* * Listado de precios de mano de obra * *";
		ListadoEntity listadoTarifas=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(TarifasManoObra.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoTarifas.listarDatosTablas();	
	}
	
	
	
	public void altaTarifaManoObra() {
		TarifasManoObra tarifa;
	
		muestraTituloCentrado("Proceso de alta de tarifas de mano de obra");
		tarifa=introduceTarifaManoObra();
		if (tarifa!=null) {
			grabaTarifaManoObraEnBD(tarifa);
		}
	}
	
	public TarifasManoObra introduceTarifaManoObra() {
		TarifasManoObra tarifa=new TarifasManoObra();
		String seccionTaller;
		String tipoVehiculo;
		boolean entradaValidada=false;
		 
		do {
			seccionTaller=Principal.introduceAtributoPorTeclado("la sección "+Mensajes.SECCIONES_DEL_TALLER,TarifasManoObra.class, "seccion", getEstructuraTablas()).toUpperCase();
			if(Mensajes.SECCIONES_DEL_TALLER.contains(seccionTaller)) entradaValidada=true; else System.out.println(Mensajes.DATO_INTRODUCIDO_ERRONEO);
		} while(!entradaValidada);
		entradaValidada=false;
		List<TiposVehiculo> tiposVehiculos;
		tiposVehiculos= CargaTiposVehiculoDeBD();
		List<String> tipos;
		tipos=CargaTiposVehiculoEnList(tiposVehiculos);
		do {
			tipoVehiculo=Principal.introduceAtributoPorTeclado("el tipo de vehículo "+tipos,TiposVehiculo.class, "tipo", getEstructuraTablas()).toUpperCase();
			if(tipos.contains(tipoVehiculo)) entradaValidada=true; else System.out.println(Mensajes.DATO_INTRODUCIDO_ERRONEO);
		}while(!entradaValidada);
		tarifa.setSeccion(seccionTaller);
		tarifa.setTiposVehiculo(TipoVehiculoIntroducido(tiposVehiculos, tipoVehiculo));
		tarifa.setPrecioHora(Float.parseFloat(Principal.introduceAtributoPorTeclado("el precio por hora",TarifasManoObra.class, "precioHora", getEstructuraTablas()).toUpperCase()));
		return tarifa;
	 }
	
	public List<TiposVehiculo> CargaTiposVehiculoDeBD() {
		List<TiposVehiculo> tiposVehiculo;
		TiposVehiculoDao tipoDao=new TiposVehiculoDao(getEstructuraTablas(), getConexionAbierta());
		tiposVehiculo=tipoDao.getAll();
		if (tiposVehiculo.isEmpty()) {
			System.out.println(Mensajes.TIPO_VEHICULO_NO_EXISTE);
		}
		return tiposVehiculo;
		
	}
	
	public List<String> CargaTiposVehiculoEnList(List<TiposVehiculo> tiposVehiculo) {
		List<String> tipos=new ArrayList<>();
		
		if (!tiposVehiculo.isEmpty()) {
			for(TiposVehiculo tv:tiposVehiculo) {
				tipos.add(tv.getTipo());
			}
		}
		return tipos;	
	}
	
	public TiposVehiculo TipoVehiculoIntroducido(List<TiposVehiculo> tiposVehiculo, String tipoIntroducido) {
		TiposVehiculo tipo=null;
		
		for(TiposVehiculo tv:tiposVehiculo) {
			if(tv.getTipo().equals(tipoIntroducido)) {
				tipo=tv;
				break;
			}
		}
		return tipo;
	}
	
	public void grabaTarifaManoObraEnBD(TarifasManoObra tarifa) {
		TarifasManoObraDao tarifaDao=new TarifasManoObraDao(getEstructuraTablas(), getConexionAbierta());
		
		if(tarifaDao.insert(tarifa)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK);
	}
	
	public void modificarTarifaManoObra() {
		int id;

		muestraTituloCentrado("Modificación de tarifa de mano de obra");	
		id=Integer.parseInt(Principal.introduceAtributoPorTeclado("el id de la tarifa a modificar",TarifasManoObra.class, "id", getEstructuraTablas()));
		if(id>0) {
			TarifasManoObra tarifaAnterior=new TarifasManoObra();
			TarifasManoObraDao tarifaDao=new TarifasManoObraDao(getEstructuraTablas(), getConexionAbierta());
			tarifaAnterior=tarifaDao.read(id);
			if(tarifaAnterior!=null) {
				TarifasManoObra tarifa=new TarifasManoObra();
				tarifa=introduceTarifaManoObra();
				if (confirmaGuardarCambios()) {
					tarifa.setId(id);
					if(tarifaDao.update(tarifa)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK);
				}
			}
		}
	}
	
	public void borrarTarifaManoObra() {
		int id;
			
		muestraTituloCentrado("Baja de tarifas de mano de obra");
		id=Integer.parseInt(Principal.introduceAtributoPorTeclado("el id de la tarifa a modificar",TarifasManoObra.class, "id", getEstructuraTablas()));
		if(id>0) {
			TarifasManoObra tarifa=new TarifasManoObra();
			TarifasManoObraDao tarifaDao=new TarifasManoObraDao(getEstructuraTablas(), getConexionAbierta());
			tarifa=tarifaDao.read(id);
			if (tarifa!=null) {
				TiposVehiculo tipoVehiculo;
				TiposVehiculoDao tipoVehiculoDao=new TiposVehiculoDao(getEstructuraTablas(), getConexionAbierta());
				tipoVehiculo=tipoVehiculoDao.read(tarifa.getTiposVehiculo().getId());
				if (tipoVehiculo!=null) {
					System.out.println("Se eliminará la tarifa de la sección "+tarifa.getSeccion()+" de "+tipoVehiculo.getTipo()+" con precio: "+tarifa.getPrecioHora());
					if (confirmaGuardarCambios()) {
						if(tarifaDao.delete(tarifa)>0) System.out.println(Mensajes.REGISTRO_BORRADO_OK);
					}
				}
			}
		}
	}


}
