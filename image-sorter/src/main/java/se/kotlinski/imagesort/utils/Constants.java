package se.kotlinski.imagesort.utils;

import java.io.File;

/**
 * Created with IntelliJ IDEA. User: Simon Date: 2013-12-23 Time: 23:30 To change this template use
 * File | MainRenamer.imagesort.Settings | File Templates.
 */
public class Constants {
	public static final String USER_FOLDER = "user.folder";
	public static final String PATH_INPUT_TEST = (System.getProperty("user.dir") + File.separator +
																								/*"image-sorter" + File.separator +*/
																								"src" + File.separator +
																								"test" + File.separator +
																								"resources" + File.separator +
																								"inputImages")/*.replace("\\", "\\\\")*/;

	public static final String PATH_OUTPUT_TEST = (System.getProperty("user.dir") + File.separator +
																								 /*"image-sorter" + File.separator +*/
																								 "src" + File.separator +
																								 "test" + File.separator +
																								 "resources" + File.separator +
																								 "output")/*.replace("\\", "\\\\")*/;
}
