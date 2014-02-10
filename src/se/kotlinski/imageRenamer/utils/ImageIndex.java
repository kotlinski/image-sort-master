package se.kotlinski.imageRenamer.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import se.kotlinski.imageRenamer.Constants;
import se.kotlinski.imageRenamer.models.ImageFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2014-02-10
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
public class ImageIndex {
	HashMap<String, ImageFile> imageFiles;


	public ImageIndex() {
		/** Index(date), ImageFile **/
		imageFiles = new HashMap<String, ImageFile>();
	}

	public void runIndexing(String inputPath) {
		FileSystemProvider fileSystemProvider = new FileSystemProvider();
		File[] files = fileSystemProvider.getListOfFiles(inputPath);

		for (File file : files) {
			String fileName = file.getName();

			Constants.FORMAT nameFormat = getFileFormat(fileName);
			String index = getIndex(fileName, nameFormat);

			ImageFile imageFile = new ImageFile();

			try {
				Metadata metadata = ImageMetadataReader.readMetadata(file);
				/*printImageTags(1, metadata);*/
				// obtain the Exif directory
				ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);

// query the tag's value
				Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
				System.out.println(date.toString());
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
				String formatted = format1.format(calendar.getTime());
				System.out.println(formatted);

			} catch (ImageProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void printImageTags(int approachCount, Metadata metadata) {
		System.out.println();
		System.out.println("*** APPROACH " + approachCount + " ***");
		System.out.println();
		// iterate over the exif data and print to System.out
		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags())
				System.out.println(tag);
			for (String error : directory.getErrors())
				System.err.println("ERROR: " + error);
		}
	}

	public String getIndex(String fileName, Constants.FORMAT nameFormat) {
		String returnString = fileName;
		Pattern pattern;
		String date;
		switch (nameFormat) {
			case DROPBOX:
				pattern = Pattern.compile(Constants.DROPBOX_FORMAT);
				date = getDateString(fileName, pattern);
				if (date != null) {
					String year = date.substring(0, 4);
					String month = date.substring(5, 7);
					String day = date.substring(8, 10);
					String time = date.substring(11, 19);
					returnString = year + "-" + month + "-" + day + "_" + time;
				}
				break;
			case SAMSUNG:
				pattern = Pattern.compile(Constants.SAMSUNG_FORMAT);
				date = getDateString(fileName, pattern);
				if (date != null) {
					String year = fileName.substring(0, 4);
					String month = fileName.substring(4, 6);
					String day = fileName.substring(6, 8);
					String time = fileName.substring(9, 11);
					time += "." + fileName.substring(11, 13);
					time += "." + fileName.substring(13, 15);
					returnString = year + "-" + month + "-" + day + "_" + time;
				}
				break;
			case GOOGLE:
				pattern = Pattern.compile(Constants.GOOGLE_DATE_FORMAT);
				date = getDateString(fileName, pattern);
				if (date != null) {
					String year = date.substring(0, 4);
					String month = date.substring(4, 6);
					String day = date.substring(6, 8);
					String time = date.substring(9, 11);
					time += "." + date.substring(11, 13);
					time += "." + date.substring(13, 15);
					returnString = year + "-" + month + "-" + day + "_" + time;
				}
				break;
			case OTHER:
				break;
		}
		return returnString;
	}

	private String getDateString(String fileName, Pattern pattern) {
		Matcher matcher = pattern.matcher(fileName);
		String date = null;
		while (matcher.find()) {
			date = matcher.group();
		}
		return date;
	}

	private Constants.FORMAT getFileFormat(String fileName) {
		if (isDropboxFormat(fileName)) {
			return Constants.FORMAT.DROPBOX;
		} else if (isSamsungFormat(fileName)) {
			return Constants.FORMAT.SAMSUNG;
		} else {
			return Constants.FORMAT.OTHER;
		}
	}

	private boolean isDropboxFormat(String filename) {
		Pattern pattern = Pattern.compile(Constants.DROPBOX_FORMAT);
		Matcher matcher = pattern.matcher(filename);
		return matcher.matches();
	}

	private boolean isSamsungFormat(String filename) {
		Pattern pattern = Pattern.compile(Constants.SAMSUNG_FORMAT);
		Matcher matcher = pattern.matcher(filename);
		return matcher.matches();
	}
}
