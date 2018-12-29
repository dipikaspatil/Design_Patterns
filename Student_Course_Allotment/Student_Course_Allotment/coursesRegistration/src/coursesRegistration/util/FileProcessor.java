package coursesRegistration.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * To open file, read file line by line and close file 
 * @author Dipika Suresh Patil
 *
 */
public class FileProcessor {

	/**
	 *  Data Members
	 */
	private String fileName;
	private BufferedReader bufferedReader;
	private FileReader fileReader;

	/**
	 * Accessors and Mutators for Private members
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public FileReader getFileReader() {
		return fileReader;
	}

	public void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
	}

	/**
	 * Constructor
	 * @param filename
	 */
	public FileProcessor(String filename) {
		this.fileName = filename;
		openFile();
	}

	/**
	 * function to open a file
	 * @return void
	 */
	private void openFile() {
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
		} catch(FileNotFoundException e) {
			System.err.println("Unable to open file " + fileName);
			e.printStackTrace();
			System.exit(1);
		} finally {

		}
	}

	/**
	 * function to return next line of file
	 * @return
	 */
	public String getNextLine() {
		String line = "";
		try {
			if ((line = bufferedReader.readLine()) != null) {
				return line;
			}
		} catch(IOException e) {
			System.err.println("Error reading file " + fileName);
			e.printStackTrace();
			System.exit(1);
		} finally {

		}
		return line;
	}

	/**
	 * function to close file
	 * @return - void
	 */
	public void closeFile() {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		} catch (IOException e) {
			System.err.println("Error closing file " + fileName);
			e.printStackTrace();
			System.exit(1);
		} finally {

		}
	}

	/**
	 * Overridden method returns file name
	 * @return - String - file name
	 */
	@Override
	public String toString() {
		return fileName;
	}
}
