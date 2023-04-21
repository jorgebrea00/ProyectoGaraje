package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import main.Conexion;
import model.Cita;


public class CitaDao {

	private static String tabla="citas";



		public static void insert(Cita cita) {
			String ordenSql="insert into "+tabla+" values (?, ?, ?, ?, ?)"; 	 
			
			try (Connection co =  Conexion.conexionMySQL();)
			{
				PreparedStatement ps = co.prepareStatement(ordenSql);
						
				ps.setNull(1, 0);										// id auto-generado
				ps.setString(2, cita.getFechaHora().toString());
				ps.setInt(5, cita.getCabecerasDiagnostico().getId());
				ps.setInt(4, cita.getCliente().getId());
				ps.setInt(3, cita.getVehiculo().getId());
				ps.execute();	
				
			}catch (SQLException e){	
				
				//System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
				e.printStackTrace();
				
			}
		}


		public static ArrayList<LocalDate> LeePorFechaEnAdelante  (LocalDate localDate) {

			ArrayList<LocalDate> arrayList = new ArrayList<>();
			String ordenSql="select fecha_hora from "+tabla+ " where fecha_hora >= ?";
			try(Connection co =  Conexion.conexionMySQL();){	
				PreparedStatement ps=co.prepareStatement(ordenSql);
				ps.setString(1, localDate.toString());			
				ps.executeQuery();
				ResultSet rs  = ps.getResultSet();
				while (rs.next()) {
				LocalDate localDate2 = LocalDate.parse(rs.getString(1)); 
					arrayList.add(localDate2);
				}

				
				}catch(SQLException e){
					System.out.println(e);
		
				}
			return arrayList;

		}
		
/*
		public Login read  (String datoBuscado, String nombreCampo) {
			Login login=new Login();
			String ordenSql="select * from "+tabla+ " where "+nombreCampo+"= ?";
			try(PreparedStatement ps=conexion.prepareStatement(ordenSql)){	
				ps.setString(1, datoBuscado);
				ps.executeQuery();
				try (ResultSet rs=ps.getResultSet()){
					rs.next();
					login.setId(rs.getInt(1));
					login.setEmail(rs.getString(2));
					login.setPassHash(rs.getNString(3));
				}catch(SQLException e){
					login=null;
				}
			}catch (Exception e1){	
				System.out.println(Mensajes.ERRORDELECTURA+e1.getMessage());
			} finally {
				return  login;
			}
		}
		
		
		@Override
		public List<Login> getAll() {
			List <Login> listaResultado=new ArrayList<>();
			String ordenarPor="email";
			String ordenSql="select * from "+tabla+" order by "+ordenarPor+" asc";
			try(Statement stm=conexion.createStatement();
					ResultSet rs=stm.executeQuery(ordenSql)){	
				while (rs.next()) {
					Login login=new Login();
					login.setId(rs.getInt(1));
					login.setEmail((rs.getString(2)));
					login.setPassHash(rs.getNString(3));
					listaResultado.add(login);
				}
				return listaResultado;
			}catch (Exception e){		
				System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
				Conexion.desconectarBBDD(conexion);
				return Collections.emptyList();
			}
		}

		@Override
		public int update(Login Login) {
			String campo1="email";
			String campo2="pass_hash";
			String campoBusqueda="id";
			String ordenSql="update "+tabla+" set "+campo1+" = ?, "+campo2+" = ? where "+campoBusqueda+"=?";
			try(PreparedStatement ps=conexion.prepareStatement(ordenSql)){
				ps.setString(1, Login.getEmail());
				ps.setString(2, Login.getPassHash());
				ps.setInt(3, Login.getId());
				int numeroRegistrosAfectados=ps.executeUpdate();
				ps.close();
				return numeroRegistrosAfectados;
			}catch (Exception e){		
				System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
				Conexion.desconectarBBDD(conexion);
				return 0;
			}
		}

		@Override
		public int delete(Login login) {
			String campoBusqueda="id";
			String ordenSql="delete from "+tabla+" WHERE "+campoBusqueda+"=?";
			try(PreparedStatement ps=conexion.prepareStatement(ordenSql)){
				ps.setInt(1, login.getId());
				int numeroRegistrosAfectados=ps.executeUpdate(); 			
				ps.close();
				return numeroRegistrosAfectados;
			}catch (Exception e){		
				System.out.println(Mensajes.ERROR_BORRAR_REGISTRO + e.getMessage() );
				Conexion.desconectarBBDD(conexion);
				return 0;
			}		
		}*/
}
