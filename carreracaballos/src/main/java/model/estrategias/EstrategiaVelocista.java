package model.estrategias;

import model.Corredor;
import model.EstrategiaAvance;

public class EstrategiaVelocista implements EstrategiaAvance {

	 @Override
	 public double avanzar(Corredor corredor) {

		 return corredor.getVelocidad() * (corredor.getEnergia() / 100) * 1.1;

	    }

	    @Override
	    public double gastarEnergia(Corredor corredor) {

	    	return (10 - (corredor.getResistencia() / 10)) * 1.1;

}
}