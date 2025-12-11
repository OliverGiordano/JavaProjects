import java.util.*;

public class Main {
	public static void main(String[] args) {
		String instructions = args[0];  // intilize the code

		// initilize the linked list for the tape
		
		listNode initialNode = new listNode();
		listNode currentCell = initialNode;

		// intiilize the linked list for keeping track of iteration
		
		iterationTrackingNode initialIterNode = new iterationTrackingNode();
		iterationTrackingNode currentIterNode = initialIterNode;


		System.out.println(instructions);
		for(int i = 0; i < instructions.length(); i++){
			//System.out.println("CURRENT INSTRUCTION" + instructions.charAt(i));
			//readTapeValues(initialNode);
			//readIterValues(initialIterNode);
			//System.out.println(instructions.charAt(i));
			switch(instructions.charAt(i)) {
				case '+':
					currentCell.incrementCell();
					break;
				case '-': 
					currentCell.deincrementCell();
					break;
				case '>':
					currentCell = newListNodeRight(currentCell);
					break;
				case '<':
					currentCell = newListNodeLeft(currentCell);
					break;
				case '.':
					currentCell.outputCell();
					//System.out.println(currentCell.getVal());
					break;
				case ',':
					Scanner sc = new Scanner(System.in);
					int input = sc.nextInt();
					currentCell.setVal(input);
					break;
				case '[':
					iterationTrackingNode tmp = currentIterNode;
					currentIterNode.setNext(new iterationTrackingNode(i));
					currentIterNode = currentIterNode.getNext();
					currentIterNode.setPrevious(tmp);

					
					//readIterValues(initialIterNode);
					//currentCell.incrementCell();
					break;
				case ']':
					while(currentIterNode.getNext() != null){  // here we traverse to the end of the iterators linked list
						iterationTrackingNode tmp2 = currentIterNode;
						currentIterNode = currentIterNode.getNext();
						currentIterNode.setPrevious(tmp2);
					}
					//while(currentIterNode.getVal().getClose() != null){
					//	currentIterNode = currentIterNode.getPrevious();
					//}
					currentIterNode.setTupleClose(i);
					//readIterValues(initialIterNode);
					//System.out.println(currentIterNode.getVal().getOpen());
					//System.out.println("I ran so far away");
					if(currentCell.getVal() != 0 ){
						i = currentIterNode.getVal().getOpen();
					} else {  // here we are going to remove the last loop from iteration
						//System.out.println("wwwwwwww");
						currentIterNode = currentIterNode.getPrevious();
						currentIterNode.setNext(null);
					}
					break;
				default:
					System.out.println("TAPE ERROR");
					return;

			}
		}
		System.out.println();
	}

	public static void readTapeValues(listNode startNode){
		System.out.println("printing linked lists value");
		listNode tmp = startNode;
		while(tmp.getNext() != null){
			System.out.print(tmp.getVal() + " , " );
			tmp = tmp.getNext();
		}
		System.out.println(tmp.getVal());
	}
	
	public static void readIterValues(iterationTrackingNode startNode){
		System.out.println("printing link lists value");
		iterationTrackingNode tmp = startNode;
		while(tmp.getNext() != null){
			System.out.println(tmp.getVal().getOpen() + " , " + tmp.getVal().getClose());
			tmp = tmp.getNext();
		}

		System.out.println(tmp.getVal().getOpen() + " , " + tmp.getVal().getClose());
		//System.out.println(tmp.getVal());

	}

	public static listNode newListNodeRight(listNode currNode){
		listNode newNode;
		if(currNode.getNext() == null){
			newNode = new listNode();
			newNode.setPrevious(currNode);
			currNode.setNext(newNode);
		} else {
			newNode = currNode.getNext();
		}
		return newNode; 
	}

	public static listNode newListNodeLeft(listNode currNode){
		listNode newNode;
		if(currNode.getPrevious() == null){
			newNode = new listNode();
			newNode.setNext(currNode);
		} else {
			newNode = currNode.getPrevious();
		}
		return newNode; 
	}
	
}

class iterationTuple {
	private int open;
	private int close;
	public iterationTuple(int open){
		this.open = open;
	}

	public int getOpen(){
		return this.open;
	}
	
	public void setClose(int close){
		this.close = close;
	}
	
	public int getClose(){
		return this.close;
	}

}


class iterationTrackingNode {
	private iterationTrackingNode previous;
	private iterationTrackingNode next;
	private iterationTuple tupleVal = new iterationTuple(-1);
	
	public iterationTrackingNode(int open){
		tupleVal = new iterationTuple(open);
	}

	public iterationTrackingNode(){}

	// -------------------
	// Setters and Getters
	// -------------------


	public iterationTrackingNode getNext(){
		return this.next;
	}

	public iterationTrackingNode getPrevious(){
		return this.previous;
	}

	public iterationTuple getVal(){
		return this.tupleVal;
	}

	public void setTupleClose(int close){
		this.tupleVal.setClose(close);
	}

	public void setNext(iterationTrackingNode next){
		this.next = next;
	}


	public void setPrevious(iterationTrackingNode previous){
		this.previous = previous;
	}

	public void setVal(iterationTuple tupleVal){
		this.tupleVal = tupleVal;
	}
}


class listNode {
	private listNode previous;
	private listNode next;
	private int val;
	
	public listNode(){
		val = 0;	
	}

	public void incrementCell(){
		this.val++;
	}

	public void deincrementCell(){
		this.val--;
	}

	public void outputCell(){
		System.out.print((char) this.val);
	}



	// -------------------
	// Setters and Getters
	// -------------------


	public listNode getNext(){
		return this.next;
	}

	public listNode getPrevious(){
		return this.previous;
	}

	public int getVal(){
		return this.val;
	}

	public void setNext(listNode next){
		this.next = next;
	}


	public void setPrevious(listNode previous){
		this.previous = previous;
	}

	public void setVal(int val){
		this.val = val;
	}
}
