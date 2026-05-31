package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import model.estrategias.EstrategiaEquilibrada;
import model.estrategias.EstrategiaResistente;
import model.estrategias.EstrategiaVelocista;

@Entity
@Table(name = "corredores")
public class Corredor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(name = "velocidad_base", nullable = false)
	private double velocidadBase;

	@Column(nullable = false)
	private double resistencia;

	@Column(nullable = false, length = 50)
	private String estrategiaNombre;

	@Transient
	private EstrategiaAvance estrategia;

	@Transient
	private double energiaActual = 100;

	@Transient
	private double distanciaRecorrida = 0;

	protected Corredor() {}

	public Corredor(String nombre, double velocidadBase, double resistencia, EstrategiaAvance estrategia) {
		this.nombre = nombre;
		this.velocidadBase = velocidadBase;
		this.resistencia = resistencia;
		this.estrategia = estrategia;
		this.estrategiaNombre = estrategia.getNombre();
		this.energiaActual = 100;
		this.distanciaRecorrida = 0;
	}

	public void avanzar(double distanciaRestante) {
		double metrosAvanzados = getEstrategia().calcularAvance(this);
		if (metrosAvanzados <= 0){
			metrosAvanzados = velocidadBase / 2;
		} 
		if (metrosAvanzados > distanciaRestante){
			metrosAvanzados = distanciaRestante;
		}
		double energiaGastada = getEstrategia().calcularGastoEnergia(this);
		distanciaRecorrida += metrosAvanzados;
		energiaActual = energiaActual - energiaGastada;
		if (energiaActual < 0) {
			energiaActual = 15;
		}
	}

	public void reiniciarEstado() {
		this.distanciaRecorrida = 0;
		this.energiaActual = 100;
	}

	public boolean llegaAMeta(double distanciaTotal) {
		return distanciaRecorrida >= distanciaTotal;
	}

	public long getId()                     { return id; }
	public String getNombre()               { return nombre; }
	public double getVelocidadBase()        { return velocidadBase; }
	public double getResistencia()          { return resistencia; }
	public double getEnergiaActual()        { return energiaActual; }
	public double getDistanciaRecorrida()   { return distanciaRecorrida; }
	public String getEstrategiaNombre()     { return estrategiaNombre; }

	public EstrategiaAvance getEstrategia() {
		if (estrategia == null) {
			if ("Velocista".equals(estrategiaNombre)){
				estrategia = new EstrategiaVelocista();
			}
			else if ("Resistente".equals(estrategiaNombre)){
				estrategia = new EstrategiaResistente();
			} 
			else{
				estrategia = new EstrategiaEquilibrada();
			}
		}
		return estrategia;
	}
}
