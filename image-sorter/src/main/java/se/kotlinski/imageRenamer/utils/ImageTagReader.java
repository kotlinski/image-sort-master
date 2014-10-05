package se.kotlinski.imageRenamer.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA. User: Simon Kotlinski Date: 2014-02-10 Time:
 * 21:40 To change this template use File | Settings | File Templates.
 */
public class ImageTagReader {

	public static Date getImageDate(File file) {
		Date date = new Date();
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			// obtain the Exif directory
			ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);

			date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		}
		catch (ImageProcessingException e) {
			System.out.println("FIle: " + file.getName());
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("FIle: " + file.getName());
			e.printStackTrace();
		}
		return date;
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

	private void printImageTags(int approachCount, Metadata metadata) {
		System.out.println();
		System.out.println("*** APPROACH " + approachCount + " ***");
		System.out.println();
		// iterate over the exif data and print to System.out
		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				System.out.println(tag);
			}
			for (String error : directory.getErrors()) {
				System.err.println("ERROR: " + error);
			}
		}
	}
}
