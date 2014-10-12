package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;

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
		File file = new File(Constants.PATH_INPUT_TEST);
		ArrayList<File> inputFolders = new ArrayList<File>();
		inputFolders.add(file);
		folderIO.inputFolders = inputFolders;
		folderIO.masterFolder = new File(Constants.PATH_OUTPUT_TEST);
		imageIndex = new ImageIndex(folderIO);
	}

	@Test
	public void testRunIndexing() throws Exception {

	}

	@Test
	public void testRunIndex() throws Exception {
		ImageMapper imageMapper = imageIndex.runIndexing();

		Assert.assertEquals("Number of Unique images", 6, imageMapper.getSizeOfUniqueImages());

		imageIndex.copyFiles();
	}
}
