package se.kotlinski.imagesort.model;

import java.io.File;
import java.util.ArrayList;

/**
 * Describe class/interface here.
 *
 * @author kristofer sommestad
 * @version $Revision: 1.1 $
 */
public class FolderIO {
	public ArrayList<File> inputFolders;
	public File masterFolder;

	@Override
	public String toString() {
		String returnString = "";
		if (inputFolders == null || masterFolder == null) {
			return "Folders not set";
		}
		else {
			for (File inputFolder : inputFolders) {
				returnString += "Input: " + inputFolder + "\n";
			}
			returnString += "Output: " + masterFolder.getAbsolutePath();
		}
		return returnString;
	}
}
