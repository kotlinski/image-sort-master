package se.kotlinski.imagesort.commandline;

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
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;
import java.util.ArrayList;


public class CmdUtils {

  private static final Logger logger = LogManager.getLogger(CmdUtils.class);
  private final HelpFormatter formatter;
  private final CommandLineParser parser;
  private final SortMasterFileUtil sortMasterFileUtil;

  @Inject
  public CmdUtils(final HelpFormatter formatter,
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

  public FolderIO runCmd(final Options options, final CommandLine cmd) throws
                                                                       InvalidMasterFolderException,
                                                                       InvalidInputFolderException {
    FolderIO folderIO = new FolderIO();
    if (cmd == null || cmd.hasOption("h")) {
      printHelp(options);
    }
    else if (cmd.hasOption("s") && cmd.hasOption("o")) {
      String[] sourcePaths = cmd.getOptionValues("s");
      ArrayList<File> inputFolders = new ArrayList<>();
      for (String sourcePath : sourcePaths) {
        File folder = new File(sourcePath);
        if (sortMasterFileUtil.isValidFolder(folder)) {
          inputFolders.add(folder);
        }
        else {
          throw new InvalidInputFolderException("SourcePath not valid: " + sourcePath);
        }
      }
      String outputPath = cmd.getOptionValue("o");
      File masterFolder = new File(outputPath);

      if (sortMasterFileUtil.isValidFolder(masterFolder)) {
        folderIO.masterFolder = masterFolder;
      }
      else {
        throw new InvalidMasterFolderException("SourcePath not valid: " + masterFolder, masterFolder);
      }
      folderIO.inputFolders = inputFolders;
    }
    else {
      logger.error("No source no output folder chosen");
      printHelp(options);
    }
    return folderIO;
  }
}
