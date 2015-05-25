package signalpocessing.model;

import signalprocessing.library.FileBuffer;

/**
 * Created by Luca on 12/05/2015.
 */
public class Hipotesi1 {

    private Signal segnaleInput;


        public double calcoloEnergiaSegnale(Signal segnale){
            double energia = 0;
            Complex tmp;
            for (Complex complex : segnale) {
                    tmp= new Complex(complex.getReale(),complex.getImmaginaria());
                    energia+= Math.pow(tmp.abs(), 2);
                }
        return energia;
        }


}
