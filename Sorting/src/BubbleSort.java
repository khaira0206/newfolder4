
public class BubbleSort {

public static void main(String args[]){
	
	int[] arr =  new int[10];
	BubbleSort bs = new BubbleSort();
	
}

public int[] bubbleSort(int arr[]){
	int j = 0;
	for(int i = 1 ; i <= arr.length ; i++){
		
		while(j < arr.length - i){
			
			if(arr[j] > arr[j++]){
				swap(arr[j], arr[j++]);
				
			}
			
		}
		
		
	}

	return null;
}

private void swap(int a, int b){
	int temp =a;
	a = b;
	b = temp;
}
	

}


