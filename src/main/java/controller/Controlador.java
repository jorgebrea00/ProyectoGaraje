package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class Controlador {

	private BasicDataSource dataSource;

	public Controlador(BasicDataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public boolean validarCredenciales(String email, String password) {
		try (Connection c = dataSource.getConnection()) {
			boolean isValid = false;
			String sql = "SELECT * FROM autenticacion WHERE email = ? AND pass_hash = ?";

			try (PreparedStatement stmt = c.prepareStatement(sql)) {
				stmt.setString(1, email);
				stmt.setString(2, password);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						isValid = true;
					}
				}
			}
			return isValid;
		} catch (SQLException e) {
			System.out.println("Error al validar credenciales: " + e.getMessage());
			return false;
		}
	}

}
