

public class Pair {
	
	int fst;
	int snd; //index
	
	public Pair(int fst, int snd) {
		this.fst = fst;
		this.snd = snd;
	}
	
	public int getFst() {
		return fst;
	}
	
	public int getSnd() {
		return snd;
	}
	
	public String toString() {
		return "(" + this.fst + ", " + this.snd + ")";
	}
	
	public void setFst(int value) {
		this.fst = value;
	}
	
	

}
