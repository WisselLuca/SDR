package signalpocessing.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Signal extends LinkedList<Complex> {

	
//	private Complex[] values;
//	private int length;

	public Signal(){

	}

	public Signal(Collection<Complex> complexes){
		this();
		this.addAll(complexes);
	}

	@Override
	public Complex[] toArray() {
		return (Complex[])super.toArray();
	}

	@Override
	public Signal subList(int fromIndex, int toIndex) {
		return new Signal(super.subList(fromIndex, toIndex));
	}

	//	public Signal(Complex[] values) {
//		super();
//		this.values = values;
//		this.length=values.length;
//	}

//	public Signal(List<Complex> values){
//		Complex[] temp= new Complex[values.size()];
//		temp = values.toArray(temp);
//		this.values = temp;
//		this.length = values.size();
//	}


//	public Complex[] getValues() {
//		return values;
//	}
//
//	public void setValues(Complex[] values) {
//		this.values = values;
//	}
//
//	public void setValues(List<Complex> values){
//		Complex[] temp = new Complex[values.size()];
//		temp = values.toArray(temp);
//		this.values = temp;
//		this.length = temp.length;
//	}
//
//	public int getLength() {
//		return length;
//	}
//
//	public void setLength(int length) {
//		this.length = length;
//	}
//
	
}
