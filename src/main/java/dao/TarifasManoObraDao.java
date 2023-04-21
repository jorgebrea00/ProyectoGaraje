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

import model.TarifasManoObra;
import model.TiposVehiculo;

public class TarifasManoObraDao extends DaoPrincipal implements Dao<TarifasManoObra> {
	private String tabla="tarifas_mano_obra";

	public TarifasManoObraDao(EstructuraBbdd estructuraTablas, Connection conexion) {
		super(estructuraTablas, conexion);
	}
	
	@Override
	public int insert(TarifasManoObra tarifa) {	
		String ordenSql=ordenSqlInsert(tabla);
		
		try (PreparedStatement ps = getConexionAbierta().prepareStatement(ordenSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setNull(1, 0);										// id auto-generado
			introduceEntityEnPs(2,tarifa,ps).executeUpdate();
			ResultSet claveGenerada=ps.getGeneratedKeys();
			claveGenerada.next();									
			return claveGenerada.getInt(1); // Devuelve la id auto-generada
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			return 0;
		}
	}
	
	public PreparedStatement introduceEntityEnPs(int primerIndice, TarifasManoObra tarifa, PreparedStatement ps) throws SQLException {
		
		ps.setString(primerIndice, tarifa.getSeccion());
		ps.setInt(primerIndice+1, tarifa.getTiposVehiculo().getId());
		ps.setFloat(primerIndice+2, tarifa.getPrecioHora());
		return ps;
	}
	
	@Override
	public TarifasManoObra read  (int id) {
		TarifasManoObra tarifa=new TarifasManoObra();
		String ordenSql="select * from "+tabla+ " where id= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, id);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				rs.next();
				tarifa=cargaDatosEnEntity(rs);
			}catch(SQLException e){
				System.out.println("El id "+id+" no existe en la base de datos.");
				tarifa=null;
			}
		}catch (Exception e1){	
			System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
			tarifa=null;
		}
		return  tarifa;
	}
	
	public TarifasManoObra cargaDatosEnEntity(ResultSet rs) throws SQLException {
		TarifasManoObra tarifa=new TarifasManoObra();
		tarifa.setId(rs.getInt(1));
		tarifa.setSeccion(rs.getString(2));
		TiposVehiculo tipoVehiculo=new TiposVehiculo();
		tipoVehiculo.setId(rs.getInt(3));
		tarifa.setTiposVehiculo(tipoVehiculo);
		tarifa.setPrecioHora(rs.getFloat(4));
		return tarifa;
	}
	
	public List<TarifasManoObra> read (String datoBuscado, String nombreCampo) {
		List<TarifasManoObra> listaResultado=new ArrayList<>();
		String ordenarPor1=nombreCampo;
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ? order by "+ordenarPor1+" desc";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setString(1, datoBuscado);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					TarifasManoObra tarifa=new TarifasManoObra();
					tarifa=cargaDatosEnEntity(rs);
					listaResultado.add(tarifa);
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
	
	public List<TarifasManoObra> readIdLogin (int idTipoVehiculo) {
		List<TarifasManoObra> listaResultado=new ArrayList<>();
		String nombreCampo="id_tipo_vehiculo";
		String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){	
			ps.setInt(1, idTipoVehiculo);
			ps.executeQuery();
			try (ResultSet rs=ps.getResultSet()){
				while (rs.next()) {
					TarifasManoObra tarifa=new TarifasManoObra();
					tarifa=cargaDatosEnEntity(rs);
					listaResultado.add(tarifa);
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
	public List<TarifasManoObra> getAll() {
		List <TarifasManoObra> listaResultado=new ArrayList<>();
		String ordenarPor1="seccion";
		
		String ordenSql="select * from "+tabla+ " order by "+ordenarPor1+" desc";
		
		try(Statement stm=getConexionAbierta().createStatement();
				ResultSet rs=stm.executeQuery(ordenSql)){	
			while (rs.next()) {
				TarifasManoObra tarifa=new TarifasManoObra();
				tarifa=cargaDatosEnEntity(rs);
				listaResultado.add(tarifa);
			}
			return listaResultado;
		}catch (Exception e){		
			System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
			return Collections.emptyList();
		}
	}
	
	@Override
	public int update(TarifasManoObra tarifa) {
		Tabla tablaBuscada;
		int numeroCamposEnTabla;
		String ordenSql;
		
		ordenSql=ordenSqlUpdate(tabla);
		tablaBuscada=getEstructuraTablas().buscaTabla(tabla);
		numeroCamposEnTabla=tablaBuscada.getCampos().size();
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(numeroCamposEnTabla, tarifa.getId());
			return introduceEntityEnPs(1, tarifa, ps).executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
			return 0;
		}
	}
	
	@Override
	public int delete(TarifasManoObra tarifa) {
		String campoBusqueda="id";
		String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
		
		try(PreparedStatement ps=getConexionAbierta().prepareStatement(ordenSql)){
			ps.setInt(1, tarifa.getId());
			return ps.executeUpdate();
		}catch (Exception e){		
			System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
			return 0;
		}		
	}

}
