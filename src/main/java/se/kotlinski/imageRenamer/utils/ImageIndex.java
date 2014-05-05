package se.kotlinski.imageRenamer.utils;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import se.kotlinski.imageRenamer.models.ImageDescriber;

import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: Simon Kotlinski Date: 2014-02-10 Time:
 * 21:40 To change this template use File | Settings | File Templates.
 */
public class ImageIndex {

	public ImageIndex() {
	}

	public ArrayList<ImageDescriber> runIndexing(String inputPath) {
		File rootSource = new File(inputPath);
		ArrayList<ImageDescriber> images = recursiveIterate(rootSource);
		return images;
/*		for (File file : files) {


			ImageDescriber imageFile = new ImageDescriber(file);
			try {

				Metadata metadata = ImageMetadataReader.readMetadata(file);
				*//*printImageTags(1, metadata);*//*
				// obtain the Exif directory
				ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);

				Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
				System.out.println(date.toString());
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
				String formatted = format1.format(calendar.getTime());
				System.out.println(formatted);

			}
			catch (ImageProcessingException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}

	private ArrayList<ImageDescriber> recursiveIterate(final File folder) {
		ArrayList<ImageDescriber> imageDescriber = new ArrayList<ImageDescriber>();

		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				imageDescriber.addAll(recursiveIterate(file));
			}	else {
				imageDescriber.add(new ImageDescriber(file));
				System.out.println(file.getName());
			}
		}
		return imageDescriber;
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
