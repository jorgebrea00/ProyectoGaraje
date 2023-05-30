package model.entity;

public class Autenticacion {
	private int id;
	private int idCliente;
	private String email;
	private String passHash;

	public Autenticacion() {
		super();
	}

	public Autenticacion(int idCliente, String email) {
		this.idCliente = idCliente;
		this.email = email;
	}

	public Autenticacion(int idCliente, String email, String passHash) {
		this.idCliente = idCliente;
		this.email = email;
		this.passHash = passHash;
	}

	public Autenticacion(int id, int idCliente, String email, String passHash) {
		this.idCliente = id;
		this.idCliente = idCliente;
		this.email = email;
		this.passHash = passHash;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassHash() {
		return passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

}
