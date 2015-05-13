package signalpocessing.model;

public class Signal {

	
	private Complex[] values;
	private int length;

	public Signal(Complex[] values) {
		super();
		this.values = values;
		this.length=length;
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
