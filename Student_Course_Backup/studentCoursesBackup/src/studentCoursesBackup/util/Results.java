package studentCoursesBackup.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import studentCoursesBackup.util.FileProcessor.FileFunction;

/**
 * Class implements 2 interface to redirect output - file , console
 * @author Dipika Suresh Patil
 */
public class Results implements FileDisplayInterface, StdoutDisplayInterface {

	/**
	 *  Data Members
	 */
	private List<String> outputResult;
	private String outputFile;
	private FileProcessor fileProcessor;
	private int hashCodeNumber;

	/**
	 * constructor
	 * @param ipOutputFormat
	 */
	public Results(List<String> ipOutputFormatIn, String outputFileIn) {
		outputResult = ipOutputFormatIn;
		outputFile = outputFileIn;
		fileProcessor = new FileProcessor(outputFile, FileFunction.Write);
		hashCodeNumber = 0;
	}

	/**
	 * Overridden method returns file name
	 * @return - String - file name
	 */
	@Override
	public String toString() {
		return "Results.java";
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
	 * Overridden method to release system resources 
	 * @return void
	 */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		this.fileProcessor.closeFile();
	}

	/**
	 * Overridden method of Object class to compare objects
	 * @return boolean - true if object matches and false if not
	 * @param object to compare
	 */
	@Override
	public boolean equals(Object objIn) {
		if (objIn instanceof Results) {
			Results result = (Results) objIn;
			return this.outputResult.equals(result.outputResult);
		}
		return false;
	}

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

	/**
	 * method to redirect output to console
	 * @param - void
	 * @return - void
	 */
	public void redirectOutputToConsole() {
		for(int i = 0; i < outputResult.size(); ++i) {
			System.out.println(outputResult.get(i));
		}
	}

	/**
	 * method to redirect output to file
	 * @param - void
	 * @return - void
	 */
	public void redirectOutputToFile() {
		for(int i = 0; i < outputResult.size(); ++i) {
			this.fileProcessor.writeToFile(outputResult.get(i));
		}
	}
}
