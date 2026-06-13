package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import controller.GameController;
import view.Vista;
import java.util.List;
import java.util.ArrayList;

public class Ventana extends JFrame {

	private static final long serialVersionUID = -1390206049433269849L;
	private JLabel blanco, negro;
	
	public Ventana() {
		this.setTitle("Submarine Attack");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //
		this.setLocationRelativeTo(null); //Esto lo hacemos para centrar en nuestra pantalla.
		
		
		Container contenedor = this.getContentPane();
		contenedor.setLayout(null); //esto lo hacemos para manejar X e Y de manera manual :)
		contenedor.setBackground(new Color(135, 206, 235));
		
		ArrayList etiquetasBarcos = new ArrayList<>();
		
		//Dejo hasta acá --> lo demás es el codigo del profe
		
		blanco = new JLabel();
		blanco.setBounds(740, 10, 30, 30);
		blanco.setOpaque(true);
		blanco.setBackground(Color.WHITE);
		blanco.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		negro = new JLabel();
		negro.setBounds(10, 10, 30, 30);
		negro.setOpaque(true);
		negro.setBackground(Color.BLACK);
		negro.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		c.add(negro);
		c.add(blanco);
		
		this.setVisible(true);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Timer timer = new Timer(10, new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

		        if (GameController.getInstance().isJuegoTerminado()) {
		            ((Timer)e.getSource()).stop();
		            JOptionPane.showMessageDialog(null, "Juego Terminado");
		            System.exit(0);
		        }

		        Vista blancoV = GameController.getInstance().getPosicionBlanco();
		        blanco.setBounds(blancoV.getX(), blancoV.getY(), blancoV.getAncho(), blancoV.getAlto());
		        Vista negroV = GameController.getInstance().getPosicionNegro();
		        negro.setBounds(negroV.getX(), negroV.getY(), negroV.getAncho(), negroV.getAlto());
		    }
		});
		
		timer.start();
	}
}
