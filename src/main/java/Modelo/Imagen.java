package Modelo;

import Vista.PanelImagen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

public class Imagen {

    private String path;
    private int[][] pixeles;
    private int alto;
    private int ancho;
    private PropertyChangeSupport cambios;
    private int[][] imgOriginal;

    public Imagen(String path) {
        this.path = path;
        cambios = new PropertyChangeSupport(this);
        leerImagen();
        imgOriginal = pixeles;
    }

    public void leerImagen(){
        BufferedImage bi = null;
        try{
            File f =  new File(path);
            bi = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initImagen(bi);
    }

    private void initImagen(BufferedImage bi){
        ancho = bi.getWidth();
        alto = bi.getHeight();

        pixeles = new int[ancho][alto];

        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                int rgb = bi.getRGB(i,j);
                pixeles[i][j] = rgb;
            }
        }

        cambios.firePropertyChange("IMAGEN", 1,0);
    }

    public void dibujar(Graphics2D g){
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                g.setColor(new Color(pixeles[i][j]));
                g.drawLine(i,j,i,j);
            }
        }
    }

    public void addObserver(PanelImagen panelImagen){
        cambios.addPropertyChangeListener(panelImagen);
    }

    public void setColor(int color, int i, int j){
        this.pixeles[i][j] = color;
    }

    public int getColor(int i, int j){
        return this.pixeles[i][j];
    }

    public void actualizarImagen(){
        cambios.firePropertyChange("IMAGEN",1,0);
    }

    public void restablecerImagen(){
        setPixeles(imgOriginal);
        actualizarImagen();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int[][] getPixeles() {
        return pixeles;
    }

    public void setPixeles(int[][] pixeles) {
        this.pixeles = pixeles;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

}
