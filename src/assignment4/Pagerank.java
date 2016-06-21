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
					"/Users/murayamayui/Documents/eclipse/GoogleAssignment/src/assignment4/medium_data.txt");
			ArrayList<Node> nodeArray = new ArrayList<Node>();
			for (int index = 0; index < 23; index++) {
				Node node = new Node();
				node.nodeName = (char) (index + 65);
				nodeArray.add(node);
			}
			// リンクの生成
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str;
			while ((str = br.readLine()) != null) {
				nodeArray = makePass(str, nodeArray);
			}
			br.close();
			// pageRankの計算
			for (int i = 0; i < 100; i++) {
				nodeArray = calPageRank(nodeArray);
				printNode(nodeArray);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// linkListをつくる
	static ArrayList<Node> makePass(String str, ArrayList<Node> nodeArray) {
		char[] charArray = str.toCharArray();
		for (int i = 0; i < nodeArray.size(); i++) {
			if (nodeArray.get(i).nodeName == charArray[0]) {
				nodeArray.get(i).linkList.add(charArray[2]);
				break;
			}
		}
		return nodeArray;
	}

	// ノードの状態を出力する
	static void printNode(ArrayList<Node> nodeArray) {
		double allPoint = 0;
		for (int i = 0; i < nodeArray.size(); i++) {
			System.out.print(nodeArray.get(i).nodeName);
			System.out.print(" 得点は" + nodeArray.get(i).point);		
			allPoint += nodeArray.get(i).point;
			System.out.println();
		}
		System.out.println(allPoint);
		System.out.println();
	}

	// 次の得点を計算する
	static ArrayList<Node> calPageRank(ArrayList<Node> nodeArray) {
		for (int i = 0; i < nodeArray.size(); i++) {
			double addPoint = nodeArray.get(i).point
					/ nodeArray.get(i).linkList.size();
			for (int s = 0; s < nodeArray.size(); s++) {
				for (int t = 0; t < nodeArray.get(i).linkList.size(); t++) {
					if (nodeArray.get(s).nodeName == nodeArray.get(i).linkList
							.get(t)) {
						nodeArray.get(s).nextPoint += addPoint;
					}
				}
			}
		}
		nodeArray = exchangePoint(nodeArray);
		return nodeArray;
	}

	// nextPointをpointに置き換える
	static ArrayList<Node> exchangePoint(ArrayList<Node> nodeArray) {
		for (int i = 0; i < nodeArray.size(); i++) {
			// double tempPoin = nodeArray.get(i).point;
			nodeArray.get(i).Exchange();
		}
		return nodeArray;
	}
}