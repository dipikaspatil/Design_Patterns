package studentCoursesBackup.myTree;

import studentCoursesBackup.util.TreeBuilder;

/**
 * This interface is implemented by Subject
 * Subject uses this interface to update observers
 * It holds the observer objects in data structure like arraylist 
 * which is modified using interface methods    
 * @author Dipika Patil
 */
public interface SubjectI {

	/**
	 * To register and notify observer
	 * @param objectIn - observer object
	 * @return - void
	 */
	public void registerObserver(Node objectIn);

	/**
	 * To remove current registration of observer
	 * @param objectIn - observer object
	 * @return - void
	 */
	public void removeObserver(Node objectIn);

	/**
	 * To notify observer
	 * @param courseIn - subject state parameter - student course
	 * 		  updateActionIn - action Insert, Delete	
	 * @return - void
	 */
	public void notifyAllObservers(String courseIn, TreeBuilder.UpdateAction updateActionIn);
}