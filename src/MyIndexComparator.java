

import java.util.Comparator;

public class MyIndexComparator<T> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		return ((Pair) o1).getSnd() - ((Pair) o2).getSnd();
	}

}
