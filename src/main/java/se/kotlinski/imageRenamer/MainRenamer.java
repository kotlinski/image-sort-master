package se.kotlinski.imageRenamer;

import org.apache.commons.cli.*;
import se.kotlinski.imageRenamer.utils.FileRenamer;
import se.kotlinski.imageRenamer.utils.FileSystemProvider;
import se.kotlinski.imageRenamer.utils.ImageIndex;

import java.io.IOException;

public class MainRenamer {
	private static ImageIndex imageIndex;

	private MainRenamer() {
	}

	public static void main(String[] argv) throws IOException {
		imageIndex = new ImageIndex();


		Options options = createOptions();
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, argv);
		} catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
		}
		runCmd(options, cmd);
	}

	private static void runCmd(Options options, CommandLine cmd) {
		if (cmd == null) {
			printHelp(options);
		} else if (cmd.hasOption("source") && cmd.hasOption("output")) {
			String sourcePath = cmd.getOptionValue("source");
			if(validSource(sourcePath)){
				imageIndex.runIndexing(sourcePath);
			} else {
				System.out.print("SourcePath not ok!");
			}
/*
			FileSystemProvider fileSystemProvider = new FileSystemProvider();
			//fileSystemProvider.createFolder("");

			FileRenamer fileRenamer = new FileRenamer();
*/
		} else {
			printHelp(options);
		}
	}

	private static boolean validSource(final String sourcePath) {
		return true;
	}

	private static Options createOptions() {
		Options options = new Options();
		options.addOption("s", "source", true, "Import from this folder.");
		options.getOption("s").setRequired(true);
		options.addOption("o", "output", true, "Export to this folder");
		options.getOption("o").setRequired(true);

		options.addOption("h", "help", false, "MainRenamer usage\n" +
						"Main purpose is to read all images from a source-path and\n" +
						"export them to a given destionation. \n\n" +
						"When you have your images backed up via dropbox and manually, \n" +
						"it may be hard giving them smart names. Sometimes you will get\n" +
						"duplicated images on your back-up drive." +
						"java -jar ImageRename <sourcePath> <outputPath>. \n\n" +
		        "The sourcePath read folders and files recursivly, so you can put all" +
		        "your folders in the same directary. For example Dropbox-folders, etc"
		);
		return options;
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("MainRenamer", options);
	}
}