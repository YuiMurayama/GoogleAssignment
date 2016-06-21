package assignment4;
import java.util.ArrayList;

public class Node {

	ArrayList<Character> linkList = new ArrayList<Character>();
	char nodeName;
	double point;
	double nextPoint;

	Node() {
		this.point = 100;
		// this.linkList = null;
	}
	
	void Exchange(){
		point = nextPoint;
		nextPoint =0;
	}
}
