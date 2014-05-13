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

	public void addImage(ImageDescriber imageDescriber) {
		String md5 = imageDescriber.getMd5();
		ArrayList<ImageDescriber> imageArray = getImageArrayForMd5(imageDescriber, md5);
		imageArray.add(imageDescriber);
	}

	private ArrayList<ImageDescriber> getImageArrayForMd5(ImageDescriber imageDescriber, String md5) {
		ArrayList<ImageDescriber> imageArray;
		if (imageMap.containsKey(md5)) {
			imageArray = imageMap.get(md5);
		} else {
			imageArray = new ArrayList<ImageDescriber>();
			imageMap.put(md5, imageArray);
			imageArray.add(imageDescriber);
		}
		return imageArray;
	}

	public void populateWithImages(String inputPath) {
		File rootSource = new File(inputPath);
		ArrayList<ImageDescriber> images = recursiveIterate(rootSource);
		for (ImageDescriber image : images) {
			addImage(image);
		}
	}

	public ArrayList<ImageDescriber> recursiveIterate(final File folder) {
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

	public int getSizeOfUniqueImages() {
		return imageMap.size();
	}
}
