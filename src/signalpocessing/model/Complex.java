package signalpocessing.model;

public class Complex {

	private double parteReale;
	private double parteImmaginaria;


	public Complex(double parteReale, double parteImmaginaria) {
		super();
		this.parteReale = parteReale;
		this.parteImmaginaria = parteImmaginaria;
	}


	public double getParteReale() {
		return parteReale;
	}


	public void setParteReale(double parteReale) {
		this.parteReale = parteReale;
	}


	public double getParteImmaginaria() {
		return parteImmaginaria;
	}


	public void setParteImmaginaria(double parteImmaginaria) {
		this.parteImmaginaria = parteImmaginaria;
	}



	//Metodi per complessi
	public void complessoCogniugato(){
		this.parteImmaginaria= - this.parteImmaginaria;
	}

	public static Complex sum(Complex c1, Complex c2){
		double parteReale = c1.getParteReale()+ c2.getParteReale();]
		double parteImmaginaria = c1.getParteImmaginaria() +c2.getParteImmaginaria();
		return new Complex(parteReale,parteImmaginaria);
	}

	public static Complex prodotto(Complex c1, Complex c2){
		double parteReale = (c1.getParteReale()* c2.getParteReale())- (c1.getParteImmaginaria() *c2.getParteImmaginaria());
		double parteImmaginaria= (c1.getParteReale()* c2.getParteImmaginaria()) + (c1.getParteImmaginaria() *c2.getParteReale());
		return new Complex(parteReale,parteImmaginaria);
	}

	public double phase(){
		return Math.atan2(this.parteImmaginaria, this.parteReale);
	}


	@Override
	public String toString() {
		return "Complex [parteReale=" + parteReale + ", parteImmaginaria="
				+ parteImmaginaria + "]";
	}

	//Copia e incolla la convoluzione

	public static void main (String []args){

		Complex c1= new Complex(1,2);
		Complex c2= new Complex(1,2);
		Complex c3= new Complex(0,0);

		System.out.println(c3.prodotto(c1, c2));

	}
}
