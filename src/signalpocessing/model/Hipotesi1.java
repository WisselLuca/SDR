package signalpocessing.model;

import signalprocessing.library.FileBuffer;

/**
 * Created by Luca on 12/05/2015.
 */
public class Hipotesi1 {

    //Calcolo dell'energia di un segnale
    //Usando la formula vista nelle slide
    public static double calcoloEnergiaSegnale(Signal segnaleDaInput){
        double energia = 0;
        Complex tmp = new Complex(-1,-1);
        for (Complex complex : segnaleDaInput) {
            tmp.setreale(complex.getReale());
            tmp.setimmaginaria(complex.getImmaginaria());
            energia+= Math.pow(tmp.abs(), 2);
        }
        return energia/segnaleDaInput.size();
    }

    //Confronta il vettore di energia del segnale in ingresso e lo confronta con la soglia, se maggiore aumenta il count e restituisce la percentuale di successo
    public static double detectionPercentage(Signal segnaleDaInput){
        Detector detector = new Detector();
        double[]energyVetctor = getEnergyVector(segnaleDaInput);
        double soglia = detector.calculateSoglia(segnaleDaInput);
        int i;
        int count=0;
        double result=0;
        for(i=0; i<energyVetctor.length; i++){
            if(energyVetctor[i]>soglia) {
                count++;
            }
        }
        result= ((double)count/(double)i)*100;
        return result;
    }

    public static double[] getEnergyVector(Signal signal) {
        double[] output = new double[signal.size() / 1000];
        int count = 0;
        Signal tempSignal;
        for (int i = 0; i < signal.size(); i += 1000) {
            tempSignal = signal.subList(i, i + 999);
            output[count] = calcoloEnergiaSegnale(tempSignal);
            count++;
        }
        return output;
    }




}
