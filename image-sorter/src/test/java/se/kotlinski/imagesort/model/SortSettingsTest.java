package se.kotlinski.imagesort.model;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SortSettingsTest {

	private SortSettings sortSettings;
  private SortMasterFileUtil sortMasterFileUtil;

  @Before
	public void setUp() throws Exception {
    sortMasterFileUtil = new SortMasterFileUtil();
    setSortSettings(new SortSettings());
  }

	@Test
	public void testToString() throws Exception {
		String folderIOString = getSortSettings().toString();
		assertEquals("To String with null values", "Folders not set", folderIOString);
		getSortSettings().inputFolders = new ArrayList<File>();
		getSortSettings().inputFolders.add(new File(sortMasterFileUtil.getTestInputPath()));
		getSortSettings().masterFolder = new File(sortMasterFileUtil.getTestOutputPath());

		String filePart = File.separator + "image-sorter" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "inputImages";
		boolean contains = getSortSettings().toString().contains(filePart);
		assertTrue("Check toString", contains);
		filePart = File.separator + "image-sorter" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "output";
		contains = getSortSettings().toString().contains(filePart);
		assertTrue("Check toString", contains);
	}

	SortSettings getSortSettings() {
		return sortSettings;
	}

	void setSortSettings(final SortSettings sortSettings) {
		this.sortSettings = sortSettings;
	}
}