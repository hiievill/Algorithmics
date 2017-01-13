

public class Node {
	
	int value;
	Node left;
	Node right;
	
	public Node(int value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}
	
	public Node(int value, Node left, Node right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public Node getLeft() {
		return this.left;
	}
	
	public Node getRight() {
		return this.right;
	}
	
	void add(int newVal) {
		if (newVal < this.value) {
			if (this.left == null)
				this.left = new Node(newVal);
			else
				this.left.add(newVal);
		}
		else {
			if (this.right == null)
				this.right = new Node(newVal);
			else
				this.right.add(newVal);
		}
	}
	
	boolean hasLeft() {
		return this.left != null;
	}
	
	boolean hasRight() {
		return this.right != null;
	}
	
	public String toString() {
		String leftString = this.left == null ? "" : this.left.toString();
		String rightString = this.right == null ? "" : this.right.toString();
		return "{" + leftString + "} " + this.value + " {" + rightString + "}";
	}
	
	public String parens() {
		String leftParens = this.left == null ? "" : this.left.parens();
		String rightParens = this.right == null ? "" : this.right.parens();
		return "(" + leftParens + "|" + rightParens + ")";
	}

	@Override
	public boolean equals(Object o) {
		/*Node n2 = (Node) o;
		if (this == null && n2 == null)
			return true;
		else if (this == null || n2 == null)
			return false;
		return this.left.equals(n2.left) && this.right.equals(n2.right);*/
		Node n2 = (Node) o;
		if (this.hasLeft() && n2.hasLeft() && this.hasRight() && n2.hasRight())
			return this.left.equals(n2.left) && this.right.equals(n2.right);
		else if (this.hasLeft() && n2.hasLeft() && !this.hasRight() && !n2.hasRight())
			return this.left.equals(n2.left);
		else if (!this.hasLeft() && !n2.hasLeft() && this.hasRight() && n2.hasRight())
			return this.right.equals(n2.right);
		else if (!this.hasLeft() && !n2.hasLeft() && !this.hasRight() && !n2.hasRight())
			return true;
		return false;
	}
	
	

}
