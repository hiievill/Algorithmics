

import java.util.Comparator;

public class MyNumberComparator<T> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		return ((Pair) o1).getFst() - ((Pair) o2).getFst();
	}

}