## Assignment Description - 
# Backup System for Student Course Assignments
* Create a class Node to store the B-Number (3 digit int) and an arraylist of course names (strings). The possible course names are "A" to "Z" (include all the letters of the alphabet).
* Write code for a tree that has the features to insert and search. You are not required to implement deletion of nodes. You need to write code for the tree by yourself. However, you are allowed to get help from a book or online source (for example the source code for BST, AVL, 2-3, 2-3-4, etc.) If you do so, cite the URL of where you got the code from both in your source code and also README.md. It will be considered CHEATING if you do not cite the source of any code on tree that you use/adapt.
* As you need to modify the code to implement the Observer pattern, you can't just use an in-built tree in Java. Each Node of the tree should implement both the Subject and the Observer interface.
* Do not use the built-in Observer classes/interfaces in Java.
* Populate the tree using the data from an input file, input.txt, that has the following format:
123:C
234:D
124:A
122:D
125:E
235:F
342:Z
...
* For class participation, post to piazza interesting input.txt and delete.txt so that I can post it to the course assignment page. The first 10 sample files will get class participation points.
* The input lines may not be unique. In such cases, ignore the repeated lines.
* Assume that the input.txt and delete.txt will be well formatted.
* Your tree builder should be such that when you create a node (nodeOrig as the variable name), you should clone it twice to get two Nodes (let's say backupNode1 and backupNode2 as the variables holding the references). Setup backupNode1 and backupNode2 as observers of nodeOrig. nodeOrig, backupNode1, and backupNode2, should be instances of the same Node class. As nodeOrig is the subject, you should store the references of backupNode1 and backupNode2 in a data structure for listeners in nodeOrig (array list of references, for example).
* The notifyAllObservers(...) method in the SubjectI interface and the update(...) method in the ObserverI interface should take two parameters: String and an enum. The String should have the course name. The enum should indicate whether the update is being called to insert a new course in an existing node, or to delete a course from an existing node.
* Apply the delete operations, processing line at a time, from the file delete.txt. The file has the following format:
189:C
845:D
634:A
...
* Search for the node with the B_Number in the line, and then delete the corresponding course in that Node. If that course does not exist to delete, then ignore and move to the next line. If a node does not exist with that BNumber, then ignore and move to the next line. Once the changes to the nodeOrig are done, the changes should be updated to both the corresponding nodes (call notifyAllObservers(...)). Note that the updates for a line in delete.txt should be sent, before the next line is processed. From the command line accept the following args: input.txt, delete.txt, output1.txt, output2.txt, output3.txt.
* Add a method to your tree, printNodes(Results r, ...), that stores in Results the list of courses for each student, sorted by the B_Number.
* Your driver code should do the following:
    * Read command line arguments.
    * Build the three trees, based on input.txt and the observer pattern.
    * Apply updates according to delete.txt
    * Create a new Results instance and call printNodes(...) on each of the three trees to store the BNumber and list of courses to store in Results. So, each of the three trees will use a different instance of Results.
    * Call a method in Results, via the method in FileDisplayInterface, to write the data stored in Results to output1.txt, output2.txt, and output3.txt, for the three trees. You should run a "diff" on the three output files to make sure your Observer pattern worked correctly.

The correct command is mentioned in README.md in git.
#### Assuming you are in the directory containing this README:
-----------------------------------------------------------------------
Following are the commands and the instructions to run ANT on your project.
#### Note: build.xml is present in studentCoursesBackup/src folder.
-----------------------------------------------------------------------
### Instruction to clean:
##### ant -buildfile studentCoursesBackup/src/build.xml clean
* It cleans up all the generated .class files from BUILD folder.
-----------------------------------------------------------------------
### Instruction to compile:
##### ant -buildfile studentCoursesBackup/src/build.xml all
* Compiles code and generates .class files inside the BUILD folder.
-----------------------------------------------------------------------
### Instruction to run:
##### ant -buildfile studentCoursesBackup/src/build.xml run -Darg0=../input/file/path/input_file.txt -Darg1=../input/file/path/delete_file.txt -Darg2=../input/file/path/output1_file.txt -Darg3=../input/file/path/output2_file.txt -Darg4=../input/file/path/output3_file.txt
Note:
* Arguments accept the absolute path of the files.
* Output file "registration_results.txt" will generate at coursesRegistration/src folder.
-----------------------------------------------------------------------
### Description:
**Backup System for Student Course Assignments - Implemented using Observer Pattern** - 
Class information. <br/>
* **Observer Interface** - <br/> This Interface implemented by observer node In observer pattern subject knows nothing about observer except that observer implements Observer Interface.
    * public void **update**(String courseIn, TreeBuilder.UpdateAction updateActionIn) - 
      Update method is implemented by observer It is invoked by subject on it's state change using observer object list maintained at subject's end

* **Subject Interface** - <br/> This interface is implemented by Subject which uses this interface to update observers. It holds the observer objects in data structure like arraylist which are modified using update interface methods  
    * public void **registerObserver**(Node objectIn) - To register observer 
    * public void **removeObserver**(Node objectIn) - To remove current registration of observer
    * public void **notifyAllObservers**(String courseIn, TreeBuilder.UpdateAction updateActionIn) - To notify observer
    
* **Subject Class** - <br/>
This class implementes Subject interface. It holds a information about all observers so that it can notify all observer on it's state change.

* **Observer Class** - <br/>
This class implements Observer interface. Every observer can override update method as per it's requirement the modify it's state once received the notification from subject. <br/>

Node - As a part of this assignment both subject and observer are Node class. Every node of original binary search tree act as Subject and every respective node of backup binary search tree act as observer. Any small change in subject i.e. node of original tree is notified to it's respective backup node so that all the time backup is in sync with original data of student <br/>


Justification for the choice of data structures (in terms of time and/or space complexity).<br/>
* **Binary Search Tree (BST)** - Structure hold all student information
    * **Node** - head node of subject node and 2 backup observer node
    * **List<String> inorderOrderOutputList** - to hold tree data in ascending order to redirect output to file
    
* **Node** - to hold single student information - it includes below data structures to hold information
    * **List<String> studentCourse** - to hold student's all courses
    * **List<Node> observerList** - to hold objects of all observer 

**Justification for using Binary Search Tree -** <br/>
* As a part of assignment, information of student courses changes frequently but once student is added it is never deleted. So in such scenario, update functionality needs to be efficient enough. By using BST, I can traverse from head node to any other node in O(logN) time where N is number of nodes.
* As there is no deletion of node, I don't need to balance BST. Balancing BST is one of the cosliest operations which is completely eliminated in this case.

For Binary Search Tree - <br/>
**Time complexity** - O(logN) for search, insert and update. There is no delete operation <br/>
**Space complexity** - O(n) where n is number of students<br/>

**Justification for using List<String> studentCourse & List<Node> observerList refering Arraylist -** <br/>
* Arraylist data structure increments at 50% rate to that of original size as opposed to vector which increase in size at double rate. Student courses and Observer - both can have limited size and can not increase expnetially so List is better data structure over vector in this scenario.
* This data structure gives more space than simple string which has limited capacity. Also I am creating object of List and pointing it to ArrayList that way in future if any changes occur, I don't have to change base structure.

**List of citations -** <br/>
Hashcode related information - 
https://www.baeldung.com/java-hashcode 

Clone functionality to create backup of every node - 
https://dzone.com/articles/shallow-and-deep-java-cloning

