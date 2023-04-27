package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Autenticacion;
import util.Multidao;

public class AutenticacionDao implements Dao<Autenticacion> {

	private Connection connection = Multidao.getCon();

	@Override
	public int insert(Autenticacion autenticacion) {
		int affectedRows = 0;
		String sql = "INSERT INTO autenticacion (id_cliente, email, pass_hash) VALUES (?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, autenticacion.getIdCliente());
			stmt.setString(2, autenticacion.getEmail());
			stmt.setString(3, autenticacion.getPassHash());

			affectedRows = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al insertar autenticacion: " + e.getMessage());
		}
		return affectedRows;
	}

	@Override
	public Autenticacion read(int id) {
		Autenticacion autenticacion = null;
		String sql = "SELECT * FROM autenticacion WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int idCliente = rs.getInt("id_cliente");
				String email = rs.getString("email");
				String passHash = rs.getString("pass_hash");

				autenticacion = new Autenticacion(id, idCliente, email, passHash);
			}
		} catch (SQLException e) {
			System.out.println("Error al leer autenticacion: " + e.getMessage());
		}

		return autenticacion;
	}

	@Override
	public List<Autenticacion> getAll() {
		List<Autenticacion> autenticaciones = new ArrayList<>();
		String sql = "SELECT * FROM autenticacion";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idCliente = rs.getInt("id_cliente");
				String email = rs.getString("email");
				String passHash = rs.getString("pass_hash");

				Autenticacion autenticacion = new Autenticacion(id, idCliente, email, passHash);
				autenticaciones.add(autenticacion);
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener autenticaciones: " + e.getMessage());
		}

		return autenticaciones;
	}

	@Override
	public int update(Autenticacion autenticacion) {
		int affectedRows = 0;
		String sql = "UPDATE autenticacion SET id_cliente = ?, email = ?, pass_hash = ? WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, autenticacion.getIdCliente());
			stmt.setString(2, autenticacion.getEmail());
			stmt.setString(3, autenticacion.getPassHash());
			stmt.setInt(4, autenticacion.getId());

			affectedRows = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al actualizar autenticacion: " + e.getMessage());
		}

		return affectedRows;
	}

	@Override
	public int delete(Autenticacion autenticacion) {
		int affectedRows = 0;
		String sql = "DELETE FROM autenticacion WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, autenticacion.getId());

			affectedRows = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al eliminar autenticacion: " + e.getMessage());
		}

		return affectedRows;
	}
}