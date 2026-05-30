package model.estrategias;

import model.Corredor;
import model.EstrategiaAvance;

public class EstrategiaResistente implements EstrategiaAvance {

	 @Override
	 public double avanzar(Corredor corredor) {

		 return corredor.getVelocidad() * (corredor.getEnergia() / 100)* 0.9;

	    }

	 @Override
	 public double gastarEnergia(Corredor corredor) {

		 return (10 - (corredor.getResistencia() / 10))*0.9;

}
}