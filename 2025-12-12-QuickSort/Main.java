import java.lang.Math;


class main{
	public static void main(String[] args){
		int[] nums = GenerateRandomList(1000000);
		PrintArray(nums);
		System.out.println("\n\n"+pickPivot(nums));

		nums = recursiveQuickSort(nums);
		PrintArray(nums);
		System.out.println(checkOrder(nums));
	}	

	public static int[] recursiveQuickSort(int[] arr){
		System.out.println("\n\n");
		PrintArray(arr);
		if(arr.length <= 1){
			return arr;
		} else {
			int tmp = arr[0];
			for(int i = 0; i < arr.length; i++){
				if(tmp != arr[i]){
					i = arr.length+100;
					tmp = -1;
					
				}
			}
			if(tmp == arr[0]){
				System.out.println("yikes");
				return arr;
			}
		}
		int numsInL = 0;
		double pivot = pickPivot(arr);
		for(int i = 0; i < arr.length; i++){
			if(arr[i] <= pivot){
				numsInL++;
			}
		}
		int numsInR = arr.length - numsInL;
		int[] numsL = new int[numsInL];
		int[] numsR = new int[numsInR];

		//System.out.println("\n\n"+numsInL);
		//System.out.println(numsInR);
		
		int L = 0;
		int R = 0;
		for(int i : arr){
			if(i <= pivot){
				numsL[L] = i;
				L++;
			} else {
				//System.out.println(R);
				numsR[R] = i;
				R++;
			}
		}

		numsL = recursiveQuickSort(numsL);
		numsR = recursiveQuickSort(numsR);
		
		for(int i = 0; i < numsL.length; i++){
			arr[i] = numsL[i];
		}
		for(int i = 0; i < numsR.length; i++){
			arr[i+numsL.length] = numsR[i];
		}
		PrintArray(arr);
		
		return arr;

	}
	
	public static boolean checkOrder(int[] arr){
		int initial = arr[0];
		for(int i : arr){
			if(initial > i){
				return false;
			}
			initial = i;
		}
		return true;

	}

	public static double pickPivot(int[] arr){ // we will pick the pivot point as the median
		int num = 0;
		for(int i : arr){
			num+=i;
		}
		return ((double)num/(double)arr.length);
	}
		
	public static int[] GenerateRandomList(int length){
		int[] numArray = new int[length];
		for(int i = 0; i < numArray.length; i++){
			numArray[i] = (int)((Math.random()*1000)+1); 
		}	
		return numArray;
	}

	public static void PrintArray(int[] arr){
		for(int i : arr){
			System.out.print(i + " , ");
		}
		System.out.println();
	}
}
