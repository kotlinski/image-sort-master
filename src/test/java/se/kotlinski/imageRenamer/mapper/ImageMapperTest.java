package se.kotlinski.imageRenamer.mapper;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imageRenamer.model.ImageDescriber;
import se.kotlinski.imageRenamer.utils.Constants;

import java.io.File;
import java.util.ArrayList;

public class ImageMapperTest {

	ImageMapper imageMapper;

	@Before
	public void setUp() throws Exception {
		imageMapper = new ImageMapper();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testAddImage() throws Exception {

	}

	@Test
	public void testPopulateWithImages() throws Exception {
		imageMapper.populateWithImages(new File(Constants.PATH_INPUT_TEST));
		Assert.assertEquals("Number of unique images in testfolder", 7, imageMapper.getSizeOfUniqueImages());
		//System.out.println("Expecting 6 files: \n" + imageMapper.toString());
	}

	@Test
	public void testRecursiveIterate() throws Exception {
		ArrayList<ImageDescriber> imageList = ImageMapper.recursiveIterate(new File(Constants.PATH_INPUT_TEST));
		Assert.assertEquals("Image found in root folder", 8, imageList.size());
	}

	@Test
	public void testGetSizeOfUniqueImages() throws Exception {

	}

	@Test
	public void testNewFilesNames() throws Exception {
		imageMapper.populateWithImages(new File(Constants.PATH_INPUT_TEST));

		ArrayList<ImageDescriber> imageDescribers = imageMapper.getUniqueImageDescribers();

		Assert.assertEquals("Unique image describer sizes", imageDescribers.size(), 7);
	}
}