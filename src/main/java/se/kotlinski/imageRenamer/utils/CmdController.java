package se.kotlinski.imageRenamer.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;

/**
 * Run the Image Renamer via command-line.
 *
 * @author kristofer sommestad
 * @version $Revision: 1.1 $
 */
public class CmdController {
	private static File inputRoot;
	private static File outputFolder;

	CommandLine cmd;
	CommandLineParser parser;
	Options options;

	public CmdController() {
		options = createOptions();
		parser = new GnuParser();
	}

	public File startCmd(String[] argv) {
		try {
			cmd = parser.parse(options, argv);
		}
		catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
		}
		return runCmd(options, cmd);
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

	private File runCmd(Options options, CommandLine cmd) {
		if (inputRoot != null && outputFolder != null) {
			return inputRoot;
		}
		else if (cmd == null) {
			printHelp(options);
		}
		else if (cmd.hasOption("source") && cmd.hasOption("output")) {
			String sourcePath = cmd.getOptionValue("source");
			return new File(sourcePath);
		}
		else {
			printHelp(options);
		}
		return null;
	}
}
