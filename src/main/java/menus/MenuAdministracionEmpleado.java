package menus;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.EmpleadoDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.ListadoEntity;
import main.Principal;
import mensajes.Mensajes;
import model.Empleado;

public class MenuAdministracionEmpleado extends Menu {

	public MenuAdministracionEmpleado(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutaMenuAdministracionEmpleado() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de Administración de EMPLEADOS",new String[] {"Volver","Consulta", "Listado", "Alta", "Modificación", "Baja"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Consulta"
					consultaEmpleado();					
					break;
					
				case 2:						// "Listado"
					listadoEmpleados();
					break;
					
				case 3:						// "Alta"
					altaEmpleado();
					break;
					
				case 4:						// "Modificación"
					 modificacionEmpleado();
					break;
					
				case 5:						// "Baja"
					bajaEmpleado();
					break;
				}
		}while (seleccionMenu!=0);	
	}
	
	public void altaEmpleado() {
		Empleado empleado;
		
		empleado=introduceDatosEmpleado();
		if (empleado!=null) {
			grabaDatosEmpleadoEnBD(empleado);
		}
	}
	
	public Empleado introduceDatosEmpleado() {
		Empleado empleado=new Empleado();
			
		muestraTituloCentrado("Proceso de alta de empleados");	
		empleado.setDni(introduceDniAltaEmpleado());
		if (empleado.getDni()!=null) {
			introduceOtrosDatosEmpleado(empleado);
		}else {
			empleado=null;
		}
		return empleado;
	}
	
	public Empleado introduceOtrosDatosEmpleado(Empleado empleado) {
	
		empleado.setNombre(Principal.introduceAtributoPorTeclado("el nombre",Empleado.class, "nombre", getEstructuraTablas()));
		empleado.setApellido1(Principal.introduceAtributoPorTeclado("el primer apellido",Empleado.class, "apellido1", getEstructuraTablas()));
		empleado.setApellido2(Principal.introduceAtributoPorTeclado("el segundo apellido",Empleado.class, "apellido2", getEstructuraTablas()));
		empleado.setPuesto(Principal.introduceAtributoPorTeclado("el puesto que ocupa",Empleado.class, "puesto", getEstructuraTablas()));
		empleado.setDepartamento(Principal.introduceAtributoPorTeclado("el departamento en el que trabaja",Empleado.class, "departamento", getEstructuraTablas()));
		return empleado;
	}
	
	public String introduceDniAltaEmpleado() {
		String dni;
		boolean dniValido;
		int maxNumeroIntendosDni=3;
		int numeroIntentosDni=0;

		do {
			dni=Principal.introduceAtributoPorTeclado("el número de NIF",Empleado.class, "dni", getEstructuraTablas());
			dniValido=validarDNI(dni);
			if (!dniValido || compruebaExisteDniEnBDEmpleado(dni)) {
				System.out.println(Mensajes.DNI_NO_VALIDO);
				dniValido=false;
			}
			numeroIntentosDni++;
		}while(!dniValido && numeroIntentosDni<maxNumeroIntendosDni );
		if ( numeroIntentosDni==maxNumeroIntendosDni) dni=null;
		return dni;
	}

	public boolean compruebaExisteDniEnBDEmpleado(String dni) {
		boolean existeDni=false;
	
		if (buscaEmpleadoEnBD(dni)!=null) existeDni=true;
		return existeDni;		 
		 }
	
	public Empleado buscaEmpleadoEnBD(String dni) {
		Empleado empleado;
		EmpleadoDao empleadoDao=new EmpleadoDao(getEstructuraTablas(),getConexionAbierta());
		
		empleado=null;
		List<Empleado> empleados=empleadoDao.read(dni, Principal.nombreDelCampoAsociadoALaEntity(Empleado.class, "dni"));
		if (!empleados.isEmpty()) empleado=empleados.get(0);				// No pueden existir dos empleados con el mismo dni
		return empleado;
	}
	
	public void grabaDatosEmpleadoEnBD(Empleado empleado) {
		EmpleadoDao empleadoDao=new EmpleadoDao(getEstructuraTablas(), getConexionAbierta());
		
		if(empleadoDao.insert(empleado)>0) {
			System.out.println(Mensajes.CAMBIO_GUARDADO_OK);
		}
		
	}
	
	public void listadoEmpleados() {
		List <Empleado> resultadoConsulta;
		EmpleadoDao empleadoDao=new EmpleadoDao(getEstructuraTablas(), getConexionAbierta());
		
		resultadoConsulta=empleadoDao.getAll();
		boolean mostrarIndices=true;
		String tituloListado="* * Listado de empleados * *";
		ListadoEntity listadoEmpleados=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Empleado.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoEmpleados.listarDatosTablas();		
	}
	
	public void consultaEmpleado() {
		Empleado empleado;
		List <Empleado> resultadoConsulta=new ArrayList<>();
		String dni;
		
		muestraTituloCentrado("Consulta de empleados");	
		dni=introduceDniConsultaEmpleado();
		if (dni!=null) {
			empleado=buscaEmpleadoEnBD(dni);
			if (empleado!=null) {
				resultadoConsulta.add(empleado);
				boolean mostrarIndices=true;
				String tituloListado="* * Empleado buscado * *";
				ListadoEntity listadoEmpleados=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Empleado.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
				listadoEmpleados.listarDatosTablas();					
			}
		}
		
	}
	
	public String introduceDniConsultaEmpleado() {
		String dni;
		boolean dniValido;
	
		dni=Principal.introduceAtributoPorTeclado("el número de NIF",Empleado.class, "dni", getEstructuraTablas());
		dniValido=validarDNI(dni);
		if (!dniValido) {
			System.out.println(Mensajes.DNI_NO_VALIDO);
			dni=null;
		}
		return dni;
	}
	
	public void modificacionEmpleado() {
		Empleado empleado;
		String dni;
		
		muestraTituloCentrado("Modificación de empleados");	
		dni=introduceDniConsultaEmpleado();
		if (dni!=null) {
			empleado=buscaEmpleadoEnBD(dni);
			if (empleado!=null) {
				Empleado empleadoNuevosDatos=new Empleado();
				empleadoNuevosDatos=introduceOtrosDatosEmpleado(empleadoNuevosDatos);
				if (confirmaGuardarCambios()) {
					empleadoNuevosDatos.setId(empleado.getId());
					empleadoNuevosDatos.setDni(empleado.getDni());
					EmpleadoDao empleadoDao=new EmpleadoDao(getEstructuraTablas(), getConexionAbierta());
					if(empleadoDao.update(empleadoNuevosDatos)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK);					
				}			
			}
		}
	}
		
	public void bajaEmpleado() {
		Empleado empleado;
		String dni;
		
		muestraTituloCentrado("Baja de empleados");	
		dni=introduceDniConsultaEmpleado();
		if (dni!=null) {
			empleado=buscaEmpleadoEnBD(dni);
			if (empleado!=null) {
				if (confirmaGuardarCambios()) {
					EmpleadoDao empleadoDao=new EmpleadoDao(getEstructuraTablas(), getConexionAbierta());
					if(empleadoDao.delete(empleado)>0) System.out.println(Mensajes.REGISTRO_BORRADO_OK);					
				}
								
			}
		}
	}

}
