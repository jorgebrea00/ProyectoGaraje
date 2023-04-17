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
import model.Iva;



public class IvaDao extends DaoPrincipal implements Dao<Iva>{
	private String tabla="ivas";
	
	public IvaDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}
	
	@Override
	public int insert(Iva iva) {
		String ordenSql=ordenSqlInsert(tabla);      
		
		try (PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setNull(1, 0);										// id auto-generado
			introduceEntityEnPs(2,iva,ps).executeUpdate();
			ResultSet claveGenerada=ps.getGeneratedKeys();
			claveGenerada.next();									
			return claveGenerada.getInt(1); // Devuelve la id auto-generada
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, Iva iva, PreparedStatement ps) throws SQLException {
		// Introduce los datos de la Entity en el PreparedStatement. Se utiliza primerIndice para empezar en el 2 en Insert y en el 1 en Update.
		ps.setFloat(primerIndice,iva.getTipoIva());
		return ps;
	}
	
	@Override
	public Iva read  (int id) {
		Iva iva=new Iva();
		String ordenSql="select * from "+tabla+ " where id= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, id);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				iva=cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+id+" no existe en la base de datos.");
				iva=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
			iva=null;
		}
		return  iva;
	}

	public Iva cargaDatosEnEntity(ResultSet rs) throws SQLException {
		Iva iva=new Iva();
		iva.setId(rs.getInt(1));
		iva.setTipoIva(rs.getFloat(2));
		return iva;
	}
	
	public List<Iva> read (String datoBuscado, String nombreCampo) {
		List<Iva> listaResultado=new ArrayList<>();
		String ordenarPor1=nombreCampo;
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ? order by "+ordenarPor1+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					Iva iva=new Iva();
					iva=cargaDatosEnEntity(rs);
					listaResultado.add(iva);
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
	
	public List<Iva> read (String datoBuscado) {
		List<Iva> listaResultado=new ArrayList<>();
		String nombreCampo="tipo_iva";
		String ordenarPor1=nombreCampo;
		String ordenSql="select * from "+tabla+ " where round("+nombreCampo+",2)= ? order by "+ordenarPor1+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					Iva iva=new Iva();
					iva=cargaDatosEnEntity(rs);
					listaResultado.add(iva);
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
	public List<Iva> getAll() {
		List <Iva> listaResultado=new ArrayList<>();
		String ordenarPor1="tipo_iva";
		String ordenSql="select * from "+tabla+ " order by "+ordenarPor1+" desc";
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				Iva iva=new Iva();
				iva=cargaDatosEnEntity(rs);
				listaResultado.add(iva);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}
	
	@Override
	public int update(Iva iva) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		String ordenSql;
		
		ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, iva.getId());
			return introduceEntityEnPs(1, iva, ps).executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}
	
	@Override
	public int delete(Iva iva) {
		String campoBusqueda="id";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, iva.getId());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}
}
