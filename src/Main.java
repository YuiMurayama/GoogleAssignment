import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		(new Main()).mainLoop();
	}
	private void mainLoop() {
		Scanner scan = new Scanner(System.in);
		String str = scan.next();
		// str = "abcdefghiyuserwtan"; // inputの16文字のアルファベット
		List<String> wordList = openFile("/usr/share/dict/words"); // wordlistというリスト

		// print_data(wordList);
		quick_sort(wordList, 0, wordList.size() - 1); // wordListが文字が小さい順に並んだ！
		// print_data(wordList);
		outputFile("Output/output.csv", wordList); // wordListをファイルに書き込む

		int[] inputArray = changeWordToMethod(str); // inputを26行メソッドに変換
		for (int i = 0; i < inputArray.length; i++) { // その行列の表示
		}
		int wordNum = findWord(str, wordList);
		System.out.println(wordList.get(wordNum));
	}

	// 辞書から一致する単語を探索
	static int findWord(String input, List<String> wordList) {
		for (int i = 0; i < wordList.size(); i++) {
			if (judgeWord(input, wordList.get(i)) == true) {
				return i;
			}
		}
		return -1;
	}

	// 辞書とインプットを入力して、作れるならtrueを返す
	static boolean judgeWord(String input, String word) {
		int[] inputArray = changeWordToMethod(input);
		int[] dictionaryWordArray = changeWordToMethod(word);
		for (int i = 0; i < inputArray.length; i++) {
			if (inputArray[i] < dictionaryWordArray[i]) {
				return false;
			}
		}
		return true;
	}

	private List<String> openFile(String fileName) {
		List<String> wordList = new ArrayList<String>();
		try {
			Reader filereader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(filereader);
			String data = null;
			while ((data = br.readLine()) != null) {
				wordList.add(data);
			}
			filereader.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return wordList;
	}

	// "outputFileメソッド"
	private void outputFile(String fileName, List<String> stringList) {
		try {
			Writer writer = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(writer);
			for (String string : stringList) {
				bw.write(string);
				bw.newLine();
			}
			bw.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 辞書リストを文字数の多い順から並び替えるためのクイックソート
	static void quick_sort(List<String> wordList, int left, int right) {
		if (left >= right) {
			return;
		}
		int p = wordList.get((left + right) / 2).length();
		int l = left, r = right;
		String tmp;
		while (l <= r) {
			while (wordList.get(l).length() > p) {
				l++;
			}
			while (wordList.get(r).length() < p) {
				r--;
			}
			if (l <= r) {
				tmp = wordList.get(l);
				wordList.set(l, wordList.get(r));
				wordList.set(r, tmp);
				l++;
				r--;
			}
		}
		quick_sort(wordList, left, r); // ピボットより左側をクイックソート
		quick_sort(wordList, l, right); // ピボットより右側をクイックソート
	}

	// 配列内のデータ列を表示する
	static void print_data(List<String> wordList) {
		for (int i = 0; i < wordList.size(); i++)
			System.out.println(wordList.get(i) + " ");
		System.out.println();
	}

	// wordを26行の配列（種類別)変換する
	static int[] changeWordToMethod(String word) {
		char[] charWord = word.toCharArray();
		int[] wordTypeMethod = new int[26];
		for (int i = 0; i < wordTypeMethod.length; i++) { // 全てを０にして初期化
			wordTypeMethod[i] = 0;
		}
		for (int i = 0; i < charWord.length; i++) {
			int order = changeStringToInt(charWord[i]); // 与えられた一つのアルファベットが何番目かをかえす
			if (order == -1)
				continue;
			wordTypeMethod[order]++;
		}
		return wordTypeMethod;
	}

	// 一文字を入れたときにそれをアルファベット順の番号で返す
	static int changeStringToInt(char word) {
		if (word == '-') {
			return -1;
		}
		int ans = Character.getNumericValue(word);
		if (word >= 'a' && word <= 'z') {
		}
		return ans - 10;
	}
}