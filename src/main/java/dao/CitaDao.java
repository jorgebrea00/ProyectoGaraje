package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import main.Conexion;
import menus.Menu;
import model.Cita;
import model.Cliente;

public class CitaDao {

	private static String tabla = "citas";

	public static void insert(Cita cita) {
		String ordenSql = "insert into " + tabla + " values (?, ?, ?, ?, ?)";

		try (Connection co = Conexion.conexionMySQL();) {
			PreparedStatement ps = co.prepareStatement(ordenSql);

			ps.setNull(1, 0); // id auto-generado
			ps.setString(2, cita.getFechaHora().toString());
			ps.setInt(5, cita.getCabecerasDiagnostico().getId());
			ps.setInt(4, cita.getCliente().getId());
			ps.setInt(3, cita.getVehiculo().getId());
			ps.execute();

		} catch (SQLException e) {

			// System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage());
			e.printStackTrace();

		}
	}

	public static ArrayList<LocalDate> LeePorFechaEnAdelante(LocalDate localDate) {

		ArrayList<LocalDate> arrayList = new ArrayList<>();
		String ordenSql = "select fecha_hora from " + tabla + " where fecha_hora >= ?";
		try (Connection co = Conexion.conexionMySQL();) {
			PreparedStatement ps = co.prepareStatement(ordenSql);
			ps.setString(1, localDate.toString());
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				LocalDate localDate2 = LocalDate.parse(rs.getString(1));
				arrayList.add(localDate2);
			}

		} catch (SQLException e) {
			System.out.println(e);

		}
		return arrayList;

	}

	public static ArrayList<Cita> readOneCliente(Cliente cliente) {

		ArrayList<Cita> arrayListCita = new ArrayList<>();

		String ordenSql = "select * from " + tabla + " where " + "id_cliente" + "= ?";

		try (Connection co = Conexion.conexionMySQL();) {

			PreparedStatement ps = co.prepareStatement(ordenSql);
			ps.setInt(1, cliente.getId());
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				Cita cita = new Cita();
				cita.setId(rs.getInt(1));
				cita.setFechaHora(LocalDate.parse(rs.getString(2)));
				// no puedo avanzar por cabecera diasnostico
				cita.setVehiculo(new VehiculoDao(Menu.getEstructuraTablas(), co).read(rs.getInt(3)));
				cita.setCliente(cliente);
				cita.setCabecerasDiagnostico(
						new CabeceraDiagnosticoDao(Menu.getEstructuraTablas(), co).read(rs.getInt(5)));

				arrayListCita.add(cita);
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return arrayListCita;
	}

	public static int delete(LocalDate localDate) {

		String ordenSql = "delete from " + tabla + " WHERE " + "fecha_hora" + "=?";

		try (Connection co = Conexion.conexionMySQL();) {

			PreparedStatement ps = co.prepareStatement(ordenSql);
			ps.setString(1, localDate.toString());

			int numeroRegistrosAfectados = ps.executeUpdate();
			ps.close();
			return numeroRegistrosAfectados;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	public static int update(LocalDate localDate , Cliente cliente) {
	
		String ordenSql = "update " + tabla + " set " + "fecha_hora" + " = ? " + " where " + "id_cliente"
				+ "=?";
		try (Connection co = Conexion.conexionMySQL();){
			
		PreparedStatement ps = co.prepareStatement(ordenSql); 

			ps.setString(1, localDate.toString());
			ps.setInt(2, cliente.getId());
			int numeroRegistrosAfectados = ps.executeUpdate();
			ps.close();
			return numeroRegistrosAfectados;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	/*
	 * 
	 * @Override public List<Login> getAll() { List <Login> listaResultado=new
	 * ArrayList<>(); String ordenarPor="email"; String
	 * ordenSql="select * from "+tabla+" order by "+ordenarPor+" asc"; try(Statement
	 * stm=conexion.createStatement(); ResultSet rs=stm.executeQuery(ordenSql)){
	 * while (rs.next()) { Login login=new Login(); login.setId(rs.getInt(1));
	 * login.setEmail((rs.getString(2))); login.setPassHash(rs.getNString(3));
	 * listaResultado.add(login); } return listaResultado; }catch (Exception e){
	 * System.out.println(Mensajes.ERRORDELECTURA + e.toString() );
	 * Conexion.desconectarBBDD(conexion); return Collections.emptyList(); } }
	 * 
	 * @Override public int update(Login Login) { String campo1="email"; String
	 * campo2="pass_hash"; String campoBusqueda="id"; String
	 * ordenSql="update "+tabla+" set "+campo1+" = ?, "+campo2+" = ? where "
	 * +campoBusqueda+"=?"; try(PreparedStatement
	 * ps=conexion.prepareStatement(ordenSql)){ ps.setString(1, Login.getEmail());
	 * ps.setString(2, Login.getPassHash()); ps.setInt(3, Login.getId()); int
	 * numeroRegistrosAfectados=ps.executeUpdate(); ps.close(); return
	 * numeroRegistrosAfectados; }catch (Exception e){
	 * System.out.println(Mensajes.ERROR_GRABAR_REGISTRO + e.getMessage() );
	 * Conexion.desconectarBBDD(conexion); return 0; } }
	 * 
	 */
}
