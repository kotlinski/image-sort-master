package se.kotlinski.imageRenamer.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imageRenamer.Constants;

/**
 * Created by Simon Kotlinski on 2014-02-19.
 */
public class ImageIndexTest {
	ImageIndex imageIndex;

	@Before
	public void setUp() {
		imageIndex = new ImageIndex();
	}

	@Test
	public void testRunIndexing() throws Exception {

	}

	@Test
	public void testGetIndex() throws Exception {
		String fileName = "asdf.jpg";
		Constants.FORMAT format = Constants.FORMAT.DROPBOX;
		String index = imageIndex.getIndex(fileName, format);
		Assert.assertEquals("test fail name", "asdf.jpg", index);

		fileName = "2013-09-17 21.14.49.jpg";
		format = Constants.FORMAT.DROPBOX;
		index = imageIndex.getIndex(fileName, format);
		Assert.assertEquals("correct Dropbox Format", "2013-09-17_21.14.49", index);


		fileName = "20121030_183705.jpg";
		format = Constants.FORMAT.SAMSUNG;
		index = imageIndex.getIndex(fileName, format);
		Assert.assertEquals("correct Samsung Format", "2012-10-30_18.37.05", index);

		fileName = "IMG_20140108_120459.jpg";
		format = Constants.FORMAT.GOOGLE;
		index = imageIndex.getIndex(fileName, format);
		Assert.assertEquals("correct Google Format", "2014-01-08_12.04.59", index);
	}

	@Test
	public void testRunIndex() throws Exception {
		imageIndex.runIndexing(Constants.PATH_INPUT_TEST);

	}
}
