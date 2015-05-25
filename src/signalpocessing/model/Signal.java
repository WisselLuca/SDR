package signalpocessing.model;

import java.util.Collection;
import java.util.LinkedList;

public class Signal extends LinkedList<Complex> {

	public Signal() {

	}

	public Signal(Collection<Complex> complexes) {
		this();
		this.addAll(complexes);
	}

	@Override
	public Complex[] toArray() {
		return (Complex[]) super.toArray();
	}

	@Override
	public Signal subList(int fromIndex, int toIndex) {
		return new Signal(super.subList(fromIndex, toIndex));
	}
}