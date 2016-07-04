package assignment4;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class Pagerank {
	public static void main(String args[]) {
		try {
			File file = new File(
					"/Users/murayamayui/Documents/eclipse/GoogleAssignment/src/assignment4/bad_example.txt");
			ArrayList<Node> nodeArray = new ArrayList<Node>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str = br.readLine();
			int nodeNum = Integer.parseInt(str);
			// ノードの生成
			for (int index = 0; index < nodeNum; index++) {
				str = br.readLine();
				Node node = new Node();
				node.nodeName = str;
				nodeArray.add(node);
			}
			str = br.readLine();
			// パスの生成
			while ((str = br.readLine()) != null) {
				nodeArray = makePass(str, nodeArray);
			}
			br.close();

			// pageRankの計算をi回
			for (int i = 0; i < 10; i++) {
				nodeArray = calPageRank(nodeArray);
				printNode(nodeArray);
			}
			// 結果を出力
//			printNode(nodeArray);
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// linkListをつくる
	static ArrayList<Node> makePass(String str, ArrayList<Node> nodeArray) {
		String[] stringArray = str.split(" ");
		for (int i = 0; i < nodeArray.size(); i++) {
			if (nodeArray.get(i).nodeName.equals(stringArray[0])) {
				nodeArray.get(i).linkList.add(stringArray[1]);
				break;
			}
		}
		return nodeArray;
	}

	// ノードの状態を出力する
	static void printNode(ArrayList<Node> nodeArray) {
		double allPoint = 0;
		for (int i = 0; i < nodeArray.size(); i++) {
			System.out.print(nodeArray.get(i).nodeName + "の得点は"
					+ (int)nodeArray.get(i).point);
			allPoint += nodeArray.get(i).point;
			System.out.println();
		}
		// System.out.println(allPoint);
		// System.out.println();
	}

	// 次の得点を計算してnextPointにいれたのち、pointと交換する
	static ArrayList<Node> calPageRank(ArrayList<Node> nodeArray) {
		for (int i = 0; i < nodeArray.size(); i++) {
			if (nodeArray.get(i).linkList.size() == 0) {
				nodeArray.get(i).nextPoint += nodeArray.get(i).point;
			}
			double addPoint = nodeArray.get(i).point
					/ nodeArray.get(i).linkList.size();
			for (int t = 0; t < nodeArray.get(i).linkList.size(); t++) {
				for (int a = 0; a < nodeArray.size(); a++) {
					if (nodeArray.get(i).linkList.get(t).equals(
							nodeArray.get(a).nodeName)) {
						nodeArray.get(a).nextPoint += addPoint;
					}
				}
			}
		}
		nodeArray = exchangePoint(nodeArray);
		return nodeArray;
	}

	// nextPointをpointに置き換える
	static ArrayList<Node> exchangePoint(ArrayList<Node> nodeArray) {
		double error = 0;
		for (int i = 0; i < nodeArray.size(); i++) {
			error += nodeArray.get(i).exchange();
		}
		error = Math.sqrt(error / nodeArray.size());
		System.out.println(error);
		return nodeArray;
	}
}