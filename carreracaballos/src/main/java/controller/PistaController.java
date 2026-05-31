package controller;
import model.Pista;
public class PistaController {

    private Pista pista;

    public PistaController(Pista pista) {
        this.pista = pista;
    }

    public String getNombrePista() {
        return pista.getNombre();
    }

    public double getDistanciaTotal() {
        return pista.getDistancia();
    }
}	
	


