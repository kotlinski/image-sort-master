package se.kotlinski.imageRenamer.model;

import java.io.File;

/**
 * Describe class/interface here.
 *
 * @author kristofer sommestad
 * @version $Revision: 1.1 $
 */
public class FolderIO {
	public File inputFolder;
	public File outputFolder;

	@Override
	public String toString() {
		if (inputFolder == null || outputFolder == null) {
			return "Folders not set";
		} else {
			return "Input: " + inputFolder.getAbsolutePath() + "\n" +
		         "Output: " + outputFolder.getAbsolutePath();
		}
	}
}
