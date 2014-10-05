package se.kotlinski.imageRenamer.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import se.kotlinski.imageRenamer.model.FolderIO;

import java.io.File;

/**
 * Describe class/interface here.
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class CommandLineUtil {

	public static Options getOptions() {
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

	public static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("MainRenamer", options);
	}

	public static CommandLine intepreterArgs(final String[] argv) {
		CommandLine cmd = null;
		try {
			cmd = getParser().parse(getOptions(), argv);
		}
		catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
		}
		return cmd;
	}

	public static CommandLineParser getParser() {
		return new GnuParser();
	}

	public static FolderIO runCmd(final Options options, final CommandLine cmd) {
		FolderIO folderIO = new FolderIO();
		if (cmd == null) {
			printHelp(options);
		}
		else if (cmd.hasOption("s") && cmd.hasOption("o")) {
			String sourcePath = cmd.getOptionValue("s");
			String outputPath = cmd.getOptionValue("o");
			folderIO.inputFolder = new File(sourcePath);
			folderIO.outputFolder = new File(outputPath);
		}
		else {
			System.out.println("No source no output fodler chosen");
			printHelp(options);
		}
		return folderIO;

	}
}
