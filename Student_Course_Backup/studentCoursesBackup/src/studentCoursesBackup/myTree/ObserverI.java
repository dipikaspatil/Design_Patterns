package studentCoursesBackup.myTree;

import studentCoursesBackup.util.TreeBuilder;

/**
 * This Interface implemented by observer node
 * In observer pattern subject knows nothing about observer except that 
 * observer implements Observer Interface- ObserverI in this assignment
 * @author Dipika Patil
 */
public interface ObserverI {

	/**
	 * Update method is implemented by observer 
	 * It is invoked by subject on it's state change using observer 
	 * object list maintained at subject's end
	 * @param courseIn - course which has state change 
	 * @param updateActionIn - enum holds action Insert, Delete
	 */
	public void update(String courseIn, TreeBuilder.UpdateAction updateActionIn);
}