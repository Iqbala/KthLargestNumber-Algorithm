import java.util.Random;
/* Name: Ali H. Iqbal
*  Course: CSC-130
*  Instructor: Professor Jinsong Ouyang
*  Assignment #2: AVL tree VS Red Black tree.  
*/
public class Driver {

	public static void main(String[] args) {
	
		int n = 3000000; //size of array.
		
		//three arrays, in ascending, descending and random order.
		int[] array1 = new int[n];
		int[] array2 = new int[n];
		int[] array3 = new int[n];
		for (int i = 0; i < n; i++) {
			array1[i] = i + 1;
			array2[i] = n - i;
			array3[i] = i + 1;
		}
		// shuffle array
		Random r = new Random();
		for (int i = n - 1; i > 0; i--) {
			int position = r.nextInt(i + 1);
			// swap with random position
			int temp = array3[i];
			array3[i] = array3[position];
			array3[position] = temp;
		}
		
		// now test them with different arrays
		System.out.printf("%20s %10s %10s\n", "TYPE", "AVL", "RB");
		test("ascending", array1);
		test("descending", array2);
		test("random", array3);
	}
	
	private static void test(String label, int[] arr) {
		long start, end, time1, time2;
		
		// build AVL tree
		AVLTree tree1 = null;
		start = System.currentTimeMillis();
		// insert values into the tree
		for (int i = 0; i < arr.length; i++) {
			tree1 = AVLTree.insert(tree1, arr[i]);
		}
		end = System.currentTimeMillis();
		time1 = end - start;
		
		
		RBTree tree2 = null;
		start = System.currentTimeMillis();
		// insert value into tree
		for (int i = 0; i < arr.length; i++) {
			tree2 = RBTree.insert(tree2, arr[i]);
		}
		end = System.currentTimeMillis();
		time2 = end - start;
		
		// display measured time
		System.out.printf("%20s %10d %10d\n", label, time1, time2);
	}

}
