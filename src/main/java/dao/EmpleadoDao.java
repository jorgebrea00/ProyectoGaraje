package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import estructuraBaseDeDatos.EstructuraBbdd;
import estructuraBaseDeDatos.Tabla;
import interfaces.Dao;
import mensajes.Mensajes;
import model.Empleado;

public class EmpleadoDao extends DaoPrincipal implements Dao<Empleado> {
	private String tabla="empleados";
	
	public EmpleadoDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}
	
	@Override
	public int insert(Empleado empleado) {	
		String ordenSql=ordenSqlInsert(tabla);
		
		try (PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setNull(1, 0);										// id auto-generado
			introduceEntityEnPs(2,empleado,ps).executeUpdate();
			ResultSet claveGenerada=ps.getGeneratedKeys();
			claveGenerada.next();									
			return claveGenerada.getInt(1); // Devuelve la id auto-generada
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, Empleado empleado, PreparedStatement ps) throws SQLException {
		// Introduce los datos de la Entity en el PreparedStatement. Se utiliza primerIndice para empezar en el 2 en Insert y en el 1 en Update.
		ps.setString(primerIndice,empleado.getDni());
		ps.setString(primerIndice+1,empleado.getNombre());
		ps.setString(primerIndice+2,empleado.getApellido1());
		ps.setString(primerIndice+3,empleado.getApellido2());
		ps.setString(primerIndice+4,empleado.getPuesto());
		ps.setString(primerIndice+5,empleado.getDepartamento());
		return ps;
	}
	
	
	@Override
	public Empleado read  (int id) {
		Empleado empleado=new Empleado();
		String ordenSql="select * from "+tabla+ " where id= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, id);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				empleado=cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+id+" no existe en la base de datos.");
				empleado=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
			empleado=null;
		}
		return  empleado;
	}
	
	public Empleado cargaDatosEnEntity(ResultSet rs) throws SQLException {
		Empleado empleado=new Empleado();
		empleado.setId(rs.getInt(1));
		empleado.setDni(rs.getString(2));
		empleado.setNombre(rs.getString(3));
		empleado.setApellido1(rs.getString(4));
		empleado.setApellido2(rs.getString(5));
		empleado.setPuesto(rs.getString(6));
		empleado.setDepartamento(rs.getString(7));
		return empleado;
	}

	public List<Empleado> read (String datoBuscado, String nombreCampo) {
		List<Empleado> listaResultado=new ArrayList<>();
		String ordenarPor1=nombreCampo;
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ? order by "+ordenarPor1+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					Empleado empleado=new Empleado();
					empleado=cargaDatosEnEntity(rs);
					listaResultado.add(empleado);
				}
				return listaResultado;
			}catch(SQLException e){
				return  Collections.emptyList();
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
			return  Collections.emptyList();
		} 
	}
	
	@Override
	public List<Empleado> getAll() {
		List <Empleado> listaResultado=new ArrayList<>();
		String ordenarPor1="apellido1";
		String ordenarPor2="apellido2";
		String ordenarPor3="nombre";
		String ordenSql="select * from "+tabla+ " order by "+ordenarPor1+","+ordenarPor2+","+ordenarPor3+" desc";
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				Empleado empleado=new Empleado();
				empleado=cargaDatosEnEntity(rs);
				listaResultado.add(empleado);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}
	
	@Override
	public int update(Empleado empleado) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		String ordenSql;
		
		ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, empleado.getId());
			return introduceEntityEnPs(1, empleado, ps).executeUpdate();
//			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}
	
	@Override
	public int delete(Empleado empleado) {
		String campoBusqueda="id";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, empleado.getId());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}
	
}
