package se.kotlinski.imagesort.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.Constants;

import java.io.File;

public class ImageDescriberTest {

	private ImageDescriber imageDescriber;
	private ImageDescriber imageDescriber2;

	@Before
	public void setup() {
		File file = new File(Constants.PATH_INPUT_TEST + "//structure//2013-10-03 13.43.20-kaffe.jpg");
		File file2 = new File(Constants.PATH_INPUT_TEST + "//structure//2013-10-26 20.20.46-kottbullar.jpg");
		imageDescriber = new ImageDescriber(file);
		imageDescriber2 = new ImageDescriber(file2);
	}

	@Test
	public void testGetMd5() throws Exception {
		Assert.assertEquals("Test MD5", "10a16e061aba9bdf721cce382756b1bc", imageDescriber.getMd5());
		Assert.assertEquals("Test MD5 testfile 2", "e53209c079f84e775ee561ab65ae0589", imageDescriber2.getMd5());
	}
}