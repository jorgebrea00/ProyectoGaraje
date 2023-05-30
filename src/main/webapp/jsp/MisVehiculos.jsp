<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mis vehiculos</title>
</head>
<body>
	<%@ page
		import="controller.ContextManager, java.sql.Connection,java.util.ArrayList,,java.util.List, model.entity.Cliente,model.entity.Vehiculo, model.dao.VehiculoDao, org.apache.commons.dbcp2.BasicDataSource"%>
	<%
	//Obtener vehiculos del cliente
	BasicDataSource dataSource = (BasicDataSource) getServletContext().getAttribute("dataSource");
	VehiculoDao vdao = new VehiculoDao(dataSource.getConnection());
	Cliente c = (Cliente) getServletContext().getAttribute("clienteLogeado");
	List<Vehiculo> vehiculos = vdao.getAll(c.getId());
	%>

</body>
</html>