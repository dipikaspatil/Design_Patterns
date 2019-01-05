package studentCoursesBackup.myTree;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import studentCoursesBackup.util.TreeBuilder;

/**
 * This class holds the node structure. Every node is a subject of 
 * observer pattern
 * @author Dipika Patil
 */
public class Node implements SubjectI , ObserverI, Cloneable {

	/**
	 * Instance data members
	 */
	private int studentBnumber;
	private List<String> studentCourse;
	private Node left;
	private Node right;
	private List<Node> observerList;
	private int hashCodeNumber;

	/**
	 * Parameterized Constructor
	 * @param studentBnumberIn
	 * @param studentCourseIn
	 */
	public Node(int studentBnumberIn, String studentCourseIn) {
		this.studentBnumber = studentBnumberIn;
		this.studentCourse = new ArrayList<>();
		this.studentCourse.add(studentCourseIn);
		this.left = null;
		this.right = null;
		this.observerList = new ArrayList<>();
		this.hashCodeNumber = 0;
	}

	/**
	 * overridden Object class method returns student number and courses
	 * @param void
	 * @return String
	 */
	@Override
	public String toString() {
		String outputStr = "";
		outputStr += String.format("%03d", this.getStudentBnumber());
		outputStr += ":";
		outputStr += String.join(" ", this.getStudentCourse());
		outputStr += "\n";
		return outputStr;
	}

	/**
	 * Overridden Object class method to get unique value to every 
	 * object - using local time-stamp - converting this date-time to
	 * number of seconds from Epoch of 1970-01-01T:00:00:00Z
	 * @param void
	 * @return int
	 */
	@Override
	public int hashCode() {
		while (this.hashCodeNumber == 0) {
			hashCodeNumber = (int) (Math.random() * ((int) LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
		}
		return hashCodeNumber;
	}

	/**
	 * Overridden method of Object class to release system resources 
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
		if (objIn instanceof Node) {
			Node node = (Node) objIn;
			return this.toString().equals(node.toString());
		}
		return false;
	}

	/**
	 * Accessors and Mutators methods of instance members
	 */
	public int getStudentBnumber() {
		return studentBnumber;
	}

	public void setStudentBnumber(int studentBnumberIn) {
		this.studentBnumber = studentBnumberIn;
	}

	public List<String> getStudentCourse() {
		return studentCourse;
	}

	public void setStudentCourse(List<String> studentCourseIn) {
		this.studentCourse = studentCourseIn;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node leftIn) {
		this.left = leftIn;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node rightIn) {
		this.right = rightIn;
	}

	public List<Node> getObserverList() {
		return observerList;
	}

	public void setObserverList(List<Node> observerListIn) {
		this.observerList = observerListIn;
	}

	/**
	 * to add course to student node
	 * @param studentCourseIn - student course eg - A, B etc
	 * @return void
	 */
	public void addCourse(String studentCourseIn) {
		if (getCourseIndx(studentCourseIn) != -1) {
			return;
		} else {
			this.studentCourse.add(studentCourseIn);
		}
		this.notifyAllObservers(studentCourseIn, TreeBuilder.UpdateAction.Insert);
	}

	/**
	 * to get index of course to perform - additional operation
	 * @param String studentCourseIn
	 * @return index
	 */
	public int getCourseIndx(String studentCourseIn) {
		try {
			for (int i = 0; i < studentCourse.size(); ++i) {
				if (this.studentCourse.get(i).equals(studentCourseIn)) {
					return i;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: Issue while fetching index of course list");
			e.printStackTrace();
			System.exit(1);
		} finally {
			//nothing to deallocate
		}
		return -1;
	}

	/**
	 * to remove course from student node
	 * @param studentCourseIn
	 * @return void
	 */
	public void removeCourse(String studentCourseIn) {
		int indx = getCourseIndx(studentCourseIn);
		if (indx == -1) {
			return;
		} else {
			this.studentCourse.remove(studentCourseIn);
		}
		this.notifyAllObservers(studentCourseIn, TreeBuilder.UpdateAction.Delete);
	}

	/**
	 * Overridden method of ObserverI interface
	 * It performs update to all observer when there is change/update 
	 * on subject side
	 * @param - courseIn - course which needs to be updated
	 * 			updateActionIn - action like Insert or Delete
	 * @return - void 
	 */
	@Override
	public void update(String courseIn, TreeBuilder.UpdateAction updateActionIn) {
		if (updateActionIn == TreeBuilder.UpdateAction.Insert) {
			studentCourse.add(courseIn);
		}
		if (updateActionIn == TreeBuilder.UpdateAction.Delete) {
			studentCourse.remove(courseIn);
		}

	}

	/**
	 * Overridden method of SubjectI interface
	 * It registers observer to subject list so that whenever there is
	 * any update in change of subject , it can notify to all observers
	 * @param - objectIn - object of observer
	 * @return - void
	 */
	@Override
	public void registerObserver(Node objectIn) {
		observerList.add(objectIn);
	}

	/**
	 * Overridden method of SubjectI interface
	 * It removes observer from subject list, observer no longer gets 
	 * any update from subject on change in state of subject
	 * @param - objectIn - object of observer
	 * @return - void
	 */
	@Override
	public void removeObserver(Node objectIn) {
		observerList.remove(objectIn);
	}

	/**
	 * Overridden method of SubjectI interface
	 * Subject notifies all observers whenever there is any update 
	 * in change of subject, it uses observer list hold by subject
	 * @param - courseIn - course which has change at subject side
	 * 		  - updateActionIn - nature of change Insert, Delete
	 * @return - void
	 */
	@Override
	public void notifyAllObservers(String courseIn, TreeBuilder.UpdateAction updateActionIn) {
		for (int i = 0; i < this.observerList.size(); ++i) {
			this.observerList.get(i).update(courseIn, updateActionIn);
		}
	}

	/**
	 * Overridden method of Cloneable interface
	 * It clones subject nodes with 2 backup copies. It does deep copy
	 * for non primitive types
	 * @param - void
	 * @return - object of Object class - as Cloneable is empty 
	 * 			 interface
	 */
	@Override
	public Object clone() throws CloneNotSupportedException{
		Node nodeBkp = (Node) super.clone();
		nodeBkp.studentCourse = new ArrayList<>();
		for (int i = 0; i < this.studentCourse.size(); ++i) {
			nodeBkp.studentCourse.add(this.studentCourse.get(i));
		}
		nodeBkp.left = null;
		nodeBkp.right = null;
		//observerList of observer node does not hold anything
		nodeBkp.observerList = null;
		return nodeBkp;
	}
}