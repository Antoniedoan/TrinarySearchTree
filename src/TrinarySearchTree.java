/*************************************************************************
 *  Author:		Peter Salama 
 *  Email:		salamap@gmail.com
 *
 *	Implementation of a tri-nary search tree using generics.
 *	A tri-nary tree is much like a binary tree but with three 
 *	child nodes for each parent instead of two -- with the 
 *	left node being values less than the parent, the right node 
 *	values greater than the parent, and the middle nodes values 
 *	equal to the parent. 
 *	NullPointerException is thrown if the client attempts to add
 * 	null node to the tree. RuntimeException is thrown if the 
 * 	client attempts to remove a node that is not in the tree.
 *	Certain helper methods have been created to aide in testing 
 *	the addition and deletion methods.
 *
 *************************************************************************/
import java.util.*;

@SuppressWarnings("hiding")
public class TrinarySearchTree <Object extends Comparable<Object>> {
	private Node root;
	
	private class Node {
		private Object data;
		private Node left, mid, right;

		public Node (Object value) {
			this.data = value;
			this.left = null;
			this.right = null;
			this.mid = null;
		}
	}
	
	/***********************************************************************
	 *  Add
	 ***********************************************************************/
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		root = add(root, value);
	}
	
	private Node add(Node node, Object value) {
		if (node == null) {
			return new Node(value);
		}
		int compare = value.compareTo(node.data);
		if (compare == 0) {
			node.mid = add(node.mid, value);
		}
		else if (compare > 0) {
			node.right = add(node.right, value);
		}
		else {
			node.left = add(node.left, value);
		}
		return node;
	}

	/***********************************************************************
	 *  Delete
	 ***********************************************************************/
	public void delete(Object value) {
		root = delete(root, value);
	}
	
	private Node delete(Node node, Object value) {
		if (node == null) {
			throw new RuntimeException(value.toString() + " Does not exist in the tree");
		}
		int compare = value.compareTo(node.data);
		if (compare < 0) {
			node.left = delete(node.left, value);
		}
		else if (compare > 0) {
			node.right = delete(node.right, value);
		}
		else {
			if(node.right == null) {
				return node.left;
			}
			if(node.left == null) {
				return node.right;
			}
			Node temp = node;
			node = findMin(temp.right);
			node.right = delete(temp.right, node.data);
			node.left = temp.left;
			}
		return node;
	}
	
	
	private Node findMin(Node node) {
		if(node.left == null) {
			return node;
		}
		return findMin(node.left);
	}

	
	public void print() {
		if(root == null) {
			return;
		}
        Queue<Node> queue = new LinkedList<Node>();
        Node flag= new Node((Object)"");
        queue.add(root);
        queue.add(flag);
        while (!queue.isEmpty()) {
            Node node = (Node)queue.poll();
            if (node != flag) {
                System.out.print(node.data + " ");
                if (node.left != null)
                    queue.add(node.left);
                if (node.mid != null)
                	queue.add(node.mid);
                if (node.right != null)
                    queue.add(node.right);
            } 
            else if (!queue.isEmpty()) {
                queue.add(flag);
                System.out.println();
            }
        }
	}
	
	
	// Testing method
	public static void main(String[] args) {
		TrinarySearchTree<Integer> tree = new TrinarySearchTree<Integer>();
		
		for(int i = 0; i <=20; i++) {		 
			int val = (int)(Math.random()*5) + 1;
			tree.add(val);
		}
		
		try {
			tree.add(null);
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println();
		}
		
		tree.print();
		System.out.println();
		System.out.println();
		
		for (int i = 0; i<=5; i++) {
			try {
				tree.delete(i);
				System.out.println("Successfully deleted " + i);
				System.out.println();
			}
			catch (RuntimeException e) {
				e.printStackTrace();
				System.out.println();
			}
			tree.print();
			System.out.println();
			System.out.println();
		}
	}

}