package model;

public class Pista {
	
	private long id;
	private String nombre;
	private double distancia;
	
	
	public Pista(int id, String nombre, double distancia) {
		 this.id = id;
	     this.nombre = nombre;
	     this.distancia = distancia;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
}