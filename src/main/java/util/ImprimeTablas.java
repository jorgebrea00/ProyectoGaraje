package util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import model.Cliente;

public class ImprimeTablas {

	public Connection con = Multidao.getCon();

	public void selectFromTablaWhereCampoEqualsValor(String nombreTabla, String campoBusqueda, String valorCampo) {

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

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void selectFromTabla(String nombreTabla) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void selectFromTablaWhereCampoEqualsValor(String nombreTabla, String campoBusqueda,
	        String valorCampo, boolean mostrarClaves) {

	    String query = String.format("SELECT * FROM %s WHERE %s = %s", nombreTabla, campoBusqueda, valorCampo);
	    try (Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(query)) {
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int numColumns = rsmd.getColumnCount();
	        List<String> primaryKeys = new ArrayList<>();
	        
	        // Obtener las columnas que forman parte de la clave primaria
	        DatabaseMetaData metaData = con.getMetaData();
	        ResultSet pkResultSet = metaData.getPrimaryKeys(null, null, nombreTabla);
	        while (pkResultSet.next()) {
	            String primaryKeyColumn = pkResultSet.getString("COLUMN_NAME");
	            primaryKeys.add(primaryKeyColumn);
	        }
	        
	        // Cabecera
	        for (int i = 1; i <= numColumns; i++) {
	            String columnName = rsmd.getColumnName(i);
	            if (mostrarClaves || !primaryKeys.contains(columnName)) {
	                System.out.format("| %-20s ", columnName);
	            }
	        }
	        System.out.println("|");
	        // Separador
	        for (int i = 1; i <= numColumns; i++) {
	            String columnName = rsmd.getColumnName(i);
	            if (mostrarClaves || !primaryKeys.contains(columnName)) {
	                System.out.print("+---------------------");
	            }
	        }
	        System.out.println("+");
	        // Datos
	        while (rs.next()) {
	            for (int i = 1; i <= numColumns; i++) {
	                String columnName = rsmd.getColumnName(i);
	                Object value = rs.getObject(i);
	                if (mostrarClaves || !primaryKeys.contains(columnName)) {
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
	            }
	            System.out.println("|");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void imprimeVehiculosPorCliente(Cliente c) {
		selectFromTablaWhereCampoEqualsValor("vehiculo", "id_cliente", String.valueOf(c.getId()), false);
	}

	public void imprimeCitasPorCliente(Cliente c) {
		selectFromTablaWhereCampoEqualsValor("citas", "id_cliente", String.valueOf(c.getId()), false);
	}

	public void imprimeDatosPorCliente(Cliente c) {
		selectFromTablaWhereCampoEqualsValor("cliente", "id", String.valueOf(c.getId()), false);
	}
}
