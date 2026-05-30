package model.estrategias;

import model.Corredor;
import model.EstrategiaAvance;

public class EstrategiaEquilibrada implements EstrategiaAvance {

	 @Override
	 public double avanzar(Corredor corredor) {

		 return corredor.getVelocidad() * (corredor.getEnergia() / 100);

	    }

	    @Override
	    public double gastarEnergia(Corredor corredor) {

	    	return (10 - (corredor.getResistencia() / 10));

}
}