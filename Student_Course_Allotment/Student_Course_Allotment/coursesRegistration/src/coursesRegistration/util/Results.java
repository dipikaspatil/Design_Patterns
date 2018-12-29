package coursesRegistration.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Class implements 2 interface to redirect output - file , console
 * @author Dipika Suresh Patil
 */
public class Results implements FileDisplayInterface, StdoutDisplayInterface {

	/**
	 *  Data Members
	 */
	private List<String> outputResult;
	private PrintStream printObj; 

	/**
	 * Accessors and Mutators for private members
	 * @return - member type for accessors and void for mutators
	 */
	public List<String> getOutputResult() {
		return outputResult;
	}

	public void setOutputResult(List<String> outputResult) {
		this.outputResult = outputResult;
	}

	public PrintStream getPrintObj() {
		return printObj;
	}

	public void setPrintObj(PrintStream printObj) {
		this.printObj = printObj;
	}

	/**
	 * constructor
	 * @param ipOutputFormat
	 */
	public Results(List<String> ipOutputFormat) {
		outputResult = ipOutputFormat;
		printObj = null;
	}

	/**
	 * method to redirect output to console
	 * @param - void
	 * @return - void
	 */
	public void redirectOutputToConsole() {
		printObj = new PrintStream(System.out);
		printObj.print(String.join("", outputResult));
	}

	/**
	 * method to redirect output to file
	 * @param - void
	 * @return - void
	 */
	public void redirectOutputToFile() {
		try {
			printObj = new PrintStream(new File("./src/registration_results.txt"));
			printObj.print(String.join("",outputResult));
		}catch (IOException e) {
			System.err.println("Error redirecting output file " + "./src/registration_results.txt");
			e.printStackTrace();
			System.exit(1);
		}finally {
			//no deallocation needed
		}
	}

	/**
	 * Overridden method returns file name
	 * @return - String - file name
	 */
	@Override
	public String toString() {
		return "Results.java";
	}
}
