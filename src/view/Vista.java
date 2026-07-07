package view;

import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Vista extends JFrame {

    // Atributos
    private static final int Y_SUPERFICIE = 30;
    private static final int ALTURA_JUEGO = 550;

    private JLabel submarino;
    private List<JLabel> barcos;
    private List<JLabel> cargasDeProfundidad;
    private List<JLabel> torpedos;
    private JLabel nivel;
    private JLabel puntaje;
    private JLabel vidas;
    private JLabel porcentajeVida;
    private JLabel profundidad;
    private JLabel lineaSuperficie;

    private Timer timer;

    // Constructor
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

    // Metodos
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
        torpedos = new ArrayList<>();

        lineaSuperficie = new JLabel();
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
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    GameController.getInstance().lanzarTorpedo();
                }
            }
        });

        timer = new Timer(20, e -> { actualizar(); });
        timer.start();
    }

    private void actualizar() {
        Container contenedor = this.getContentPane();

        int vidasAntes = GameController.getInstance().getVidas();
        int nivelAntes = GameController.getInstance().getNivel();

        GameController.getInstance().actualizar();

        profundidad.setText("Profundidad: " + GameController.getInstance().getSubmarinoView().getPosicionY() + " mts");

        if (GameController.getInstance().getPorcentajeVida() >= 70) {
            porcentajeVida.setForeground(Color.GREEN);
        } else if (GameController.getInstance().getPorcentajeVida() >= 50) {
            porcentajeVida.setForeground(Color.YELLOW);
        } else if (GameController.getInstance().getPorcentajeVida() >= 30) {
            porcentajeVida.setForeground(Color.ORANGE);
        } else {
            porcentajeVida.setForeground(Color.RED);
        }

        if (vidasAntes > GameController.getInstance().getVidas()) {
            JOptionPane.showMessageDialog(this, "Perdiste una vida!", "Vida Perdida", JOptionPane.WARNING_MESSAGE);
        }
        if (vidasAntes < GameController.getInstance().getVidas()) {
            JOptionPane.showMessageDialog(this, "Ganaste una vida!", "Vida Adquirida", JOptionPane.INFORMATION_MESSAGE);
        }
        if (nivelAntes < GameController.getInstance().getNivel()) {
            JOptionPane.showMessageDialog(this, "Pasaste al siguiente nivel!", "Nivel Superado", JOptionPane.INFORMATION_MESSAGE);
        }

        SubmarinoView vistaSubmarino = GameController.getInstance().getSubmarinoView();
        int yPixel = (int)(Y_SUPERFICIE + (vistaSubmarino.getPosicionY() / 800.0) * ALTURA_JUEGO);
        submarino.setLocation(vistaSubmarino.getPosicionX(), yPixel);

        // Barcos
        List<BarcoView> barcoViews = GameController.getInstance().getBarcoView();
        while (barcos.size() < barcoViews.size()) {
            JLabel labelBarco = new JLabel("Barco");
            labelBarco.setOpaque(true);
            labelBarco.setBackground(new Color(211, 205, 193));
            contenedor.add(labelBarco);
            barcos.add(labelBarco);
        }
        while (barcos.size() > barcoViews.size()) {
            JLabel labelSobrante = barcos.remove(barcos.size() - 1);
            contenedor.remove(labelSobrante);
        }
        for (int i = 0; i < barcoViews.size(); i++) {
            BarcoView barcoVista = barcoViews.get(i);
            barcos.get(i).setBounds(barcoVista.getPosicionX(), barcoVista.getPosicionY(), 40, 20);
        }

        // Cargas
        List<CargaDeProfundidadView> cargaViews = GameController.getInstance().getCargaView();
        while (cargasDeProfundidad.size() < cargaViews.size()) {
            JLabel labelCarga = new JLabel("Bomb");
            labelCarga.setOpaque(true);
            labelCarga.setBackground(new Color(214, 40, 40));
            contenedor.add(labelCarga);
            cargasDeProfundidad.add(labelCarga);
        }
        while (cargasDeProfundidad.size() > cargaViews.size()) {
            JLabel labelSobrante = cargasDeProfundidad.remove(cargasDeProfundidad.size() - 1);
            contenedor.remove(labelSobrante);
        }
        for (int i = 0; i < cargaViews.size(); i++) {
            CargaDeProfundidadView cargaVista = cargaViews.get(i);
            yPixel = (int)(Y_SUPERFICIE + (cargaVista.getPosicionY() / 800.0) * ALTURA_JUEGO);
            cargasDeProfundidad.get(i).setBounds(cargaVista.getPosicionX(), yPixel, 35, 15);
        }

        // Torpedos
        List<TorpedoView> torpedoViews = GameController.getInstance().getTorpedoView();
        while (torpedos.size() < torpedoViews.size()) {
            JLabel labelTorpedo = new JLabel();
            labelTorpedo.setOpaque(true);
            labelTorpedo.setBackground(new Color(255, 255, 0));
            contenedor.add(labelTorpedo);
            torpedos.add(labelTorpedo);
        }
        while (torpedos.size() > torpedoViews.size()) {
            JLabel labelSobrante = torpedos.remove(torpedos.size() - 1);
            contenedor.remove(labelSobrante);
        }
        for (int i = 0; i < torpedoViews.size(); i++) {
            TorpedoView torpedoVista = torpedoViews.get(i);
            yPixel = (int)(Y_SUPERFICIE + (torpedoVista.getPosicionY() / 800.0) * ALTURA_JUEGO);
            torpedos.get(i).setBounds(torpedoVista.getPosicionX(), yPixel, torpedoVista.getAncho(), torpedoVista.getAlto());
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