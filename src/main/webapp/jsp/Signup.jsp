<%@page import="model.entity.Cliente"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="model.entity.Autenticacion, model.entity.Cliente, model.dao.ClienteDao, model.dao.AutenticacionDao, org.apache.commons.dbcp2.BasicDataSource, java.sql.Connection"%>
<html>
<head>
<title>Registro</title>
</head>
<body>
	<%-- Se incluye el HTML estático del formulario de ingreso --%>
	<%@ include file="../html/Signup.html"%>

	<%
	String registrarse = request.getParameter("registrarse");
	if (registrarse != null && registrarse.equals("true")) {
		// Obtener los datos del formulario
		String dni = request.getParameter("dni");
		String email = request.getParameter("email");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String telefono = request.getParameter("telefono");
		String password = request.getParameter("password");
		String confirm_password = request.getParameter("confirm_password");

		// Validar que las contraseñas coinciden
		if (!password.equals(confirm_password) && !password.equals(null)) {
			response.sendRedirect("Registro.html?error=contrasenas_no_coinciden");
			return;
		}
		
		BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
		Connection c = dataSource.getConnection();
		
		// Insertar el nuevo usuario en la base de datos		
		Cliente cliente = new Cliente();
		cliente.setDni(dni);
		cliente.setEmail(email);
		cliente.setNombre(nombre);
		cliente.setApellido(apellido);
		cliente.setTelefono(Integer.parseInt(telefono));		
		ClienteDao cdao = new ClienteDao(c);
		cdao.insert(cliente);

		Autenticacion auth = new Autenticacion();
		auth.setIdCliente(cliente.getId());
		auth.setEmail(email);
		auth.setPassHash(password);
		AutenticacionDao adao = new AutenticacionDao(c);
		adao.insert(auth); 
		
		// Redirigir al usuario a la página de inicio		
		response.sendRedirect("/Moto/html/Login.html");
	}
	%>

</body>
</html>
