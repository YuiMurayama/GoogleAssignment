
public class Calculator2 {

	public static void main(String[] args){
		(new Calculator2()).mainLoop();
	}

	private void mainLoop(){
		String str = "2 * (2+1*3)";
		str = str.replace(" ", "");
		System.out.println(str +" = "+erazeBrackets(str));
	}

	private double erazeBrackets(String str){
		System.out.println("start : " + str);
		int[] array = new int[2];
		int start = 0;
		int end = 0;
		String newStr = "";
		for(int i = 0; i < str.length() ;i++){
			if(str.charAt(i) == '(' || str.charAt(i) == '{'){
				if(array[0] == 0){
					start = i;
					newStr += str.substring(end,start);
				}
				array[0]++;
			}else if(str.charAt(i) == ')' || str.charAt(i) == '}'){
				array[1]++;
				if(array[0] == array[1]){
					String s = str.substring(start+1,i);
					newStr += Double.toString(erazeBrackets(s));
					array[0] = 0;
					array[1] = 0;
					end= i+1;
				}
			}
		}
		newStr += str.substring(end);

		System.out.println(newStr);
		return calculate(newStr);
	}

	private double calculate(String str){
		String[] plusArray = str.split("\\+");
		double result = 0;
		for(String plus:plusArray ){
			double minusResult = 0;
			String[] minusArray = plus.split("\\-");
			for(int j = 0; j < minusArray.length; j++){
				double playResult = 1;
				for(String play : minusArray[j].split("\\*")){
					String[] slushArray = play.split("\\/");
					double y = Double.parseDouble(slushArray[0]);
					for(int i = 1; i < slushArray.length;i++){
						y /= Double.parseDouble(slushArray[i]);
					}
					playResult *= y;
				}
				if(j == 0){
					minusResult += playResult;
				}else{
					minusResult -= playResult;
				}
			}
			result += minusResult;
		}
		return result;
	}

}
