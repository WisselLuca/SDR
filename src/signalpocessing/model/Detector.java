package signalpocessing.model;

import signalprocessing.library.ErroreInverso;
import signalprocessing.library.FileBuffer;
import signalprocessing.library.SignalProcessor;

import java.util.LinkedList;
import java.util.List;

import static signalprocessing.library.ErroreInverso.InvErf;

/**
 * Created by Luca on 12/05/2015.
 */
public class Detector {
    private SignalProcessor processore = new SignalProcessor();
    private double pfa = 0.001;


    //Calcolo snr

    public double calcolaSNR(Signal segnaleDaInput){
        double snr= 1/ calcoloPotenzaRumore(segnaleDaInput);
            return snr;
    }

    public double calcoloPotenzaRumore(Signal segnaleDaInput){
        double potenzaRumore=0;
        potenzaRumore= calcoloEnergiaSegnale(segnaleDaInput)-1;
        return potenzaRumore;
    }



//Genero 1/0,001= 1000 Rumori con SNR calcolato prima e di lunghezza pari alla lunghezza del segnale del campione, nel nostro caso 1 milione
    public Noise[] generaNoise( Signal segnaleDaInput){
       // int numberOfNoises = (int)(1.0/this.pfa);
        int numberOfNoises = 100;
        Noise[] generati = new Noise[numberOfNoises];
        for (int i=0; i<numberOfNoises; i++){
            generati[i]= new Noise(calcolaSNR(segnaleDaInput), segnaleDaInput.getLength());
        }
        return generati;
    }

//calcolo dell'energia di un segnale di rumore
    public double calcoloEnergiaRumore(Noise noise){
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

//Calcolo dell'energia di ogni campione di rumore e lo mette in un array
    public double[] vettoreEnergiaRumore(Signal segnaleDaInput){

        Noise[] generati= generaNoise(segnaleDaInput);
        double[]energia = new double[generati.length];
        for(int i=0; i<generati.length; i++) {
            energia[i] = calcoloEnergiaRumore(generati[i]);
        }
        return energia;
    }

//CAlcolo dell'energia di un segnale
    public double calcoloEnergiaSegnale(Signal segnaleDaInput){
        double energia = 0;
        Complex tmp;
        for (Complex complex : segnaleDaInput.getValues()) {
            tmp= new Complex(complex.getReale(),complex.getImmaginaria());
            energia+= Math.pow(tmp.abs(), 2);
        }
        return energia/segnaleDaInput.getLength();
    }


//calcolo della soglia
    public double calculateSoglia(Signal segnaleDaInput){
        double result = -1.1;
        double med= processore.avrage(vettoreEnergiaRumore(segnaleDaInput));
        double varia = processore.varianza(vettoreEnergiaRumore(segnaleDaInput));
        try {
            double error= ErroreInverso.InvErf(1 - (2 * pfa));
            result = med + (Math.sqrt(varia * 2) * error);

        }catch (Exception e){
            System.out.println();
        }
        return result;
    }


    //Confrontare il vettoreEnergiaRumore con la soglia..

  public double[] getEnergyVector(Signal signal) {
      double[] output = new double[signal.getLength() / 1000];
      List<Complex> signalValues = new LinkedList<>();
      for (Complex complex : signal.getValues())
          signalValues.add(complex);
      int count = 0;
      for (int i = 0; i < signal.getLength(); i += 1000) {
          List<Complex> temp;
          temp = signalValues.subList(i, i + 999);
          Signal tempSignal = new Signal(temp);
          output[count] = this.calcoloEnergiaSegnale(tempSignal);
          count++;
      }
      return output;
  }
}

/* RIVEDERE QUESTO CODICE!!!!!!!!!!!!!!
            dovrebbe essere il confronto tra la soglia e il vettore di energie di rumore calcolato prima

    public int detectionFreeSpace(double[]energia, int pfa, List<Signal> segnali) throws Exception {
        double soglia = calculateSoglia(energia,pfa);
        double[] energy = vettoreEnergiaH1(segnali);
        int count=0;
        for (int i=0; i==energy.length; i++){
            if (energy[i]>soglia)
                count++;
        }
        return count;
    }*/


