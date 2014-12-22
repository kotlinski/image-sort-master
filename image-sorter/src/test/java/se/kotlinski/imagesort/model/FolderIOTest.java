package se.kotlinski.imagesort.model;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FolderIOTest {

	private FolderIO folderIO;
  private ImageFileUtil imageFileUtil;

  @Before
	public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();
    setFolderIO(new FolderIO());
  }

	@Test
	public void testToString() throws Exception {
		String folderIOString = getFolderIO().toString();
		assertEquals("To String with null values", "Folders not set", folderIOString);
		getFolderIO().inputFolders = new ArrayList<>();
		getFolderIO().inputFolders.add(new File(imageFileUtil.getTestInputPath()));
		getFolderIO().masterFolder = new File(imageFileUtil.getTestOutputPath());

		String filePart = File.separator + "image-sorter" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "inputImages";
		boolean contains = getFolderIO().toString().contains(filePart);
		assertTrue("Check toString", contains);
		filePart = File.separator + "image-sorter" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output";
		contains = getFolderIO().toString().contains(filePart);
		assertTrue("Check toString", contains);
	}

	FolderIO getFolderIO() {
		return folderIO;
	}

	void setFolderIO(final FolderIO folderIO) {
		this.folderIO = folderIO;
	}
}