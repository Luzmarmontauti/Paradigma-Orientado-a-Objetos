package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
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
    
    // HUD
    private JLabel lblTitulo;
    private JLabel lblPuntaje;
    private JLabel lblVidas;
    private JLabel lblNivel;
    private JLabel lblEnergia;
    private JLabel lblProfundidad; // RF-25
    
    private Container contenedor;
    
    // Variables de control para evitar bucles infinitos de Pop-ups
    private int vidasAnteriores = 3;
    private int nivelAnterior = 1;

    public Ventana() {
        this.setTitle("Submarine Attack");
        this.setSize(850, 900); // Ajustamos levemente para que entre todo cómodo
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        this.setLocationRelativeTo(null); 
        
        contenedor = this.getContentPane();
        contenedor.setLayout(null); 
        contenedor.setBackground(new Color(24, 44, 97)); 

        etiquetasBarcos = new ArrayList<>();
        etiquetasCargas = new ArrayList<>();

        // Inicialización del HUD
        lblTitulo = new JLabel("SUBMARINE ATTACK");
        lblTitulo.setBounds(15, 10, 200, 25);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        contenedor.add(lblTitulo);

        lblPuntaje = new JLabel("Puntos: 0");
        lblPuntaje.setBounds(230, 15, 110, 20);
        lblPuntaje.setFont(new Font("Arial", Font.BOLD, 13));
        lblPuntaje.setForeground(Color.YELLOW);
        contenedor.add(lblPuntaje);

        lblVidas = new JLabel("Vidas: 3");
        lblVidas.setBounds(350, 15, 80, 20);
        lblVidas.setFont(new Font("Arial", Font.BOLD, 13));
        lblVidas.setForeground(Color.GREEN);
        contenedor.add(lblVidas);

        lblNivel = new JLabel("Nivel: 1");
        lblNivel.setBounds(440, 15, 80, 20);
        lblNivel.setFont(new Font("Arial", Font.BOLD, 13));
        lblNivel.setForeground(Color.CYAN);
        contenedor.add(lblNivel);

        lblEnergia = new JLabel("Energía: 100%");
        lblEnergia.setBounds(530, 15, 120, 20);
        lblEnergia.setFont(new Font("Arial", Font.BOLD, 13));
        lblEnergia.setForeground(Color.ORANGE);
        contenedor.add(lblEnergia);

        // RF-25: Indicador de Profundidad en metros
        lblProfundidad = new JLabel("Profundidad: 0 m");
        lblProfundidad.setBounds(660, 15, 160, 20);
        lblProfundidad.setFont(new Font("Arial", Font.BOLD, 13));
        lblProfundidad.setForeground(new Color(236, 240, 241));
        contenedor.add(lblProfundidad);

        this.setFocusable(true);
        this.requestFocusInWindow();
        configurarTeclado();
        this.setVisible(true);
        
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (GameController.getInstance().isJuegoTerminado()) {
                    ((Timer)e.getSource()).stop();
                    JOptionPane.showMessageDialog(null, "GAME OVER - El submarino fue destruido por completo.");
                    System.exit(0);
                }

                // Guardar estados antes del update para comparar cambios
                int vidasAntes = GameController.getInstance().getVidas();
                int nivelAntes = GameController.getInstance().getNivel();

                // Avanzar físicas
                GameController.getInstance().actualizar();

                int vidasDespues = GameController.getInstance().getVidas();
                int nivelDespues = GameController.getInstance().getNivel();

                // RF-27: Mensaje al perder una vida
                if (vidasDespues < vidasAntes && vidasDespues > 0) {
                    JOptionPane.showMessageDialog(null, "¡ALERTA! Perdiste una vida. Te quedan: " + vidasDespues);
                }
                
                // RF-28: Mensaje al ganar una vida extra (por llegar a múltiplos de 500 puntos)
                if (vidasDespues > vidasAntes) {
                    JOptionPane.showMessageDialog(null, "❤️ Conseguiste 500 puntos y ganaste una VIDA EXTRA. Vidas: " + vidasDespues);
                }

                // RF-30: Mensaje al avanzar de nivel
                if (nivelDespues > nivelAntes) {
                    JOptionPane.showMessageDialog(null, "¡NIVEL COMPLETADO! Avanzás al Nivel " + nivelDespues + "\nLos barcos enemigos ahora son más rápidos.");
                }

                // RF-29: Mensaje flotante de puntos por explosión
                String msgPuntos = GameController.getInstance().checkMensajePuntos();
                if (msgPuntos != null && !msgPuntos.isEmpty()) {
                    System.out.println(msgPuntos); // Lo tira en consola para no pausar frenéticamente el gameplay gráfico
                }

                // Actualizar HUD
                lblPuntaje.setText("Puntos: " + GameController.getInstance().getPuntaje());
                lblVidas.setText("Vidas: " + vidasDespues);
                lblNivel.setText("Nivel: " + nivelDespues);
                lblEnergia.setText("Energía: " + GameController.getInstance().getPorcentajeVida() + "%");
                
                // RF-25: Muestra la profundidad en metros dinámicamente
                lblProfundidad.setText("Profundidad: " + (int)GameController.getInstance().getSubmarinoProfundidad() + " m");

                // Renderizado gráfico común
                for (JLabel label : etiquetasBarcos) { contenedor.remove(label); }
                etiquetasBarcos.clear();

                for (JLabel label : etiquetasCargas) { contenedor.remove(label); }
                etiquetasCargas.clear();

                if (etiquetaSubmarino != null) { contenedor.remove(etiquetaSubmarino); }

                // Pintar elementos
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

                Vista vSub = GameController.getInstance().getSubmarinoVista();
                if (vSub != null) {
                    etiquetaSubmarino = new JLabel();
                    etiquetaSubmarino.setBounds(vSub.getX(), vSub.getY(), vSub.getAncho(), vSub.getAlto());
                    etiquetaSubmarino.setOpaque(true);
                    etiquetaSubmarino.setBackground(new Color(46, 204, 113));
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
                if (codigo == KeyEvent.VK_UP) GameController.getInstance().moverTecla("arriba");
                if (codigo == KeyEvent.VK_DOWN) GameController.getInstance().moverTecla("abajo");
                if (codigo == KeyEvent.VK_LEFT) GameController.getInstance().moverTecla("izquierda");
                if (codigo == KeyEvent.VK_RIGHT) GameController.getInstance().moverTecla("derecha");
            }
        });
    }
}