package estructuraBaseDeDatos;
import java.sql.*;

public class ImprimeTablas {
	
	public Connection con;

	public ImprimeTablas(Connection con) {
		super();
		this.con = con;
	}

	public void selectFromTablaWhereCampoEqualsValor(String nombreTabla, String campoBusqueda,
			String valorCampo) throws SQLException {

		String query = String.format("SELECT * FROM %s WHERE %s = %s", nombreTabla, campoBusqueda, valorCampo);
		try (Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(query)) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();
			// Cabecera
			for (int i = 1; i <= numColumns; i++) {
				System.out.format("| %-20s ", rsmd.getColumnName(i));
			}
			System.out.println("|");
			// Separador
			for (int i = 1; i <= numColumns; i++) {
				System.out.print("+---------------------");
			}
			System.out.println("+");
			// Datos
			while (rs.next()) {
				for (int i = 1; i <= numColumns; i++) {
					Object value = rs.getObject(i);
					if (value == null) {
						System.out.format("| %-20s ", "NULL");
					} else if (value instanceof String) {
						System.out.format("| %-20s ", (String) value);
					} else if (value instanceof Integer) {
						System.out.format("| %-20d ", (Integer) value);
					} else if (value instanceof Double) {
						System.out.format("| %-20.2f ", (Double) value);
					} else {
						System.out.format("| %-20s ", value.toString());
					}
				}
				System.out.println("|");
			}
		}
	}

	public void selectFromTabla(String nombreTabla) throws SQLException {
		String query = String.format("SELECT * FROM %s", nombreTabla);
		try (Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(query)) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();
			// Header
			for (int i = 1; i <= numColumns; i++) {
				System.out.format("| %-20s ", rsmd.getColumnName(i));
			}
			System.out.println("|");
			// Separador
			for (int i = 1; i <= numColumns; i++) {
				System.out.print("+---------------------");
			}
			System.out.println("+");
			// Datos
			while (rs.next()) {
				for (int i = 1; i <= numColumns; i++) {
					Object value = rs.getObject(i);
					if (value == null) {
						System.out.format("| %-20s ", "NULL");
					} else if (value instanceof String) {
						System.out.format("| %-20s ", (String) value);
					} else if (value instanceof Integer) {
						System.out.format("| %-20d ", (Integer) value);
					} else if (value instanceof Double) {
						System.out.format("| %-20.2f ", (Double) value);
					} else {
						System.out.format("| %-20s ", value.toString());
					}
				}
				System.out.println("|");
			}
		}
	}
}
