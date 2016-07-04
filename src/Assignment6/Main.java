package Assignment6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		try {
			String fileplace ="/Users/murayamayui/Documents/eclipse/GoogleAssignment/src/assignment6/";
			int sample_number = 3;

			HashMap<Integer, double[]> cities = get_file(fileplace+"input_"
					+ sample_number + ".csv");
			int cityNum = cities.size();

			int[] visit_order_array = get_visit_order_array(
					fileplace+"solution_greedy_"
							+ sample_number + ".csv", cityNum);

			// 最初の仮の距離
			double temp_allDistance = cal_allDistance(visit_order_array, cities);
			System.out.println(temp_allDistance);

			double changed_allDistance = cal_allDistance(
					change_path(visit_order_array), cities);
		
			for (int i = 0; i < 10; i++) {
				if (temp_allDistance > changed_allDistance) {
					temp_allDistance = changed_allDistance;
				}
				changed_allDistance = cal_allDistance(
						change_path(visit_order_array), cities);
				System.out.println(temp_allDistance);
			}

			FileWriter fw = new FileWriter(fileplace+"solution_yours_" + sample_number
					+ ".csv", false);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			pw.print("index");
			pw.println();
			for (int i = 0; i < visit_order_array.length; i++) {
				pw.print(visit_order_array[i]);
				pw.println();
			}
			pw.close();

			// // System.out.println(temp_allDistance);

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	static int[] change_path(int[] visit_order_array) {
		int a = 0;
		int b = 0;
		while (a == b) {
			a = (int) (Math.random() * (visit_order_array.length - 1));
			b = (int) (Math.random() * (visit_order_array.length - 1));
		}
		int tempNum = visit_order_array[a];
		visit_order_array[a] = visit_order_array[b];
		visit_order_array[b] = tempNum;
		return visit_order_array;

	}

	// ファイルからhashmapを生成する
	static HashMap<Integer, double[]> get_file(String filename)
			throws IOException {
		File file1 = new File(filename);
		BufferedReader br1 = new BufferedReader(new FileReader(file1));
		String str1 = br1.readLine();
		str1 = br1.readLine();
		int index = 0;
		HashMap<Integer, double[]> cities = new HashMap<Integer, double[]>();
		while (str1 != null) {
			cities = make_cityMap(str1, index, cities);
			str1 = br1.readLine();
			index++;
		}
		return cities;
	}

	// ファイルからgreedyの順番を取る
	static int[] get_visit_order_array(String filename, int cityNum)
			throws IOException {
		File file2 = new File(filename);
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		String str2 = br2.readLine();
		int[] visit_order_array = new int[cityNum];
		for (int i = 0; i < cityNum; i++) {
			str2 = br2.readLine();
			visit_order_array[i] = Integer.parseInt(str2);
		}
		return visit_order_array;
	}

	// 座標を読み取ってhashmapのcityを作る
	static HashMap<Integer, double[]> make_cityMap(String str, int index,
			HashMap<Integer, double[]> cities) {
		String[] placeString = str.split(",", 0);
		double[] placeDouble = new double[2];
		placeDouble[0] = Double.parseDouble(placeString[0]);
		placeDouble[1] = Double.parseDouble(placeString[1]);
		cities.put(index, placeDouble);
		return cities;
	}

	// 二つの年の距離を計算
	static double cal_distance(int fromNum, int toNum,
			HashMap<Integer, double[]> cities) {
		double distance = Math.sqrt((Math.pow(
				(cities.get(fromNum)[0] - cities.get(toNum)[0]), 2) + Math.pow(
				(cities.get(fromNum)[1] - cities.get(toNum)[1]), 2)));
		// System.out.println("個別の距離は" + distance);
		return distance;
	}

	static double cal_allDistance(int[] visit_order_array,
			HashMap<Integer, double[]> cities) {
		double all_Distance = 0;
		for (int i = 0; i < visit_order_array.length - 1; i++) {
			// System.out.println(visit_order_array[i]+" "+visit_order_array[i+1]);
			all_Distance += cal_distance(visit_order_array[i],
					visit_order_array[i + 1], cities);
		}
		// System.out.println(visit_order_array[visit_order_array.length-1]+" "+visit_order_array[0]);
		//
		all_Distance += cal_distance(visit_order_array[0],
				visit_order_array[visit_order_array.length - 1], cities);
		// System.out.println(all_Distance);
		return all_Distance;

	}
	// TODO 自動生成されたメソッド・スタブ

}
