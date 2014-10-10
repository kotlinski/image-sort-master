package se.kotlinski.imagesort.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import se.kotlinski.imagesort.model.FolderIO;

import java.io.File;
import java.util.ArrayList;

/**
 * Describe class/interface here.
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class CommandLineUtil {

	public static Options getOptions() {

		//Implement CLI SourceDestArgument
		//


		Options options = new Options();
		Option option = new Option("s", "source", true, "Import from this folder.");
		option.setArgs(Option.UNLIMITED_VALUES);
		option.setValueSeparator(',');

		options.addOption(option);
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
		if (cmd == null || cmd.hasOption("h")) {
			printHelp(options);
		}
		else if (cmd.hasOption("s") && cmd.hasOption("o")) {
			String[] sourcePaths = cmd.getOptionValues("s");
			ArrayList<File> inputFolders = new ArrayList<File>();
			for (String sourcePath : sourcePaths) {
				File folder = new File(sourcePath);
				if(isValidFolder(folder)){
					inputFolders.add(folder);
				}
				else {
					System.err.println(Constants.PATH_INPUT_TEST);
					System.err.println("SourcePath not valid: " + sourcePath);
					throw new NullPointerException();
				}
			}
			String outputPath = cmd.getOptionValue("o");
			inputFolders.add(folderIO.masterFolder);

			folderIO.inputFolders = inputFolders;
			folderIO.masterFolder = new File(outputPath);
		}
		else {
			System.out.println("No source no output folder chosen");
			printHelp(options);
		}
		return folderIO;
	}

	private static boolean isValidFolder(final File folder) {
		return folder.exists() && folder.isDirectory();
	}
}
