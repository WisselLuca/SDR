	package signalprocessing.library;

	import signalprocessing.model.*;


	public class SignalProcessor {

		private Detector detector;

		public static Signal convoluzione(Signal segnaleIn, Signal rispImpulsivaFiltro){
			int n = segnaleIn.size() + rispImpulsivaFiltro.size();
			Signal result  = new Signal();
			int upperBound = 0;
			int lowerBound = 0;
			for (int k = 0; k < n; k++) {
				upperBound = Math.min(k,rispImpulsivaFiltro.size()-1);
				lowerBound = Math.max(0,k - segnaleIn.size()+1);
				for (int j = lowerBound;j<=upperBound; j++){
					result.add(k,result.get(k).somma(segnaleIn.get(k-j).prodotto(rispImpulsivaFiltro.get(j))));
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
			Signal lpf = new Signal();
			int simmetria = (numCampioni) / 2;

			for(int n = - simmetria; n <= simmetria; n++){
				double realval = f1*2* band * sinc(n, 2 * band);
				lpf.add(n + simmetria, new Complex(realval, 0));
			}


			return lpf;
		}

		public Signal espansione(Signal segnaleIngresso,int f1) {
			Signal sequenzaEspansa = new Signal();
			int j = 0;
			for (int i = 0; i < segnaleIngresso.size() * f1; i++) {
				if (i % f1 == 0) {
					sequenzaEspansa.add(i,segnaleIngresso.get(j));
					j++;
				} else
					sequenzaEspansa.add(i, new Complex(0,0));
			}
			return sequenzaEspansa;
		}


		public static Signal interpolazione(Signal signalIn, int F1){

			double band = 1/(2.0*F1);
			Signal lpf = lowPassFilter(band, F1);
			Signal interpolato = convoluzione(signalIn, lpf);
			Signal val = new Signal();
			int n = (lpf.size() - 1)/2;
			int j = 0;

			for (int i = n; i < interpolato.size() - n; i++){
				val.add(j,interpolato.get(i));
				j++;
			}

			return val;

		}

		public static Signal decimazione(Signal in, int F2) {
			Signal vectorDecimato = new Signal();

			int j = 0;
			for (int i = 0; i < in.size(); i++) {
				if(i % F2 ==0 && j <in.size()/F2) {
					vectorDecimato.add(j,in.get(i));
					j++;
				}
			}
			return vectorDecimato;
		}


		public double avrage(double[] energia) {
			double somma=0.0;
			for(int i=0; i<energia.length; i++)
				somma += energia[i];
			return somma/energia.length;
		}

		public double varianza(double[] energia){
			double m = avrage(energia);
			double sommaScartiQuad = 0;
			for(int i=0; i<energia.length; i++)
				sommaScartiQuad += (energia[i]-m)*(energia[i]-m);
			return sommaScartiQuad/energia.length;
		}


	}

