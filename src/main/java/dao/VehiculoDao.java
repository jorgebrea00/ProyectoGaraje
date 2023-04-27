package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Vehiculo;
import util.Multidao;

public class VehiculoDao implements Dao<Vehiculo> {

	private Connection connection = Multidao.getCon();

	@Override
	public int insert(Vehiculo vehiculo) {
		int id = -1;
		String query = "INSERT INTO vehiculo (id_cliente, matricula, tipo_vehiculo, marca, modelo, año) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, vehiculo.getIdCliente());
			statement.setString(2, vehiculo.getMatricula());
			statement.setString(3, vehiculo.getTipoVehiculo());
			statement.setString(4, vehiculo.getMarca());
			statement.setString(5, vehiculo.getModelo());
			statement.setInt(6, vehiculo.getAnio());
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 1) {
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error en VehiculoDao.insert: " + e.getMessage());
		}
		return id;
	}

	@Override
	public Vehiculo read(int id) {
		Vehiculo vehiculo = null;
		String query = "SELECT * FROM vehiculo WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				vehiculo = new Vehiculo();
				vehiculo.setId(resultSet.getInt("id"));
				vehiculo.setIdCliente(resultSet.getInt("id_cliente"));
				vehiculo.setMatricula(resultSet.getString("matricula"));
				vehiculo.setTipoVehiculo(resultSet.getString("tipo_vehiculo"));
				vehiculo.setMarca(resultSet.getString("marca"));
				vehiculo.setModelo(resultSet.getString("modelo"));
				vehiculo.setAnio(resultSet.getInt("año"));
			}
		} catch (SQLException e) {
			System.out.println("Error en VehiculoDao.read: " + e.getMessage());
		}
		return vehiculo;
	}

	@Override
	public List<Vehiculo> getAll() {
		List<Vehiculo> vehiculos = new ArrayList<>();
		String query = "SELECT * FROM vehiculo";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Vehiculo vehiculo = new Vehiculo();
				vehiculo.setId(resultSet.getInt("id"));
				vehiculo.setIdCliente(resultSet.getInt("id_cliente"));
				vehiculo.setMatricula(resultSet.getString("matricula"));
				vehiculo.setTipoVehiculo(resultSet.getString("tipo_vehiculo"));
				vehiculo.setMarca(resultSet.getString("marca"));
				vehiculo.setModelo(resultSet.getString("modelo"));
				vehiculo.setAnio(resultSet.getInt("año"));
				vehiculos.add(vehiculo);
			}
		} catch (SQLException e) {
			System.out.println("Error en VehiculoDao.getAll: " + e.getMessage());
		}
		return vehiculos;
	}

	@Override
	public int update(Vehiculo vehiculo) {
		int rowsAffected = 0;
		String query = "UPDATE vehiculo SET id_cliente=?, matricula=?, tipo_vehiculo=?, marca=?, modelo=?, año=? WHERE id=?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, vehiculo.getIdCliente());
			stmt.setString(2, vehiculo.getMatricula());
			stmt.setString(3, vehiculo.getTipoVehiculo());
			stmt.setString(4, vehiculo.getMarca());
			stmt.setString(5, vehiculo.getModelo());
			stmt.setInt(6, vehiculo.getAnio());
			stmt.setInt(7, vehiculo.getId());
			rowsAffected = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	@Override
	public int delete(Vehiculo vehiculo) {
		int rowsAffected = 0;
		String query = "DELETE FROM vehiculo WHERE id=?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, vehiculo.getId());
			rowsAffected = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error en VehiculoDao.delete: " + e.getMessage());
		}
		return rowsAffected;
	}
	

	public List<Vehiculo> getAll(int idCliente) {
	    List<Vehiculo> vehiculos = new ArrayList<>();
	    String query = "SELECT * FROM vehiculo WHERE id_cliente = ?";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setInt(1, idCliente);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            Vehiculo vehiculo = new Vehiculo();
	            vehiculo.setId(resultSet.getInt("id"));
	            vehiculo.setIdCliente(resultSet.getInt("id_cliente"));
	            vehiculo.setMatricula(resultSet.getString("matricula"));
	            vehiculo.setTipoVehiculo(resultSet.getString("tipo_vehiculo"));
	            vehiculo.setMarca(resultSet.getString("marca"));
	            vehiculo.setModelo(resultSet.getString("modelo"));
	            vehiculo.setAnio(resultSet.getInt("año"));
	            vehiculos.add(vehiculo);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error en VehiculoDao.getAll: " + e.getMessage());
	    }
	    return vehiculos;
	}


}
