package se.kotlinski.imageRenamer;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 2013-12-23
 * Time: 23:30
 * To change this template use File | se.kotlinski.imageRenamer.Settings | File Templates.
 */
public class Constants {
	public static final String PATH_INPUT = System.getProperty("user.dir") + "\\InputFolder";
	public static final String PATH_INPUT_TEST = System.getProperty("user.dir") + "\\TestAssets\\inputImages";
	public static final String PATH_OUTPUT = System.getProperty("user.dir") + "\\Output";
	public static final String PATH_OUTPUT_TEST = System.getProperty("user.dir") + "\\TestAssets\\outImages"; /*
	public static final String PATH_MERGED = System.getProperty("user.dir") + "\\Output\\Merged";
	public static final String PATH_SAMSUNG = System.getProperty("user.dir") + "\\Output\\Samsung";
	public static final String PATH_DROPBOX = System.getProperty("user.dir") + "\\Output\\Dropbox";
	public static final String PATH_GOOGLE = System.getProperty("user.dir") + "\\Output\\Google";
	public static final String PATH_OTHER = System.getProperty("user.dir") + "\\Output\\Other";
*/
	public static final String USER_FOLDER = "user.folder";

	public final static String DROPBOX_FORMAT = "([0-9]){4}-([0-9]){2}-([0-9]){2} ([0-9]|.){8}.(jpeg|JPG|JPEG|jpg)";
	public final static String SAMSUNG_FORMAT = "([0-9]){8}_([0-9]){6}.(jpeg|JPG|JPEG|jpg)";
	public final static String GOOGLE_FORMAT = "(IMG_)([0-9]){8}_([0-9]){6}.(jpeg|JPG|JPEG|jpg)";

	public final static String DROPBOX_DATE_FORMAT = "([0-9]){4}-([0-9]){2}-([0-9]){2} ([0-9]|.){8}";
	public final static String SAMSUNG_DATE_FORMAT = "([0-9]){8}_([0-9]){6}";
	public final static String GOOGLE_DATE_FORMAT = "([0-9]){8}_([0-9]){6}";

	public enum FORMAT {
		DROPBOX,
		SAMSUNG,
		GOOGLE,
		OTHER
	}

	public enum OUTPUT {
		MERGED,
		SAMSUNG,
		DROPBOX,
		GOOGLE,
		OTHER
	}
}
