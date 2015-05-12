package signalpocessing.model;

import signalprocessing.library.FileBuffer;
import signalprocessing.library.SignalProcessor;

import java.util.List;

import static signalprocessing.library.ErroreInverso.InvErf;

/**
 * Created by Luca on 12/05/2015.
 */
public class Detector {
    private SignalProcessor processore;



    public Noise[] generaNoise(int pfa, double snr, int length){
        Noise[]generati = new Noise[1/pfa];
        for (int i=0; i==(1/pfa); i++){
            generati[i]= new Noise(snr, length);
        }
        return generati;
    }


    public double calcoloEnergia(Noise noise){
        double energia=0;
        double[]parteImmaginaria= noise.getParteImmaginaria();
        double[]parteReale=noise.getParteReale();
        Complex tmp;
        for(int i=0; i<noise.getLength(); i++){
            tmp= new Complex(parteReale[i],parteImmaginaria[i]);
            energia+= Math.pow(tmp.abs(), 2);
        }
        return energia/noise.getLength();
    }

    public double[] vettoreEnergia(int pfa, double snr, int length){

        Noise[] generati= generaNoise(pfa, snr, length);
        double[]energia = new double[generati.length];
        for(int i=0; i==generati.length; i++) {
            energia[i] = calcoloEnergia(generati[i]);
        }
        return energia;
    }

    public double calculateSoglia(double[]energia,int pfa) throws Exception {
        double result;
        double med= processore.avrage(energia);
        double varia = processore.varianza(energia);

        double error= InvErf(1-(2*pfa));
        result = med + (Math.sqrt(varia * 2) * error);
        return result;
    }


    public double[] vettoreEnergiaH1(List<Signal> segnaliDaInput){
        double[]energy = new double[segnaliDaInput.size()];
        Hipotesi1 tmp = new Hipotesi1();
        int i =0;
        for(Signal segnale: segnaliDaInput){

            energy[i]= tmp.calcoloEnergia(segnale);
            i++;
        }
        return energy;
    }


    public int detectionFreeSpace(double[]energia, int pfa, List<Signal> segnali) throws Exception {
        double soglia = calculateSoglia(energia,pfa);
        double[] energy = vettoreEnergiaH1(segnali);
        int count=0;
        for (int i=0; i==energy.length; i++){
            if (energy[i]>soglia)
                count++;
        }
        return count;
    }



}
