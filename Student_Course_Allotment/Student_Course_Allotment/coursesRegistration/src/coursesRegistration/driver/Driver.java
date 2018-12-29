package coursesRegistration.driver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import coursesRegistration.scheduler.CourseAllotment;
import coursesRegistration.util.FileDisplayInterface;
import coursesRegistration.util.FileProcessor;
import coursesRegistration.util.Results;
import coursesRegistration.util.StdoutDisplayInterface;

/**
 * Driver code class
 * @author Dipika Suresh Patil
 *
 */
public class Driver {

	/**
	 * Driver code
	 * @param args - two file names 
	 * 1st - student_coursePrefs.txt 
	 * 2nd - courseInfo.txt
	 */
	public static void main(String[] args) {
		validateArguments(args);
		FileProcessor fpCourseInfo = null;
		FileProcessor fpCourseStudentPref = null;
		try {
			//Local variables
			fpCourseInfo = new FileProcessor(args[1]);
			fpCourseStudentPref = new FileProcessor(args[0]);

			//parse Course file		
			CourseAllotment courseAllotment = new CourseAllotment();
			String line = "";
			while ((line = fpCourseInfo.getNextLine()) != null) {
				courseAllotment.parseCourseLine(line);
			}

			//process file line by line
			while ((line = fpCourseStudentPref.getNextLine()) != null) {
				courseAllotment.setPref(line);
			}

			//get the output in required format
			List<String> finalStudentAllotment = courseAllotment.getFinalOutput();

			//redirect to file and console
			StdoutDisplayInterface stdoutDisplay = new Results(finalStudentAllotment);
			stdoutDisplay.redirectOutputToConsole();

			FileDisplayInterface fileDisplay = (FileDisplayInterface) stdoutDisplay;
			fileDisplay.redirectOutputToFile();

		} catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: Issue with accessing incorrect array index");
			e.printStackTrace();
			System.exit(1);
		} finally {
			//this will always execute
			fpCourseInfo.closeFile();
			fpCourseStudentPref.closeFile();
		}

	}

	/**
	 * Validating input arguments
	 * @param args - two file names 
	 * 1st - student_coursePrefs.txt 
	 * 2nd - courseInfo.txt
	 */
	private static void validateArguments(String[] args) {
		//argument count validation - argument should be exactly 2
		if (args.length != 2 || args[0].equals("${arg0}") || args[1].equals("${arg1}")) {
			System.err.println("Error: Incorrect number of arguments. Program accepts 2 argumnets.");
			System.exit(1);
		}

		//to check correct file path for args[0] student_coursePrefs.txt
		Path path = Paths.get(args[0]);
		if (!Files.exists(path)) {
			System.err.println("Error: Incorrect path for args[0] - student_coursePrefs.txt");
			System.exit(1);
		}

		//to check correct file path for args[1] courseInfo.txt
		path = Paths.get(args[1]);
		if (!Files.exists(path)) {
			System.err.println("Error: Incorrect path for args[1] - courseInfo.txt");
			System.exit(1);
		}

		//to check if args[0] student_coursePrefs.txt is file
		path = Paths.get(args[0]);
		if (!Files.isRegularFile(path)) {
			System.err.println("Error: args[0] - student_coursePrefs.txt is not regular file");
			System.exit(1);
		}

		//to check if args[1] courseInfo.txt is file
		path = Paths.get(args[1]);
		if (!Files.isRegularFile(path)) {
			System.err.println("Error: courseInfo.txt is not regular file");
			System.exit(1);
		}

		//to check args[0] student_coursePrefs.txt is empty file
		path = Paths.get(args[0]);
		if (new File(args[0]).length() == 0) {
			System.err.println("Error: args[0] - student_coursePrefs.txt can not be empty");
			System.exit(1);
		}

		//to check args[1] student_coursePrefs.txt is empty file
		path = Paths.get(args[1]);
		if (new File(args[1]).length() == 0) {
			System.err.println("Error: args[1] - courseInfo.txt can not be empty");
			System.exit(1);
		}

	}


}
