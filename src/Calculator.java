import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
	public static void main(String[] args) {
		ArrayList<Number> array = new ArrayList<Number>();
		System.out.println("数式を入力して下さい。");
		Scanner scan = new Scanner(System.in);
		String str = scan.next();
		char[] charArray = str.toCharArray();
		array = makeArray(charArray); // 数字と記号の配列に変換
//		printKeyValue(array);
		
		// int[] tempArray = findBrackets(array); // かっこがある位置を返す
		// かっこがなくなるまで続ける(かっこを除去してる)

		boolean isBrackets = isBrackets(array);

		System.out.println("最初の判定"+isBrackets);
		
		while (isBrackets == true) {
			System.out.println("a");
			array = deleteBracket(array);
			
			isBrackets = isBrackets(array);
			System.out.println(isBrackets);
//			printKeyValue(array);
			
		}

		// while( array.indexOf())

		// while (tempArray[0] != 0) {
		// System.out.println(tempArray[0] + " " + tempArray[1]);
		// if (tempArray[0] != 0) {
		// array = calBrackets(array, tempArray[0], tempArray[1]); //
		// かっこなしのものに変換
		// }
		// tempArray = findBrackets(array); // かっこがある位置を返す
		// // }
		// System.out.println(tempArray[0] + " " + tempArray[1]);
		// if (tempArray[0] != 0) {
		// array = calBrackets(array, tempArray[0], tempArray[1]); //
		// かっこなしのものに変換
		// }
		// tempArray = findBrackets(array); // かっこがある位置を返す

//		System.out.println("掛け算割り算の処理前");
//		printKeyValue(array);
		array = changeArray(array); // 掛け算割り算の処理
//		System.out.println("最終処理の前");
//		printKeyValue(array);
		System.out.println(evaluate(array));
	}

	static boolean isBrackets(ArrayList<Number> array) {
		boolean isBrackets = false;
		for (int a = 0; a < array.size(); a++) {
			if (array.get(a).key == '(') {
				isBrackets = true;
				return isBrackets;
			}	
		}
		return isBrackets;
	}
	
	
	
	
	
	// かっこを取り除いて計算して、新しいarrayを返す
	static ArrayList<Number> deleteBracket(ArrayList<Number> array) {
		int[] tempArray = findBrackets(array);
		if (tempArray[0] != 0) {
			array = calBrackets(array, tempArray[0], tempArray[1]); // かっこなしのものに変換
		}
		tempArray = findBrackets(array);

		return array;
	}

	static Number readNum(char[] array, int index) {
		double number = 0;
		Number n = new Number();
		while (index < array.length) {
			if (!Character.isDigit(array[index])) {
				break;
			}
			number = number * 10 + Integer.parseInt("" + array[index]);
			index += 1;
		}
		if (index < array.length) {
			if (array[index] == '.') {
				index += 1;
				double keta = 0.1;
				while (index < array.length) {
					if (!Character.isDigit(array[index])) {
						break;
					}
					number += Integer.parseInt("" + array[index]) * keta;
					keta *= 0.1;
					index += 1;
				}
			}
		}
		n.value = number;
		n.index = index - 1;
		return n;
	}

	static ArrayList<Number> makeArray(char[] charArray) {
		ArrayList<Number> array = new ArrayList<Number>();
		Number firstNum = new Number();
		firstNum.key = '+';
		array.add(firstNum);
		int index = 0;
		while (index < charArray.length) {
			Number n = new Number();
			if (Character.isDigit(charArray[index])) {
				// System.out.println(readNum(charArray, index));
				n = readNum(charArray, index);
			} else if (charArray[index] == '+' | charArray[index] == '-'
					| charArray[index] == '*' | charArray[index] == '('
					| charArray[index] == '/' | charArray[index] == ')') {
				n.key = charArray[index];
				n.index = index;
			} else {
				System.out.println("Invalid character found");
			}
			index = n.index + 1;
			array.add(n);
		}
		Number lastNum = new Number();
		lastNum.key = '+';
		array.add(lastNum);
		return array;
	}

	static double evaluate(ArrayList<Number> array) {
		double answer = 0;
		array.get(0).key = '+';
		int i = 1;
		while (i < array.size()) {
			if (array.get(i).key == 'n') {
				if (array.get(i - 1).key == '+') {
					answer += array.get(i).value;
				} else if (array.get(i - 1).key == '-') {
					answer -= array.get(i).value;
				} else {
					// System.out.println(array.get(i).key);
					System.out.println("Invalid syntax");
				}
			}
			i += 1;
		}
		return answer;
	}

	// iからjの範囲の掛け算割り算の計算
	static ArrayList<Number> calNum(ArrayList<Number> array, int i, int j) {
		System.out.println("掛け算範囲は" + i + " " + j);
		double answer = 0;
		int index = 0;

		// 掛け算割り算の親となる数字を見つけ出す
		for (int a = i + 1; 0 < i; a--) {
			if (array.get(a).key == 'n') {
				answer = array.get(a).value;
				index = a;
				break;
			}
		}

		int laterIndex = 0;
		for (int a = i; a < j; a++) {
			if (array.get(a + 1).key == '*') {
				System.out.println("a");
				answer *= array.get(a + 2).value;

			} else if (array.get(a + 1).key == '/') {
				answer /= array.get(a + 2).value;
			}
			laterIndex = a + 2;
		}
		array.get(index).value = answer;
		array.get(index).key = 'n';
		for (int a = index + 1; a < laterIndex; a++) {
			array.get(a).value = 0;
			array.get(a).key = '+';
		}
		System.out.println("かけた後answer" + answer);
		return array;
	}

	static ArrayList<Number> changeArray(ArrayList<Number> array) {
		for (int i = 0; i < array.size(); i++) {
			for (int j = i + 1; j < array.size(); j++) {
				// iは*か/のいち、jは＋か-のいち
				if (array.get(i).key == '*' | array.get(i).key == '/') {
					if (array.get(j).key == '+' | array.get(j).key == '-') {
						System.out.println("iとjは" + i + " " + j);
						array = calNum(array, i - 1, j - 1);
					}
				}
			}
		}
		return array;
	}

	// キーの表示
	static void printKeyValue(ArrayList<Number> array) {
		for (int i = 0; i < array.size(); i++) {
			System.out.println("keyは" + array.get(i).key + " valueは"
					+ array.get(i).value);
		}
	}

	// かっこないの計算(iは(の番目、jは)の番目)

	static ArrayList<Number> calBrackets(ArrayList<Number> array, int i, int j) {
		ArrayList<Number> newArray = new ArrayList<Number>();
		for (int a = i - 1; a < j; a++) {
			newArray.add(array.get(a)); // 新しいリストの生成
		}
		printKeyValue(newArray);
		newArray.get(0).key = '+';
		newArray.get(j - i).key = '+';
		newArray = changeArray(newArray); // 掛け算割り算の処理
		System.out.println(evaluate(newArray));
		array.get(i - 1).value = evaluate(newArray);
		for (int a = i; a < j; a++) {
			array.get(a).key = '+';
			array.get(a).value = 0;
		}
		array.get(i - 1).key = 'n';
		System.out.println();
		return array;
	}

	// かっこの位置を見つけ出して、"("の番号と")"の番号の配列を返す
	static int[] findBrackets(ArrayList<Number> array) {
		int firstBNum = 0;
		int lastBNum = 0;
		int[] bracketsRange = { firstBNum, lastBNum };

		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).key == '(') {
				firstBNum = i;
			} else if (array.get(i).key == ')') {
				lastBNum = i;
				break;
			}
		}
		bracketsRange[0] = firstBNum + 1;
		bracketsRange[1] = lastBNum + 1;
		System.out
				.println("かっこの数は" + bracketsRange[0] + " " + bracketsRange[1]);

		return bracketsRange;
	}
}

// if (array.get(i).key == ')') {
//
//
// countBrackets[0]++;
// firstBNum = i;
// System.out.println("(" + countBrackets[0]);
// } else if (array.get(i).key == ')') {
// countBrackets[1]++;
// lastBNum = i;
// System.out.println(")" + countBrackets[1]);
// }
// if (countBrackets[0] != 0 & countBrackets[1] != 0
// & countBrackets[0] == countBrackets[1]) {
// bracketsRange[0] = firstBNum + 1;
// bracketsRange[1] = lastBNum + 1;
// System.out.println("かっこの数は" + countBrackets[0] + " "
// + countBrackets[1]);
// return bracketsRange;
// }
// }

// static int[] findBrackets(ArrayList<Number> array) {
// int[] countBrackets = { 0, 0 };
// int firstBNum = 0;
// int lastBNum = 0;
// int[] bracketsRange = { firstBNum, lastBNum };

// for (int i = 0; i < array.size(); i++) {
// if (array.get(i).key == '(') {
// countBrackets[0]++;
// firstBNum = i;
// System.out.println("(" + countBrackets[0]);
// } else if (array.get(i).key == ')') {
// countBrackets[1]++;
// lastBNum = i;
// System.out.println(")" + countBrackets[1]);
// }
// if (countBrackets[0] != 0 & countBrackets[1] != 0
// & countBrackets[0] == countBrackets[1]) {
// bracketsRange[0] = firstBNum + 1;
// bracketsRange[1] = lastBNum + 1;
// System.out.println("かっこの数は" + countBrackets[0] + " "
// + countBrackets[1]);
// return bracketsRange;
// }
// }
