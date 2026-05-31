package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pistas")
public class Pista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(name = "distancia_total", nullable = false)
	private double distanciaTotal;

	protected Pista() {}

	public Pista(String nombre, double distanciaTotal) {
		this.nombre = nombre;
		this.distanciaTotal = distanciaTotal;
	}

	public long getId()                  { return id; }
	public String getNombre()            { return nombre; }
	public double getDistanciaTotal()    { return distanciaTotal; }
}
