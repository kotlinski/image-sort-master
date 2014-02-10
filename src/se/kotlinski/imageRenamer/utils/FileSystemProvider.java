package se.kotlinski.imageRenamer.utils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2013-10-12
 * Time: 10:35
 * To change this template use File | se.kotlinski.imageRenamer.Settings | File Templates.
 */
public class FileSystemProvider {
	public boolean createFolder(String path) {
		File file = new File(path);
		boolean result = false;
		// if the directory does not exist, create it
		if (!file.exists()) {
			result = file.mkdir();
		}
		return result;
	}

	public File[] getListOfFiles(String folderPath) {
		File folder = new File(folderPath);
		return folder.listFiles();
	}
}
