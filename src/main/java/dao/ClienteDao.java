package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import util.Multidao;

public final class ClienteDao implements Dao<Cliente> {

	private Connection connection = Multidao.getCon();

	@Override
	public int insert(Cliente cliente) {
		try {
			String query = "INSERT INTO cliente (dni, email, nombre, apellido, telefono) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, cliente.getDni());
			statement.setString(2, cliente.getEmail());
			statement.setString(3, cliente.getNombre());
			statement.setString(4, cliente.getApellido());
			statement.setInt(5, cliente.getTelefono());

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("La inserción del cliente ha fallado, no se han afectado filas.");
			}

			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				cliente.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("La inserción del cliente ha fallado, no se ha generado una clave primaria.");
			}

			statement.close();

			return cliente.getId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Cliente read(int id) {
		try {
			String query = "SELECT * FROM cliente WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();

			Cliente cliente = null;

			if (resultSet.next()) {
				cliente = new Cliente();
				cliente.setId(resultSet.getInt("id"));
				cliente.setDni(resultSet.getString("dni"));
				cliente.setEmail(resultSet.getString("email"));
				cliente.setNombre(resultSet.getString("nombre"));
				cliente.setApellido(resultSet.getString("apellido"));
				cliente.setTelefono(resultSet.getInt("telefono"));
			}

			resultSet.close();
			statement.close();

			return cliente;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cliente> getAll() {
		try {
			String query = "SELECT * FROM cliente";
			PreparedStatement statement = connection.prepareStatement(query);

			ResultSet resultSet = statement.executeQuery();

			List<Cliente> clientes = new ArrayList<>();

			while (resultSet.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(resultSet.getInt("id"));
				cliente.setDni(resultSet.getString("dni"));
				cliente.setEmail(resultSet.getString("email"));
				cliente.setNombre(resultSet.getString("nombre"));
				cliente.setApellido(resultSet.getString("apellido"));
				cliente.setTelefono(resultSet.getInt("telefono"));

				clientes.add(cliente);
			}

			resultSet.close();
			statement.close();

			return clientes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(Cliente cliente) {
		try {
			String query = "UPDATE cliente SET dni = ?, email = ?, nombre = ?, apellido = ?, telefono = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, cliente.getDni());
			statement.setString(2, cliente.getEmail());
			statement.setString(3, cliente.getNombre());
			statement.setString(4, cliente.getApellido());
			statement.setInt(5, cliente.getTelefono());
			statement.setInt(6, cliente.getId());

			int rowsAffected = statement.executeUpdate();

			statement.close();

			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int delete(Cliente cliente) {
		try {
			String query = "DELETE FROM cliente WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, cliente.getId());

			int rowsAffected = statement.executeUpdate();

			statement.close();

			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
