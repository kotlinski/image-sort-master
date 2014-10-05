package se.kotlinski.imageRenamer.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imageRenamer.mappers.ImageMapper;
import se.kotlinski.imageRenamer.models.FolderIO;
import se.kotlinski.imageRenamer.models.ImageDescriber;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Simon Kotlinski on 2014-02-19.
 */
public class ImageIndexTest {
	ImageIndex imageIndex;

	@Before
	public void setUp() {

		FolderIO folderIO = new FolderIO();
		folderIO.inputFolder = new File(Constants.PATH_INPUT_TEST);
		folderIO.outputFolder = new File(Constants.PATH_OUTPUT_TEST);
		imageIndex = new ImageIndex(folderIO);
	}

	@Test
	public void testRunIndexing() throws Exception {

	}

	@Test
	public void testRunIndex() throws Exception {
		ImageMapper imageMapper = imageIndex.runIndexing();

		Assert.assertEquals("Number of Unique images", 7, imageMapper.getSizeOfUniqueImages());

		imageIndex.copyFiles();
	}
}
