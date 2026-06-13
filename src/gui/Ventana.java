package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import controller.GameController;
import view.Vista;

public class Ventana extends JFrame {

	private static final long serialVersionUID = -1390206049433269849L;
	

	private List<JLabel> etiquetasBarcos;
	private List<JLabel> etiquetasCargas;
	private JLabel etiquetaSubmarino; 
	private Container contenedor;
	
	public Ventana() {
		this.setTitle("Submarine Attack");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		this.setLocationRelativeTo(null); 
		
		contenedor = this.getContentPane();
		contenedor.setLayout(null); 
		contenedor.setBackground(new Color(135, 206, 235));
		
		etiquetasBarcos = new ArrayList<>();
	
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		
		configurarTeclado();
		
		this.setVisible(true);
		
		Timer timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				
				if (GameController.getInstance().isJuegoTerminado()) {
					((Timer)e.getSource()).stop();
					JOptionPane.showMessageDialog(null, "Juego Terminado - GAME OVER");
					System.exit(0);
				}

			
				GameController.getInstance().actualizar();

			
				for (JLabel label : etiquetasBarcos) { contenedor.remove(label); }
				etiquetasBarcos.clear();

				for (JLabel label : etiquetasCargas) { contenedor.remove(label); }
				etiquetasCargas.clear();

				if (etiquetaSubmarino != null) { 
					contenedor.remove(etiquetaSubmarino); 
				}

				List<Vista> listaBarcos = GameController.getInstance().getBarcosVista();
				for (Vista v : listaBarcos) {
					JLabel labelBarco = new JLabel();
					labelBarco.setBounds(v.getX(), v.getY(), v.getAncho(), v.getAlto());
					labelBarco.setOpaque(true);
					labelBarco.setBackground(Color.GRAY); 
					labelBarco.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					contenedor.add(labelBarco);
					etiquetasBarcos.add(labelBarco);
				}

				List<Vista> listaCargas = GameController.getInstance().getCargasVista();
				for (Vista v : listaCargas) {
					JLabel labelCarga = new JLabel();
					labelCarga.setBounds(v.getX(), v.getY(), v.getAncho(), v.getAlto());
					labelCarga.setOpaque(true);
					labelCarga.setBackground(Color.RED); 
					labelCarga.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					contenedor.add(labelCarga);
					etiquetasCargas.add(labelCarga);
				}

				// 6. DIBUJAR SUBMARINO (Verde Oscuro)
				Vista vSub = GameController.getInstance().getSubmarinoVista();
				if (vSub != null) {
					etiquetaSubmarino = new JLabel();
					etiquetaSubmarino.setBounds(vSub.getX(), vSub.getY(), vSub.getAncho(), vSub.getAlto());
					etiquetaSubmarino.setOpaque(true);
					etiquetaSubmarino.setBackground(new Color(0, 100, 0)); 
					etiquetaSubmarino.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					contenedor.add(etiquetaSubmarino);
				}

				
				contenedor.repaint();
			}
		});
		
		timer.start();
	}

	
	private void configurarTeclado() {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int codigo = e.getKeyCode();
				
				if (codigo == KeyEvent.VK_UP) {
					GameController.getInstance().moverTecla("arriba");
				} else if (codigo == KeyEvent.VK_DOWN) {
					GameController.getInstance().moverTecla("abajo");
				} else if (codigo == KeyEvent.VK_LEFT) {
					GameController.getInstance().moverTecla("izquierda");
				} else if (codigo == KeyEvent.VK_RIGHT) {
					GameController.getInstance().moverTecla("derecha");
				}
			}
		});
	}
}