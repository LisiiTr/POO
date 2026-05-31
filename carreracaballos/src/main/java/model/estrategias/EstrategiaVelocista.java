package model.estrategias;

import model.Corredor;
import model.EstrategiaAvance;

public class EstrategiaVelocista implements EstrategiaAvance {

	@Override
	public double calcularAvance(Corredor corredor) {
		return corredor.getVelocidadBase() * (corredor.getEnergiaActual() / 100) * 1.1;
	}

	@Override
	public double calcularGastoEnergia(Corredor corredor) {
		return (10 - (corredor.getResistencia() / 10)) * 1.1;
	}

	@Override
	public String getNombre() { return "Velocista"; }
}
