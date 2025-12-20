class Main{
	public static void main(String[] args){
		hashMap map = new hashMap(10);
		//map.addItem(10, 5);
		//map.addItem(4, 3);
		map.addItem(3,1);
		map.addItem(13, 2);
		map.addItem(23, 3);
		map.addItem(23, 6);
		map.addItem(23, 64);
		map.print();
		//map.addItem(3, -1);
		//map.addItem(23, 5);
		//map.print();
		System.out.println(map.getVal(13));
	}	
}

class hashMap{
	horizantalNode[] arrayOfNode; //this is a refrences!
	public hashMap(int size){
		arrayOfNode = new horizantalNode[size];
	}

	public void addItem(int k, int v){
		horizantalNode newNode = new horizantalNode(k,v, arrayOfNode.length); // time to add collisions
		if(arrayOfNode[newNode.getHash()] != null){
			if(arrayOfNode[newNode.getHash()].getKey() == k){ // if the keys are the same replace
				newNode.setNextNode(arrayOfNode[newNode.getHash()].getNextNode());
				arrayOfNode[newNode.getHash()] = newNode;
			
			//} else if (arrayOfNode) { // ok if the have the same hash
			} else { // if the keys are not the same, we must add horizantaly 
				horizantalNode tmpNode = arrayOfNode[newNode.getHash()];
				while(tmpNode.getNextNode() != null){
					horizantalNode prev = tmpNode;
					tmpNode = tmpNode.getNextNode();
					if(tmpNode.getKey() == k){	
						newNode.setNextNode(tmpNode.getNextNode());
						tmpNode = newNode;
						prev.setNextNode(tmpNode);
						//arrayOfNode[newNode.getHash()] = newNode;
						return;
					}
				}
				arrayOfNode[newNode.getHash()].addNextNode(newNode);
			}
		} else {
			arrayOfNode[newNode.getHash()] = newNode;
		}


	}

	public void print(){
		System.out.print("[");
		for(horizantalNode i : arrayOfNode){
			if(i != null){
				i.print();
			} else {
				System.out.print("(EMPTY)");
			}
		}
		System.out.println("]");
	}

	public int getVal(int key){
		//return arrayOfNode[horizantalNode.getHash(key, arrayOfNode.length)].getVal();
		if(arrayOfNode[horizantalNode.getHash(key, arrayOfNode.length)].getKey()==key){
			return arrayOfNode[horizantalNode.getHash(key, arrayOfNode.length)].getVal();
		} else {
			horizantalNode tmp = arrayOfNode[horizantalNode.getHash(key, arrayOfNode.length)];
			while(tmp.getNextNode().getKey() != key){
				tmp = tmp.getNextNode();
			}
			tmp = tmp.getNextNode();
			return tmp.getVal();
		}

	}
}

class horizantalNode{
	private int key;
	private int val;
	private int hashVal;

	private horizantalNode nextNode;

	public horizantalNode(int k, int v, int mLength){
		key = k;
		val = v;
		hashVal = createHash(mLength);
	}

	private int createHash(int mLength){
		int hash = key%mLength;
		return hash;
	}

	public int getHash(){
		return hashVal;
	}
	
	public static int getHash(int key, int mLength){
		return key%mLength;
	}

	public int getKey(){
		return key;
	}

	public int getVal(){
		return val;
	}

	public void print(){
		System.out.print("("+key+":"+val+":"+hashVal+")");
		if(nextNode != null){
			horizantalNode nodeToAddTo = nextNode;
			nodeToAddTo.print();
		}

	}

	public horizantalNode getNextNode(){
		return nextNode;
	}

	public void setNextNode(horizantalNode n){
		nextNode = n;
	}

	public void addNextNode(horizantalNode n){
		if(nextNode == null){
			nextNode = n;
			return;
		}
		horizantalNode nodeToAddTo = nextNode;
		while(nodeToAddTo.getNextNode() != null){
			//System.out.println("yikes");
			//nodeToAddTo.print();
			nodeToAddTo = nodeToAddTo.getNextNode();
		}
		//System.out.println("yikes");
		nodeToAddTo.setNextNode(n);
	}
}	
