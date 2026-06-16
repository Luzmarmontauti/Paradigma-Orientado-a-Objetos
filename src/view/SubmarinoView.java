package view;

public class SubmarinoView {

    //Atributos
    private int posicionX;
    private int posicionY;
    private int ancho;
    private int alto;

    //Constructor


    public SubmarinoView(int posicionX, int posicionY, int ancho, int alto) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.ancho = ancho;
        this.alto = alto;
    }

    //Getters y Setters
    public int getPosicionX() { return posicionX; }

    public void setPosicionX(int posicionX) { this.posicionX = posicionX; }

    public int getPosicionY() { return posicionY; }

    public void setPosicionY(int posicionY) { this.posicionY = posicionY; }

    public int getAlto() { return alto; }

    public void setAlto(int alto) { this.alto = alto; }

    public int getAncho() { return ancho; }

    public void setAncho(int ancho) { this.ancho = ancho; }


}
