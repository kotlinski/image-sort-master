package se.kotlinski.imageRenamer.mappers;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imageRenamer.utils.Constants;

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
		imageMapper.populateWithImages(Constants.PATH_INPUT_TEST );
		Assert.assertEquals("Number of unique images in testfolder", 6, imageMapper.getSizeOfUniqueImages());

	}

	@Test
	public void testRecursiveIterate() throws Exception {

	}

	@Test
	public void testGetSizeOfUniqueImages() throws Exception {

	}
}