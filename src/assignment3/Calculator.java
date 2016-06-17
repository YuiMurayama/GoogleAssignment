package assignment3;

import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
	public static void main(String[] args) {
		ArrayList<Token> array = new ArrayList<Token>();
		System.out.println("数式を入力して下さい。");
		Scanner scan = new Scanner(System.in);
		String str = scan.next();
		char[] charArray = str.toCharArray();
		array = makeArray(charArray); 
		
		int[] bracketPlace = findBrackets(array);
	
		//括弧の処理
		while (bracketPlace[0] !=1) {
			array = evaluateBracket(array);
			bracketPlace = findBrackets(array);
		}
		
		//掛け算割り算の処理
		array = evaluateMultiAndDiv(array);
	
		//足し算引き算の処理
		double answer  = evaluatePlusAndMinus(array);
		System.out.println("答えは"+answer);
	}

//	//かっこがあるかないかを判定
//	static boolean judgeBrackets(ArrayList<Token> array) {
//		boolean isBrackets = false;
//		for (int a = 0; a < array.size(); a++) {
//			if (array.get(a).key == '(') {
//				isBrackets = true;
//				return isBrackets;
//			}
//		}
//		return isBrackets;
//	}

	// かっこを取り除いて計算して、新しいarrayを返す
	static ArrayList<Token> evaluateBracket(ArrayList<Token> array) {
		int[] bracketPlace = findBrackets(array);
		if (bracketPlace[0] != 0) {
			array = evaluateBrackets(array, bracketPlace[0], bracketPlace[1]);
		}
		return array;
	}
	
	//inputから数字を生成
	static Token creatNum(char[] array, int index) {
		double number = 0;
		Token n = new Token();
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

	//数字と記号が含まれるarrayを生成
	static ArrayList<Token> makeArray(char[] charArray) {
		ArrayList<Token> array = new ArrayList<Token>();
		Token firstNum = new Token();
		firstNum.key = '+';
		array.add(firstNum);
		int index = 0;
		while (index < charArray.length) {
			Token n = new Token();
			if (Character.isDigit(charArray[index])) {
				n = creatNum(charArray, index);
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
		Token lastNum = new Token();
		lastNum.key = '+';
		array.add(lastNum);
		return array;
	}
	
	//＋と−を計算
	static double evaluatePlusAndMinus(ArrayList<Token> array) {
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
					System.out.println("Invalid syntax");
				}
			}
			i += 1;
		}
		return answer;
	}

	// iからjの範囲の掛け算割り算の計算
	static ArrayList<Token> calMultiAndDiv(ArrayList<Token> array, int i, int j) {
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
		return array;
	}
	
	//掛け算割り算の計算
	static ArrayList<Token> evaluateMultiAndDiv(ArrayList<Token> array) {
		for (int i = 0; i < array.size(); i++) {
			for (int j = i + 1; j < array.size(); j++) {
				if (array.get(i).key == '*' | array.get(i).key == '/') {
					if (array.get(j).key == '+' | array.get(j).key == '-') {
						array = calMultiAndDiv(array, i - 1, j - 1);
					}
				}
			}
		}
		return array;
	}

	// キーの表示
	static void printToken(ArrayList<Token> array) {
		for (int i = 0; i < array.size(); i++) {
			System.out.println("keyは" + array.get(i).key + " valueは"
					+ array.get(i).value);
		}
	}
	
	// ()の中の計算
	static ArrayList<Token> evaluateBrackets(ArrayList<Token> array, int i, int j) {
		ArrayList<Token> newArray = new ArrayList<Token>();
		for (int a = i - 1; a < j; a++) {
			newArray.add(array.get(a)); 
		}
		newArray.get(0).key = '+';
		newArray.get(j - i).key = '+';
		newArray = evaluateMultiAndDiv(newArray); 
		array.get(i - 1).value = evaluatePlusAndMinus(newArray);
		for (int a = i; a < j; a++) {
			array.get(a).key = '+';
			array.get(a).value = 0;
		}
		array.get(i - 1).key = 'n';
		return array;
	}

	// かっこの位置を見つけ出して、そのインデックスを返す
	static int[] findBrackets(ArrayList<Token> array) {
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
		return bracketsRange;
	}
}
