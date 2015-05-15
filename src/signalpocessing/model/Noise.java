package signalpocessing.model;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Luca on 12/05/2015.
 */


public class Noise {

    private double varianza;
    private double pot_rumore;
    private int length;
    private double[] parteReale;
    private double[]parteImmaginaria;

    public Noise(){

    }

    public Noise(double snr, int length) {
        this.calculateAttributes(snr, length);
//        Random campione;
//        double snr_linearizzato = Math.pow(10, (snr/10));
//        this.pot_rumore = (1/snr_linearizzato);
//        this.length = length;
//        this.parteReale = new double[length];
//        for (int i = 0; i < this.length; i++) {
//            campione = new Random();
//            parteReale[i] = campione.nextGaussian() *
//                    Math.sqrt(pot_rumore/2);
//        }
//        this.parteImmaginaria = new double[length];
//        for (int i = 0; i < this.length; i++) {
//            campione = new Random();
//            parteImmaginaria[i] = campione.nextGaussian() *
//                    Math.sqrt(pot_rumore/2);
//        }
    }

    public void calculateAttributes(double snr, int length){
        Random campione;
        double snr_linearizzato = Math.pow(10, (snr/10));
        this.pot_rumore = (1/snr_linearizzato);
        this.length = length;
        this.parteReale = new double[length];
        for (int i = 0; i < this.length; i++) {
            campione = new Random();
            parteReale[i] = campione.nextGaussian() *
                    Math.sqrt(pot_rumore/2);
        }
        this.parteImmaginaria = new double[length];
        for (int i = 0; i < this.length; i++) {
            campione = new Random();
            parteImmaginaria[i] = campione.nextGaussian() *
                    Math.sqrt(pot_rumore/2);
        }
    }

    public double[] getParteImmaginaria() {
        return parteImmaginaria;
    }

    public void setParteImmaginaria(double[] parteImmaginaria) {
        this.parteImmaginaria = parteImmaginaria;
    }

    public double[] getParteReale() {
        return parteReale;
    }

    public void setParteReale(double[] parteReale) {
        this.parteReale = parteReale;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getPot_rumore() {
        return pot_rumore;
    }

    public void setPot_rumore(double pot_rumore) {
        this.pot_rumore = pot_rumore;
    }

    public double getVarianza() {
        return varianza;
    }

    public void setVarianza(double varianza) {
        this.varianza = varianza;
    }


    @Override
    public String toString() {
        return "Noise{" +
                "parteImmaginaria=" + Arrays.toString(parteImmaginaria) +
                ", parteReale=" + Arrays.toString(parteReale) +
                '}';
    }
}
