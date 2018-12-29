package coursesRegistration.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Useful common methods to debug code
 * @author Dipika Suresh Patil
 *
 */
public class CommonUtils {

	/**
	 * method to print map of any type / map without any specific type
	 * @param map name 
	 * @return void
	 */
	public void printMap(Map<?,?> map) {
		Iterator<?> entries = map.entrySet().iterator();
		Map.Entry<?,?> entry = null;
		while(entries.hasNext()) {
			entry = (Map.Entry<?,?>) entries.next();
			System.out.println(entry.getKey() + ":" + entry.getValue().toString());
		}
	}

	/**
	 * Overridden method returns Filename
	 * @return - String - Filename
	 */
	@Override
	public String toString() {
		return "CommonUtils.java";
	}
}
