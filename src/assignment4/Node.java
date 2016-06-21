package assignment4;
import java.util.ArrayList;

public class Node {

	ArrayList<String> linkList = new ArrayList<String>();
	String nodeName;
	double point;
	double nextPoint;

	Node() {
		this.point = 100;
		// this.linkList = null;
	}
	
	double Exchange(){

		double error =0;
		error = Math.pow((nextPoint-point),2);
		point = nextPoint;
		nextPoint = 0;
		
		return error;
	}
}
