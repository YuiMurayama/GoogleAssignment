import java.util.List;

public class Practice {

	public static void main(String[] args) {
		String word = "denshirenzi";
		int[] array = changeWordToMethod(word);
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
		}
	}

	static int[] changeWordToMethod(String word) {
		char[] charWord = word.toCharArray();
		int[] wordTypeMethod = new int[26];

		for (int i = 0; i < wordTypeMethod.length; i++) { // 全てを０にして初期化
			wordTypeMethod[i] = 0;
		}
		for (int i = 0; i < charWord.length; i++) {
			int order = changeStringToInt(charWord[i]); // 与えられた一つのアルファベットが何番目かをかえす
			wordTypeMethod[order]++;
		}

		return wordTypeMethod;
	}

	// 文字を入れたときにそれをアルファベット順の番号で返す
	static int changeStringToInt(char word) {
		if (word >= 'a' && word <= 'z') {
			// アルファベット小文字の場合
			// System.out.println(word - 'a');
		}
		int ans = Character.getNumericValue(word);
		return ans - 10;
	}

	
}
