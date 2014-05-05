package se.kotlinski.imageRenamer.models;

import java.io.File;

/**
 * Created by Simon on 2014-01-01.
 */
public class ImageDescriber {
	private String md5;
	private String filePaths;

	private String timeStamp;

	private int year;
	private int month;
	private int day;

	private int hour;
	private int minute;
	private int second;

	public ImageDescriber(File file) {

	}

	public ImageDescriber() {

	}

	public String getMd5() {
		return md5;
	}
}
