package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.model.ImageDescriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
	public void testRunIndex() throws Exception {
		ImageMapper imageMapper = imageIndex.runIndexing();
		Assert.assertEquals("Number of Unique images", 7, imageMapper.getSizeOfUniqueImages());
		imageIndex.copyFiles();
	}

	@Test
	public void testRunIndexInvalidInput() throws Exception {
		imageIndex = new ImageIndex(null);
		Assert.assertNull("Null for folderIO", imageIndex.runIndexing());
		FolderIO folderIO = new FolderIO();
		imageIndex = new ImageIndex(folderIO);
		Assert.assertNull("Invalid folders in folderIO", imageIndex.runIndexing());

		folderIO.masterFolder = new File("SomeInvalidFilePath");
		ArrayList<File> inputFolders = new ArrayList<File>();
		inputFolders.add(folderIO.masterFolder);
		folderIO.inputFolders = inputFolders;
		imageIndex = new ImageIndex(folderIO);
		Assert.assertNull("Invalid folders in folderIO", imageIndex.runIndexing());

		folderIO.masterFolder = new File(Constants.PATH_OUTPUT_TEST);
		inputFolders = new ArrayList<File>();
		inputFolders.add(folderIO.masterFolder);
		folderIO.inputFolders = inputFolders;
		imageIndex = new ImageIndex(folderIO);
		Assert.assertNotNull("Valid folderIO", imageIndex.runIndexing());
	}


	@Test
	public void testCopyFiles() throws Exception {
		ImageIndex imageIndexSpy = spy(imageIndex);
		imageIndexSpy.runIndexing();
		doThrow(new IOException()).when(imageIndexSpy).createImageFile(any(ImageDescriber.class), any(
				String.class));
		Assert.assertFalse("Test should throw exception", imageIndexSpy.copyFiles());
	}


}
