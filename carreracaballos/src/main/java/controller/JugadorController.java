package controller;
import model.Corredor;
import model.Jugador;

public class JugadorController {
	   private Jugador jugador;

	    public JugadorController(Jugador jugador) {
	        this.jugador = jugador;
	    }

	    public String getNombre() {
	        return jugador.getNombre();
	    }

	    public String getMail() {
	        return jugador.getMail();
	    }

	    public double getPuntajeAcumulado() {
	        return jugador.getPuntajeAcumulado();
	    }

	    public Corredor getCorredorSeleccionado() {
	        return jugador.getCorredorSeleccionado();
	    }

	    public void seleccionarCorredor(Corredor corredor) {
	        jugador.setCorredorSeleccionado(corredor);
	    }

	    public void actualizarPuntaje(double puntos) {
	        jugador.actualizarPuntaje(puntos);
	    }
	}

