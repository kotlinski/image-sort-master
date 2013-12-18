import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

public class main {
//    private String[] menuOptions={"","dropbox","input","output","settings","quit"};
    private static BackupSystem backupSystem;

    public static void main(String[] argv) throws IOException {

        backupSystem = new BackupSystem();

        Options options = new Options();
        options.addOption("run", false, "run backup, import images from dropbox and phone.");
        options.addOption("dropbox", true, "add a dropbox path.");
        options.addOption("help", false, "print this message");

        CommandLineParser parser = new GnuParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse( options, argv);
        } catch (ParseException e) {
            System.err.println( "Parsing failed.  Reason: " + e.getMessage() );
        }

        if( cmd == null )
        {
            printHelp(options);
        }
        else if( cmd.hasOption( "run" ) ) {

        }
        else if( cmd.hasOption( "help" ) ) {
            printHelp(options);
        }
        else if( cmd.hasOption( "dropbox"))
        {
            String dropboxPath = cmd.getOptionValue( "dropbox" );
            boolean isValid = validatePath(dropboxPath);
            if( isValid )
            {
                backupSystem.getSettings().setDropboxAlbum(dropboxPath);
            }
            else {
                System.out.println(dropboxPath + " is not a valid path");
            }
        }


//
//        if (argv.length == 0) {
//            printRunTips();
//        }
//        else
//        {
//            if (argv[0].equals(menuOptions[Menu.SETTINGS.ordinal()]))
//            {
//                executeSettings();
//            }
//            else if (argv[0].equals(menuOptions[Menu.DROPBOX.ordinal()])) {
//                Settings settings = backupSystem.getSettings();
//
//            }
//            else if (argv[0].equals("run")) {
//
//            }
//        }


//
//        System.out.println("argv.length: " + argv.length);
//        if (argv.length == 2) {
//            sourecefolder = argv[1];
//            destinationFolder = sourecefolder;
//        } else if (argv.length == 1) {
//        }
//        fileRenamer.setSourceFolder(sourecefolder);
//        fileRenamer.setDestinationFolder(destinationFolder);
//
//        fileRenamer.renameFiles();
    }

    private static boolean validatePath(String path) {
        File file = new File(path);
        return file.isDirectory() && file.exists();
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ant", options);
    }

    private void printRunTips() {
        System.out.println("Please choose a command!");
        System.out.println("run\truns a backup");
        System.out.println("dropbox\tpath to dropbox image folder");
        System.out.println("input\tsecondary input folder");
        System.out.println("output\toutput path");
        System.out.println("settings\tshow settings");
        System.out.println("quit");
    }

    private void executeSettings() {
        Settings settings = backupSystem.getSettings();
        System.out.println(settings.toString());
    }
}