import java.io.IOException;

public class main {

    public static void main(String[] argv) throws IOException {
        FileRenamer fileRenamer = new FileRenamer();
        String sourecefolder = System.getProperty("user.dir");
        String destinationFolder = sourecefolder;
        if(argv.length == 0) {
            System.out.println("Please choose a command!");
            System.out.println("rename");
        }
        else if(argv[0].equals("rename")){
            System.out.println("argv.length: " + argv.length);
            if(argv.length == 2)
            {
                sourecefolder = argv[1];
                destinationFolder = sourecefolder;
             }
            else if(argv.length == 1)
            {
            }
            fileRenamer.setSourceFolder(sourecefolder);
            fileRenamer.setDestinationFolder(destinationFolder);

            fileRenamer.renameFiles();
        }
    }
}