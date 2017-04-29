//AVL tree class implements insert & delete node.
public class AVLTree {

		private int data;      
		private int height;    
		private AVLTree left;  
		private AVLTree right; 
		
		public AVLTree(int data) {
			this.data = data;
			this.height = 0;
		}
		

		public static AVLTree insert(AVLTree tree, int val) {
			if (tree == null) {
				return new AVLTree(val);
			}
			if (val < tree.data) { // insert into left
				tree.left = insert(tree.left, val);
				tree.updateHeight();
			
				if (height(tree.left) - height(tree.right) > 1) {
					if (val > tree.left.data) {
						tree.left = tree.left.rotateLeft();
						tree.updateHeight();
					}
					return tree.rotateRight();
				}
				
			} else if (val > tree.data) { //insert into right
				tree.right = insert(tree.right, val);
				tree.updateHeight();
			
				if (height(tree.right) - height(tree.left) > 1) {
					if (val < tree.right.data) {
						// left rotation on current node.
						tree.right = tree.right.rotateRight();
						tree.updateHeight();
					}
					return tree.rotateLeft();
				}
			}
			return tree;
		}
		
	
		  //Remove value from tree.
		
		public static AVLTree remove(AVLTree tree, int value) {
			
			if (tree == null) {
				//if value is not in tree
				return null;
			}
			if (value < tree.data) {
				// remove from left child
				tree.left = remove(tree.left, value);
			} else if (value > tree.data) {
				// remove from right child
				tree.right = remove(tree.right, value);
			} else {
				
				if (tree.left == null) { 
					return tree.right;
				}
				if (tree.right == null) {
					return tree.left;
				}
	
				AVLTree leftmost = tree.right;
				while (leftmost.left != null) {
					leftmost = leftmost.left;
				}
				tree.data = leftmost.data;
				tree.right = remove(tree.right, leftmost.data);
			}
			
			// after normal deletion.
	
			tree.updateHeight();
			if (height(tree.left) - height(tree.right) > 1) {
				// unbalanced, left tree is taller
				if (height(tree.left.left) < height(tree.left.right)) {
					// perform a left rotation on left child
					tree.left = tree.left.rotateLeft();
					tree.updateHeight();
				}
				// then a right rotation on this tree root
				return tree.rotateRight();
			}
			
			else if (height(tree.right) - height(tree.left) > 1) {
				
				if (height(tree.right.left) > height(tree.right.right)) {
					//right rotation on the right child
					tree.right = tree.right.rotateRight();
					tree.updateHeight();
				}
				//left rotate on this tree root
				return tree.rotateLeft();
			}
			
			return tree;
		}
		
		
		private AVLTree rotateLeft() {
			AVLTree right = this.right;
			this.right = right.left;
			updateHeight();
			right.left = this;
			right.updateHeight();
			return right;
		}
		
	
		private AVLTree rotateRight() {
			AVLTree left = this.left;
			this.left = left.right;
			updateHeight();
			left.right = this;
			left.updateHeight();
			return left;
		}
		
		
		  //update tree height
		 
		private void updateHeight() {
			this.height = 1 + Math.max(height(left), height(right));
		}
		
	
		 ///return height of the tree, if null return -1 
		private static int height(AVLTree tree) {
			if (tree == null) {
				return -1;
			}
			return tree.height;
		}
}

	

