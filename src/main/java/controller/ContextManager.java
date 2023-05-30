package controller;

import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.BasicDataSource;

import model.entity.Cliente;

public class ContextManager implements ServletContextListener {

	private BasicDataSource bddMotorracing;
	private Cliente clienteLogeado;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		agregarBaseDatosAlContexto(sce);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		cerrarBaseDatos();
	}

	public void agregarBaseDatosAlContexto(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();

		// Recuperar los valores de configuración del contexto
		String driver = sc.getInitParameter("db.driver");
		String url = sc.getInitParameter("db.url");
		String username = sc.getInitParameter("db.username");
		String password = sc.getInitParameter("db.password");

		// Crear la pool
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(driver);
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);

		// Colocar el dataSource en el ServletContext
		sc.setAttribute("bddMotorracing", datasource);
	}

	private void cerrarBaseDatos() {
		if (bddMotorracing != null) {
			try {
				bddMotorracing.close();
			} catch (SQLException e) {
				System.out.println("Error al cerrar la fuente de datos: " + e.getMessage());
			}
		}
	}

}
