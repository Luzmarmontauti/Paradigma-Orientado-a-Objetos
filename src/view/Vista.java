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
    private static final int Y_SUPERFICIE = 30;//constante para escalar las profundidades
    private static final int ALTURA_JUEGO = 550;//constante para escalar las profundidades

    private JLabel submarino;
    private List<JLabel> barcos;
    private List<JLabel> cargasDeProfundidad;
    private JLabel nivel;
    private JLabel puntaje;
    private JLabel vidas;
    private JLabel porcentajeVida;
    private JLabel profundidad;
    private JLabel lineaSuperficie;

    private Timer timer;

    //Constructor
    public Vista() {
        configurar();
        int ancho = GameController.getInstance().getAnchoArea();
        int alto = GameController.getInstance().getAltoArea();
        getContentPane().setPreferredSize(new Dimension(ancho, alto));
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
        setResizable(false);
        this.setTitle("Submarine Attack!");
        eventos();
    }

    //Metodos
    private void configurar() {
        Container c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(27, 60, 83));
        submarino = new JLabel("Sub");
        SubmarinoView subview = GameController.getInstance().getSubmarinoView();
        submarino.setBounds(subview.getPosicionX(), subview.getPosicionY(), subview.getAncho(), subview.getAlto());
        submarino.setHorizontalAlignment(SwingConstants.CENTER);
        submarino.setForeground(Color.BLACK);
        submarino.setOpaque(true);
        submarino.setBackground(Color.YELLOW);
        c.add(submarino);

        barcos = new ArrayList<>();
        cargasDeProfundidad = new ArrayList<>();

        lineaSuperficie =  new JLabel();
        lineaSuperficie.setOpaque(true);
        lineaSuperficie.setBackground(new Color(57, 177, 209));
        lineaSuperficie.setBounds(0, 40, GameController.getInstance().getAnchoArea(), 2);
        c.add(lineaSuperficie);

        nivel = new JLabel("Nivel: " + String.valueOf(GameController.getInstance().getNivel()));
        nivel.setForeground(new Color(252, 191, 73));
        nivel.setBounds(50, 590, 150, 30);
        c.add(nivel);

        puntaje = new JLabel("Puntaje: " + String.valueOf(GameController.getInstance().getPuntaje()));
        puntaje.setForeground(new Color(252, 191, 73));
        puntaje.setBounds(180, 590, 150, 30);
        c.add(puntaje);

        vidas = new JLabel("Vidas: " + String.valueOf(GameController.getInstance().getVidas()));
        vidas.setForeground(new Color(252, 191, 73));
        vidas.setBounds(350, 590, 150, 30);
        c.add(vidas);

        porcentajeVida = new JLabel("Vida: " + String.valueOf(GameController.getInstance().getPorcentajeVida()) + " %");
        porcentajeVida.setForeground(Color.GREEN);
        porcentajeVida.setBounds(500, 590, 150, 30);
        c.add(porcentajeVida);

        profundidad = new JLabel("Profundidad: " + GameController.getInstance().getSubmarinoView().getPosicionY() + " mts");
        profundidad.setForeground(new Color(252, 191, 73));
        profundidad.setBounds(630, 590, 150, 30);
        c.add(profundidad);

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

        int vidasAntes = GameController.getInstance().getVidas();
        int nivelAntes = GameController.getInstance().getNivel();

        //Actualizamos
        GameController.getInstance().actualizar();

        //Actualizacion Stat profundidad
        profundidad.setText("Profundidad: " + GameController.getInstance().getSubmarinoView().getPosicionY() + " mts");

        //Cambio de color en porcentaje vida
        if (GameController.getInstance().getPorcentajeVida() >= 70) {
            porcentajeVida.setForeground(Color.GREEN);
        } else if (GameController.getInstance().getPorcentajeVida() >= 50) {
            porcentajeVida.setForeground(Color.YELLOW);
        }  else if (GameController.getInstance().getPorcentajeVida() >= 30) {
            porcentajeVida.setForeground(Color.ORANGE);
        } else porcentajeVida.setForeground(Color.RED);

        //Comparamos y mostramos los mensajes correspondientes si corresponde
        if (vidasAntes > GameController.getInstance().getVidas()) {JOptionPane.showMessageDialog(this, "Perdiste una vida!", "Vida Perdida", JOptionPane.WARNING_MESSAGE);}
        if (vidasAntes < GameController.getInstance().getVidas()) {JOptionPane.showMessageDialog(this, "Ganaste una vida!", "Vida Adquirida", JOptionPane.INFORMATION_MESSAGE);}
        if (nivelAntes < GameController.getInstance().getNivel()) {JOptionPane.showMessageDialog(this, "Pasaste al siguiente nivel!", "Nivel Superado", JOptionPane.INFORMATION_MESSAGE);}


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
            labelBarco.setBounds(barcoVista.getPosicionX(), barcoVista.getPosicionY(), 40, 20);
            labelBarco.setOpaque(true);
            labelBarco.setBackground(new Color(211, 205, 193));
            contenedor.add(labelBarco);
            barcos.add(labelBarco);
        }

        for (JLabel labelCarga : cargasDeProfundidad) { contenedor.remove(labelCarga); }
        cargasDeProfundidad.clear();
        for (CargaDeProfundidadView cargaVista : GameController.getInstance().getCargaView()) {
            JLabel labelCarga = new JLabel("Bomb");
            yPixel = (int)(Y_SUPERFICIE + (cargaVista.getPosicionY() / 800.0) * ALTURA_JUEGO);
            labelCarga.setBounds(cargaVista.getPosicionX(), yPixel, 35, 15);
            labelCarga.setOpaque(true);
            labelCarga.setBackground(new Color(214, 40, 40));
            contenedor.add(labelCarga);
            cargasDeProfundidad.add(labelCarga);
        }

        nivel.setText("Nivel: " + GameController.getInstance().getNivel());
        puntaje.setText("Puntaje: " + GameController.getInstance().getPuntaje());
        vidas.setText("Vidas: " + GameController.getInstance().getVidas());
        porcentajeVida.setText("Vida: " + GameController.getInstance().getPorcentajeVida() + " %");

        repaint();
        if (GameController.getInstance().isJuegoTerminado()) {
            JOptionPane.showMessageDialog(this, "Perdiste! Juego terminado", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            timer.stop();
            GameController.resetearInstancia();
            new Menu();
            dispose();
        }
    }

}
