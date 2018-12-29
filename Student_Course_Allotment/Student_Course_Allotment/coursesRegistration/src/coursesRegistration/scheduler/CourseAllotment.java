package coursesRegistration.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * To parse Student and Course file and to make course allotment 
 * line by line
 * @author Dipika Suresh Patil
 *
 */
public class CourseAllotment {

	/**
	 * declaration of constants and enums
	 * enum is to maintain - student year
	 */
	public static final int MAX_COURSE_ALLOTMENT = 3;
	public static final int MAX_COURSE_AVAILABLE = 6;
	enum StudentYear {FIRST_YEAR, SECOND_YEAR, THIRD_YEAR;}

	/**
	 * Data members: to maintain courseInfo in logical form
	 */
	private Map<String, Integer> courseCapacity;
	private Map<Integer, String> courseTime;
	private Map<String, String>  courseConflictPair;

	/**
	 * Data members: Stack data structure to maintain the state 
	 * for E and F course
	 */
	private Stack<String> couseE1Yr;
	private Stack<String> couseF1Yr;
	private Stack<String> couseE2Yr;
	private Stack<String> couseF2Yr;

	/**
	 * Data member: result map with student allotment
	 */
	private Map<String, StudentPrefInfo> resultPref;

	/**
	 * Accessors and Mutators for private members
	 * @return member type for accessors and void for mutators
	 */
	public Map<String, Integer> getCourseCapacity() {
		return courseCapacity;
	}

	public void setCourseCapacity(Map<String, Integer> courseCapacity) {
		this.courseCapacity = courseCapacity;
	}

	public Map<Integer, String> getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(Map<Integer, String> courseTime) {
		this.courseTime = courseTime;
	}

	public Map<String, String> getCourseConflictPair() {
		return courseConflictPair;
	}

	public void setCourseConflictPair(Map<String, String> courseConflictPair) {
		this.courseConflictPair = courseConflictPair;
	}

	public List<String> getCouseE1Yr() {
		return couseE1Yr;
	}

	public void setCouseE1Yr(Stack<String> couseE1Yr) {
		this.couseE1Yr = couseE1Yr;
	}

	public List<String> getCouseF1Yr() {
		return couseF1Yr;
	}

	public void setCouseF1Yr(Stack<String> couseF1Yr) {
		this.couseF1Yr = couseF1Yr;
	}

	public List<String> getCouseE2Yr() {
		return couseE2Yr;
	}

	public void setCouseE2Yr(Stack<String> couseE2Yr) {
		this.couseE2Yr = couseE2Yr;
	}

	public List<String> getCouseF2Yr() {
		return couseF2Yr;
	}

	public void setCouseF2Yr(Stack<String> couseF2Yr) {
		this.couseF2Yr = couseF2Yr;
	}

	public Map<String, StudentPrefInfo> getResultPref() {
		return resultPref;
	}

	public void setResultPref(Map<String, StudentPrefInfo> resultPref) {
		this.resultPref = resultPref;
	}

	/**
	 * constructor
	 */
	public CourseAllotment() {
		//instantiate private members
		courseCapacity = new HashMap<>();
		courseTime = new HashMap<>();
		courseConflictPair = new HashMap<>();
		couseE1Yr = new Stack<String>();
		couseF1Yr = new Stack<String>();
		couseE2Yr = new Stack<String>();
		couseF2Yr = new Stack<String>();
		resultPref = new HashMap<>();
	}

	/**
	 * Private class to maintain student specific information 
	 */
	private final class StudentPrefInfo {
		private String studentName;
		private String [] studentPref;
		private Map<String, Boolean> coursePrefAllot;
		private StudentYear studentYear;
		private int allottedCourseIndx;
		private int currentAllotment;
		StudentPrefInfo() {
			studentName = "";
			studentPref = new String[6];
			coursePrefAllot = new HashMap<>();
			studentYear = null;
			allottedCourseIndx = 0;
			currentAllotment = 0;
		}

		/**
		 * Overriding to string method to return allotment
		 * @return String with allotment
		 * @param void
		 */
		@Override
		public String toString() {
			try {

			} catch (NullPointerException e) {
				System.out.println("Error: Issue with handling string to toString methiod");
				e.printStackTrace();
				System.exit(1);
			}
			String currentAllotment = "";

			for (Map.Entry<String, Boolean> varMap : coursePrefAllot.entrySet()) {
				if (varMap.getValue() == true) {
					currentAllotment += varMap.getKey() + ",";
				}
			}
			return currentAllotment.length() >= 1 ? currentAllotment.substring(0, currentAllotment.length()-1) : "";
		}
	}

	/**
	 * Overriding to string method to return allotment
	 * @return file name
	 * @param void
	 */
	@Override
	public String toString() {
		return "CourseAllotment.java";
	}

	/**
	 * To split line of courseInfo.txt into required fields like 
	 * course_name, capacity and class_time
	 * @param line - one line of file in below format
	 * 
	 * courseInfo.txt will have the following format, 
	 * <course_name>-CAPACITY:<capacity>;CLASS_TIMING:<class_time>
	 */
	public void parseCourseLine(String line) {
		//local variables
		String courseName = "";
		int capacity = 0;
		int courseTiming = 0;
		try {
			courseName = line.split("-")[0].trim();
			capacity = Integer.parseInt((line.split(";")[0]).split(":")[1].trim());
			courseTiming = Integer.parseInt((line.split(";")[1]).split(":")[1].trim());
		}
		catch (java.lang.ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: in parsing line of courseInfo.txt");
			e.printStackTrace();
			System.exit(1);
		} 
		finally {
			//nothing to deallocate at this time
		}
		//Fill info- courseCapacity and courseTime for specific student 
		courseCapacity.put(courseName, capacity);
		if (courseTime.containsKey(courseTiming)) {
			courseTime.put(courseTiming, courseTime.get(courseTiming) + courseName);
		} else {
			courseTime.put(courseTiming, courseName);
		}

		//check for course conflict when last course is parsed
		try {
			if (courseCapacity.size() == MAX_COURSE_AVAILABLE) {
				String courseList = "";
				for (Map.Entry<Integer, String> entry: courseTime.entrySet()) {
					if (entry.getValue().length() > 1) {
						courseList = entry.getValue();
						for (int i = 0; i < courseList.length(); ++i) {
							for (int j = 0; j < courseList.length(); ++j) {
								if (i != j) {
									if (courseConflictPair.containsKey(courseList.substring(i, i+1))) {
										courseConflictPair.put(courseList.substring(i, i+1), 
												courseConflictPair.get(courseList.substring(i, i+1)) + courseList.substring(j, j+1));
									} else {
										courseConflictPair.put(courseList.substring(i, i+1), courseList.substring(j, j+1));
									}
								}
							}
						}
					}
				}

			}
		} catch (NullPointerException e) {
			System.out.println("Error: Issue while parsing course conflict");
			e.printStackTrace();
			System.exit(1);
		} finally {
			//nothing to deallocate
		}


	}

	/**
	 * To split line of student_coursePrefs.txt into required fields 
	 * like course_name, capacity and class_time
	 * @param line - one line of file in below format
	 * 
	 * student_coursePrefs.txt will have the following format, 
	 * <student_name> <PREF_1>, <PREF_2>, <PREF_3>, <PREF_4>, <PREF_5>, 
	 * <PREF_6>;STUDENT_LEVEL:<[first_yr,second_yr,third_yr]>
	 * 
	 * */
	public void setPref(String line) {
		try {
			String[] firstStr = (line.split(";")[0]).split(" ");
			String studentName = firstStr[0];
			String[] studentPrefs = new String[6];
			StudentYear studentYear = StudentYear.valueOf(line.split(":")[1].trim());
			StudentPrefInfo tempObj = new StudentPrefInfo();

			tempObj.studentName = studentName;
			tempObj.studentPref = studentPrefs;
			tempObj.studentYear = studentYear;

			for (int i = 1; i < firstStr.length; ++i) {
				studentPrefs[i-1] = firstStr[i].substring(0,1);
				//initially assume that we can not allot any course 
				tempObj.coursePrefAllot.put(studentPrefs[i-1], false); 
			}


			//if course is not available, just add student name with 
			//null string allotment else call decidePref()

			if (!isAnyCourseAvailable() && studentYear == StudentYear.FIRST_YEAR) {
				resultPref.put(studentName, tempObj);
			} else {
				resultPref.put(studentName, tempObj);
				decidePref(studentName, tempObj);
			}



		} catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.IllegalArgumentException e) {
			System.err.println("Error: while setting up preferences to student");
			e.printStackTrace();
			System.exit(1);
		} finally {
			//nothing to deallocate
		}
	}

	/**
	 * To decide actual preferences of students when there is enough 
	 * capacity
	 * @param studentName = name of student , studentInfo - specific 
	 * information of student
	 * */
	private void decidePref(String studentName, StudentPrefInfo studentInfo) {
		try {
			for(int i = 0; i < studentInfo.studentPref.length && resultPref.get(studentName).currentAllotment < MAX_COURSE_ALLOTMENT; ++i) {
				if (checkCourseConflict(studentInfo, studentInfo.studentPref[i])) {
					continue;
				}
				//for courses other than E and F - check for capacity only 
				if (!studentInfo.studentPref[i].equals("E") && !studentInfo.studentPref[i].equals("F")) {
					if (getCourseCapacity(studentInfo.studentPref[i]) != 0) {
						resultPref.get(studentName).allottedCourseIndx = i;
						resultPref.get(studentName).currentAllotment += 1;
						studentInfo.coursePrefAllot.put(studentInfo.studentPref[i], true);
						reduceCourseCapacity(studentInfo.studentPref[i]);
					}
				} else {
					if (getCourseCapacity(studentInfo.studentPref[i]) != 0) {
						resultPref.get(studentName).allottedCourseIndx = i;
						resultPref.get(studentName).currentAllotment += 1;
						studentInfo.coursePrefAllot.put(studentInfo.studentPref[i], true);
						reduceCourseCapacity(studentInfo.studentPref[i]);
						if (studentInfo.studentYear == StudentYear.FIRST_YEAR) {
							if(studentInfo.studentPref[i].equals("E")){
								couseE1Yr.push(studentName);
							} else if (studentInfo.studentPref[i].equals("F")) {
								couseF1Yr.push(studentName);
							}
						} else if (studentInfo.studentYear == StudentYear.SECOND_YEAR) {
							if(studentInfo.studentPref[i].equals("E")){
								couseE2Yr.push(studentName);
							} else if (studentInfo.studentPref[i].equals("F")) {
								couseF2Yr.push(studentName);
							}
						}
					} else {
						if (modifyExistingPref(studentInfo.studentPref[i], studentInfo)) {
							resultPref.get(studentName).allottedCourseIndx = i;
							resultPref.get(studentName).currentAllotment += 1;
							studentInfo.coursePrefAllot.put(studentInfo.studentPref[i], true);
						}
					}
				}

			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error : Issue while deciding preference for student");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * @param ipCourseName - course name to reduce course capacity
	 */
	private void reduceCourseCapacity(String ipCourseName){
		if ((courseCapacity.get(ipCourseName)-1) < 0) {
			courseCapacity.put(ipCourseName, 0);
			return;
		}
		courseCapacity.put(ipCourseName, courseCapacity.get(ipCourseName)-1);
	}

	/**
	 * to get current course capacity
	 * @param ipCourseName course name to get course capacity
	 * @return
	 */
	private int getCourseCapacity(String ipCourseName){
		return courseCapacity.get(ipCourseName) < 0 ? 0 : courseCapacity.get(ipCourseName) ;
	}

	/**
	 * 
	 * @param ipCourseRequest
	 * @param studentInfo
	 * @return
	 */
	private boolean modifyExistingPref(String ipCourseRequest, StudentPrefInfo studentInfo) {
		String studentName = "";
		if (studentInfo.studentYear == StudentYear.THIRD_YEAR) {
			if (ipCourseRequest.equals("E")) {
				if (couseE1Yr.size() != 0) {
					studentName = couseE1Yr.pop();
				}
				else if (couseE2Yr.size() != 0) {
					studentName = couseE2Yr.pop();	
				} else {
					return false;
				}

			} else if (ipCourseRequest.equals("F")) {
				if (couseF1Yr.size() != 0) {
					studentName = couseF1Yr.pop();
				} else if (couseF2Yr.size() != 0) {
					studentName = couseF2Yr.pop();
				} else {
					return false;
				}
			} else {
				return false;
			}

		} else if (studentInfo.studentYear == StudentYear.SECOND_YEAR) {
			if (ipCourseRequest.equals("E")) {
				if (couseE1Yr.size() != 0) {
					studentName = couseE1Yr.pop();
					couseE2Yr.push(studentInfo.studentName);
				} else {
					return false;
				}
			} else if (ipCourseRequest.equals("F")) {
				if (couseF1Yr.size() != 0) {
					studentName = couseF1Yr.pop();
					couseF2Yr.push(studentInfo.studentName);
				} else {
					return false;
				}
			}
		}
		if (!studentName.equals("")) {
			resultPref.get(studentName).coursePrefAllot.put(ipCourseRequest, false);
			resultPref.get(studentName).currentAllotment -= 1;
			checkNextAvailableCourse(resultPref.get(studentName), ipCourseRequest);
			return true;
		}

		return false;
	}

	/**
	 * To check if course capacity is available
	 * @return - true if course capacity is available
	 * @param - void
	 */
	private boolean isAnyCourseAvailable() {
		for (Map.Entry<String, Integer> varMap : courseCapacity.entrySet()) {
			if (varMap.getValue() != 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * To check if there is any course available in case of course 
	 * change for first or second year student due to request from 
	 * next/higher year student 
	 * @param studentInfo - object of StudentPrefInfo class
	 * @return - void
	 */
	private void checkNextAvailableCourse(StudentPrefInfo studentInfo, String currentRemovedCourse) {
		for (int i = 0; i < MAX_COURSE_AVAILABLE; ++i) {
			/*
			 * current preference course should not be equal to current removed course
			 * current preference course is not already alloted
			 * current preference course don't have conflict with already alloted courses
			 */
			if (!currentRemovedCourse.equals(studentInfo.studentPref[i]) && !studentInfo.coursePrefAllot.get(studentInfo.studentPref[i]) 
					&& !checkCourseConflict(studentInfo, studentInfo.studentPref[i])) {
				//if course capacity is not zero
				if (getCourseCapacity(studentInfo.studentPref[i]) != 0) {
					reduceCourseCapacity(studentInfo.studentPref[i]);
					resultPref.get(studentInfo.studentName).allottedCourseIndx = i;
					resultPref.get(studentInfo.studentName).currentAllotment += 1;
					studentInfo.coursePrefAllot.put(studentInfo.studentPref[i], true);

					if (studentInfo.studentPref[i].equals("E")) {
						if (studentInfo.studentYear == StudentYear.SECOND_YEAR) {
							couseE2Yr.push(studentInfo.studentName);
						} else if (studentInfo.studentYear == StudentYear.FIRST_YEAR) {
							couseE1Yr.push(studentInfo.studentName);
						}
					} else if (studentInfo.studentPref[i].equals("F")) {
						if (studentInfo.studentYear == StudentYear.SECOND_YEAR) {
							couseF2Yr.push(studentInfo.studentName);
						} else if (studentInfo.studentYear == StudentYear.FIRST_YEAR) {
							couseF1Yr.push(studentInfo.studentName);
						}
					}
					return;
				} else {
					//if course capacity is zero and requesting student 
					//is from second year then take course from first year
					if (!(studentInfo.studentPref[i].equals("E") || studentInfo.studentPref[i].equals("F"))){
						continue;
					} else {
						if (studentInfo.studentYear == StudentYear.FIRST_YEAR) {
							continue;
						} else if (studentInfo.studentYear == StudentYear.SECOND_YEAR
								&& modifyExistingPref(studentInfo.studentPref[i], studentInfo)){
							resultPref.get(studentInfo.studentName).allottedCourseIndx = i;
							resultPref.get(studentInfo.studentName).currentAllotment += 1;
							studentInfo.coursePrefAllot.put(studentInfo.studentPref[i], true);

							if (studentInfo.studentPref[i].equals("E")) {
								couseE2Yr.push(studentInfo.studentName);
							} else if (studentInfo.studentPref[i].equals("F")) {
								couseF2Yr.push(studentInfo.studentName);
							}
							return;
						}
					}
				}
			}
		}
	}

	/**
	 * To check if there is any course conflict between currently 
	 * allotted course and newly alloted course
	 * @param studentInfo - object of StudentPrefInfo
	 * @param ipCheckCourse - course for which checking conflict 
	 * @return - true if there is conflict otherwise false
	 */
	private boolean checkCourseConflict(StudentPrefInfo studentInfo, String ipCheckCourse) {
		//check when there is conflict with one course
		if (courseConflictPair.containsKey(ipCheckCourse)) {
			if (courseConflictPair.get(ipCheckCourse).length() == 1) {
				return studentInfo.coursePrefAllot.get(courseConflictPair.get(ipCheckCourse));
			} else {
				//check for more than one conflict
				String courseConflictList = courseConflictPair.get(ipCheckCourse);
				for (int i = 0; i < courseConflictList.length(); ++i) {
					if (studentInfo.coursePrefAllot.get(courseConflictList.substring(i, i+1))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param map
	 * @return - required output in specific format 
	 */
	public List<String> getMap(Map<?,?> map) {
		List<String> outputResult = new ArrayList<>();
		Iterator<?> entries = map.entrySet().iterator();
		Map.Entry<?,?> entry = null;
		while(entries.hasNext()) {
			entry = (Map.Entry<?,?>) entries.next();
			outputResult.add(entry.getKey() + ":" + entry.getValue().toString() + "\n");
		}
		return outputResult;
	}

	/**
	 * To convert output in required format 
	 * @return result to calling method in required format
	 */
	public List<String> getFinalOutput() {
		List<String> finalStudentAllotment = getMap(resultPref);
		return finalStudentAllotment;
	}
}
