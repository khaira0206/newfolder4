import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

class TreeNode {

	int data;
	TreeNode left, right;

	TreeNode(int data) {

		this.data = data;
	}

}

public class minimalTree {

	TreeNode createMinimalBST(int arr[]) {

		return createMinimalBST(arr, 0, arr.length - 1);
	}

	TreeNode createMinimalBST(int arr[], int start, int end) {

		if (end < start) {
			return null;
		}
		int mid = (start + end) / 2;

		TreeNode n = new TreeNode(arr[mid]);

		n.left = createMinimalBST(arr, start, mid - 1);
		n.right = createMinimalBST(arr, mid + 1, end);

		return n;
	}

	public void show(TreeNode node) {
		if (node != null) {
			show(node.left);
			System.out.println("" + node.data);
			show(node.right);
		}
	}

	ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {

		ArrayList<LinkedList<TreeNode>> lists = new ArrayList<LinkedList<TreeNode>>();
		createLevelLinkedList(root, lists, 0);
		return lists;
	}

	void createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {

		if (root == null) {
			return;
		}

		LinkedList<TreeNode> list;

		if (lists.size() == level) {
			list = new LinkedList<TreeNode>();
			lists.add(list);

		} else {
			list = lists.get(level);

		}
		list.add(root);
		createLevelLinkedList(root.left, lists, level + 1);
		createLevelLinkedList(root.right, lists, level + 1);

	}
	void printList(ArrayList<LinkedList<TreeNode>> lists){
		
		for(LinkedList<TreeNode> list : lists)
		{
			for(TreeNode lst : list){
				
				System.out.print(" "+lst.data +" ");
				}
			
			System.out.println(" ");
		}
		
		
	}
	
	boolean checkBalanced(TreeNode root){
		
		return checkHeight(root)!= Integer.MIN_VALUE;
	}
	int checkHeight(TreeNode root){
		
		if(root == null)
			return -1;
		int leftHeight = checkHeight(root.left);
		if(leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int rightHeight = checkHeight(root.right);
		if(rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int diff = leftHeight - rightHeight;
		
		if(Math.abs(diff) > 1){
			return Integer.MIN_VALUE;
		}
		else{
			return Math.max(leftHeight, rightHeight) + 1;
		}
		
		
	}

	public static void main(String args[]) {
		minimalTree mt = new minimalTree();
		int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		TreeNode n = mt.createMinimalBST(arr);
		//mt.show(n);
		ArrayList<LinkedList<TreeNode>> lists = mt.createLevelLinkedList(n);
		mt.printList(lists);
		
		boolean bal = mt.checkBalanced(n);
  System.out.println(bal == true ? "Tree is balanced" : "Tree is not balanced");
	}
}
