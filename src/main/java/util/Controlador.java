package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import main.main;

public class Controlador {
	
	private Connection connection = Multidao.getCon();

	public void iniciarSesion() {		
		main.getSesion().setInicioSesion(LocalDateTime.now());
		main.getSesion().setIdCliente(main.getClienteLogeado().getId());
	}

	public void cerrarSesion() {
		main.getSesion().setFinSesion(LocalDateTime.now());
		main.dao.getSesiondao().insert(main.getSesion());
		main.setClienteLogeado(null); 	
	}
	
	public boolean validarCredenciales(String email, String password) {
		boolean isValid = false;
		String sql = "SELECT * FROM autenticacion WHERE email = ? AND pass_hash = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				// Si hay un resultado en la consulta, significa que los datos de acceso son válidos
				isValid = true;
			}
		} catch (SQLException e) {
			System.out.println("Error al validar credenciales: " + e.getMessage());
		}
		return isValid;
	}
}
