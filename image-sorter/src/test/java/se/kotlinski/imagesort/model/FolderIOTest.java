package se.kotlinski.imagesort.model;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.Constants;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FolderIOTest {

	FolderIO folderIO;

	@Before
	public void setUp() throws Exception {
		folderIO = new FolderIO();
		folderIO.inputFolders = null;
		folderIO.masterFolder = null;

	}

	@Test
	public void testToString() throws Exception {
		String folderIOString = folderIO.toString();
		assertEquals("To String with null values", "Folders not set", folderIOString);
		folderIO.inputFolders = new ArrayList<File>();
		folderIO.inputFolders.add(new File(Constants.PATH_INPUT_TEST));
		folderIO.masterFolder = new File(Constants.PATH_OUTPUT_TEST);

		String filePart = "image-sort-master" + File.separator + "image-sorter" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "inputImages";
		boolean contains = folderIO.toString().contains(filePart);
		assertTrue("Check toString", contains);
		filePart = "image-sort-master" + File.separator + "image-sorter" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output";
		contains = folderIO.toString().contains(filePart);
		assertTrue("Check toString", contains);
	}
}