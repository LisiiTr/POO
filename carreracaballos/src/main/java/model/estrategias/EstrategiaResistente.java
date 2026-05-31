package model.estrategias;

import model.Corredor;
import model.EstrategiaAvance;

public class EstrategiaResistente implements EstrategiaAvance {

	@Override
	public double calcularAvance(Corredor corredor) {
		return corredor.getVelocidadBase() * (corredor.getEnergiaActual() / 100) * 0.9;
	}

	@Override
	public double calcularGastoEnergia(Corredor corredor) {
		return (10 - (corredor.getResistencia() / 10)) * 0.9;
	}

	@Override
	public String getNombre() { return "Resistente"; }
}
