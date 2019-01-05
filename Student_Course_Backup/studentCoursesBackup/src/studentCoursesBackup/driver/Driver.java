package studentCoursesBackup.driver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import studentCoursesBackup.util.FileDisplayInterface;
import studentCoursesBackup.util.FileProcessor;
import studentCoursesBackup.util.FileProcessor.FileFunction;
import studentCoursesBackup.util.Results;
import studentCoursesBackup.util.TreeBuilder;

/**
 * Driver code class
 * @author Dipika Suresh Patil
 *
 */
public class Driver {
	/**
	 * Driver code
	 * @param args - two file names 
	 * 1st - input_file.txt 
	 * 2nd - courseInfo.txt
	 * 3rd - output1_file.txt
	 * 4th - output2_file.txt
	 * 5th - output3_file.txt
	 */
	public static void main(String[] args) {
		//validating input arguments
		validateArguments(args);
		
		FileProcessor fpInputFileInfo = null;
		FileProcessor fpDeleteFileInfo = null;

		try {
			//Local variables
			fpInputFileInfo = new FileProcessor(args[0], FileFunction.Read);
			fpDeleteFileInfo = new FileProcessor(args[1], FileFunction.Read);

			TreeBuilder treeBuilder = new TreeBuilder();
			//building the three trees, based on input.txt
			treeBuilder.createTree(fpInputFileInfo);

			//applying updates according to delete.txt
			treeBuilder.deleteTree(fpDeleteFileInfo);

			//get the output in required format for every tree
			List<String> origTreeOutput = treeBuilder.printNodes(TreeBuilder.NodeType.Subject);
			List<String> bkp1TreeOutput = treeBuilder.printNodes(TreeBuilder.NodeType.Backup1);
			List<String> bkp2TreeOutput = treeBuilder.printNodes(TreeBuilder.NodeType.Backup2);

			//redirect to 3 output files
			FileDisplayInterface fileDisplay = new Results(origTreeOutput, args[2]);
			FileDisplayInterface fileDisplayBkp1 = new Results(bkp1TreeOutput, args[3]);
			FileDisplayInterface fileDisplayBkp2 = new Results(bkp2TreeOutput, args[4]);

			fileDisplay.redirectOutputToFile();
			fileDisplayBkp1.redirectOutputToFile();
			fileDisplayBkp2.redirectOutputToFile();
		} catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: Issue with accessing incorrect array index");
			e.printStackTrace();
			System.exit(1);
		} finally {
			//this will always execute
			fpInputFileInfo.closeFile();
			fpDeleteFileInfo.closeFile();
		}
	}

	/**
	 * Validating input arguments
	 * @param args - 5 file names 
	 * 1st - input_file.txt 
	 * 2nd - delete_file.txt
	 * 3rd - output1_file.txt
	 * 4th - output2_file.txt
	 * 5th - output3_file.txt
	 */
	private static void validateArguments(String[] args) {
		//argument count validation - argument should be exactly 5
		if (args.length != 5 || args[0].equals("${arg0}") || args[1].equals("${arg1}") 
				|| args[2].equals("${arg2}") || args[3].equals("${arg3}") || args[4].equals("${arg4}")) {
			System.err.println("Error: Incorrect number of arguments. Program accepts 5 argumnets.");
			System.exit(1);
		}

		//to check correct file path for args[0] input_file.txt
		Path path = Paths.get(args[0]);
		if (!Files.exists(path)) {
			System.err.println("Error: Incorrect path for args[0] - input_file.txt");
			System.exit(1);
		}

		//to check correct file path for args[1] delete_file.txt
		path = Paths.get(args[1]);
		if (!Files.exists(path)) {
			System.err.println("Error: Incorrect path for args[1] - delete_file.txt");
			System.exit(1);
		}

		//to check if args[0] input_file.txt is file
		path = Paths.get(args[0]);
		if (!Files.isRegularFile(path)) {
			System.err.println("Error: args[0] - input_file.txt is not regular file");
			System.exit(1);
		}

		//to check if args[1] delete_file.txt is file
		path = Paths.get(args[1]);
		if (!Files.isRegularFile(path)) {
			System.err.println("Error: args[1] - delete_file.txt is not regular file");
			System.exit(1);
		}
	}


}
