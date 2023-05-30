package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.entity.Sesion;

public class SesionDao implements Dao<Sesion> {
	
	private Connection connection;

	public SesionDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insert(Sesion sesion) {
		String query = "INSERT INTO sesion (inicio_sesion, fin_sesion, id_cliente) VALUES (?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setObject(1, sesion.getInicioSesion());
			statement.setObject(2, sesion.getFinSesion());
			statement.setInt(3, sesion.getIdCliente());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int generatedId = resultSet.getInt(1);
					sesion.setId(generatedId);
					return generatedId;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Sesion read(int id) {
		String query = "SELECT * FROM sesion WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int sesionId = resultSet.getInt("id");
				LocalDateTime inicioSesion = resultSet.getObject("inicio_sesion", LocalDateTime.class);
				LocalDateTime finSesion = resultSet.getObject("fin_sesion", LocalDateTime.class);
				int idCliente = resultSet.getInt("id_cliente");
				return new Sesion(sesionId, inicioSesion, finSesion, idCliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Sesion> getAll() {
		String query = "SELECT * FROM sesion";
		List<Sesion> sesiones = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int sesionId = resultSet.getInt("id");
				LocalDateTime inicioSesion = resultSet.getObject("inicio_sesion", LocalDateTime.class);
				LocalDateTime finSesion = resultSet.getObject("fin_sesion", LocalDateTime.class);
				int idCliente = resultSet.getInt("id_cliente");
				sesiones.add(new Sesion(sesionId, inicioSesion, finSesion, idCliente));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sesiones;
	}

	@Override
	public int update(Sesion sesion) {
		String query = "UPDATE sesion SET inicio_sesion = ?, fin_sesion = ?, id_cliente = ? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setObject(1, sesion.getInicioSesion());
			statement.setObject(2, sesion.getFinSesion());
			statement.setInt(3, sesion.getIdCliente());
			statement.setInt(4, sesion.getId());

			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int delete(Sesion sesion) {
		String query = "DELETE FROM sesion WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, sesion.getId());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}