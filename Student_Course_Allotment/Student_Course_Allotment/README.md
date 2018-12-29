## Assignment Description -
**Assignment Goal:** Develop a program, using Java, to assign courses to students based on their preferences.
* There are 6 courses being offered. Each course has the following information.
    * Capacity - The total number of students that be registered for this course.
    * Class Timings - For simplicity, timings are just represented as integers, starting from 0.
    * Course Name (A,B,C,D,E or F)
* The following information is associated with each student.
    * Student ID (a 3 digit integer)
    * Levels:
        FIRST_YEAR
        SECOND_YEAR
        THIRD_YEAR

Hint: Enums can be used to specify the levels of a student.
The following rules MUST be followed when registering students to courses.
1. A student cannot be registered to multiple courses that have the same class timing.
2. Two courses (E and F) need to be assigned to a first year student only if there is no 2nd and 3rd year student still
waiting for it. Among 2nd and 3rd year students, the priority has to be given to the 3rd year student.
3. If a course has been filled up, then any further registration requests for that course are rejected.

**INPUT FORMAT**
Program should take two input files - **student_coursePrefs.txt and courseInfo.txt**.

student_coursePrefs.txt will have the following format -
<student_name> <PREF_1>,<PREF_2>,<PREF_3>,<P REF_4>,<PREF_5>,<PREF_6>;STUDENT_LEVEL:
<[first_yr,second_yr,third_yr]>

courseInfo.txt will have the following format -
<course_name>-CAPACITY:<capacity>;CLA SS_TIMING:< class_time>

INPUT EXAMPLES
student_coursePrefs.txt
001 A, B, C, D, E, F; STUDENT_LEVEL: FIRST_YEAR
222 F, E, D, C, B, A; STUDENT_LEVEL: SECOND_YEAR
333 F, D, E, A, B, C; STUDENT_LEVEL: THIRD_YEAR

courseInfo.txt
A-CAPACITY:30; CLASS_TIMING: 7
B-CAPACITY:20; CLASS_TIMING: 8
C-CAPACITY:40; CLASS_TIMING: 7
D-CAPACITY:60; CLASS_TIMING: 9
E-CAPACITY:40; CLASS_TIMING: 2
F-CAPACITY:50; CLASS_TIMING: 8

OUTPUT
Program should write the registration results to an output file called registration_results.txt.
registration_results.txt will have the following format - 
<student_name>:<course_1>,<course_2>,<course_3>

-----------------------------------------------------------------------

Assuming you are in the directory containing this README:

-----------------------------------------------------------------------

Following are the commands and the instructions to run ANT on your project.
#### Note: build.xml is present in coursesRegistration/src folder.

-----------------------------------------------------------------------
## Instruction to clean:

#### ant -buildfile coursesRegistration/src/build.xml clean

* It cleans up all the generated .class files from BUILD folder.

-----------------------------------------------------------------------
## Instruction to compile:

#### ant -buildfile coursesRegistration/src/build.xml all

* Compiles code and generates .class files inside the BUILD folder.

-----------------------------------------------------------------------
## Instruction to run:

#### ant -buildfile coursesRegistration/src/build.xml run -Darg0=\<full path for student_coursePrefs.txt> -Darg1=\<full path for courseInfo.txt>

Note:
* Arguments accept the absolute path of the files.
* Output file "registration_results.txt" will generate at coursesRegistration/src folder.

-----------------------------------------------------------------------

## Description:

Justification for the choice of data structures (in terms of time and/or space complexity).<br/>

**For courseInfo.txt** - I have used 3 map to store information as follows - 

* **Map<String, Integer> courseCapacity** - stores courses and their capacity
* **Map<Integer, String> courseTime** - stores course time and course value, useful for course conflict
				    for eg - if course A and B have same time 1, entry will be <1, AB>
* **Map<String, String> courseConflictPair** - stores course and courses which has conflict with this course
			         	   for eg - if course A has conflict with B and C there will be 3
						    values in map - <A, BC>, <B, AC>, <C, AB>

**Justification for using map -** <br/>
Map data structure does fetching and inserting in O(1) time. Course data structure is used at multiple places 
while allocating courses to students - especially to get capacity and to check conflict of courses. So by using map - 
overall fetching time can be significantly reduces over usage of any other structure like - ArrayList 

For each map - <br/>
**Time complexity** - O(1) for select and O(1) for insert (there is no update or delete)<br/>
**Space complexity** - O(n) where n is number of courses<br/>

**For student_coursePrefs.txt -**<br/>
I have created a private inner class analogous to concept of struct in C
Object of this class holds the information of student and process student preferences line by line<br/>

private final class StudentPrefInfo {<br/>
	private String studentName; <br/>
	private String [] studentPref;<br/>
	private Map<String, Boolean> coursePrefAllot; <br/>
	private StudentYear studentYear;<br/>
	private int currentAllotment;<br/>
}<br/>

studentName - to store student info
studentPref - to store student preferences
coursePrefAllot - to store and allocate or deallocate 
studentYear - to store student year - object of enum which stores values as FIRST_YEAR, SECOND_YEAR, THIRD_YEAR 
currentAllotment - to store current allotment of student\

**Justification of using private class -** <br/>
I have created this class private so that it can not be accessible outside class. The object od class holds the information of student 
which is required only for allotment algorithm so this info is specific to this algorithm. So creating private class is useful.
Also I have create this class final, so that it will not be exteciated even by current class which is holding it. The main purpose of this
class is to just act like a struct concept of C in which information of various different data types can be easily included under one structure.
This way, I can easily pass student information object from one method to other while processing one student. I can easily access or change
information of student current preferences in O(1) time using internal map structure which holds all the courses and make true for those 
courses which are alloted to student.

For object of class- <br/>
**Time complexity -** O(1) for insertion, O(1) for selection of any specific information of student while allocating courses <br/>
**Space complexity -** O(n) for n number of students <br/>

Result is stored as Map<String, StudentPrefInfo> resultPref - where key is student name and StudentPrefInfo is object of Student class<br/>

**Justification -** <br/>
I have created internal result structure which has Map data structure which can efficiently insert or fetch data for any student
This way even if I have to go back and change any particular course of any particular student from first or second year - I can do it in O(1) time.
Also fetching of any student can be easily done in O(1) time due to map data structure. By storing whole object as value of this map entry, I can 
easily fulfill output file format requirement without making any additional changes to current structure.

For result -  <br/>
**Time complexity -** O(1) for select, intsert and update  <br/>
**Space complexity -** O(n) where n is number of student <br/>

To maintain the **list of students with courses E and F - stack data structure is used** <br/>

Stack\<String> couseE1Yr;  <br/>
Stack\<String> couseF1Yr;  <br/>
Stack\<String> couseE2Yr;  <br/>
Stack\<String> couseF2Yr;  <br/>

**Justification -**  <br/>
Stack holds data in Last in First out form, so it will be useful to fetch student from first or second year with E and F course
This is mainly helpful to fulfill the requirement that - "Third year student gets priority for course E and F over first and second year student and
Second year student get priority for course E and F over first year student" In these specific scenario, if any third year demands E/F, which is already
allotted to any prior year student like first/second year, using stack structure, I can get student name from first/second year with E/F course in O(1) time 
which is key of result structure. So fetching and then updating first/second year student's prior allotment can be done in O(1) time 

For stack - 
**Time complexity -** O(1) for insertion and O(1) for deletion (there is no delete) <br/>
**Space complexity -** O(n) where n is number of students  <br/>

Final file structure -  <br/>
List\<String> = new ArrayList\<String>(); 

**Justification -**  <br/>
Final output is redirected to console or file. So I have created List data structure which holds String in datatype.
This data structure gives more space than simple string which has limited capacity. Also I am creating object of List and pointing it to 
ArrayList that way in future if any changes occur, I don't have to change base structure.

For ArrayList -  <br/>
**Time Complexity -** to redirect to console/file - O(1)  <br/>
**Space Complexity -** O(n) where n is number of student records  <br/>

**List of citations -** <br/>
Map related information - 
https://www.testingexcellence.com/4-different-ways-iterate-map-java/

File related infomation - 
http://www.avajava.com/tutorials/lessons/how-do-i-redirect-standard-output-to-a-file.html






