import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2013-10-12
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
class FileSystemProvider {
    public static String USER_FOLDER = "user.folder";

    public FileSystemProvider(){

    }
	public static boolean createFolder(String path) {
		File file = new File(path);
		boolean result = false;
		// if the directory does not exist, create it
		if (!file.exists()) {
			System.out.println("creating directory: " + path);
			result = file.mkdir();
		}
		return result;
	}
}
