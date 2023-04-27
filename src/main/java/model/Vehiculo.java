package model;

public class Vehiculo {
	private int id;
	private int idCliente;
	private String matricula;
	private String tipoVehiculo;
	private String marca;
	private String modelo;
	private int anio;

	public int getId() {
		return id;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public int getAnio() {
		return anio;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public Vehiculo(int id, int idCliente, String matricula, String tipoVehiculo, String marca, String modelo,
			int anio) {
		this.id = id;
		this.idCliente = idCliente;
		this.matricula = matricula;
		this.tipoVehiculo = tipoVehiculo;
		this.marca = marca;
		this.modelo = modelo;
		this.anio = anio;
	}

	public Vehiculo() {
		super();
	}
}
