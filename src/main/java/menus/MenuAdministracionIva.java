package menus;

import java.sql.Connection;
import java.util.List;

import dao.IvaDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.ListadoEntity;
import main.Principal;
import mensajes.Mensajes;
import model.Iva;

public class MenuAdministracionIva extends Menu{

	public MenuAdministracionIva(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutaMenuAdministracionIva() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de Administración TIPO DE IVA",new String[] {"Volver","Listar", "Introducir","Modificar", "Eliminar"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Listar"
					listadoIvas();
					break;
				
				case 2:						// "Introducir"
					altaIva();					
					break;
				case 3:						// "Modificar"
					modificarIva();
					break;
					
				case 4: 					// "Eliminar"
					borrarIva();
					break;
				
				}
		}while (seleccionMenu!=0);	
	}
	
	public void altaIva() {
		String tipoIva;
		Iva iva=new Iva();
	
		muestraTituloCentrado("Proceso de alta de tipos de IVA");
		tipoIva=introduceIva();
		if (tipoIva!=null && !compruebaExisteIvaenBD(tipoIva)) {
			iva.setTipoIva(Float.parseFloat(tipoIva));
			grabaIvaEnBD(iva);			
		}else if(compruebaExisteIvaenBD(tipoIva)) System.out.println(Mensajes.REGISTRO_YA_EXISTE_EN_BD);
	}
	
	public String introduceIva(){
		String tipoIva=null;
		
		tipoIva=Principal.introduceAtributoPorTeclado("el tipo de IVA",Iva.class, "tipoIva", getEstructuraTablas());
		return tipoIva;
	}
	
	public boolean compruebaExisteIvaenBD(String iva) {
		boolean existeIva=false;
		
		if (buscaIvaEnBD(iva)!=null) existeIva=true;
		return existeIva;		
	}
	
	public Iva buscaIvaEnBD(String tipoIva) {
		Iva iva;
		IvaDao ivaDao=new IvaDao(getEstructuraTablas(),getConexionAbierta());
		
		iva=null;
		List<Iva> ivas=ivaDao.read(tipoIva);
		if (!ivas.isEmpty()) iva=ivas.get(0); // No pueden existir dos tipos de IVA iguales
		return iva;
	}
	
	public void grabaIvaEnBD(Iva iva) {
		IvaDao ivaDao=new IvaDao(getEstructuraTablas(), getConexionAbierta());
		
		if(ivaDao.insert(iva)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK);
	}
	
	public void listadoIvas() {
		List<Iva> resultadoConsulta;
		IvaDao ivaDao=new IvaDao(getEstructuraTablas(), getConexionAbierta());
		
		resultadoConsulta=ivaDao.getAll();
		boolean mostrarIndices=true;
		String tituloListado="* * Listado de tipos de IVA * *";
		ListadoEntity listadoEmpleados=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Iva.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoEmpleados.listarDatosTablas();	
	}
	
	public void modificarIva() {
		Iva iva;
		String tipoIvaBuscado;
		String nuevoTipoIva;
		
		
		
		muestraTituloCentrado("Modificación de tipos de IVA");	
		tipoIvaBuscado=introduceIva();
		if(tipoIvaBuscado!=null) {
			iva=buscaIvaEnBD(tipoIvaBuscado);
			if(iva!=null) {
				System.out.println("- NUEVO TIPO DE IVA -");
				nuevoTipoIva=introduceIva();
				if (confirmaGuardarCambios()) {
					iva.setTipoIva(Float.parseFloat(nuevoTipoIva));
					IvaDao ivaDao=new IvaDao(getEstructuraTablas(), getConexionAbierta());
					if(ivaDao.update(iva)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK);	
				}
			}
		}
	}
	
	public void borrarIva() {
		Iva iva;
		String tipoIva;
		muestraTituloCentrado("Baja de tipos de IVA");	
		tipoIva=introduceIva();
		if(tipoIva!=null) {
			iva=buscaIvaEnBD(tipoIva);
			if(iva!=null) {
				if (confirmaGuardarCambios()) {
					IvaDao ivaDao=new IvaDao(getEstructuraTablas(), getConexionAbierta());
					if(ivaDao.delete(iva)>0) System.out.println(Mensajes.REGISTRO_BORRADO_OK);
				}
			}
		}
	}


}
