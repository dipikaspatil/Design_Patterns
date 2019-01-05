package studentCoursesBackup.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * To open file, read file line by line and close file 
 * @author Dipika Suresh Patil
 */
public class FileProcessor {
	/**
	 * enum to specify file function
	 */
	public static enum FileFunction {Read, Write} 

	/**
	 *  Data Members
	 */
	private String fileName;
	private BufferedReader bufferedReader;
	private FileReader fileReader;
	private PrintStream printStream;

	/**
	 * Parameterized Constructor for file read, write
	 * @param filename
	 * @param fileFunctionIn - read , write
	 */
	public FileProcessor(String filenameIn, FileFunction fileFunctionIn) {
		if (fileFunctionIn == FileFunction.Read) {
			this.fileName = filenameIn;
			openFile();
		}

		if (fileFunctionIn == FileFunction.Write) {
			this.fileName = filenameIn;
			try {
				this.printStream = new PrintStream(new File(fileName));
			} catch (FileNotFoundException e) {
				System.err.println(1);
				e.printStackTrace();
				System.exit(1);
			}
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

	/**
	 * Overridden Object class method to get unique value to every 
	 * object - for FileProcessor it is just a placeholder
	 * @param void
	 * @return int
	 */
	@Override
	public int hashCode() {
		return 0;
	}

	/**
	 * Overridden method to release system resources 
	 * @return void
	 * @param void
	 */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		this.bufferedReader.close();
		this.fileReader.close();
		this.printStream.flush();
	}

	/**
	 * Overridden method of Object class to compare objects
	 * @return boolean - true if object matches and false if not
	 * @param object to compare
	 */
	@Override
	public boolean equals(Object objIn) {
		if (objIn instanceof FileProcessor) {
			FileProcessor fileProcessor = (FileProcessor) objIn;
			return this.fileName.equals(fileProcessor.fileName);
		}
		return false;
	}

	/**
	 * Accessors and Mutators for Private members
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileNameIn) {
		this.fileName = fileNameIn;
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReaderIn) {
		this.bufferedReader = bufferedReaderIn;
	}

	public FileReader getFileReader() {
		return fileReader;
	}

	public void setFileReader(FileReader fileReaderIn) {
		this.fileReader = fileReaderIn;
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
			//nothing to deallocate
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
			//nothing to deallocate
		}
		return line;
	}

	/**
	 * method to write into file line by line
	 * @param lineIn
	 */
	public void writeToFile(String lineIn) {
		this.printStream.print(lineIn);
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
			//nothing to deallocate
		}
	}


}
