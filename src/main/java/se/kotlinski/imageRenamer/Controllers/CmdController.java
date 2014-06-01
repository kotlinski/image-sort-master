package se.kotlinski.imageRenamer.Controllers;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import se.kotlinski.imageRenamer.models.FolderIO;

import java.io.File;

/**
 * Run the Image Renamer via command-line.
 *
 * @author kristofer sommestad
 * @version $Revision: 1.1 $
 */
public class CmdController {

	private CommandLine cmd;
	private CommandLineParser parser;
	private Options options;
	private FolderIO folderIO;

	public CmdController() {
		setOptions(createOptions());
		setParser(new GnuParser());
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

	private static FolderIO runCmd(Options options, CommandLine cmd) {
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

	public FolderIO getFolderIO() {
		return folderIO;
	}

	public void startCmd(String[] argv) {
		folderIO = intepreterArgs(argv);

		System.out.println(folderIO);
	}

	private FolderIO intepreterArgs(final String[] argv) {
		FolderIO folderIO;
		try {
			setCmd(getParser().parse(getOptions(), argv));
		}
		catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
		}
		folderIO = runCmd(getOptions(), getCmd());
		return folderIO;
	}

	public CommandLine getCmd() {
		return cmd;
	}

	public void setCmd(final CommandLine cmd) {
		this.cmd = cmd;
	}

	public CommandLineParser getParser() {
		return parser;
	}

	public void setParser(final CommandLineParser parser) {
		this.parser = parser;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(final Options options) {
		this.options = options;
	}
}
