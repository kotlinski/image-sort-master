package se.kotlinski.imagesort.exception;

import java.io.File;

/**
 * Describe class/interface here.
 *
 * Date: 2014-10-11
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class NoMasterFolderException extends Exception {
	private final File masterFolder;

	public NoMasterFolderException(final String message, final File masterFolder) {
		super(message);
		this.masterFolder = masterFolder;
	}

	public File getMasterFolder() {
		return masterFolder;
	}
}
