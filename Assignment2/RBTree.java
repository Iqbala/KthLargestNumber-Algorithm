//Red black tree class implements insert & delete node.
public class RBTree {

	public static final boolean RED = true;
	public static final boolean BLACK = false;
	
	private int data;     
	private boolean color; 
	private RBTree parent; 
	private RBTree left;   
	private RBTree right; 
	
	public RBTree(int data) {
		this.data = data;
		this.color = RED;
	}
	
	/*
	public void inorder() {
		if (left != null) {
			left.inorder();
		}
		System.out.print(data + " ");
		if (right != null) {
			right.inorder();
		}
	}
	*/
	
	// return true if node is black or null.
	private static boolean isBlack(RBTree node) {
		return node == null || node.color == BLACK;
	}
	
	/**
	 * Insert a new value into the RB tree.
	 * @param value the value to insert
	 * @return the new tree
	 */
	public static RBTree insert(RBTree tree, int value) {
		// find position to insert
		RBTree parent = null;
		RBTree current = tree;
		while (current != null) {
			parent = current;
			if (value < current.data) {
				//insert into left node
				current = parent.left;
			} else if (value > current.data) {
				//insert into right node
				current = parent.right;
			} else {
				//no duplicate node
				return tree;
			}
		}
		
		// create a red leaf node.
		RBTree node = new RBTree(value);
		node.parent = parent;
		
		if (parent == null) {
			tree = node;
		} else {
			// otherwise, add it into the tree
			if (value > parent.data) {
				parent.right = node;
			} else {
				parent.left = node;
			}
			// maintain color after insertion
			tree = maintainInsert(tree, node);
			tree = node;
		}
		
		
		tree.color = BLACK;
		
		return tree;
	}
	
	private static RBTree maintainInsert(RBTree root, RBTree node) {
		while (true) {
			RBTree parent = node.parent;
			if (parent == null) {
				
				return node;
			}
			if (parent == root) {
				break;
			}
			if (isBlack(parent)) {
				
				break;
			}
			
			if (parent.parent.left == parent.parent) {
				// parent is the left child of the grandfather.
				// check uncle's color
				if (isBlack(parent.parent.right)) {
					// uncle is black, if this is the right child
					// perform a left rotation on parent
					if (node == parent.right) {
						node = parent;
						rotateLeft(parent);
						parent = node.parent;
					}
					// recolor the grand father and parent
					// then rotate right on grandfather
					parent.color = BLACK;
					parent.parent.color = RED; 
					rotateRight(parent.parent);
					break;
				} else {
					// otherwise, uncle is red, recoloring father, uncle, grandfather.
					parent.color = BLACK;
					parent.parent.color = RED;
					parent.parent.right.color = BLACK;
					node = parent.parent;
				}
				
			} else {
				// the mirror case
				if (isBlack(parent.parent.left)) {
				
					if (node == parent.left) {
						node = parent;
						rotateRight(parent);
						parent = node.parent;
					}
					
					parent.color = BLACK;
					parent.parent.color = RED; 
					rotateLeft(parent.parent);
					break;
				} else {
					
					parent.color = BLACK;
					parent.parent.color = RED;
					parent.parent.right.color = BLACK;
					node = parent.parent;
				}
			}
		}
		return root;
	}
	

	public static RBTree remove(RBTree tree, int value) {
		// find the node to remove
		RBTree current = tree;
		while (current != null && value != current.data) {
			if (value < current.data) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		if (current == null) {
			// the value to be removed does not exist in the tree
			return tree;
		}
		
		// normal BST removal
		if (current.left != null && current.right != null) {
			
			RBTree min = current.right;
			while (min.left != null) {
				min = min.left;
			}
			current.data = min.data;
			current = min;
		}
		
		
		RBTree child = current.left == null ? current.right : current.left;
		// remove current node from tree
		if (child != null) {
			child.parent = current.parent;
		}
		if (current.parent == null) {
			
			return child;
		}
		
		if (current.parent.left == current) {
			current.parent.left = child;
		} else {
			current.parent.right = child;
		}
		if (current.color == BLACK) {
			maintainDelection(child, current.parent);
			// rebuild the root node
			while (child.parent != null) {
				child = child.parent;
			}
			tree = child;
		}
		if (tree != null) {
			tree.color = BLACK;
		}
		
		return tree;
	}
	
	private static void maintainDelection(RBTree node, RBTree parent) {
		while (node != null) {
			if (node.parent == null || !isBlack(node)) {
				node.color = BLACK;
				break;
			}
			
			if (node == node.parent.left) { 
		
                RBTree sibling = node.parent.right;
                if (!isBlack(sibling)) {
                 
                    parent.color = RED;
                    sibling.color = BLACK;
                    rotateLeft(parent);
                    sibling = parent.right;
                }
                if (isBlack(sibling.left) && isBlack(sibling.right)) {
                  
                    sibling.color = RED;
                    node = parent;
                    parent = node.parent;
                } else {
                    if (isBlack(sibling.right)) {
                    	// double rotation
                        sibling.color = RED;
                        sibling.left.color = BLACK;
                        rotateRight(sibling);
                        sibling = parent.right;
                    }

                    // two red children
                    sibling.color = parent.color;
                    parent.color = BLACK;
                    sibling.right.color = BLACK;
                    rotateLeft(parent);
                    break;
                }
                
			} else { //mirror case
				RBTree sibling = node.parent.left;
	            if (!isBlack(sibling)) {
	                
	                parent.color = RED;
	                sibling.color = BLACK;
	                rotateRight(parent);
	                sibling = parent.left;
	            }
	            if (isBlack(sibling.left) && isBlack(sibling.right)) {
	            
	                sibling.color = RED;
	                node = parent;
	                parent = node.parent;
	            } else {
	            	if (!isBlack(sibling.left)) {
	            		// double rotation
	                    sibling.color = RED;
	                    sibling.right.color = BLACK;
	                    rotateLeft(sibling);
	                    sibling = parent.left;
	                }

	                // two red children
	                sibling.color = parent.color;
	                parent.color = BLACK;
	                sibling.left.color = BLACK;
	                rotateRight(parent);
	                break;
	            }
			}
		}
	}
	
	private static void rotateLeft(RBTree node) {
		RBTree parent = node.parent;
		RBTree right = node.right;
		node.right = right.left;
		if (node.right != null) {
			node.right.parent = node;
		}
		right.left = node;
		node.parent = right;
		// fix parent/child link
		right.parent = parent;
		if (parent != null) {
			if (parent.left == node) {
				parent.left = right;
			} else {
				parent.right = right;
			}
		}
	}
	
	private static void rotateRight(RBTree node) {
		RBTree parent = node.parent;
		RBTree left = node.left;
		node.left = left.right;
		if (node.left != null) {
			node.left.parent = node;
		}
		left.right = node;
		node.parent = left;
		left.parent = parent;
		if (parent != null) {
			if (node == parent.left) {
				parent.left = left;
			} else {
				parent.right = left;
			}
		}
	}
}
