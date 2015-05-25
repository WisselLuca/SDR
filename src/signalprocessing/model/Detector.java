package signalprocessing.model;

import signalprocessing.library.ErroreInverso;
import signalprocessing.library.SignalProcessor;

/**
 * Created by Luca on 12/05/2015.
 */
public class Detector {
    private SignalProcessor processore = new SignalProcessor();

    private double pfa = 0.01;


    //Calcolo snr
    //Potenza SegnaleUtile / Potenza Rumore
    public double calcolaSNR(Signal segnaleDaInput){
        double snr= 1/ calcoloPotenzaRumore(segnaleDaInput);
        snr= Math.log10(snr)*10;
            return snr;
    }

    //Passando da input il Segnale, sapendo che la potenza del segnale � stata calcolata con il meteodo dell'energia,
    //dalla Potenza del Rumore=1, ottengo per differenza la potenza del rumore
    public double calcoloPotenzaRumore(Signal segnaleDaInput){
        double potenzaRumore=0;
        potenzaRumore= Hipotesi1.calcoloEnergiaSegnale(segnaleDaInput)-1;
        return potenzaRumore;
    }



//Genero 1/0,001= 1000 Rumori con SNR calcolato prima e di lunghezza pari alla lunghezza del segnale del campione, nel nostro caso 1 milione
 //Optimized
    public double[] vettoreEnergiaRumore(Signal segnaleDaInput){
        int numberOfNoises = (int)(1.0/this.pfa);
        Noise generato = new Noise();
        double snr = calcolaSNR(segnaleDaInput);
        double[]energyNoiseVector= new double[numberOfNoises];
        for (int i=0; i<numberOfNoises; i++){
            generato.calculateAttributes(snr, segnaleDaInput.size());
            energyNoiseVector[i]=calcoloEnergiaRumore(generato);
        }
        return energyNoiseVector;
    }

//calcolo dell'energia di un segnale di rumore con la formula delle slide
    public double calcoloEnergiaRumore(Noise noise){
        double energia=0;
        double[]parteImmaginaria= noise.getParteImmaginaria();
        double[]parteReale=noise.getParteReale();
        Complex tmp = new Complex(-1,-1);
        for(int i=0; i<noise.getLength(); i++){
            tmp.setreale(parteReale[i]);
            tmp.setimmaginaria(parteImmaginaria[i]);
            energia+= Math.pow(tmp.abs(), 2);
        }
        return energia/noise.getLength();
    }


//calcolo della soglia
    //CAlcolo della media, calcolo della varianza, calcolo dell'errrore inverso e utilizzando la formula delle slide calcolo la soglia
    public double calculateSoglia(Signal segnaleDaInput){
        double result = -1.1;
        double[] energyNoiseVector = vettoreEnergiaRumore(segnaleDaInput);
        double med= processore.avrage(energyNoiseVector);
        double varia = processore.varianza(energyNoiseVector);
        try {
            double error= ErroreInverso.InvErf(1 - (2 * pfa));
            result = med + (Math.sqrt(varia * 2) * error);

        }catch (Exception e){
            System.out.println(e.toString());
        }
        System.out.println("soglia: "+ result);
        return result;
    }

}





