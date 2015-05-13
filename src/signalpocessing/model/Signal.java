package signalpocessing.model;

import java.util.List;

public class Signal {

	
	private Complex[] values;
	private int length;

	public Signal(Complex[] values) {
		super();
		this.values = values;
		this.length=values.length;
	}

	public Signal(List<Complex> values){
		Complex[] temp= new Complex[values.size()];
		temp = values.toArray(temp);
		this.values = temp;
		this.length = values.size();
	}


	public Complex[] getValues() {
		return values;
	}

	public void setValues(Complex[] values) {
		this.values = values;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	
}
