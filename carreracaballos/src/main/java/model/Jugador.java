package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "jugadores")
public class Jugador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(length = 100)
	private String mail;

	@Column(name = "puntaje_acumulado")
	private double puntajeAcumulado;

	@Transient
	private Corredor corredorSeleccionado;

	protected Jugador() {}

	public Jugador(String nombre, String mail) {
		this.nombre = nombre;
		this.mail = mail;
		this.puntajeAcumulado = 0;
	}

	public void seleccionarCorredor(Corredor corredor) {
		this.corredorSeleccionado = corredor;
	}

	public void actualizarPuntaje(double puntos) {
		this.puntajeAcumulado += puntos;
	}

	public long getId()                           { return id; }
	public String getNombre()                     { return nombre; }
	public String getMail()                       { return mail; }
	public double getPuntajeAcumulado()           { return puntajeAcumulado; }
	public Corredor getCorredorSeleccionado()     { return corredorSeleccionado; }
	public void setCorredorSeleccionado(Corredor c) { this.corredorSeleccionado = c; }
}
