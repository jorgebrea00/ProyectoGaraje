<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>MotorRacing</title>
</head>
<body>
	<%-- Se incluye el HTML estático del formulario de ingreso --%>
	<%@ include file="../html/Login.html"%>
	<%@ page import="controller.ConnectionPool, controller.Controlador, model.entity.Cliente, org.apache.commons.dbcp2.BasicDataSource"%>
	<%-- Se añade código JSP para procesar el formulario --%>
	<%
	
	String registro = request.getParameter("registro");
	if (registro != null && registro.equals("true")) {
		response.sendRedirect("Signup.jsp");
	}
	
	String usuario = request.getParameter("usuario");
	String password = request.getParameter("contrasena");
	String validar = request.getParameter("validar");
	
	if (usuario != null && password != null && validar.equals("true")) {		
		
		BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
		Controlador c = new Controlador(dataSource);
		
		if (c.validarCredenciales(usuario, password)) {
			response.sendRedirect("Profile.jsp");
		}
	} else {
		out.println("<p>Por favor, introduce tus datos de acceso</p>");
	}
	%>
</body>
</html>
