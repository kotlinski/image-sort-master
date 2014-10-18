package se.kotlinski.imagesort.model;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FolderIOTest {

	FolderIO folderIO;
  private ImageFileUtil imageFileUtil;

  @Before
	public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();
    folderIO = new FolderIO();
    folderIO.inputFolders = null;
    folderIO.masterFolder = null;

  }

	@Test
	public void testToString() throws Exception {
		String folderIOString = folderIO.toString();
		assertEquals("To String with null values", "Folders not set", folderIOString);
		folderIO.inputFolders = new ArrayList<File>();
		folderIO.inputFolders.add(new File(imageFileUtil.getTestInputPath()));
		folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());

		String filePart = "image-sort-master" + File.separator + "image-sorter" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "inputImages";
		boolean contains = folderIO.toString().contains(filePart);
		assertTrue("Check toString", contains);
		filePart = "image-sort-master" + File.separator + "image-sorter" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output";
		contains = folderIO.toString().contains(filePart);
		assertTrue("Check toString", contains);
	}
}