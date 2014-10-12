package se.kotlinski.imagesort.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA. User: Simon Kotlinski Date: 2014-02-10 Time: 21:40 To change this
 * template use File | Settings | File Templates.
 */
public class ImageTagReader {

	public static Date getImageDate(File file) {
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			ExifSubIFDDirectory exifSubIFDDirectory = metadata.getDirectory(ExifSubIFDDirectory.class);
			int tagDatetimeOriginal = ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL;
			ExifIFD0Directory exifIFD0Directory = metadata.getDirectory(ExifIFD0Directory.class);
			int tagDatetime = ExifIFD0Directory.TAG_DATETIME;

			if (exifSubIFDDirectory != null && exifSubIFDDirectory.getDate(tagDatetimeOriginal) != null) {
				return exifSubIFDDirectory.getDate(tagDatetimeOriginal);
			}
			if (exifIFD0Directory != null && exifIFD0Directory.getDate(tagDatetime) != null) {
				return exifIFD0Directory.getDate(tagDatetime);
			}
			return null;
		}
		catch (ImageProcessingException e) {
			System.out.println("File: " + file.getName());
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("File: " + file.getName());
			e.printStackTrace();
		}
		return null;
	}

	public static String formatPathDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy\\MM");
		String formatted = format1.format(calendar.getTime());
		return formatted;
	}

	public static String formatFileDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd_hh.mm.ss");
		String formatted = format1.format(calendar.getTime());
		return formatted;
	}

	public static boolean isValidImageFile(File file) {
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			if (getImageDate(file) != null) {
				return true;
			}
			else {
				System.out.println("File: " + file);
				System.out.println("Metadata");
				printAllInfo(metadata);
				return false;
			}
		}
		catch (ImageProcessingException e) {
			return false;
		}
		catch (IOException e) {
			return false;
		}
	}

	private static void printAllInfo(final Metadata metadata) {
		// Read the date
		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				System.out.println("\t" + tag);
			}
		}

	}

}
