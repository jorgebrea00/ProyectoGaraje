package mensajes;

import java.util.Arrays;
import java.util.List;

public class Mensajes {
	public static final String SALTOLINEA="\n";
	public static final String TABULADOR="\t";
	
	public static final String AGRADECIMIENTO="Gracias por utilizar la aplicación."+SALTOLINEA;
	
	public static final String ERRORDELECTURA="Error al leer la base de datos."+SALTOLINEA;
	public static final String ERRORENTRADATECLADO="Error en la entrada por teclado"+SALTOLINEA;
	public static final String ERRORALINTRODUCIRNUMERO="Atención! Debe introducir un número."+SALTOLINEA;
	public static final String ERROR_GRABAR_REGISTRO="Atención! Se ha producido un error al grabar el registro."+SALTOLINEA;
	public static final String ERROR_CIERRE_BBDD="Error al cerrar la base de datos."+SALTOLINEA;
	public static final String ERROR_BORRAR_REGISTRO="Error al borrar el registro de la base de datos."+SALTOLINEA;
	public static final String ERROR_JBDC="ERROR: no se pudo cargar el HSQLDB JDBC driver.";
	public static final String ERROR_TABLA_NO_EXISTE="Atención: La tabla no existe.";
	public static final String ERROR_AL_CARGAR_TABLAS="Atención! No se ha cargado estructura de la base de datos.";
	public static final String ERROR_ACCESO_ATRIBUTOS_CLASE="Error al acceder a los atributos de la clase: ";
	public static final String ERROR_ACCESO_VALORES_CLASE="Error al acceder a los valores de los atributos de la clase: ";
	public static final String ERROR_ACCESO_LOGIN="Atención! Usuario o contraseña no válidos."+SALTOLINEA;
	public static final String ERROR_USUARIO_YA_EXISTE="Atención! el usuario ya existe."+SALTOLINEA;
	public static final String DATO_INTRODUCIDO_ERRONEO="Atencion! El dato introducido no es correcto."+SALTOLINEA;
	public static final String DATO_NO_PUEDE_SER_NULO="Atención! El dato introducido no puede ser nulo."+SALTOLINEA;
	public static final String FECHA_INCORRECTA="Atención! formato de fecha incorrecto."+SALTOLINEA;
	public static final String DNI_NO_VALIDO="Atención! el NIF introducido no es válido o ya existe. Debe introducir los 9 dígitos del NIF sin puntos, comas, ni espacios (ejemplo: 00000000A)"+SALTOLINEA;
	public static final String CREA_ACCESO_KO="Atención! las contraseñas no son iguales. Inténtelo nuevamente."+SALTOLINEA;
	
	public static final String CERRAR_CONEXION_BBDD="Cerrada conexión gestor base de datos."+SALTOLINEA;
	
	
	public static final String SELECCIONAR_OPCION="Por favor, seleccione una opción:"+SALTOLINEA;
	public static final String PRIMER_CARACTER_OPCION_MENU="[";
	public static final String NUMERO_OPCION_INEXISTENTE="Atencion! Número de opción inexistente. Vuelva a intentarlo."+SALTOLINEA;
	
	public static final String DESEA_GUARDAR_CAMBIOS="¿Desea guardar los cambios (Sí/No)?"+SALTOLINEA;	
	public static final String CAMBIO_GUARDADO_OK="Cambios guardados correctamente."+SALTOLINEA;
	public static final String CAMBIO_GUARDADO_KO="Atención! no se pudieron guardar los cambios."+SALTOLINEA;
	public static final String REGISTRO_BORRADO_OK="Registro borrado correctamente."+SALTOLINEA;
	public static final String REGISTRO_NO_EXISTE_EN_BD="Atención! El registro no existe en la base de datos."+SALTOLINEA;
	public static final String REGISTRO_YA_EXISTE_EN_BD="Atención! El registro ya existe en la base de datos."+SALTOLINEA;
	public static final String TIPO_VEHICULO_NO_EXISTE="Atención! El tipo de vehículo no existe en la base de datos."+SALTOLINEA;;
	
	public static final List<String> DIAS_SEMANA=Arrays.asList("LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES");
	public static final List<String> TURNOS=Arrays.asList("MAÑANA", "TARDE");
	public static final List<String> SECCIONES_DEL_TALLER=Arrays.asList("CHAPA", "PINTURA", "MECÁNICA", "ELECTRICIDAD", "TAPICERÍA");



// mensajes cita
	public static final String CITA_WELCOME="Bienvenido al menu de citas";

	public static final String CITA_OPCIONES= 
	        "aqui puede"
			+ " 1.- Crear una cita"
			+ " 2.- Ver sus citas "
			+ " 3.- Modificar una cita"
			+ " 4.- Cancelar una cita"
			+ " 5.- Salir";
	
	public static final String CITA_OPCION_VALOR_INCORRECTO =  "Escoga una opcion valida"; 
	
	public static final String CITA_DESPEDIDA = "***********************";
	
	
	
	
	
}
