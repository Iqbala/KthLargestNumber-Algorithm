import java.util.Random;
import java.util.Arrays;
/* Name: Ali H. Iqbal
*  Course: CSC-130
*  Instructor: Professor Jinsong Ouyang
*  Assignment #4 : KthLargestNumber* 
*/
public class KthLargestNum {

    public static void main(String[] args) {
        Random gen = new Random(1234567);
        int[] num = new int[1000000];
        for (int i = 0; i < num.length; i++) {
            num[i] = gen.nextInt();
        } 

        int kthLargest, kth = 7;
		Heap hp = new Heap(num); 
        kthLargest = hp.kthLargest(kth);
        System.out.printf("HeapSort: The %dth largest number is %d.\n", kth, kthLargest);
	    
        quickSelect quick = new quickSelect(num);
        kthLargest = quick.kthLargest(kth);
        System.out.printf("QuickSelect: The %dth largest number is %d.\n", kth, kthLargest);
    }
}

class quickSelect {
    private int[] arr;

    public quickSelect(int[] arr){
        this.arr = Arrays.copyOf(arr, arr.length);
    }

    public int kthLargest(int kth){
        return kthLargest(0, arr.length-1, kth);
    }

    public int kthLargest(int left, int right, int kth){
        int curr = numSplit(left, right);
        if (curr-left == kth-1) {
            return arr[curr];
        }
        if (curr-left > kth-1) {
            return kthLargest(left, curr-1, kth);
        }

        return kthLargest(curr+1, right, kth-curr+left-1);
    }

    public void swap(int x, int y){
        int z = arr[x];
        arr[x] = arr[y];
        arr[y] = z;
    }

    public int part(int left, int right){//partition
        int i = left;
        for (int j=left; j <= right-1; j++){
            if (arr[j] > arr[right]){
                swap(i, j);
                i++;
            }
        }
        swap(i, right);
        return i;
    }

    public int numSplit(int left, int right){
        int piv = (int)(Math.random()*(right-left+1));
        swap(left + piv, right);
        return part(left, right);
    }
}

class Heap {
    private int[] arr;
    private int hpSize;

    public Heap(int[] arr){
        this.arr = Arrays.copyOf(arr, arr.length);
        this.hpSize = this.arr.length;
    }
	public void buildHeap(){
        hpSize = size() - 1;
        for(int i=(size()/2); i >= 0; i--){
            heapify(i);
        }
    }
    public int size(){
        return arr.length;
    }

    public void swap(int x, int y){
        int z = arr[x];
        arr[x] = arr[y];
        arr[y] = z;
    }

    public int kthLargest(int kth){
        buildHeap();
        hpSize = size() - 1;
        for(int i=hpSize; i >= size()-kth; i--){
            swap(0, hpSize);
            hpSize--;
            heapify(0);
        }
        return arr[arr.length - kth];
    }
    
    public void heapify(int j){
        int biggest = j;
        int right = j*2 + 1;
		int left = j*2;
        
		
		if(right <= hpSize && arr[right] > arr[biggest]){
            biggest = right;
        }
        if(left <= hpSize && arr[left] > arr[j]){
            biggest = left;
        }
        
        if(biggest != j){
            swap(j, biggest);
            heapify(biggest);
        }
    }
}
