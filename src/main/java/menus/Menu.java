package menus;

import java.sql.Connection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.LoginDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.Principal;
import mensajes.Mensajes;
import model.Cliente;
import model.Login;
import model.PreferenciasCliente;

public abstract class Menu{
	private EstructuraBbdd estructuraTablas;
	private Connection conexionAbierta;

	public Menu(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
			super();
			this.estructuraTablas=estructuraTablas;
			this.conexionAbierta=conexionAbierta;	
	}
	
	public Menu() {
		super();
	}
	
	public EstructuraBbdd getEstructuraTablas() {
		return estructuraTablas;
	}

	public void setEstructuraTablas(EstructuraBbdd estructuraTablas) {
		this.estructuraTablas = estructuraTablas;
	}

	public Connection getConexionAbierta() {
		return conexionAbierta;
	}

	public void setConexionAbierta(Connection conexionAbierta) {
		this.conexionAbierta = conexionAbierta;
	}
	
	public int opcionSeleccionadaDelMenu(String opcionesMenu) {
		int numeroOpciones=0;					
		int opcionSeleccionada=0;
		String numeroIntroducido;
	
		numeroOpciones=numeroOpcionesMenu(opcionesMenu);
		if (numeroOpciones>0) {
			do {
				System.out.println(opcionesMenu);
				numeroIntroducido=Principal.introNumeroTeclado();
				if (numeroIntroducido!=null) {
					opcionSeleccionada=Integer.parseInt(numeroIntroducido);
					if(opcionSeleccionada<0 || opcionSeleccionada>numeroOpciones) {
						System.out.println(Mensajes.NUMERO_OPCION_INEXISTENTE);
					}
				}
			}while((opcionSeleccionada<0 || opcionSeleccionada>numeroOpciones) || numeroIntroducido==null);
		}		
		return opcionSeleccionada;
	}
	
	public int numeroOpcionesMenu(String cadenaMenu) {
				// Calcula el número de opciones de la cadena que contiene el menú en función del primer caracter de cada opción.
		int numeroOpciones=0;					
		int numeroCaracteres=cadenaMenu.length();
						
		for(int x=0;x<numeroCaracteres;x++) {	
			if((x+1)<numeroCaracteres) {
				if (cadenaMenu.codePointAt(x)==Mensajes.PRIMER_CARACTER_OPCION_MENU.codePointAt(0)) numeroOpciones++;
			}	
		}
		numeroOpciones--;				// Los números de opción empiezan en cero, por lo que quito uno para
										// que el límite sea el correcto.
		return numeroOpciones;
	}
	
	public String cadenaMenu(String titulo, String[] opciones) {
		// Crea un String con el título y las opciones indicadas.

		String stringMenu="";
		String caracterLinea="*";
		String caracterLateral="*";
		String caracterBlanco=" ";
		int espaciosIzquierdaOpcion=10;
		int numeroOpciones=opciones.length;
		int anchoMenu=50;							// Ancho menu por defecto
		int caracteresTitulo=titulo.length();
		if (caracteresTitulo>=anchoMenu) anchoMenu=caracteresTitulo+2;
		int espaciosIzquierda=((anchoMenu-caracteresTitulo)/2)-2;
		int espaciosDerecha=anchoMenu-caracteresTitulo-espaciosIzquierda-2;
		
		// Cabecera menu
		stringMenu=Mensajes.SALTOLINEA+caracterLinea.repeat(anchoMenu)+Mensajes.SALTOLINEA+
				caracterLateral+caracterBlanco.repeat(espaciosIzquierda)+titulo+caracterBlanco.repeat(espaciosDerecha)+caracterLateral+Mensajes.SALTOLINEA+
				caracterLinea.repeat(anchoMenu)+Mensajes.SALTOLINEA+
				Mensajes.SALTOLINEA+
				Mensajes.SELECCIONAR_OPCION;

					// Opciones Mneu
		if (numeroOpciones>0){
			int x=1;
			while(numeroOpciones>1 && x<numeroOpciones) {
				stringMenu+=caracterBlanco.repeat(espaciosIzquierdaOpcion)+Mensajes.PRIMER_CARACTER_OPCION_MENU+x+"] "+opciones[x]+Mensajes.SALTOLINEA;
				x++;
			}
			stringMenu+=caracterBlanco.repeat(espaciosIzquierdaOpcion)+Mensajes.PRIMER_CARACTER_OPCION_MENU+"0] "+opciones[0]+Mensajes.SALTOLINEA;	
		}
		return stringMenu;
	}
	
	public Cliente introduceDireccionCliente(Cliente cliente) {
		String numero;	
		
		cliente.setTipoVia(Principal.introduceAtributoPorTeclado("el tipo de vía (Ej. Avenida, calle, ...)",Cliente.class, "tipoVia", getEstructuraTablas()));
		cliente.setNombreVia(Principal.introduceAtributoPorTeclado("el nombre de la vía",Cliente.class, "nombreVia", getEstructuraTablas()));
		numero=Principal.introduceAtributoPorTeclado("el número de la vía",Cliente.class, "numeroVia", getEstructuraTablas());
		if (numero==null) cliente.setNumeroVia(0); else cliente.setNumeroVia(Integer.parseInt(numero));
		numero=Principal.introduceAtributoPorTeclado("el número de piso",Cliente.class, "piso", getEstructuraTablas());
		if (numero==null) cliente.setPiso(0); else cliente.setPiso(Integer.parseInt(numero));
		cliente.setPuerta(Principal.introduceAtributoPorTeclado("la letra",Cliente.class, "puerta", getEstructuraTablas()));
		numero=Principal.introduceAtributoPorTeclado("el código postal",Cliente.class, "codigoPostal", getEstructuraTablas());
		if (numero==null) cliente.setCodigoPostal(0); else cliente.setCodigoPostal(Integer.parseInt(numero));
		cliente.setProvincia(Principal.introduceAtributoPorTeclado("la provincia",Cliente.class, "provincia", getEstructuraTablas()));
		return cliente;
	}
	
	public Cliente asignaNuevaDireccionAlCliente(Cliente cliente, Cliente nuevaDireccion) {
		
		cliente.setTipoVia(nuevaDireccion.getTipoVia());
		cliente.setNombreVia(nuevaDireccion.getNombreVia());
		cliente.setNumeroVia(nuevaDireccion.getNumeroVia());
		cliente.setPiso(nuevaDireccion.getPiso());
		cliente.setPuerta(nuevaDireccion.getPuerta());
		cliente.setCodigoPostal(nuevaDireccion.getCodigoPostal());
		cliente.setProvincia(nuevaDireccion.getProvincia());
		return cliente;
	}
	
	
	public Cliente introduceTelefonoCliente(Cliente cliente) {
		String numero;
		numero=Principal.introduceAtributoPorTeclado("su número de teléfono",Cliente.class, "telefono", getEstructuraTablas());
		if (numero==null) cliente.setTelefono(0); else cliente.setTelefono(Integer.parseInt(numero));
		return cliente;
	}
	
	public Cliente asignaNuevoTelefonoAlCliente(Cliente cliente, Cliente nuevoTelefono) {
	
		cliente.setTelefono(nuevoTelefono.getTelefono());
		return cliente;
	}

	public boolean confirmaGuardarCambios() {
		String confirmaCambios;
		boolean cambioOK=false;
		
		System.out.println(Mensajes.DESEA_GUARDAR_CAMBIOS);
		confirmaCambios=Principal.introTextoTeclado(2);
		if (confirmaCambios.equalsIgnoreCase("SÍ") || confirmaCambios.equalsIgnoreCase("SI") || confirmaCambios.equalsIgnoreCase("S")) cambioOK=true;
		return cambioOK;
	}
	
	public String pideAcceso() {
		String cadena;
		int hash;
		
		cadena=Principal.introduceAtributoPorTeclado("su contraseña",Login.class, "passHash", estructuraTablas);
		hash=Principal.hashCode(cadena);
		return Integer.toString(hash);
	}
	
	public String creaAcceso() {
		String acceso1;
		String acceso2;
		int hash1;
		int hash2;
		boolean validacion=false;
		
		do {
			acceso1=Principal.introduceAtributoPorTeclado("su contraseña",Login.class, "passHash", estructuraTablas);
			hash1=Principal.hashCode(acceso1);
			acceso2=Principal.introduceAtributoPorTeclado("su contraseña otra vez",Login.class, "passHash", estructuraTablas);
			hash2=Principal.hashCode(acceso2);
			if (hash1==hash2) validacion=true; else System.out.println(Mensajes.CREA_ACCESO_KO);
		}while(!validacion);
		return Integer.toString(hash1);
		
	}
	
	public void muestraTituloCentrado(String titulo) {
			int longitud=50;
			int diferencia;
			int longitudMedia;
			String espacioEnBlanco=" ";
			
			diferencia=longitud-titulo.length();
			if(diferencia>1) longitudMedia=diferencia/2; else longitudMedia=1;
			String mensaje=Mensajes.SALTOLINEA+espacioEnBlanco.repeat(longitudMedia)+titulo+espacioEnBlanco.repeat(longitudMedia)+Mensajes.SALTOLINEA;
			String caracter="_";
			String linea=caracter.repeat(mensaje.length());
			String cabecera=linea+mensaje+linea;
			System.out.println(cabecera);
		}
	
	public Login borraPassEnMemoria(Login login) {
		login.setPassHash(" ");				// Por seguridad, se borra después del acceso;
		return login;
	}
	
	public Login loguearse() {
		LoginDao loginDao=new LoginDao(getEstructuraTablas(), getConexionAbierta());
		Login login;
		login=null;
		String passIntroducido;
		String emailIntroducido;
		
		System.out.println("Por favor, Introduzca su e-mail:");
		emailIntroducido=Principal.introTextoTeclado(30);
		passIntroducido=pideAcceso();
		if(passIntroducido!=null) {
			login=loginDao.read(emailIntroducido, "email");	
			if (login==null || !(passIntroducido.equals(login.getPassHash()))) login=null; else login=borraPassEnMemoria(login);
		}
		return login;
	}
	
	public PreferenciasCliente introducePreferenciasCliente(Cliente cliente) {
		 String diaSemana;
		 String turno;
		 boolean entradaValidada=false;
		 
		 muestraTituloCentrado("Preferencias de atención");
		 PreferenciasCliente preferenciasCliente=new PreferenciasCliente();
		 do {
			 diaSemana=Principal.introduceAtributoPorTeclado("el día de la semana que desea ser atendido ("+Mensajes.DIAS_SEMANA+")",PreferenciasCliente.class, "diaSemana", getEstructuraTablas()).toUpperCase();
			 if(Mensajes.DIAS_SEMANA.contains(diaSemana)) entradaValidada=true; else System.out.println(Mensajes.DATO_INTRODUCIDO_ERRONEO);
		 } while(!entradaValidada);
		 entradaValidada=false;
		 do {
			 turno=Principal.introduceAtributoPorTeclado("el turno en que desea ser atendido ("+Mensajes.TURNOS+")",PreferenciasCliente.class, "turno", getEstructuraTablas()).toUpperCase();
			 if(Mensajes.TURNOS.contains(turno)) entradaValidada=true; else System.out.println(Mensajes.DATO_INTRODUCIDO_ERRONEO);
		 }while(!entradaValidada);
		 preferenciasCliente.setDiaSemana(diaSemana);
		 preferenciasCliente.setTurno(turno);
		 preferenciasCliente.setFechaHora(fechaActual());
		 preferenciasCliente.setIdCliente(cliente.getId());
		 return preferenciasCliente;
	 }
	
	public Date fechaActual() {	
		return new Date();
	}
	
	public boolean validarDNI(String dni) {
		 
		 boolean dniValido=false;
		 if(verificaPatronDni(dni)) {
			 String letrasDni="TRWAGMYFPDXBNJZSQVHLCKE"; 
			 String parteEnteraDni = dni.substring(0, 8);  							//  recoge la parte entera del DNI (carácteres del 0 al 7).
			 char letraDni = dni.toUpperCase().charAt(8);
			 int valNumDni = Integer.parseInt(parteEnteraDni) % 23;
			 if (letrasDni.charAt(valNumDni)==letraDni) dniValido=true;
		 }
		 return dniValido;
	 }
 
	public Boolean verificaPatronDni(String dni) {
		boolean dniValido=false;
	
		Pattern patron=Pattern.compile("^[0-9]{8}[A-Za-z]$");			// {7,8} Permite 7 u 8 carácteres numéricos {8} sólo 8 y una letra mayúscula o minúscula
		Matcher matcher=patron.matcher(dni);
		if (matcher.find()) dniValido=true;
		return dniValido;
	}	
	
	

}
