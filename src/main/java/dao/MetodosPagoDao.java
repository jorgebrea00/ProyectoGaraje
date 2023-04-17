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
import model.MetodosPago;

public class MetodosPagoDao extends DaoPrincipal implements Dao<MetodosPago>{
	private String tabla="metodos_pago";

	public MetodosPagoDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}

	@Override
	public int insert(MetodosPago metodosPago) {
		String ordenSql=ordenSqlInsert(tabla);      
		
		try (PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setNull(1, 0);										// id auto-generado
			introduceEntityEnPs(2,metodosPago,ps).executeUpdate();
			ResultSet claveGenerada=ps.getGeneratedKeys();
			claveGenerada.next();									
			return claveGenerada.getInt(1); // Devuelve la id auto-generada
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, MetodosPago metodosPago, PreparedStatement ps) throws SQLException {
		// Introduce los datos de la Entity en el PreparedStatement. Se utiliza primerIndice para empezar en el 2 en Insert y en el 1 en Update.
		ps.setString(primerIndice,metodosPago.getDescripcion());
		return ps;
	}
	
	@Override
	public MetodosPago read  (int id) {
		MetodosPago metodosPago=new MetodosPago();
		String ordenSql="select * from "+tabla+ " where id= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, id);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				metodosPago=cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+id+" no existe en la base de datos.");
				metodosPago=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
			metodosPago=null;
		}
		return  metodosPago;
	}
	
	public MetodosPago cargaDatosEnEntity(ResultSet rs) throws SQLException {
		MetodosPago metodosPago=new MetodosPago();
		metodosPago.setId(rs.getInt(1));
		metodosPago.setDescripcion(rs.getString(2));
		return metodosPago;
	}
	
	public List<MetodosPago> read (String datoBuscado, String nombreCampo) {
		List<MetodosPago> listaResultado=new ArrayList<>();
		String ordenarPor1=nombreCampo;
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ? order by "+ordenarPor1+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					MetodosPago metodosPago=new MetodosPago();
					metodosPago=cargaDatosEnEntity(rs);
					listaResultado.add(metodosPago);
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
	public List<MetodosPago> getAll() {
		List <MetodosPago> listaResultado=new ArrayList<>();
		String ordenarPor1="descripcion";
		String ordenSql="select * from "+tabla+ " order by "+ordenarPor1+" desc";
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				MetodosPago metodosPago=new MetodosPago();
				metodosPago=cargaDatosEnEntity(rs);
				listaResultado.add(metodosPago);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}
	
	@Override
	public int update(MetodosPago metodosPago) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		String ordenSql;
		
		ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, metodosPago.getId());
			return introduceEntityEnPs(1, metodosPago, ps).executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}
	
	@Override
	public int delete(MetodosPago metodosPago) {
		String campoBusqueda="id";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, metodosPago.getId());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}
	
}
