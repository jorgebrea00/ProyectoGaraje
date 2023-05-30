package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import model.entity.Cliente;

public final class ClienteDao implements Dao<Cliente> {

	private Connection connection;
	private QueryRunner queryRunner;
	
	public ClienteDao(Connection c) {
	    this.connection = c;
	    this.queryRunner = new QueryRunner();
	}

	@Override
	public int insert(Cliente cliente) {
		try {
			String query = "INSERT INTO cliente (dni, email, nombre, apellido, telefono) VALUES (?, ?, ?, ?, ?)";
			Object[] params = { cliente.getDni(), cliente.getEmail(), cliente.getNombre(), cliente.getApellido(),
					cliente.getTelefono() };

			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			queryRunner.fillStatement(statement, params);

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
			Object[] params = { id };

			ResultSetHandler<Cliente> handler = new BeanHandler<>(Cliente.class);
			Cliente cliente = queryRunner.query(connection, query, handler, params);

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

			ResultSetHandler<List<Cliente>> handler = new BeanListHandler<>(Cliente.class);
			List<Cliente> clientes = queryRunner.query(connection, query, handler);

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

			Object[] params = { cliente.getDni(), cliente.getEmail(), cliente.getNombre(), cliente.getApellido(),
					cliente.getTelefono(), cliente.getId() };
			int rowsAffected = queryRunner.update(connection, query, params);

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

			int rowsAffected = queryRunner.update(connection, query, cliente.getId());

			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Get cliente por email
	public Cliente read(String email) {
		try {
			String query = "SELECT * FROM cliente WHERE email = ?";

			return queryRunner.query(connection, query, new BeanHandler<>(Cliente.class), email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
