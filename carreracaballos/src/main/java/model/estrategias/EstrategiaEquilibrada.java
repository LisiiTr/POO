package model.estrategias;

import model.Corredor;
import model.EstrategiaAvance;

public class EstrategiaEquilibrada implements EstrategiaAvance {

	@Override
	public double calcularAvance(Corredor corredor) {
		return corredor.getVelocidadBase() * (corredor.getEnergiaActual() / 100);
	}

	@Override
	public double calcularGastoEnergia(Corredor corredor) {
		return 10 - (corredor.getResistencia() / 10);
	}

	@Override
	public String getNombre() { return "Equilibrada"; }
}
