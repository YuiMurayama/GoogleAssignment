import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		(new Main()).mainLoop();
	}

	private void mainLoop() {
		Scanner scan = new Scanner(System.in);
		String str = scan.next();
		List<String> wordList = openFile("/usr/share/dict/words"); // wordlistというリスト
		quickSort(wordList, 0, wordList.size() - 1); // wordListが文字が小さい順に並んだ！
		outputFile("Output/output.csv", wordList); // wordListをファイルに書き込む

		int wordNum = findWord(str, wordList);
		System.out.println(wordList.get(wordNum));
	}

	// 辞書から一致する単語を探索
	int findWord(String input, List<String> wordList) {
		for (int i = 0; i < wordList.size(); i++) {
			if (judgeWord(input, wordList.get(i)) == true) {
				return i;
			}
		}
		return -1;
	}

	// 辞書とインプットを入力して、作れるならtrueを返す
	boolean judgeWord(String input, String word) {
		int[] inputArray = changeWordToArray(input);
		int[] dictionaryWordArray = changeWordToArray(word);
		for (int i = 0; i < inputArray.length; i++) {
			if (inputArray[i] < dictionaryWordArray[i]) {
				return false;
			}
		}
		return true;
	}

	List<String> openFile(String fileName) {
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
	void outputFile(String fileName, List<String> stringList) {
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
	void quickSort(List<String> wordList, int left, int right) {
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
		quickSort(wordList, left, r); // ピボットより左側をクイックソート
		quickSort(wordList, l, right); // ピボットより右側をクイックソート
	}

	// wordを26行の配列（種類別)変換する
	int[] changeWordToArray(String word) {
		char[] charWord = word.toCharArray();
		int[] wordTypeMethod = new int[26];
		for (int i = 0; i < wordTypeMethod.length; i++) {
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
	int changeStringToInt(char word) {
		if (word == '-') {
			return -1;
		}
		int ans = Character.getNumericValue(word);
		if (word >= 'a' && word <= 'z') {
		}
		return ans - 10;
	}
}