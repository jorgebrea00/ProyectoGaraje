package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import model.dao.ClienteDao;
import model.entity.Cliente;

public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usuario = request.getParameter("usuario");
		String pass = request.getParameter("password");

		// Proceso de autenticación y asignación del cliente logeado
		if (validarCredenciales(usuario, pass)) {
			asignarClienteLogeado(usuario);
			// Redireccionar a la página de inicio del área de usuario
			response.sendRedirect("/Moto/jsp/Profile.jsp");

		}

	}

	private void asignarClienteLogeado(String usuario) {
		// Obtener cliente
		BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("bddMotorracing");
		try {
			// Obtener conexion
			Connection con = dataSource.getConnection();
			// Obtener cliente
			Cliente c = new ClienteDao(con).read(usuario);
			// Asignar el cliente al contexto
			ServletContext sc = getServletContext();
			sc.setAttribute("clienteLogeado", c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean validarCredenciales(String email, String password) {
		BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("bddMotorracing");
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
