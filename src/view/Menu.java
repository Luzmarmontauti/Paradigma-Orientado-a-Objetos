package view;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    //Atributos
    private JLabel titulo;
    private JButton jugar;
    private JButton salir;

    //Constructor
    public Menu() {
        configurar();
        int ancho = 400;
        int alto = 200;
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

        titulo =  new JLabel("Submarine Attack!");
        titulo.setBounds(150, 50, 150, 20);
        titulo.setOpaque(true);
        titulo.setForeground(Color.BLACK); 

        jugar = new JButton("Jugar");
        jugar.setBounds(50, 120, 100, 25);
        jugar.setOpaque(true);
        jugar.setForeground(Color.BLACK);

        salir = new JButton("Salir");
        salir.setBounds(250, 120, 100, 25);
        salir.setOpaque(true);
        salir.setForeground(Color.BLACK);

        c.add(titulo);
        c.add(jugar);
        c.add(salir);
    }

    private void eventos() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jugar.addActionListener(e -> {
            new Vista();
            dispose();
        });

        salir.addActionListener(e -> {
            System.exit(0);
        });
    }
}
