package model;

public class Jugador {
	private long id;
	private String nombre;
	private String mail;
	private double puntajeAcumulado;
	private Corredor corredorSeleccionado;
	
	
	public Jugador(int id, String nombre,String mail) {
		this.id = id;
		this.mail = mail;
		this.nombre = nombre;
		this.puntajeAcumulado = 0;
	}
	
	public void actualizarPuntaje(double puntos) {
		this.setPuntajeAcumulado(this.getPuntajeAcumulado() + puntos);
	}

	public double getPuntajeAcumulado() {
		return puntajeAcumulado;
	}

	public void setPuntajeAcumulado(double puntajeAcumulado) {
		this.puntajeAcumulado = puntajeAcumulado;
	}

	public String getNombre() {
		return nombre;
	}

	public String getMail() {
		return mail;
	}
	
	public Corredor getCorredorSeleccionado() {
		return corredorSeleccionado;
	}

	public void setCorredorSeleccionado(Corredor corredorSeleccionado) {
		this.corredorSeleccionado = corredorSeleccionado;
	}
	
}
