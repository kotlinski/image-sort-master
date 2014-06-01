package se.kotlinski.imageRenamer.mappers;

import se.kotlinski.imageRenamer.models.ImageDescriber;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Simon Kotlinski on 2014-05-07.
 */
public class ImageMapper {
	HashMap<String, ArrayList<ImageDescriber>> imageMap;

	public ImageMapper() {
		imageMap = new HashMap<String, ArrayList<ImageDescriber>>();
	}

	public static ArrayList<ImageDescriber> recursiveIterate(final File folder) {
		ArrayList<ImageDescriber> imageDescriber = new ArrayList<ImageDescriber>();
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				imageDescriber.addAll(recursiveIterate(file));
			}
			else {
				imageDescriber.add(new ImageDescriber(file));
			}
		}
		return imageDescriber;
	}

	public void addImage(ImageDescriber imageDescriber) {
		String md5 = imageDescriber.getMd5();
		ArrayList<ImageDescriber> imageArray = getImageArrayForMd5(imageDescriber, md5);
		imageArray.add(imageDescriber);
	}

	private ArrayList<ImageDescriber> getImageArrayForMd5(ImageDescriber imageDescriber, String md5) {
		ArrayList<ImageDescriber> imageArray;
		if (imageMap.containsKey(md5)) {
			imageArray = imageMap.get(md5);
		}
		else {
			imageArray = new ArrayList<ImageDescriber>();
			imageMap.put(md5, imageArray);
		}
		return imageArray;
	}

	public void populateWithImages(File rootFolder) {
		//File rootSource = new File(inputPath);
		ArrayList<ImageDescriber> images = recursiveIterate(rootFolder);
		System.out.println("Images in " + rootFolder.getName() + ": " + images.size());
		for (ImageDescriber image : images) {
			addImage(image);
		}
	}

	public ArrayList<String> generateFileList(){
		for (String s : imageMap.keySet()) {
			for (ImageDescriber imageDescriber : imageMap.get(s)) {
				imageDescriber.getRenamedFilePath();
			}
		}
	}

	public int getSizeOfUniqueImages() {
		return imageMap.size();
	}

	@Override
	public String toString() {
		String retString = "Files in input folder: \n";
		for (String s : imageMap.keySet()) {
			retString += s + ", including files: " + "\n";
			for (ImageDescriber imageDescriber : imageMap.get(s)) {
				retString += "\t" + imageDescriber.toString() + "\n";
			}
		}
		return retString;
	}


}
