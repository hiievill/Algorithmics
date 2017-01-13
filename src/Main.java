import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.jfree.ui.RefineryUtilities;


public class Main {
	
	// a function returning a list with minimal values yielding the same tree 
	public static List<Integer> getMinInsertOrder(Node tree) {
		List<Integer> minOrder = new ArrayList<>();
		List<Node> nextNodes = new ArrayList<>();
		nextNodes.add(tree);
		while (!nextNodes.isEmpty()) {
			Collections.sort(nextNodes, new MyNodeComparator<>());
			Node n = nextNodes.get(0);
			minOrder.add(n.getValue());
			if (n.hasLeft())
				nextNodes.add(n.getLeft());
			if (n.hasRight())
				nextNodes.add(n.getRight());
			nextNodes.remove(0);
		}
		return minOrder;
	}
	
	// calculating the Catalan number, using dynamic programming instead of recursion
	public static int getCatalanNumber(int nr) {
		Map<Integer, Integer> t = new HashMap<>();
		t.put(0, 1);
		t.put(1, 1);
		for (int n = 2; n <= nr; n++) {		//n <- 2..nr
			int value = 0;
			for (int i = 1; i <= n; i++) {	//i <- 1..n
				value += t.get(i-1) * t.get(n-i);
			}
			t.put(n, value);
		}
		return t.get(nr);
	}
	
	// creating trees from a 2-d array
	public static Node[] createNodes(int[][] data) {
		Node[] nodes = new Node[data.length];
		for (int i = 0; i < data.length; i++) {
			Node node = createNode(data[i]);
			nodes[i] = node;
		}
		return nodes;
	}
	
	// finding the size of the set of string representations of trees
	public static int getSetByStrings(Node[] nodes) {
		Set<String> set = new HashSet<>();
		for (Node node : nodes) {
			set.add(node.parens());
		}
		return set.size();
	}
	
	// finding the number of different structures by converting the tree into a list with minimal
	// numbers that outputs the same tree
	public static int getSetByMinInsertOrder(int[][] data) {
		Set<List<Integer>> minOrderLists = new HashSet<>();
		for (int i = 0; i < data.length; i++) {
			int[] row = data[i];
			List<Integer> rowList = new ArrayList<>();					// one tree
			for (int nr : row)
				rowList.add(nr);
			List<Pair> indexedList = new ArrayList<>();
			for (int j = 0; j < rowList.size(); j++)
				indexedList.add(new Pair(rowList.get(j), j));			// with indices
			Collections.sort(indexedList, new MyNumberComparator<>());	// sorting by values
			for (int j = 0; j < indexedList.size(); j++) {				// decreasing values
				Pair newPair = indexedList.get(j);
				newPair.setFst(j);
				indexedList.set(j, newPair);
			}
			Collections.sort(indexedList, new MyIndexComparator<>());
			List<Integer> finalList = indexedList.stream()
					.map(pair -> pair.getFst())
					.collect(Collectors.toList());
			Node tree = createNode(finalList);
			List<Integer> minOrderList = getMinInsertOrder(tree);
			minOrderLists.add(minOrderList);
		}
		return minOrderLists.size();
	}
	
	// creating a tree from an array of integers
	public static Node createNode(int[] row) {
		assert row.length > 0; 
		Node tree = new Node(row[0]);
		for (int i = 1; i < row.length; i++) {
			tree.add(row[i]);
		}
		return tree;
	}
	
	// creating a tree from a list of integers
	public static Node createNode(List<Integer> row) {
		assert row.size() > 0; 
		Node tree = new Node(row.get(0));
		for (int i = 1; i < row.size(); i++) {
			tree.add(row.get(i));
		}
		return tree;
	}
	
	// reading data from the input file
	public static int[][] readData(File file) {
		try {
			Scanner sc = new Scanner(file);
			int n = sc.nextInt();
			int k = sc.nextInt();
			int[][] data = new int[n][k];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < k; j++)
					data[i][j] = sc.nextInt();
			}
			sc.close();
			return data;
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	// getting the correct answer from te answer file
	public static int getAnswer(File file) {
		try {
			Scanner sc = new Scanner(file);
			int answer = sc.nextInt();
			sc.close();
			return answer;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	// finding the number of different structures by comparing structures
	public static int getSetByStructure(Node[] nodes) {
		Set<Node> trees = new HashSet<>();
		for (Node node : nodes) {
			boolean exists = false;
			for (Node tree : trees) {
				if (tree.equals(node)) {
					exists = true;
					break;
				}
			}
			if (!exists)
				trees.add(node);
		}
		return trees.size();
	}
	
	
	// reading input data and right answers from the file
	public static void runTests(String algorithm, String filePath) {
		System.out.println("My answer" + "\t" + "Right answer" + "\t" + "Time (ms)" + "\n");
		File dir = new File(filePath);
		File[] files = dir.listFiles();
		for (int fileNr = 0; fileNr < files.length; fileNr += 2) {
			int[][] data = readData(files[fileNr+1]);
			int answer = getAnswer(files[fileNr]);
			int result = -1;
			Node[] nodes;
			long start = System.nanoTime();
			switch (algorithm) {
			case "stringComparison":
				nodes = createNodes(data);
				result = getSetByStrings(nodes);
				break;
			case "orderComparison":
				result = getSetByMinInsertOrder(data);
				break;
			case "structureComparison":
				nodes = createNodes(data);
				result = getSetByStructure(nodes);
				break;
			}
			double end = (System.nanoTime() - start) / Math.pow(10,  6);
			System.out.println(result + "\t\t" + answer + "\t\t" + end);
		}
	}
	
	// running an algorithm with some data and outputting the time elapsed
	public static double run(String algorithm, int[][] data) {
		int result;
		Node[] nodes;
		long start = System.nanoTime();
		switch (algorithm) {
		case "stringComparison":
			nodes = createNodes(data);
			result = getSetByStrings(nodes);
			break;
		case "orderComparison":
			result = getSetByMinInsertOrder(data);
			break;
		case "structureComparison":
			nodes = createNodes(data);
			result = getSetByStructure(nodes);
			break;
		}
		double end = (System.nanoTime() - start) / Math.pow(10,  6);
		return end;
	}
	
	// generating random data for n trees of size k
	public static int[][] generateRandomData(int n, int k) {
		int[][] data = new int[n][k];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				data[i][j] = (int) (10*k*Math.random());
			}
		}
		return data;
	}
	
	// function for drawing the charts
	public static void drawCharts() {
		Chart chart = new Chart("");
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	// a function calculating the average time
	public static double averageTime(List<Double> times) {
		double sum = 0;
		for (Double time : times)
			sum += time;
		return sum / (1.0 * times.size());
	}
	
	public static void main(String[] args) {
		
		//runTests("structureComparison", "C:\\Users\\Hiie\\OOP\\Algoritmika_Projekt\\src\\application\\ceiling\\secret");
		
		drawCharts(); // draws charts comparing the three algorithms
		
		System.out.println("Catalan(4) = " + getCatalanNumber(4));

	}
}
