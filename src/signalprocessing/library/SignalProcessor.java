package signalprocessing.library;

import signalpocessing.model.Complex;

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
				;
			}
		}

		return result;
	}

	public static double sinc(double band, ){
	
		double result =0;
		
		return result;
	}
	
	
	
	public static void main(String[] args){


	}
}
