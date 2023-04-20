package dao;

import interfaces.Dao;

import mensajes.Mensajes;
import model.TiposVehiculo;

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


public class TiposVehiculoDao extends DaoPrincipal implements Dao<TiposVehiculo> {
private String tabla="tipos_vehiculos";

	public TiposVehiculoDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}

	@Override
	public int insert(TiposVehiculo tv) {
		String ordenSql=ordenSqlInsert(tabla);
		try (PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql, Statement.RETURN_GENERATED_KEYS);
				) {
			ps.setNull(1, 0);										// id auto-generado
			introduceEntityEnPs(2,tv,ps).executeUpdate();
			ResultSet claveGenerada=ps.getGeneratedKeys();
			claveGenerada.next();									
			return claveGenerada.getInt(1); // Devuelve la id auto-generada
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, TiposVehiculo tv, PreparedStatement ps) throws SQLException {
		
		ps.setString(primerIndice, tv.getTipo());		
		return ps;		
	}
	
	@Override
	public TiposVehiculo read  (int id) {
		TiposVehiculo tv=new TiposVehiculo();
		String ordenSql="select * from "+tabla+ " where id= ?";
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, id);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				tv=cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+id+" no existe en la base de datos.");
				tv=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
		} 
		return  tv;
	}
	
	public TiposVehiculo cargaDatosEnEntity(ResultSet rs) throws SQLException {
		
		TiposVehiculo tv=new TiposVehiculo();
		tv.setId(rs.getInt(1));
		tv.setTipo(rs.getString(2));		
		return tv;
	}
	
	public TiposVehiculo read  (String datoBuscado, String nombreCampo) {
		TiposVehiculo tv=new TiposVehiculo();
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ?";
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				tv=cargaDatosEnEntity(rs);
			}catch(SQLException e){
				tv=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
		}
		return  tv;
	}
	
	@Override
	public List<TiposVehiculo> getAll() {
		List <TiposVehiculo> listaResultado=new ArrayList<>();
		String ordenarPor="tipo";
		String ordenSql="select * from "+tabla+" order by "+ordenarPor+" asc";
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				TiposVehiculo tv=new TiposVehiculo();
				tv=cargaDatosEnEntity(rs);
				listaResultado.add(tv);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}

	@Override
	public int update(TiposVehiculo tv) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		
		String ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, tv.getId());
			return introduceEntityEnPs(1, tv, ps).executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}

	@Override
	public int delete(TiposVehiculo tv) {
		String campoBusqueda="id";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, tv.getId());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}
}
