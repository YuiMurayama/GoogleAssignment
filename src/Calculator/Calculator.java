import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
	public static void main(String[] args) {
		ArrayList<Number> array = new ArrayList<Number>();
		System.out.println("数式を入力して下さい。");
		Scanner scan = new Scanner(System.in);
		String str = scan.next();
		char[] charArray = str.toCharArray();
		array = tokenize(charArray);
		for (int i = 0; i < array.size(); i++) {
			System.out.println("keyは" + array.get(i).key + " valueは"
					+ array.get(i).value);
		}
//		System.out.println(evaluate(array));
	}

	static Number readNum(char[] charArray, int index) {
		double number = 0;
		Number n = new Number();
		while (index < charArray.length) {
			if (!Character.isDigit(charArray[index])) {
				break;
			}
			number = number * 10 + Integer.parseInt("" + charArray[index]);
			index += 1;
		}

		if (index < charArray.length) {
			if (charArray[index] == '.') {
				index += 1;
				double keta = 0.1;
				while (index < charArray.length
						& Character.isDigit(charArray[index])) {
					number += Integer.parseInt("" + charArray[index]) * keta;
					keta *= 0.1;
					index += 1;
				}
			}
		}
		n.value = number;
		n.index = index - 1;
		return n;
	}

	static ArrayList<Number> tokenize(char[] charArray) {
		ArrayList<Number> array = new ArrayList<Number>();
		Number firstNum = new Number();
		firstNum.key = "+";
		array.add(firstNum);

		int index = 0;
		while (index < charArray.length) {
			Number n = new Number();
			if (Character.isDigit(charArray[index])) {
				// System.out.println(readNum(charArray, index));
				n = readNum(charArray, index);
			} else if (charArray[index] == '+') {
				n.key = "+";
				n.index = index;
			} else if (charArray[index] == '-') {
				n.key = "-";
				n.index = index;			
			}else if (charArray[index] == '*'){
				n.key = "*";
				n.index = index;
			}else if (charArray[index] == '/'){
				n.key = "/";
				n.index = index;
			}else {
				System.out.println("Invalid character found");
			}
			index = n.index + 1;
			array.add(n);
		}
		return array;
	}

	static double evaluate(ArrayList<Number> array) {
		double answer = 0;
		array.get(0).key = "+";
		int i = 1;
		while (i < array.size()) {
			if (array.get(i).key == null) {
				if (array.get(i - 1).key == "+") {
					answer += array.get(i).value;
				} else if (array.get(i - 1).key == "-") {
					answer -= array.get(i).value;
				} else {
					System.out.println("Invalid syntax");
				}
			}
			i += 1;
		}
		return answer;
	}
}