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
import model.TotalesDiagnostico;


public class TotalesDiagnosticoDao extends DaoPrincipal implements Dao<TotalesDiagnostico> {
	private String tabla = "totales_diagnosticos";
	
	public TotalesDiagnosticoDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}
	
	@Override
	public int insert(TotalesDiagnostico totalesDiagnostico) {
		String ordenSql=ordenSqlInsert(tabla);
		
		try(PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, totalesDiagnostico.getIdCabeceraDiagnostico());
			introduceEntityEnPs(2,totalesDiagnostico,ps).executeUpdate();
			return totalesDiagnostico.getIdCabeceraDiagnostico();			
		}catch (Exception e) {
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, TotalesDiagnostico totalesDiagnostico, PreparedStatement ps) throws SQLException {	
		
		ps.setInt(primerIndice, totalesDiagnostico.getTotalPiezas());
		ps.setFloat(primerIndice+1, totalesDiagnostico.getTotalManoObra());
		ps.setFloat(primerIndice+2, totalesDiagnostico.getTiempoTotal());
		return ps;
	}

	@Override
	public TotalesDiagnostico read (int idCabeceraDiagnostico) {
	TotalesDiagnostico totalesDiagnostico=new TotalesDiagnostico();
	String ordenSql="select * from " + tabla+ " where id_cabecera_diagnostico= ?";
	
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, idCabeceraDiagnostico);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				totalesDiagnostico = cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+idCabeceraDiagnostico+" no existe en la base de datos.");
				totalesDiagnostico=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
		} 
		return  totalesDiagnostico;
	}

	public TotalesDiagnostico cargaDatosEnEntity(ResultSet rs) throws SQLException {
		TotalesDiagnostico totalesDiagnostico = new TotalesDiagnostico();	
	
		totalesDiagnostico.setIdCabeceraDiagnostico(rs.getInt(1));
		totalesDiagnostico.setTotalPiezas(rs.getInt(2));
		totalesDiagnostico.setTotalManoObra(rs.getFloat(3));
		totalesDiagnostico.setTiempoTotal(rs.getFloat(4));
	
		return totalesDiagnostico;

	}
	
	public List<TotalesDiagnostico> read (String datoBuscado, String nombreCampo) {
		List<TotalesDiagnostico> listaResultado=new ArrayList<>();
		
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					TotalesDiagnostico totalesDiagnostico=new TotalesDiagnostico();
					totalesDiagnostico=cargaDatosEnEntity(rs);
					listaResultado.add(totalesDiagnostico);
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
	public List<TotalesDiagnostico> getAll() {
		List <TotalesDiagnostico> listaResultado=new ArrayList<>();
				
		String ordenSql="select * from "+tabla;
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				TotalesDiagnostico totalesDiagnostico=new TotalesDiagnostico();
				totalesDiagnostico =cargaDatosEnEntity(rs);
				listaResultado.add(totalesDiagnostico);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public int update(TotalesDiagnostico totalesDiagnostico) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		String ordenSql;
		
		ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, totalesDiagnostico.getIdCabeceraDiagnostico());
			return introduceEntityEnPs(1, totalesDiagnostico, ps).executeUpdate();
//			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}
	
	
	@Override
	public int delete(TotalesDiagnostico totalesDiagnostico) {
		String campoBusqueda="id_cabecera_diagnostico";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, totalesDiagnostico.getIdCabeceraDiagnostico());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}
}
