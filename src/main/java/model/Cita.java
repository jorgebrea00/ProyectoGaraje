package model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;


/**
 * The persistent class for the citas database table.
 * 
 */
@Entity
@Table(name="citas")
@NamedQuery(name="Cita.findAll", query="SELECT c FROM Cita c")
public class Cita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;


	@Column(name="fecha_hora")
	private LocalDate fechaHora;

	//bi-directional many-to-one association to CabecerasDiagnostico
	@ManyToOne
	@JoinColumn(name="id_diagnostico")
	private CabecerasDiagnostico cabecerasDiagnostico;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Vehiculo
	@ManyToOne
	@JoinColumn(name="id_vehiculo")
	private Vehiculo vehiculo;

	public Cita() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}

	public CabecerasDiagnostico getCabecerasDiagnostico() {
		return this.cabecerasDiagnostico;
	}

	public void setCabecerasDiagnostico(CabecerasDiagnostico cabecerasDiagnostico) {
		this.cabecerasDiagnostico = cabecerasDiagnostico;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

}