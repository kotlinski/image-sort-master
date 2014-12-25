package se.kotlinski.imagesort.model;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FolderIOTest {

	private FolderIO folderIO;
  private SortMasterFileUtil sortMasterFileUtil;

  @Before
	public void setUp() throws Exception {
    sortMasterFileUtil = new SortMasterFileUtil();
    setFolderIO(new FolderIO());
  }

	@Test
	public void testToString() throws Exception {
		String folderIOString = getFolderIO().toString();
		assertEquals("To String with null values", "Folders not set", folderIOString);
		getFolderIO().inputFolders = new ArrayList<>();
		getFolderIO().inputFolders.add(new File(sortMasterFileUtil.getTestInputPath()));
		getFolderIO().masterFolder = new File(sortMasterFileUtil.getTestOutputPath());

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