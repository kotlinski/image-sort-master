package se.kotlinski.imagesort.model;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SortSettingsTest {

	private SortSettings sortSettings;
  private MediaFileTestUtil mediaFileTestUtil;

  @Before
	public void setUp() throws Exception {
	  mediaFileTestUtil = new MediaFileTestUtil();
    setSortSettings(new SortSettings());
  }

	@Test
	public void testToString() throws Exception {
		String folderIOString = getSortSettings().toString();
		assertEquals("To String with null values", "Folders not set", folderIOString);
		getSortSettings().masterFolder = new File(mediaFileTestUtil.getTestOutputPath());

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