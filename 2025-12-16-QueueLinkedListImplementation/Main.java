class Main{
	public static void main(String[] args){
		Queue tmp = new Queue();
		tmp.offer(1);
		tmp.offer(2);
		tmp.offer(3);
		System.out.println(tmp.peek());
		System.out.println(tmp.peek());
		System.out.println(tmp.poll());
		System.out.println(tmp.poll());
		System.out.println(tmp.poll());
	}
}
	

class Queue{
	Node start = null;
	Node end = start;

	public Queue(){
		
	}

	public void offer(int v){
		if(start == null){
			start = new Node(null, v);
			end = start;
		} else {
			Node tmpNode = new Node(start, v);
			start.setPrevious(tmpNode);
			start = tmpNode;
		}
	}

	public int peek(){
		return end.getValue();
	}

	public int poll(){
		int v = end.getValue();
		end.setNext(null);	
		end = end.getPrevious();
		return v;
	}
}

class Node{
	Node previous = null;
	Node next = null;
	int value = 0;

	public Node(Node next, int value){
		this.next = next;
		this.value = value;
	}

	public Node addNext(Node nextNode){
		this.next = nextNode;
		nextNode.previous = this;
		return nextNode;
	}

	public Node deleteNode(){
		this.previous.setNext(null);
		return this.previous;
	}

	public int getValue(){
		return value;
	}

	public Node getNext(){
		return next;
	}

	public void setNext(Node next){
		this.next = next;
	}
	
	public void setPrevious(Node previous){
		this.previous = previous;
	}

	public Node getPrevious(){
		return previous;
	}
}
