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
    private static final int Y_SUPERFICIE = 30;
    private static final int ALTURA_JUEGO = 530;

    private JLabel submarino;
    private List<JLabel> barcos;
    private List<JLabel> cargasDeProfundidad;
    private JLabel nivel;
    private JLabel puntaje;
    private JLabel vidas;
    private JLabel porcentajeVida;
    private JLabel lineaSuperficie;

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
        c.setBackground(new Color(0, 20, 60));
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

        lineaSuperficie =  new JLabel();
        lineaSuperficie.setOpaque(true);
        lineaSuperficie.setBackground(Color.CYAN);
        lineaSuperficie.setBounds(0, 50, GameController.getInstance().getAnchoArea(), 3);
        c.add(lineaSuperficie);

        nivel = new JLabel("Nivel: " + String.valueOf(GameController.getInstance().getNivel()));
        nivel.setForeground(Color.WHITE);
        nivel.setBounds(50, 560, 150, 30);
        c.add(nivel);

        puntaje = new JLabel("Puntaje: " + String.valueOf(GameController.getInstance().getPuntaje()));
        puntaje.setForeground(Color.WHITE);
        puntaje.setBounds(250, 560, 150, 30);
        c.add(puntaje);

        vidas = new JLabel("Vidas: " + String.valueOf(GameController.getInstance().getVidas()));
        vidas.setForeground(Color.WHITE);
        vidas.setBounds(500, 560, 150, 30);
        c.add(vidas);

        porcentajeVida = new JLabel("Vida: " + String.valueOf(GameController.getInstance().getPorcentajeVida()) + " %");
        porcentajeVida.setForeground(Color.WHITE);
        porcentajeVida.setBounds(680, 560, 150, 30);
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
        Container contenedor = this.getContentPane();
        GameController.getInstance().actualizar();

        // yPixel = Y_SUPERFICIE + (profundidad / 800.0) * ALTURA_JUEGO
        // profundidad / 800.0 fraccion del recorrido total (ej: 400/800 = 0.5 = mitad del fondo)
        // * ALTURA_JUEGO convierte esa fraccion a pixeles disponibles en pantalla (530px)
        // + Y_SUPERFICIE desplaza hacia abajo porque el agua no empieza en el pixel 0 sino en el 30
        SubmarinoView vistaSubmarino = GameController.getInstance().getSubmarinoView();
        int yPixel = (int)(Y_SUPERFICIE + (vistaSubmarino.getPosicionY() / 800.0) * ALTURA_JUEGO);
        submarino.setLocation(vistaSubmarino.getPosicionX(), yPixel);

        for (JLabel labelBarco : barcos) { contenedor.remove(labelBarco); }
        barcos.clear();
        for (BarcoView barcoVista : GameController.getInstance().getBarcoView()) {
            JLabel labelBarco = new JLabel("Barco");
            labelBarco.setBounds(barcoVista.getPosicionX(), barcoVista.getPosicionY(), 30, 20);
            labelBarco.setOpaque(true);
            labelBarco.setBackground(Color.ORANGE);
            contenedor.add(labelBarco);
            barcos.add(labelBarco);
        }

        for (JLabel labelCarga : cargasDeProfundidad) { contenedor.remove(labelCarga); }
        cargasDeProfundidad.clear();
        for (CargaDeProfundidadView cargaVista : GameController.getInstance().getCargaView()) {
            JLabel labelCarga = new JLabel("Bomba");
            yPixel = (int)(Y_SUPERFICIE + (cargaVista.getPosicionY() / 800.0) * ALTURA_JUEGO);
            labelCarga.setBounds(cargaVista.getPosicionX(), yPixel, 30, 20);
            labelCarga.setOpaque(true);
            labelCarga.setBackground(Color.RED);
            contenedor.add(labelCarga);
            cargasDeProfundidad.add(labelCarga);
        }

        nivel.setText("Nivel: " + GameController.getInstance().getNivel());
        puntaje.setText("Puntaje: " + GameController.getInstance().getPuntaje());
        vidas.setText("Vidas: " + GameController.getInstance().getVidas());
        porcentajeVida.setText("Vida: " + GameController.getInstance().getPorcentajeVida() + " %");

        repaint();
        if (GameController.getInstance().isJuegoTerminado()) { timer.stop(); }
    }

}
