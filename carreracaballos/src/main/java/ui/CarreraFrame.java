package ui;

import controller.CarreraController;
import dao.CarreraDAO;
import dao.JugadorDAO;
import dto.CarreraResultadoDTO;
import dto.CorredorDTO;
import dto.JugadorDTO;
import dto.PistaDTO;
import model.CalculadorPuntaje;

import javax.swing.*;
import java.awt.*;

public class CarreraFrame extends JFrame {

	// ===== Controller =====
	private final CarreraController carreraController = new CarreraController(new JugadorDAO(), new CarreraDAO(), new CalculadorPuntaje());

	// ===== Navegacion =====
	private final CardLayout cards = new CardLayout();
	private final JPanel cardContainer = new JPanel(cards);

	// ===== Estado =====
	private JugadorDTO jugadorActual;
	private Timer timerCarrera;

	// ===== Componentes Registro =====
	private JTextField nombreField;
	private JTextField mailField;

	// ===== Componentes Main (seleccion + carrera) =====
	private JLabel lblBienvenida;
	private JComboBox<String> comboCorredores;
	private JButton btnIniciarCarrera;
	private JLabel statusLabel;
	private PistaPanel pistaPanel;

	// =====================================================

	public CarreraFrame() {
		setTitle("Carrera de Caballos");
		setSize(870, 610);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		cardContainer.add(buildPanelRegistro(), "registro");
		cardContainer.add(buildPanelMain(),     "main");

		setContentPane(cardContainer);
		cards.show(cardContainer, "registro");
	}

	private JPanel buildPanelRegistro() {
		JPanel root = new JPanel(new BorderLayout());
		root.setBackground(UIUtils.BG_DARK);

		JLabel title = new JLabel("CARRERA DE CABALLOS", SwingConstants.CENTER);
		title.setFont(new Font("SansSerif", Font.BOLD, 28));
		title.setForeground(UIUtils.COLOR_ORO);
		title.setBorder(BorderFactory.createEmptyBorder(30, 0, 6, 0));

		JLabel subtitle = new JLabel("Registrate para empezar a jugar", SwingConstants.CENTER);
		subtitle.setFont(new Font("SansSerif", Font.ITALIC, 14));
		subtitle.setForeground(new Color(95, 55, 135));
		subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		JPanel titleBlock = new JPanel();
		titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
		titleBlock.setBackground(UIUtils.BG_DARK);
		titleBlock.add(title);
		titleBlock.add(subtitle);

		JPanel formCard = new JPanel();
		formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
		formCard.setBackground(UIUtils.BG_PANEL);
		formCard.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Registro"),
			BorderFactory.createEmptyBorder(16, 40, 20, 40)
		));

		nombreField = new JTextField();
		nombreField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		UIUtils.stylizeField(nombreField);

		mailField = new JTextField();
		mailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		UIUtils.stylizeField(mailField);

		JButton btnEntrar = UIUtils.buildButton("ENTRAR", UIUtils.COLOR_VERDE);
		btnEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		btnEntrar.addActionListener(e -> onRegistrar());

		formCard.add(UIUtils.fieldLabel("Nombre:"));
		formCard.add(nombreField);
		formCard.add(Box.createRigidArea(new Dimension(0, 14)));
		formCard.add(UIUtils.fieldLabel("Mail (opcional):"));
		formCard.add(mailField);
		formCard.add(Box.createRigidArea(new Dimension(0, 20)));
		formCard.add(btnEntrar);

		JPanel center = new JPanel(new GridBagLayout());
		center.setBackground(UIUtils.BG_DARK);
		formCard.setPreferredSize(new Dimension(400, 280));
		center.add(formCard);

		root.add(titleBlock, BorderLayout.NORTH);
		root.add(center, BorderLayout.CENTER);
		return root;
	}

	private JPanel buildPanelMain() {
		JPanel root = new JPanel(new BorderLayout(0, 8));
		root.setBackground(UIUtils.BG_DARK);
		root.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

		lblBienvenida = new JLabel("Elegí tu caballo:");
		lblBienvenida.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblBienvenida.setForeground(UIUtils.COLOR_ORO);

		comboCorredores = new JComboBox<>();
		comboCorredores.setPreferredSize(new Dimension(360, 30));
		comboCorredores.setFont(new Font("SansSerif", Font.PLAIN, 13));

		btnIniciarCarrera = UIUtils.buildButton("INICIAR CARRERA", UIUtils.COLOR_VERDE);
		btnIniciarCarrera.addActionListener(e -> onIniciarCarrera());

		JPanel selectionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
		selectionBar.setBackground(UIUtils.BG_PANEL);
		selectionBar.setBorder(BorderFactory.createTitledBorder("Selección"));
		selectionBar.add(lblBienvenida);
		selectionBar.add(comboCorredores);
		selectionBar.add(btnIniciarCarrera);

		pistaPanel = new PistaPanel();

		statusLabel = new JLabel(" ", SwingConstants.CENTER);
		statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
		statusLabel.setForeground(new Color(95, 55, 135));

		root.add(selectionBar, BorderLayout.NORTH);
		root.add(pistaPanel,   BorderLayout.CENTER);
		root.add(statusLabel,  BorderLayout.SOUTH);
		return root;
	}

	private void onRegistrar() {
		String nombre = nombreField.getText().trim();
		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor ingresá tu nombre.", "Atención", JOptionPane.WARNING_MESSAGE);
			return;
		}
		jugadorActual = carreraController.registrarJugador(nombre, mailField.getText().trim());
		prepararSeleccion();
	}

	private void prepararSeleccion() {
		CorredorDTO[] corredores = carreraController.prepararCarrera();

		lblBienvenida.setText("Hola " + jugadorActual.getNombre() + ", elegí tu caballo:");

		comboCorredores.removeAllItems();
		for (CorredorDTO corredor : corredores) {
			comboCorredores.addItem(corredor.getNombre()
				+ "  —  Vel: " + (int) corredor.getVelocidadBase()
				+ "  Res: " + (int) corredor.getResistencia()
				+ "  (" + corredor.getEstrategiaNombre() + ")");
		}
		comboCorredores.setEnabled(true);
		btnIniciarCarrera.setEnabled(true);
		pistaPanel.limpiar();
		statusLabel.setText(" ");
		cards.show(cardContainer, "main");
	}

	private void onIniciarCarrera() {
		int idx = comboCorredores.getSelectedIndex();
		if (idx < 0) return;

		comboCorredores.setEnabled(false);
		btnIniciarCarrera.setEnabled(false);

		PistaDTO pista = carreraController.iniciarCarrera(idx);
		CorredorDTO[] estado = carreraController.getCorredoresEstado();

		statusLabel.setText("Carrera en " + pista.getNombre() + " (" + (int) pista.getDistanciaTotal() + "m) — comenzó!");
		pistaPanel.inicializar(estado, pista.getDistanciaTotal());

		timerCarrera = new Timer(100, e -> onTick());
		timerCarrera.start();
	}

	private void onTick() {
		boolean finalizada = carreraController.simularTurno();
		CorredorDTO[] estado = carreraController.getCorredoresEstado();
		pistaPanel.actualizar(estado);

		if (!finalizada) {
			CorredorDTO lider = carreraController.getLider();
			if (lider != null) {
				statusLabel.setText("Va adelante: " + lider.getNombre()
					+ "   —   " + String.format("%.0f", lider.getDistanciaRecorrida()) + "m");
			}
		}

		if (finalizada) {
			timerCarrera.stop();
			statusLabel.setText("Carrera finalizada!");
			Timer delay = new Timer(900, e -> mostrarResultado());
			delay.setRepeats(false);
			delay.start();
		}
	}

	private void mostrarResultado() {
		CarreraResultadoDTO resultado = carreraController.obtenerResultado();
		if (resultado == null) return;

		String pos;
		if (resultado.getPosicionJugador() == 1)      pos = "1ro";
		else if (resultado.getPosicionJugador() == 2) pos = "2do";
		else                                           pos = "3ro o 4to";

		String mensaje = "Ganador: " + resultado.getNombreGanador() + "\n"
			+ "Segundo: " + resultado.getNombreSegundo() + "\n\n"
			+ "Tu caballo (" + resultado.getNombreCorredorJugador() + ") llegó " + pos + "\n"
			+ "+" + resultado.getPuntajeObtenido() + " puntos ganados\n"
			+ "Puntaje acumulado: " + (int) resultado.getPuntajeAcumuladoJugador() + " pts";

		Object[] opciones = { "Jugar otra vez", "Salir" };
		int opcion = JOptionPane.showOptionDialog(this, mensaje, "Resultado de la carrera",
			JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

		if (opcion == 0) {
			prepararSeleccion();
		} else {
			nombreField.setText("");
			mailField.setText("");
			jugadorActual = null;
			cards.show(cardContainer, "registro");
		}
	}

	class PistaPanel extends JPanel {

		private CorredorDTO[] corredores;
		private double distanciaTotal;

		private final int ALTO_CARRIL    = 70;
		private final int ESPACIO_CARRIL = 12;
		private final int MARGEN_IZQ     = 115;
		private final int MARGEN_DER     = 60;
		private final int ANCHO_MARCADOR = 36;

		PistaPanel() {
			setBackground(new Color(245, 241, 255));
		}

		void inicializar(CorredorDTO[] corredores, double distanciaTotal) {
			this.corredores = corredores;
			this.distanciaTotal = distanciaTotal;
			repaint();
		}

		void actualizar(CorredorDTO[] corredores) {
			this.corredores = corredores;
			repaint();
		}

		void limpiar() {
			this.corredores = null;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (corredores == null) {
				g.setColor(new Color(95, 55, 135));
				g.setFont(new Font("SansSerif", Font.ITALIC, 14));
				String msg = "Seleccioná un caballo e iniciá la carrera";
				FontMetrics fm = g.getFontMetrics();
				g.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
				return;
			}

			Graphics2D g2 = (Graphics2D) g;

			int anchoCarril = getWidth() - MARGEN_IZQ - MARGEN_DER;
			int alturaTotal = corredores.length * ALTO_CARRIL + (corredores.length - 1) * ESPACIO_CARRIL;
			int inicioY = (getHeight() - alturaTotal) / 2;

			for (int i = 0; i < corredores.length; i++) {
				int y = inicioY + i * (ALTO_CARRIL + ESPACIO_CARRIL);
				dibujarCarril(g2, i, y, anchoCarril);
			}
		}

		private void dibujarCarril(Graphics2D g2, int indice, int y, int anchoCarril) {
			CorredorDTO corredor = corredores[indice];
			Color colorCaballo = UIUtils.COLORES_CABALLOS[indice % UIUtils.COLORES_CABALLOS.length];
			boolean esMiCaballo = corredor.isEsDelJugador();

			g2.setColor(new Color(210, 240, 215));
			g2.fillRoundRect(MARGEN_IZQ, y, anchoCarril, ALTO_CARRIL, 14, 14);

			if (esMiCaballo) {
				g2.setColor(UIUtils.COLOR_ORO);
				g2.setStroke(new BasicStroke(2.5f));
			} else {
				g2.setColor(new Color(160, 195, 165));
				g2.setStroke(new BasicStroke(1.2f));
			}
			g2.drawRoundRect(MARGEN_IZQ, y, anchoCarril, ALTO_CARRIL, 14, 14);
			g2.setStroke(new BasicStroke(1f));

			int xMeta = MARGEN_IZQ + anchoCarril - 24;
			int tamañoTile = 12;
			for (int fila = 0; fila < ALTO_CARRIL / tamañoTile + 1; fila++) {
				for (int col = 0; col < 2; col++) {
					int ty = y + fila * tamañoTile;
					if (ty + tamañoTile > y + ALTO_CARRIL) break;
					g2.setColor(((fila + col) % 2 == 0) ? Color.WHITE : new Color(40, 40, 40));
					g2.fillRect(xMeta + col * tamañoTile, ty, tamañoTile, tamañoTile);
				}
			}

			g2.setColor(colorCaballo);
			g2.fillRoundRect(5, y + 14, 8, ALTO_CARRIL - 28, 4, 4);

			g2.setColor(esMiCaballo ? UIUtils.COLOR_ORO : new Color(40, 30, 60));
			g2.setFont(new Font("SansSerif", esMiCaballo ? Font.BOLD : Font.PLAIN, 13));
			g2.drawString(corredor.getNombre(), 18, y + ALTO_CARRIL / 2 + 5);

			double proporcion = Math.min(corredor.getDistanciaRecorrida() / distanciaTotal, 1);
			int alturaMarcador = ALTO_CARRIL - 16;
			int xMarcador = MARGEN_IZQ + 4 + (int)(proporcion * (anchoCarril - ANCHO_MARCADOR - 30));
			int yMarcador = y + 8;

			g2.setColor(new Color(0, 0, 0, 40));
			g2.fillRoundRect(xMarcador + 3, yMarcador + 3, ANCHO_MARCADOR, alturaMarcador, 10, 10);
			g2.setColor(colorCaballo);
			g2.fillRoundRect(xMarcador, yMarcador, ANCHO_MARCADOR, alturaMarcador, 10, 10);
			g2.setColor(new Color(255, 255, 255, 60));
			g2.fillRoundRect(xMarcador + 5, yMarcador + 4, ANCHO_MARCADOR - 16, alturaMarcador / 3, 6, 6);

			g2.setColor(Color.WHITE);
			g2.setFont(new Font("SansSerif", Font.BOLD, 14));
			g2.drawString(String.valueOf(indice + 1), xMarcador + ANCHO_MARCADOR / 2 - 5, yMarcador + alturaMarcador / 2 + 6);

			g2.setColor(new Color(80, 60, 100));
			g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
			g2.drawString(String.format("%.0fm", corredor.getDistanciaRecorrida()), MARGEN_IZQ + anchoCarril + 5, y + ALTO_CARRIL / 2 + 5);
		}
	}
}
