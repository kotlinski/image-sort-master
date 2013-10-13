import java.io.IOException;

public class main {
    BackupSystem backupSystem;

    private enum Menu {
        RUN,
        DROPBOX,
        INPUT,
        OUTPUT,
        SETTINGS,
        QUIT
    }
    private String[] menuOptions={"run","dropbox","input","output","settings","quit"};

    public main(String[] argv) throws IOException {
        backupSystem = new BackupSystem();
        if (argv.length == 0) {
            printRunTips();
        }
        else
        {
            if (argv[0].equals(menuOptions[Menu.SETTINGS.ordinal()]))
            {
                executeSettings();
            }
            else if (argv[0].equals(menuOptions[Menu.DROPBOX.ordinal()])) {
                Settings settings = backupSystem.getSettings();

            }
            else if (argv[0].equals("run")) {

            }
        }


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