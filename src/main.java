import java.io.IOException;
import org.apache.commons.cli.*;

public class main {
    private static BackupSystem backupSystem;

    public static void main(String[] argv) throws IOException {

        backupSystem = new BackupSystem();

        Options options = createOptions();

        CommandLineParser parser = new GnuParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse( options, argv);
        } catch (ParseException e) {
            System.err.println( "Parsing failed.  Reason: " + e.getMessage() );
        }

        runCmd(options, cmd);
    }

    private static void runCmd(Options options, CommandLine cmd) {
        if( cmd == null )
        {
            printHelp(options);
        }
        else if( cmd.hasOption( "generate" ) ) {
			String dropboxPath = System.getProperty("user.dir")+"\\Dropbox";
			boolean pathUpdated = FileSystemProvider.createFolder(dropboxPath);
			if(pathUpdated) {
				backupSystem.getSettings().setDropboxPath(dropboxPath);
				backupSystem.updateSettingsFile(backupSystem.getSettings());
			}

			String samsungPath = System.getProperty("user.dir")+"\\Samsung";
			pathUpdated = FileSystemProvider.createFolder(samsungPath);
			if(pathUpdated) {
				backupSystem.getSettings().setSamsungPath(samsungPath);
				backupSystem.updateSettingsFile(backupSystem.getSettings());
			}
			String resultPath = System.getProperty("user.dir")+"\\Result";
			FileSystemProvider.createFolder(resultPath);
        }
        else if( cmd.hasOption( "run" ) ) {
			FileRenamer fileRenamer = new FileRenamer();
			MoveSettings moveSettings = new MoveSettings(Enums.FORMAT.SAMSUNG, Enums.FORMAT.SAMSUNG);
			fileRenamer.renameFiles(moveSettings);
			moveSettings = new MoveSettings(Enums.FORMAT.DROPBOX, Enums.FORMAT.SAMSUNG);
			fileRenamer.renameFiles(moveSettings);
        }
        else { //if( cmd.hasOption( "help" ) ) {
            printHelp(options);
        }
    }

    private static Options createOptions() {
        Options options = new Options();
        options.addOption("run", false, "run backup, import images from dropbox and phone.");
        // options.addOption("dropbox", true, "add a dropbox path.");
        options.addOption("generate", false, "generate default folders");
        options.addOption("help", false, "Populate Dropbox folder, Samsung folder with images and run.");
        return options;
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ImageRenamer", options);
    }
}