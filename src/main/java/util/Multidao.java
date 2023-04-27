package util;

import java.sql.Connection;

import dao.AutenticacionDao;
import dao.ClienteDao;
import dao.SesionDao;
import dao.VehiculoDao;

public class Multidao {
	
	public ClienteDao clientedao;
	public AutenticacionDao autenticaciondao;
	public SesionDao sesiondao;
	public VehiculoDao vehiculodao;
	public static Connection con;
	
	public Multidao() {
		super();
		Multidao.con = Conector.conectar();
		this.clientedao = new ClienteDao();
		this.autenticaciondao = new AutenticacionDao();
		this.sesiondao = new SesionDao();
		this.vehiculodao = new VehiculoDao();
	}

	public ClienteDao getClientedao() {
		return clientedao;
	}

	public AutenticacionDao getAutenticaciondao() {
		return autenticaciondao;
	}

	public SesionDao getSesiondao() {
		return sesiondao;
	}
	
	public static Connection getCon() {
		return con;
	}

	public VehiculoDao getVehiculodao() {
		return vehiculodao;
	}		
	
	
}
