package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import estructuraBaseDeDatos.EstructuraBbdd;
import estructuraBaseDeDatos.Tabla;
import interfaces.Dao;
import mensajes.Mensajes;
import model.CabecerasDiagnostico;

import model.Vehiculo;

public class CabeceraDiagnosticoDao extends DaoPrincipal implements Dao<CabecerasDiagnostico> {
	
	private String tabla="cabeceras_diagnosticos";
	
	public CabeceraDiagnosticoDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}

	@Override
	public int insert(CabecerasDiagnostico cabecerasDiagnostico) {
		String ordenSql=ordenSqlInsert(tabla);
		
		try (PreparedStatement ps= getConexionAbierta().prepareStatement(ordenSql, Statement.RETURN_GENERATED_KEYS)){
			ps.setNull(1, 0); 
			introduceEntityEnPs(2,cabecerasDiagnostico, ps).executeUpdate();
			ResultSet claveGenerada=ps.getGeneratedKeys();
			claveGenerada.next();
			return claveGenerada.getInt(1); //Devuelve la id auto-generada
		}catch(Exception e){
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, CabecerasDiagnostico cabecerasDiagnostico, PreparedStatement ps) throws SQLException {
		
		if(cabecerasDiagnostico.getFechaHora()!=null) ps.setTimestamp(primerIndice, new Timestamp(cabecerasDiagnostico.getFechaHora().getTime())); else ps.setTimestamp(primerIndice, null);  
		ps.setInt(primerIndice+1, cabecerasDiagnostico.getVehiculo().getId());		
		return ps;
		
	}
	
	@Override
	public CabecerasDiagnostico read (int id) {
		CabecerasDiagnostico cabecerasDiagnostico=new CabecerasDiagnostico();
		String ordenSql="select * from " + tabla+ " where id= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, id);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				cabecerasDiagnostico = cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+id+" no existe en la base de datos.");
				cabecerasDiagnostico=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
		} 
		return  cabecerasDiagnostico;
	}
			
	public CabecerasDiagnostico cargaDatosEnEntity(ResultSet rs) throws SQLException {
		
		CabecerasDiagnostico cabecerasDiagnostico = new CabecerasDiagnostico();	
		cabecerasDiagnostico.setId(rs.getInt(1));
		if (rs.getDate(2)==null) cabecerasDiagnostico.setFechaHora(null); else cabecerasDiagnostico.setFechaHora(new Date(rs.getTimestamp(2).getTime())); 
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(rs.getInt(3));
		cabecerasDiagnostico.setVehiculo(vehiculo);
		return cabecerasDiagnostico;
	}	
	
	
	public List<CabecerasDiagnostico> read (String datoBuscado, String nombreCampo) {
		List<CabecerasDiagnostico> listaResultado=new ArrayList<>();
		String ordenarPor="fecha_hora";
		String ordenSql="select * from " +tabla+ " where " +nombreCampo+" = ? order by "+ordenarPor+" desc";
		
		try(PreparedStatement ps= getConexionAbierta().prepareStatement(ordenSql)){
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try(ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					CabecerasDiagnostico cabecerasDiagnostico =new CabecerasDiagnostico();
					cabecerasDiagnostico=cargaDatosEnEntity(rs);
					listaResultado.add(cabecerasDiagnostico);
				}
				return listaResultado;
			}catch(SQLException e) {
				return Collections.emptyList();
			}
		}catch (Exception e1) {
			System.out.println( Mensajes.ERRORDELECTURA+e1.getMessage());
			return Collections.emptyList();
		}
	}	
	//leer por dato foraneo
	public List<CabecerasDiagnostico> readIdVehiculo (int idVehiculo) {
		List<CabecerasDiagnostico> listaResultado=new ArrayList<>();
		String nombreCampo="id_vehiculo";
		String ordenarPor="fecha_hora";
		String ordenSql="select * from " +tabla+ " where "+nombreCampo+"= ? order by "+ordenarPor+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, idVehiculo);
			ps.executeQuery();
			try(ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					CabecerasDiagnostico cabecerasDiagnostico=new CabecerasDiagnostico();
					cabecerasDiagnostico=cargaDatosEnEntity(rs);
					listaResultado.add(cabecerasDiagnostico);
				}
				return listaResultado;
			}catch (Exception e) {
				return Collections.emptyList();
			}
		}catch (Exception e1) {
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
			return Collections.emptyList();
		}
		
	}
		
	@Override
	public List<CabecerasDiagnostico> getAll(){
		List <CabecerasDiagnostico> listaResultado=new ArrayList<>();
		String ordenarPor ="id";
		String ordenSql="select * from "+tabla+" order by " +ordenarPor+" asc";
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs =stm.executeQuery(ordenSql)){
			while (rs.next()) {
				CabecerasDiagnostico cabecerasDiagnostico=new CabecerasDiagnostico();
				cabecerasDiagnostico=cargaDatosEnEntity(rs);
				listaResultado.add(cabecerasDiagnostico);
			}
			return listaResultado;
			
		}catch (Exception e) {
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}
	
	@Override
	public int update (CabecerasDiagnostico cabecerasDiagnostico) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		
		String ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, cabecerasDiagnostico.getId());
			return introduceEntityEnPs(1, cabecerasDiagnostico, ps).executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}
	@Override
	public int delete(CabecerasDiagnostico cabecerasDiagnostico) {
		String campoBusqueda="id";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, cabecerasDiagnostico.getId());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}
}	