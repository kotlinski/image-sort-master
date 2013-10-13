import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2013-10-12
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
class BackupSystem {
    public static String USER_FOLDER = "user.folder";
    private Gson gson;
    private Settings settings;
    private FileRenamer fileRenamer;

    public BackupSystem(){
        this.gson = new Gson();
        settings = new Settings();
        fileRenamer = new FileRenamer();
    }

    public void updateSettingsFile(Settings settings)
    {
        File settingsFile = getSettingsFile();
        try {
            FileUtils.writeStringToFile(settingsFile, gson.toJson(settings));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readSettingsFromFile()
    {
        File settingsFile = getSettingsFile();
        String settingsString = getFileContent(settingsFile);
        System.out.println(settingsString);
        return settingsString;
    }

    private File getSettingsFile() {
        File settingsFile = new File(System.getProperty("user.dir")+"/settings.json");
        if (!settingsFile.exists()) {
            createSettingsFile(settingsFile);
        }
        return settingsFile;
    }

    private String getFileContent(File settingsFile) {
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(settingsFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String settings = "";
        try {
            assert it != null;
            while (it.hasNext()) {
                settings += it.nextLine();
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        return settings;
    }

    private void createSettingsFile(File settingsFile) {
        boolean result = false;
        try {
            result = settingsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result) {
            System.out.println("File created");
        }
    }

    public Settings getSettings() {
        String settingsString = readSettingsFromFile();
        return gson.fromJson(settingsString, Settings.class);
    }
}
