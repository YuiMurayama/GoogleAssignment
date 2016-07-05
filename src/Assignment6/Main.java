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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try {
			for (int fileNum = 2; fileNum < 5; fileNum++) {
				// 座標の読み取り
				String fileplace = "/Users/murayamayui/Documents/eclipse/GoogleAssignment/src/assignment6/";
				int kaisu = 100000;
				HashMap<Integer, double[]> cities = get_file(fileplace
						+ "input_" + fileNum + ".csv");
				int cityNum = cities.size();

				FileWriter fw = new FileWriter(fileplace + "solution_greedy_"
						+ fileNum + ".csv", false);
				PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
				pw.println("index");
				pw.println("0");

				// greedyの結果出力
				ArrayList<Integer> visited_city_index = new ArrayList<Integer>();
				visited_city_index.add(0);
				int next_city = 0;

				while (visited_city_index.size() <= cities.size() - 1) {
					next_city = find_nearestCityNum(next_city, cities,
							visited_city_index);
					pw.println(next_city);
				}
				pw.close();

				int[] visit_order_array = get_visit_order_array(fileplace
						+ "solution_greedy_" + fileNum + ".csv", cityNum);

				 for(int i =0;i< visit_order_array.length;i++){
				 visit_order_array[i]= i;
				 }

				// パスの交換
				double temp_allDistance = cal_allDistance(visit_order_array,
						cities);
				System.out.println(temp_allDistance);
				double changed_allDistance = cal_allDistance(
						change_path(visit_order_array), cities);

				for (int i = 0; i < kaisu; i++) {
					if (temp_allDistance > changed_allDistance) {

						// System.out.println("更新" + changed_allDistance);
						temp_allDistance = changed_allDistance;
					}
					changed_allDistance = cal_allDistance(
							change_path(visit_order_array), cities);
				}

				System.out.println(fileNum + "番目は" + temp_allDistance);
				fw = new FileWriter(fileplace + "solution_yours_" + fileNum
						+ ".csv", false);
				pw = new PrintWriter(new BufferedWriter(fw));
				pw.print("index");
				pw.println();
				for (int i = 0; i < visit_order_array.length; i++) {
					pw.print(visit_order_array[i]);
					pw.println();
				}
				pw.close();
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	// ある都市から一番近い都市のindexを返す
	static int find_nearestCityNum(int current_city,
			HashMap<Integer, double[]> cities,
			ArrayList<Integer> visited_city_index) {
		int next_cityNum = 0;
		double min_distance = 0;
		boolean isfirst = true;
		for (int i = 0; i < cities.size(); i++) {
			if (visited_city_index.indexOf(i) == -1) {
				if (isfirst == true) {
					min_distance = cal_distance(current_city, i, cities);
					isfirst = false;
					next_cityNum = i;
				} else {
					double distance = cal_distance(current_city, i, cities);
					if (distance < min_distance) {
						next_cityNum = i;
						min_distance = distance;
					}
				}
			}
		}
		visited_city_index.add(next_cityNum);
		return next_cityNum;

	}

	// 乱数で二つの都市を選択してリバースする
	static int[] change_path(int[] visit_order_array) {
		int a = 0;
		int b = 0;
		while (a == b | a >= b) {
			a = (int) (Math.random() * (visit_order_array.length - 1));
			b = (int) (Math.random() * (visit_order_array.length - 1));
		}
		// System.out.println(a+"番目と"+ b+"番目を入れかえ");
		int[] tempArray = new int[b - a + 1];

		for (int i = 0; i < b - a + 1; i++) {
			tempArray[i] = visit_order_array[a + i];
		}
		// ここでリバース
		for (int i = a; i < b + 1; i++) {
			visit_order_array[i] = tempArray[b - a - (i - a)];
		}
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

	// すべての経路の和を計算する
	static double cal_allDistance(int[] visit_order_array,
			HashMap<Integer, double[]> cities) {
		double all_Distance = 0;

		// for (int i = 0; i < visit_order_array.length; i++) {
		// System.out.print(visit_order_array[i]+" ");
		// }
		// System.out.println();

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
		// System.out.println();
		return all_Distance;

	}

}
