package se.kotlinski.imagesort.commandline.argument;

import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.InvalidInputFolderException;
import se.kotlinski.imagesort.exception.InvalidMasterFolderException;
import se.kotlinski.imagesort.model.SortSettings;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;
import java.util.ArrayList;


public class Transformer {

  private static final Logger logger = LogManager.getLogger(Transformer.class);
  private final HelpFormatter formatter;
  private final CommandLineParser parser;
  private final SortMasterFileUtil sortMasterFileUtil;

  @Inject
  public Transformer(final HelpFormatter formatter,
      final CommandLineParser parser,
      final SortMasterFileUtil sortMasterFileUtil) {
    this.formatter = formatter;
    this.parser = parser;
    this.sortMasterFileUtil = sortMasterFileUtil;
  }

  public Options getOptions() {
    Options options = new Options();
    Option option = new Option("s", "source", true, "Import from this folder.");
    option.setArgs(Option.UNLIMITED_VALUES);
    option.setValueSeparator(',');

    options.addOption(option);
    options.getOption("s").setRequired(true);
    options.addOption("o", "output", true, "Export to this folder");
    options.getOption("o").setRequired(true);

    options.addOption("h", "help", false, "MainRenamer usage\n" +
                                          "Main purpose is to read all images from a source-path " +
                                          "and\n" +
                                          "export them to a given destination. \n\n" +
                                          "When you have your images backed up via dropbox and " +
                                          "manually, \n" +
                                          "it may be hard giving them smart names. Sometimes you " +
                                          "will get\n" +
                                          "duplicated images on your back-up drive." +
                                          "java -jar ImageRename <sourcePath> <outputPath>. \n\n" +
                                          "The sourcePath read folders and files recursively, so " +
                                          "you can put all" +
                                          "your folders in the same directory. For example " +
                                          "Dropbox-folders, etc");
    return options;
  }

  public void printHelp(Options options) {
    formatter.printHelp("MainRenamer", options);
  }

  public CommandLine interpreterArgs(final String[] arguments) throws Exception {
    CommandLine cmd;
    try {
      cmd = parser.parse(getOptions(), arguments);
    }
    catch (ParseException e) {
      logger.error("Parsing failed, " + e);
      throw new Exception("Parsing failed");
    }
    return cmd;
  }

  public SortSettings transformCommandLineArguments(final Options options, final CommandLine commandLine) throws
                                                                                              InvalidMasterFolderException,
                                                                                              InvalidInputFolderException {
    SortSettings sortSettings = new SortSettings();
    if (commandLine == null || commandLine.hasOption("h")) {
      printHelp(options);
    }
    else if (commandLine.hasOption("s") && commandLine.hasOption("o")) {
      String[] sourcePaths = commandLine.getOptionValues("s");
      ArrayList<File> inputFolders = new ArrayList<File>();
      for (String sourcePath : sourcePaths) {
        File folder = new File(sourcePath);
        if (sortMasterFileUtil.isValidFolder(folder)) {
          inputFolders.add(folder);
        }
        else {
          throw new InvalidInputFolderException("SourcePath not valid: " + sourcePath);
        }
      }
      String outputPath = commandLine.getOptionValue("o");
      File masterFolder = new File(outputPath);

      if (sortMasterFileUtil.isValidFolder(masterFolder)) {
        sortSettings.masterFolder = masterFolder;
      }
      else {
        throw new InvalidMasterFolderException("SourcePath not valid: " + masterFolder,
                                               masterFolder);
      }
      sortSettings.inputFolders = inputFolders;
    }
    else {
      logger.error("No source no output folder chosen");
      printHelp(options);
    }
    return sortSettings;
  }
}
