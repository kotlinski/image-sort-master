package se.kotlinski.imageRenamer;

import org.apache.commons.cli.*;
import se.kotlinski.imageRenamer.utils.FileRenamer;
import se.kotlinski.imageRenamer.utils.FileSystemProvider;
import se.kotlinski.imageRenamer.utils.ImageIndex;

import java.io.IOException;

public class main {
	public static void main(String[] argv) throws IOException {
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
		} else if (cmd.hasOption("generate")) {
			FileSystemProvider fileSystemProvider = new FileSystemProvider();
			fileSystemProvider.createFolder(Constants.PATH_INPUT);
			fileSystemProvider.createFolder(Constants.PATH_OUTPUT);
			/*
			fileSystemProvider.createFolder(Constants.PATH_MERGED);
			fileSystemProvider.createFolder(Constants.PATH_SAMSUNG);
			fileSystemProvider.createFolder(Constants.PATH_DROPBOX);
			fileSystemProvider.createFolder(Constants.PATH_GOOGLE);
			fileSystemProvider.createFolder(Constants.PATH_OTHER);*/

		} else if (cmd.hasOption("run")) {
			ImageIndex imageIndex = new ImageIndex();
			imageIndex.runIndexing(Constants.PATH_INPUT);

			FileRenamer fileRenamer = new FileRenamer();
			// fileRenamer.renameFiles(se.kotlinski.imageRenamer.Constants.FORMAT.SAMSUNG, se.kotlinski.imageRenamer.Constants.FORMAT.SAMSUNG);
			// fileRenamer.renameFiles(se.kotlinski.imageRenamer.Constants.FORMAT.DROPBOX, se.kotlinski.imageRenamer.Constants.FORMAT.SAMSUNG);
		} else {
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