package view;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal del juego. Maneja la interfaz gráfica,
 * el game loop y los eventos de teclado del jugador.
 */
public class Vista extends JFrame {

    //Atributos
    private JLabel submarino;
    private List<JLabel> barcos;
    private List<JLabel> cargasDeProfundidad;
    private JLabel nivel;
    private JLabel puntaje;
    private JLabel vidas;
    private JLabel porcentajeVida;

    private Timer timer;

    //Constructor
    public Vista() {
        configurar();
        int ancho = GameController.getInstance().getAnchoArea();
        int alto = GameController.getInstance().getAltoArea();
        getContentPane().setPreferredSize(new Dimension(ancho, alto));
        pack();
        this.setVisible(true);
        setResizable(false);
        this.setTitle("Submarine Attack!");
        eventos();
    }

    //Metodos
    private void configurar() {
        Container c = this.getContentPane();
        c.setLayout(null);
        submarino = new JLabel("Sub");
        SubmarinoView subview = GameController.getInstance().getSubmarinoView();
        submarino.setBounds(subview.getPosicionX(), subview.getPosicionY(), subview.getAncho(), subview.getAlto());
        submarino.setHorizontalAlignment(SwingConstants.CENTER);
        submarino.setForeground(Color.BLACK);
        submarino.setOpaque(true);
        submarino.setBackground(Color.BLUE);
        c.add(submarino);

        barcos = new ArrayList<>();
        cargasDeProfundidad = new ArrayList<>();

        nivel = new JLabel("Nivel: " + String.valueOf(GameController.getInstance().getNivel()));
        nivel.setForeground(Color.BLACK);
        nivel.setBounds(10, 560, 150, 30);
        c.add(nivel);

        puntaje = new JLabel("Puntaje: " + String.valueOf(GameController.getInstance().getPuntaje()));
        puntaje.setForeground(Color.BLACK);
        puntaje.setBounds(200, 560, 150, 30);
        c.add(puntaje);

        vidas = new JLabel("Vidas: " + String.valueOf(GameController.getInstance().getVidas()));
        vidas.setForeground(Color.BLACK);
        vidas.setBounds(450, 560, 150, 30);
        c.add(vidas);

        porcentajeVida = new JLabel("Vida: " + String.valueOf(GameController.getInstance().getPorcentajeVida()) + " %");
        porcentajeVida.setForeground(Color.BLACK);
        porcentajeVida.setBounds(630, 560, 150, 30);
        c.add(porcentajeVida);
    }

    private void eventos() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    GameController.getInstance().moverTecla("arriba");
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    GameController.getInstance().moverTecla("abajo");
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    GameController.getInstance().moverTecla("derecha");
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    GameController.getInstance().moverTecla("izquierda");
                }

            }
        });

        timer = new Timer(20, e -> {actualizar(); });
        timer.start();
    }

    private void actualizar() {
        Container c = this.getContentPane();
        GameController.getInstance().actualizar();
        SubmarinoView subView = GameController.getInstance().getSubmarinoView();
        submarino.setLocation(subView.getPosicionX(), subView.getPosicionY());

        for (JLabel labelBarco : barcos) { c.remove(labelBarco); }
        barcos.clear();
        for (BarcoView bv : GameController.getInstance().getBarcoView()) {
            JLabel labelBarco = new JLabel("Barco");
            labelBarco.setBounds(bv.getPosicionX(), bv.getPosicionY(), 30, 20);
            labelBarco.setOpaque(true);
            labelBarco.setBackground(Color.ORANGE);
            c.add(labelBarco);
            barcos.add(labelBarco);
        }

        for (JLabel labelCarga : cargasDeProfundidad) { c.remove(labelCarga); }
        cargasDeProfundidad.clear();
        for (CargaDeProfundidadView cv : GameController.getInstance().getCargaView()) {
            JLabel labelCarga = new JLabel("Bomba");
            labelCarga.setBounds(cv.getPosicionX(), cv.getPosicionY(), 30, 20);
            labelCarga.setOpaque(true);
            labelCarga.setBackground(Color.RED);
            c.add(labelCarga);
            cargasDeProfundidad.add(labelCarga);
        }


        nivel.setText("Nivel: " + String.valueOf(GameController.getInstance().getNivel()));
        puntaje.setText("Puntaje: " + String.valueOf(GameController.getInstance().getPuntaje()));
        vidas.setText("Vidas: " + String.valueOf(GameController.getInstance().getVidas()));
        porcentajeVida.setText("Vida: " + String.valueOf(GameController.getInstance().getPorcentajeVida()) + " %");


        repaint();
        if (GameController.getInstance().isJuegoTerminado()) { timer.stop(); }
    }

}
