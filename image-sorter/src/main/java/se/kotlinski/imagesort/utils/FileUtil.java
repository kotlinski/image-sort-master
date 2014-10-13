package se.kotlinski.imagesort.utils;

import java.io.File;

/**
 * Describe class/interface here.
 *
 * Date: 2014-10-13
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class FileUtil {

	public static boolean isValidFolder(final File folder) {
		return folder != null && folder.exists() && folder.isDirectory();
	}
}
