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
import model.CabecerasDiagnostico;
import model.Iva;
import model.LineasDiagnostico;

public class LineasDiagnosticoDao extends DaoPrincipal implements Dao<LineasDiagnostico>{
	private String tabla="lineas_diagnosticos";

	public LineasDiagnosticoDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}
	
	@Override
	public int insert(LineasDiagnostico lineasDiagnostico) {
		String ordenSql=ordenSqlInsert(tabla);
		//System.out.println("Orden Sql " +ordenSql);
		
		try(PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql, Statement.RETURN_GENERATED_KEYS)){
			ps.setNull(1, 0);
			introduceEntityEnPs(2,lineasDiagnostico, ps).executeUpdate();
			ResultSet claveGenerada =ps.getGeneratedKeys();
			claveGenerada.next();
			return claveGenerada.getInt(1);			
		}catch (Exception e) {
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, LineasDiagnostico lineasDiagnostico, PreparedStatement ps) throws SQLException {
			
		
		ps.setInt(primerIndice, lineasDiagnostico.getCabecerasDiagnostico().getId());
		ps.setInt(primerIndice+1, lineasDiagnostico.getIva().getId());
		
		ps.setInt(primerIndice+2, lineasDiagnostico.getNumeroLinea());
		ps.setString(primerIndice+3, lineasDiagnostico.getConcepto());
		ps.setInt(primerIndice+4, lineasDiagnostico.getCantidad());
		ps.setInt(primerIndice+5, lineasDiagnostico.getCodigoArticulo());
		return ps;
	}
		
	@Override
	public LineasDiagnostico read (int id) {
		LineasDiagnostico lineasDiagnostico=new LineasDiagnostico();
		String ordenSql="select * from " + tabla+ " where id= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, id);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				lineasDiagnostico = cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+id+" no existe en la base de datos.");
				lineasDiagnostico=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
		} 
		return  lineasDiagnostico;
	}
	public LineasDiagnostico cargaDatosEnEntity(ResultSet rs) throws SQLException {
		LineasDiagnostico lineasDiagnostico = new LineasDiagnostico();	
		
		lineasDiagnostico.setId(rs.getInt(1));
		
		CabecerasDiagnostico CabecerasDiagnosticoId=new CabecerasDiagnostico();
		CabecerasDiagnosticoId.setId(rs.getInt(2));
		lineasDiagnostico.setCabecerasDiagnostico(CabecerasDiagnosticoId);
		
		Iva IvaId=new Iva();
		IvaId.setId(rs.getInt(3));
		lineasDiagnostico.setIva(IvaId);
		
		lineasDiagnostico.setNumeroLinea(rs.getInt(4));
		lineasDiagnostico.setConcepto(rs.getString(5));
		lineasDiagnostico.setCantidad(rs.getInt(6));
		lineasDiagnostico.setCodigoArticulo(rs.getInt(7));
	
		return lineasDiagnostico;
	
	}
	
	public List<LineasDiagnostico> read (String datoBuscado, String nombreCampo) {
		List<LineasDiagnostico> listaResultado=new ArrayList<>();
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					LineasDiagnostico lineasDiagnostico=new LineasDiagnostico();
					lineasDiagnostico=cargaDatosEnEntity(rs);
					listaResultado.add(lineasDiagnostico);
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
	
	//leer por datos foraneos	id_cabecera_diagnostico" e "id_iva"
	
	public List<LineasDiagnostico> readIdCabeceraDiagnostico (int idCabeceraDiagnostico) {
		List<LineasDiagnostico> listaResultado=new ArrayList<>();
		String nombreCampo="id_cabecera_diagnostico";
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ? ";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, idCabeceraDiagnostico);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					LineasDiagnostico lineasDiagnostico=new LineasDiagnostico();
					lineasDiagnostico=cargaDatosEnEntity(rs);
					listaResultado.add(lineasDiagnostico);
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
	
	public List<LineasDiagnostico> readIdIva (int idIva) {
		List<LineasDiagnostico> listaResultado=new ArrayList<>();
		String nombreCampo="id_iva";
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+" =?";
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, idIva);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					LineasDiagnostico lineasDiagnostico=new LineasDiagnostico();
					lineasDiagnostico=cargaDatosEnEntity(rs);
					listaResultado.add(lineasDiagnostico);
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
	public List<LineasDiagnostico> getAll() {
		List <LineasDiagnostico> listaResultado=new ArrayList<>();
		String ordenarPor1="codigo_articulo";
		String ordenarPor2="numero_linea";
		
		String ordenSql="select * from "+tabla+ " order by "+ordenarPor1+","+ordenarPor2+" desc";
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				LineasDiagnostico lineasDiagnostico=new LineasDiagnostico();
				lineasDiagnostico =cargaDatosEnEntity(rs);
				listaResultado.add(lineasDiagnostico);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}
	
	@Override
	public int update(LineasDiagnostico lineasDiagnostico) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		String ordenSql;
		
		ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, lineasDiagnostico.getId());
			return introduceEntityEnPs(1, lineasDiagnostico, ps).executeUpdate();
//			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}
	
	@Override
	public int delete(LineasDiagnostico lineasDiagnostico) {
		String campoBusqueda="id";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, lineasDiagnostico.getId());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}
	
}
	
