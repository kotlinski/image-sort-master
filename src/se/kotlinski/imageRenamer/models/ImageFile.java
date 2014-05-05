package se.kotlinski.imageRenamer.models;

import se.kotlinski.imageRenamer.Constants;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Simon on 2014-01-01.
 */
public class ImageFile {
	private String indexID; // unique date

	private HashMap<Constants.FORMAT, File> duplicates;

	public ImageFile(Constants.FORMAT format, File file) {

	}

	public ImageFile() {

	}
}
