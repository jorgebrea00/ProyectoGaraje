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
import model.PreferenciasCliente;


public class PreferenciasClientesDao extends DaoPrincipal implements Dao<PreferenciasCliente> {
	private String tabla="preferencias_clientes";

	public PreferenciasClientesDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}
	
	@Override
	public int insert(PreferenciasCliente preferenciasCliente) {	
		String ordenSql=ordenSqlInsert(tabla);
		
		try (PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql)) {
			ps.setInt(1,preferenciasCliente.getIdCliente() );
			
			introduceEntityEnPs(2, preferenciasCliente, ps);
//			if(preferenciasCliente.getFechaHora()!=null) ps.setTimestamp(2, new Timestamp(preferenciasCliente.getFechaHora().getTime())); else ps.setTimestamp(2, null); 
//			ps.setString(3,preferenciasCliente.getDiaSemana());
//			ps.setString(4,preferenciasCliente.getTurno());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	
public PreparedStatement introduceEntityEnPs(int primerIndice, PreferenciasCliente preferenciasCliente, PreparedStatement ps) throws SQLException {
	if(preferenciasCliente.getFechaHora()!=null) ps.setTimestamp(primerIndice, new Timestamp(preferenciasCliente.getFechaHora().getTime())); else ps.setTimestamp(primerIndice, null); 
	ps.setString(primerIndice+1,preferenciasCliente.getDiaSemana());
	ps.setString(primerIndice+2,preferenciasCliente.getTurno());
		return ps;
	}

	@Override
	public PreferenciasCliente read  (int id) {
		
		return  null;
	}
	
	public List<PreferenciasCliente> read (String datoBuscado, String nombreCampo) {
		List<PreferenciasCliente> listaResultado=new ArrayList<>();
		String ordenarPor="id_cliente";
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ? order by "+ordenarPor+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					PreferenciasCliente preferenciasCliente=new PreferenciasCliente();
					preferenciasCliente=cargaDatosEnEntity(rs);
					listaResultado.add(preferenciasCliente);
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
	
	public PreferenciasCliente cargaDatosEnEntity(ResultSet rs) throws SQLException {
		
		PreferenciasCliente preferenciasCliente=new PreferenciasCliente();
		preferenciasCliente.setIdCliente(rs.getInt(1));
		if (rs.getDate(2)==null) preferenciasCliente.setFechaHora(null); else preferenciasCliente.setFechaHora(new Date(rs.getTimestamp(2).getTime())); 
		preferenciasCliente.setDiaSemana(rs.getString(3));
		preferenciasCliente.setTurno(rs.getString(4));
		return preferenciasCliente;
	}
	
	public List<PreferenciasCliente> readIdCliente (int idCliente) {
		List<PreferenciasCliente> listaResultado=new ArrayList<>();
		String nombreCampo="id_cliente";
		String ordenarPor="fecha_hora";
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ? order by "+ordenarPor+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, idCliente);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					PreferenciasCliente preferenciasCliente=new PreferenciasCliente();
					preferenciasCliente=cargaDatosEnEntity(rs);
					listaResultado.add(preferenciasCliente);
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
	public List<PreferenciasCliente> getAll() {
		List<PreferenciasCliente> listaResultado=new ArrayList<>();
		String ordenarPor="id_cliente";
		String ordenSql="select * from "+tabla+" order by "+ordenarPor+" desc";
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				PreferenciasCliente preferenciasCliente=new PreferenciasCliente();
				preferenciasCliente=cargaDatosEnEntity(rs);
				listaResultado.add(preferenciasCliente);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}

	@Override
	public int update(PreferenciasCliente preferenciasCliente) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		
		String ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, preferenciasCliente.getIdCliente());
			return introduceEntityEnPs(1, preferenciasCliente, ps).executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}

	@Override
	public int delete(PreferenciasCliente preferenciasCliente) {
		String campoBusqueda="id_cliente";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, preferenciasCliente.getIdCliente());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}

}
