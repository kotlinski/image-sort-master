import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2013-10-12
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
public class Settings {
    private String samsungPath = "";
    private String dropboxPath = "";
	private String outputPath = "";

    public Settings()
    {
    }

    public void setSamsungPath(String samsungPath) {
        this.samsungPath = samsungPath;
    }
    public void setDropboxPath(String dropboxPath) {
        this.dropboxPath = dropboxPath;
    }
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

    public String getSamsungPath() {
        return samsungPath;
    }
    public String getDropboxPath() {
        return dropboxPath;
    }
	public String getOutputPath() {
		return outputPath;
	}


}
