package signalprocessing.library;

import signalpocessing.model.*;

public class SignalProcessor {


	public static double[] convoluzione(double[] v1, double[] v2){
		int n = v1.length+v2.length;
		double[] result = new double[n];
		int upperBound=0;
		int lowerBound=0;

		for(int k=0; k<n;k++){
			upperBound=Math.min(k,v2.length-1);
			lowerBound=Math.max(0,k-v1.length+1);
			for(int j= lowerBound; j<lowerBound; j++){
				result[k] += (v1[k-j]*v2[j]);
			}
		}

		return result;

	}


	public static Complex[] convoluzione(Complex[] v1, Complex[] v2){
		int n = v1.length+v2.length;
		Complex[] result = new Complex[n];
		int upperBound=0;
		int lowerBound=0;

		for(int k=0; k<n;k++){
			upperBound=Math.min(k,v2.length-1);
			lowerBound=Math.max(0,k-v1.length+1);
			for(int j= lowerBound; j<lowerBound; j++){
				result[k] = result[k].somma(v1[k - j].prodotto(v2[j]));
			}
		}

		return result;
	}

	public static double sinc(double n, double band){
		double res = 0;
		if(n==0)
			res = 1;
		else if(n % (1/band) == 0)
			return 0;
		else
			res = (Math.sin(Math.PI*band*n))/(Math.PI*band*n);

		return res;
	}

	private static Signal lowPassFilter(double band, double f1) {

		double fs = 2*band;
		double tc = 1/fs;
		int numCampioni;
		int lunghezza = (int)(tc*10);
		if(lunghezza%2 == 0)
			numCampioni = lunghezza - 1;
		else numCampioni = lunghezza;
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;

		for(int n = - simmetria; n <= simmetria; n++){
			double realval = f1*2* band * sinc(n, 2 * band);
			values[n + simmetria] = new Complex(realval, 0);
		}

		Signal lpf = new Signal(values);

		return lpf;
	}

	public Signal espansione(Signal segnaleIngresso,int f1) {
		Complex[] sequenzaIN = segnaleIngresso.getValues();
		Complex[] sequenzaEspansa = new Complex[sequenzaIN.length * f1];
		int j = 0;
		for (int i = 0; i < sequenzaEspansa.length; i++) {
			if (i % f1 == 0) {
				sequenzaEspansa[i] = sequenzaIN[j];
				j++;
			} else
				sequenzaEspansa[i] = new Complex(0,0);
		}
		return new Signal(sequenzaEspansa);
	}

/*	public Signal lowPassFilter(double f1) {
		band = 1 / F1;
		numCampioni = (2 * (5 / band) + 1);
		array[] values = array[numCampioni];
		center = n / 2;
		values[center] = Complex(1, 0);
		for (i = 1; i <= n / 2; i++) {
			value = Complex(sinc(band, i), 0);
			values[center + i] = value;
			values[center - i] = value;
		}
		Signal lpf = Signal(values);
		return lpf;
	}*/
}
