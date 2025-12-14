import java.util.ArrayDeque;
import java.util.Deque;

class Main{
	public static void main(String[] args){
		Deque<Double> numbersStack = new ArrayDeque<>(); 
		String calculation = args[0];
		String[] calcArr = calculation.split(" ");
		String currentOpp = ""; 
		for(int i = 0; i < calcArr.length; i++){
			System.out.println(calcArr[i]);
			switch(calcArr[i]){
				case "(":
				case ")":
				case "+":
				case "-":
				case "*":
				case "/":
					if(currentOpp==""){
						currentOpp = calcArr[i];
					} else if(oppOneHasPrecedent(currentOpp, calcArr[i])){
						numbersStack.push(useOpp(currentOpp, numbersStack.pop(), numbersStack.pop()));
						currentOpp = calcArr[i];
					} else {
						numbersStack.push(useOpp(calcArr[i], (double)Integer.parseInt(calcArr[i+1]), numbersStack.pop()));
						i++;
					}
					break;
				default:
					numbersStack.push((double)Integer.parseInt(calcArr[i]));	
					break;
			}
			System.out.println(numbersStack);
		}
		numbersStack.push(useOpp(currentOpp, numbersStack.pop(), numbersStack.pop()));	
		System.out.println(numbersStack);
		System.out.println();
	}

	public static double useOpp(String opp, double numOne, double numTwo){
		switch(opp){
			case "+":
				return (double)(numOne+numTwo);
			case "-":
				return (double)(numTwo-numOne);
			case "*":
				return (double)(numOne*numTwo);
			case "/":
				return (double)(numTwo/numOne);
		}	
		return 0.0;
	}
	
	public static boolean oppOneHasPrecedent(String opp1, String opp2){
		int opp1Prec = -1;
		int opp2Prec = -1;
		if(opp1.equals("+") || opp1.equals("-")){
			opp1Prec = 1;
		}	else {
			opp1Prec = 2;
		}
		if(opp2.equals("+") || opp2.equals("-")){
			opp2Prec = 1;
		}	else {
			opp2Prec = 2;
		}
		if(opp1Prec >= opp2Prec){
			return true;
		}
		return false;

	}
}
