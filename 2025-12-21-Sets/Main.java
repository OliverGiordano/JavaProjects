class Main{
	public static void main(String[] args){
		Set n = new Set(20);
		n.add(12);
		n.add(3);
		n.add(7);
		n.add(3);
		n.add(6);
		System.out.println(n.size());
		n.print();
		n.remove(3);
		n.remove(6);
		n.print();
	}
}


class Set{
	private int size;
	private Node[] nodes;
	public Set(int size){
		nodes = new Node[size];	
		this.size = size;
	}

	public void add(int val){
		nodes[Node.getHash(val, size)] = new Node(val);
	}

	public void clear(){
		nodes = new Node[size];
	}

	public void remove(int val){
		nodes[Node.getHash(val, size)] = null;
	}

	public int size(){
		int s = 0;
		for(Node i : nodes){
			if(i != null){
				s++;
			}
		}
		return s;
	}
	
	public boolean contains(int val){
		return (nodes[Node.getHash(val, size)] != null);
	}

	public void print(){
		System.out.print("[");
		for(Node i : nodes){
			if(i != null){
				System.out.print(i.getVal()+",");
			}
		}
		System.out.println("]");
	}
}

class Node{
	//private int hash;
	private int val;

	public Node(int val){
		this.val = val;
		//this.hash = Node.getHash(val);
	}

	public static int getHash(int val, int size){
		return val%size;
	}

	public int getVal(){
		return val;
	}
}
