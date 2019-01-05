package studentCoursesBackup.util;

import java.util.ArrayList;
import java.util.List;

import studentCoursesBackup.myTree.Node;

/**
 * This class builds 3 tree using input.txt and the observer pattern
 * @author Dipika Patil
 */
public class TreeBuilder{
	/**
	 * enum to indicate whether the update method is being called 
	 * to insert a new course in an existing node, or 
	 * to delete a course from an existing node or
	 * to insert a new node or
	 * to delete a new node
	 * @author Dipika Patil
	 */
	public static enum UpdateAction {Insert, Delete}

	/**
	 * enum to indicate type of node 
	 * Observable - Subject node
	 * Observer - Backup node
	 * @author Dipika Patil
	 */
	public static enum NodeType{Subject, Backup1, Backup2}

	/**
	 * Instance members
	 */
	private Node head, headBkp1, headBkp2;
	private List<String> inorderOrderOutputList, inorderOrderOutputListBkp1, inorderOrderOutputListBkp2;

	/**
	 * constructor
	 */
	public TreeBuilder() {
		head = null;
		headBkp1 = null;
		headBkp2 = null;
		inorderOrderOutputList = new ArrayList<>();
		inorderOrderOutputListBkp1 = new ArrayList<>();
		inorderOrderOutputListBkp2 = new ArrayList<>();
	}

	/**
	 * overridden Object class method returns student number and courses
	 * @param void
	 * @return String
	 */
	@Override
	public String toString() {
		List<String> tree = new ArrayList<>();
		inOrderTraversal(head, tree);
		return String.join("", tree);
	}

	/**
	 * Overridden Object class method to get unique value to every 
	 * object - for Tree it is just a placeholder
	 * @param void
	 * @return int
	 */
	@Override
	public int hashCode() {
		return 0;
	}

	/**
	 * Overridden method to release system resources 
	 * @return void
	 * @param void
	 */
	@Override
	public void finalize() throws Throwable {
		//no system resource to release
		super.finalize();
	}

	/**
	 * Overridden method of Object class to compare objects
	 * @return boolean - true if object matches and false if not
	 * @param object to compare
	 */
	@Override
	public boolean equals(Object objIn) {
		if (objIn instanceof TreeBuilder) {
			TreeBuilder treeBuilder = (TreeBuilder) objIn;
			return this.toString().equals(treeBuilder.toString());
		}
		return false;
	}

	/**
	 * Accessors and Mutators methods of instance members
	 */
	public Node getHead() {
		return head;
	}

	public void setHead(Node headIn) {
		this.head = headIn;
	}

	public Node getHeadBkp1() {
		return headBkp1;
	}

	public void setHeadBkp1(Node headBkp1In) {
		this.headBkp1 = headBkp1In;
	}

	public Node getHeadBkp2() {
		return headBkp2;
	}

	public void setHeadBkp2(Node headBkp2In) {
		this.headBkp2 = headBkp2In;
	}

	public List<String> getInorderOrderOutputList() {
		return inorderOrderOutputList;
	}

	public void setInorderOrderOutputList(List<String> inorderOrderOutputListIn) {
		this.inorderOrderOutputList = inorderOrderOutputListIn;
	}

	public List<String> getInorderOrderOutputListBkp1() {
		return inorderOrderOutputListBkp1;
	}

	public void setInorderOrderOutputListBkp1(List<String> inorderOrderOutputListBkp1In) {
		this.inorderOrderOutputListBkp1 = inorderOrderOutputListBkp1In;
	}

	public List<String> getInorderOrderOutputListBkp2() {
		return inorderOrderOutputListBkp2;
	}

	public void setInorderOrderOutputListBkp2(List<String> inorderOrderOutputListBkp2In) {
		this.inorderOrderOutputListBkp2 = inorderOrderOutputListBkp2In;
	}

	/**
	 * Method to read and parse input file line by line
	 * @param FileIn - file descriptor of file opened in driver code
	 * @return - void
	 */
	public void createTree(FileProcessor FileIn) {
		String line = "";
		int studentBnumber;
		String studentCourse = "";
		while ((line = FileIn.getNextLine()) != null) {
			try {
				studentBnumber = Integer.parseInt(line.split(":")[0].trim());
				studentCourse = line.split(":")[1].trim();
				createTree(studentBnumber, studentCourse);
			} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
				System.err.println("Error: Issue with reading and parsing input file line by line");
				e.printStackTrace();
				System.exit(1);
			} finally {
				//no deallocation needed here
			}
		}
	}

	/**
	 * Overloaded method to create node in tree using required 
	 * parameters
	 * @param studentBnumberIn
	 * @param studentCourseIn
	 * @return - void
	 */
	public void createTree(int studentBnumberIn, String studentCourseIn) {
		//when root is null
		if (head == null) {
			try {
				Node nodeOrig = new Node(studentBnumberIn, studentCourseIn);
				Node nodeBkp1 = (Node) nodeOrig.clone();
				Node nodeBkp2 = (Node) nodeOrig.clone();

				nodeOrig.registerObserver(nodeBkp1);
				nodeOrig.registerObserver(nodeBkp2);

				head = nodeOrig;
				headBkp1 = nodeBkp1;
				headBkp2 = nodeBkp2;
			} catch (CloneNotSupportedException e) {
				System.err.println("Error: Issue with creating/cloning first node");
				e.printStackTrace();
				System.exit(1);
			} finally {
				//no deallocation needed here
			}
		} else {
			allocateNode(studentBnumberIn, studentCourseIn, head, UpdateAction.Insert);
		}
	}

	/**
	 * Method to read and parse delete/update file line by line
	 * @param FileIn - file descriptor of file opened in driver code
	 * @return - void
	 */
	public void deleteTree(FileProcessor FileIn) {
		String line = "";
		int studentBnumber;
		String studentCourse = "";
		while ((line = FileIn.getNextLine()) != null) {
			try {
				studentBnumber = Integer.parseInt(line.split(":")[0].trim());
				studentCourse = line.split(":")[1].trim();
				deleteTree(studentBnumber,studentCourse);
			} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
				System.err.println("Error: Issue with reading and parsing delete file line by line");
				e.printStackTrace();
				System.exit(1);
			} finally {
				//no deallocation needed here
			}
		}
	}

	/**
	 * Overloaded method to delete tree using required parameters
	 * @param studentBnumberIn
	 * @param studentCourseIn
	 * @return void
	 */
	public void deleteTree(int studentBnumberIn, String studentCourseIn) {
		if (head == null) {
			return;
		} else {
			allocateNode(studentBnumberIn, studentCourseIn, head, UpdateAction.Delete);
		}
	}

	/**
	 * Method to allocate newly created backup node to backup tree
	 * @param bkpNodeIn - node created by cloning subject node
	 * @param headNodeIn - head node of tree
	 * @param functionIn - Insert, Delete
	 * @param studentCourseIn - course - when node exist in tree
	 */
	public void allocateBackupNode(Node bkpNodeIn, Node headNodeIn, UpdateAction functionIn, String studentCourseIn) {
		//node Bnumber < head node Bnumber - traverse left in BST
		if (headNodeIn.getStudentBnumber() > bkpNodeIn.getStudentBnumber()) {
			if (headNodeIn.getLeft() != null) {
				allocateBackupNode(bkpNodeIn, headNodeIn.getLeft(), functionIn, studentCourseIn);
			} else {
				headNodeIn.setLeft(bkpNodeIn);
			}
			//node Bnumber > head node Bnumber - traverse right in BST	
		} else if (headNodeIn.getStudentBnumber() < bkpNodeIn.getStudentBnumber()) {
			if (headNodeIn.getRight() != null) {
				allocateBackupNode(bkpNodeIn, headNodeIn.getRight(), functionIn, studentCourseIn);
			} else {
				headNodeIn.setRight(bkpNodeIn);
			}
		} else {
			//node exist - update the course as per action
			if (functionIn == UpdateAction.Insert) {
				bkpNodeIn.addCourse(studentCourseIn);
				return;
			} else if (functionIn == UpdateAction.Delete) {
				bkpNodeIn.removeCourse(studentCourseIn);
				return;
			}
		}
	}

	/**
	 * Method to allocate newly created subject node to tree
	 * @param studentBnumberIn - BST is arranged using B# 
	 * @param studentCourseIn - course of student - which keep changing
	 * @param headNodeIn - head node of tree
	 * @param functionIn - Insert, Delete action on node
	 */
	public void allocateNode(int studentBnumberIn, String studentCourseIn, Node headNodeIn, UpdateAction functionIn) {
		//head node B# > new node B# - traverse left in BST
		if (headNodeIn.getStudentBnumber() > studentBnumberIn) {
			if (headNodeIn.getLeft() != null) {
				allocateNode(studentBnumberIn, studentCourseIn, headNodeIn.getLeft(), functionIn);
			} else {
				//this case is possible only in case of new node creation
				if (functionIn == UpdateAction.Insert) {
					try {
						Node nodeOrig = new Node(studentBnumberIn, studentCourseIn);
						Node nodeBkp1 = (Node) nodeOrig.clone();
						Node nodeBkp2 = (Node) nodeOrig.clone();

						nodeOrig.registerObserver(nodeBkp1);
						nodeOrig.registerObserver(nodeBkp2);
						allocateBackupNode(nodeBkp1, headBkp1, functionIn, studentCourseIn);
						allocateBackupNode(nodeBkp2, headBkp2, functionIn, studentCourseIn);
						headNodeIn.setLeft(nodeOrig);
					} catch (CloneNotSupportedException e) {
						System.err.println("Error: Issue with cloning backup node");
						e.printStackTrace();
						System.exit(1);
					} finally {
						//no deallocation needed here
					}
				}
				return;
			}
			//head node B# < new node B# - traverse right in BST	
		} else if (headNodeIn.getStudentBnumber() < studentBnumberIn) {
			if (headNodeIn.getRight() != null) {
				allocateNode(studentBnumberIn, studentCourseIn, headNodeIn.getRight(), functionIn);
			} else {
				//this case is possible only in case of new node creation
				if (functionIn == UpdateAction.Insert) {
					try {
						Node nodeOrig = new Node(studentBnumberIn, studentCourseIn);
						Node nodeBkp1 = (Node) nodeOrig.clone();
						Node nodeBkp2 = (Node) nodeOrig.clone();

						nodeOrig.registerObserver(nodeBkp1);
						nodeOrig.registerObserver(nodeBkp2);
						allocateBackupNode(nodeBkp1, headBkp1, functionIn, studentCourseIn);
						allocateBackupNode(nodeBkp2, headBkp2, functionIn, studentCourseIn);

						headNodeIn.setRight(nodeOrig);
					} catch (CloneNotSupportedException e) {
						System.err.println("Error: Issue with cloning backup node");
						e.printStackTrace();
						System.exit(1);
					} finally {
						//no deallocation needed here
					}
				}
				return;
			}
		} else {
			//node exist - update the course as per action
			if (functionIn == UpdateAction.Insert) {
				headNodeIn.addCourse(studentCourseIn);
				return;
			} else if (functionIn == UpdateAction.Delete) {
				headNodeIn.removeCourse(studentCourseIn);
				return;
			}
		}
	}

	/**
	 * print node as per node type
	 * @param node type
	 * @return List<String> - which holds tree data in ascending order
	 */
	public List<String> printNodes(NodeType nodeTypeIn){
		switch (nodeTypeIn) {
		case Subject: inOrderTraversal(head, inorderOrderOutputList); 
		return inorderOrderOutputList;
		case Backup1: inOrderTraversal(headBkp1, inorderOrderOutputListBkp1);
		return inorderOrderOutputListBkp1;
		case Backup2: inOrderTraversal(headBkp2, inorderOrderOutputListBkp2);
		return inorderOrderOutputListBkp2;
		}
		return null; 
	}

	/**
	 * in order traversal of BST - to get node data in ascending order
	 * @param nodeIn - invoked with head node
	 * @param ListIn - list holding data
	 * @return - void
	 */
	public void inOrderTraversal(Node nodeIn, List<String> ListIn) {
		//traverse as left --> head -->right - ascending order
		if (nodeIn != null) {
			if (nodeIn.getLeft() != null) {
				inOrderTraversal(nodeIn.getLeft(), ListIn);
			}
			ListIn.add(nodeIn.toString());
			if (nodeIn.getRight() != null) {
				inOrderTraversal(nodeIn.getRight(), ListIn);
			}
		}
	}
}