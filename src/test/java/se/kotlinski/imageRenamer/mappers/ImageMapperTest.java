package se.kotlinski.imageRenamer.mappers;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imageRenamer.models.ImageDescriber;
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
		Assert.assertEquals("Number of unique images in testfolder", 6, imageMapper.getSizeOfUniqueImages());
		System.out.println("Expecting 6 files: \n" + imageMapper.toString());

	}

	@Test
	public void testRecursiveIterate() throws Exception {
		ArrayList<ImageDescriber> imageList = imageMapper.recursiveIterate(new File(Constants.PATH_INPUT_TEST));
		Assert.assertEquals("Image found in root folder", 7, imageList.size());
	}

	@Test
	public void testGetSizeOfUniqueImages() throws Exception {

	}
}