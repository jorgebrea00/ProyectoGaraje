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
import model.InventariosPieza;

public class InventariosPiezaDao extends DaoPrincipal implements Dao<InventariosPieza> {
	private String tabla="inventarios_piezas";
	
	public InventariosPiezaDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}
	
	@Override
	public int insert(InventariosPieza inventariosPieza) {	
		String ordenSql=ordenSqlInsert(tabla);
		
		try (PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setNull(1, 0);										// id auto-generado
			introduceEntityEnPs(2,inventariosPieza,ps).executeUpdate();
			ResultSet claveGenerada=ps.getGeneratedKeys();
			claveGenerada.next();									
			return claveGenerada.getInt(1); // Devuelve la id auto-generada
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, InventariosPieza inventariosPieza, PreparedStatement ps) throws SQLException {
		// Introduce los datos de la Entity en el PreparedStatement. Se utiliza primerIndice para empezar en el 2 en Insert y en el 1 en Update.
		ps.setString(primerIndice,inventariosPieza.getNombre());
		ps.setString(primerIndice+1,inventariosPieza.getDescripcion());
		ps.setInt(primerIndice+2,inventariosPieza.getStockTotal());
		ps.setFloat(primerIndice+3,inventariosPieza.getPrecioVenta());
		return ps;
	}

	@Override
	public InventariosPieza read  (int id) {
		InventariosPieza inventariosPieza=new InventariosPieza();
		String ordenSql="select * from "+tabla+ " where id= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, id);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				inventariosPieza=cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+id+" no existe en la base de datos.");
				inventariosPieza=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
			inventariosPieza=null;
		}
		return  inventariosPieza;
	}
	
	public InventariosPieza cargaDatosEnEntity(ResultSet rs) throws SQLException {
		InventariosPieza inventariosPieza=new InventariosPieza();
		inventariosPieza.setId(rs.getInt(1));
		inventariosPieza.setNombre(rs.getString(2));
		inventariosPieza.setDescripcion(rs.getString(3));
		inventariosPieza.setStockTotal(rs.getInt(4));
		inventariosPieza.setPrecioVenta(rs.getFloat(5));
		return inventariosPieza;
	}
	
	public List<InventariosPieza> read (String datoBuscado, String nombreCampo) {
		List<InventariosPieza> listaResultado=new ArrayList<>();
		String ordenarPor1=nombreCampo;
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ? order by "+ordenarPor1+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					InventariosPieza inventariosPieza=new InventariosPieza();
					inventariosPieza=cargaDatosEnEntity(rs);
					listaResultado.add(inventariosPieza);
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
	public List<InventariosPieza> getAll() {
		List <InventariosPieza> listaResultado=new ArrayList<>();
		String ordenarPor1="nombre";
		String ordenSql="select * from "+tabla+ " order by "+ordenarPor1+" desc";
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				InventariosPieza inventariosPieza=new InventariosPieza();
				inventariosPieza=cargaDatosEnEntity(rs);
				listaResultado.add(inventariosPieza);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}
	
	@Override
	public int update(InventariosPieza inventariosPieza) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		String ordenSql;
		
		ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, inventariosPieza.getId());
			return introduceEntityEnPs(1, inventariosPieza, ps).executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}
	
	@Override
	public int delete(InventariosPieza inventariosPieza) {
		String campoBusqueda="id";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, inventariosPieza.getId());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}
}
