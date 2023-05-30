package controller;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPool implements ServletContextListener {

	private BasicDataSource dataSource;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
	    ServletContext sc = servletContextEvent.getServletContext();
	    
	    // Recuperar los valores de configuración del contexto
	    String driver = sc.getInitParameter("db.driver");
	    String url = sc.getInitParameter("db.url");
	    String username = sc.getInitParameter("db.username");
	    String password = sc.getInitParameter("db.password");

	    // Crear la pool
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName(driver);
	    dataSource.setUrl(url);
	    dataSource.setUsername(username);
	    dataSource.setPassword(password);

	    // Colocar el dataSource en el ServletContext
	    sc.setAttribute("dataSource", dataSource);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// Cerrar la pool y liberar recursos
		if (dataSource != null) {
			try {
				dataSource.close();
			} catch (SQLException e) {
				System.out.println("Error al cerrar la fuente de datos: " + e.getMessage());
			}
		}
	}
	
}
