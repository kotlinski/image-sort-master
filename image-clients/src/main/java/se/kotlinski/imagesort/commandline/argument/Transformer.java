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
import se.kotlinski.imagesort.exception.InvalidArgumentsException;
import se.kotlinski.imagesort.model.SortSettings;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;

public class Transformer {

  private static final Logger LOGGER = LogManager.getLogger(Transformer.class);
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

  public CommandLine parseArgs(final String[] arguments) throws Exception {
    CommandLine cmd;
    try {
      cmd = parser.parse(getOptions(), arguments);
    }
    catch (ParseException e) {
      printHelp(getOptions());
      LOGGER.error("Parsing failed, " + e);
      throw new Exception("Parsing failed");
    }
    return cmd;
  }

  public SortSettings transformCommandLineArguments(final CommandLine commandLine) throws
                                                                                         Exception {
    SortSettings sortSettings = new SortSettings();
    if (commandLine == null || commandLine.hasOption("h") || !commandLine.hasOption('s')) {
      printHelp(getOptions());
    }
    else if (commandLine.hasOption("s")) {
      String sourcePath = commandLine.getOptionValue("s");
      File folder = new File(sourcePath);
      if (sortMasterFileUtil.isValidFolder(folder)) {
        sortSettings.masterFolder = folder;
        return sortSettings;
      }
      else {
        throw new InvalidArgumentsException("SourcePath not valid: " + sourcePath);
      }
    }
    throw new InvalidArgumentsException("Could not create sortSettings for attributes");
  }



  final Options getOptions() {
    Options options = new Options();
    Option sourceOption = new Option("s", "source", true, "Folder to 'image sort'");
    sourceOption.setType(String.class);
    options.addOption(sourceOption);
    options.addOption("h", "help", false, "print help");
    return options;
  }

  void printHelp(Options options) {
    String helpText = "\n" +
                      "How and when do you sort and back up your images from your camera-devices?" +
                      " \n" +
                      "\n" +
                      "I have my images stored on a few hard drive backups, dropbox and various " +
                      "devices;\n" +
                      "My dropbox, my nexus 5 and my old Samsung galaxy device. The problem is " +
                      "that \n" +
                      "Dropbox, Nexus 5 and Samsung all have their own way of naming the images " +
                      "and their \n" +
                      "storage's always get full at different points of time. Dropbox gets full " +
                      "once in a year,\n" +
                      "my phone every two or three months, or sometimes, devices get wasted. \n" +
                      "\n" +
                      "Over time it gets hard to detect duplicates and you don't bother delete " +
                      "poor-\n" +
                      "quality images, because you know that it may exist in at least one more " +
                      "location.\n" +
                      "\n" +
                      "With this tool your images will be sorted and renamed in this structure:\n" +
                      "\n" +
                      "`year/[month(optional)]/<flavour(existing folder structure)>/yyyy-mm-dd " +
                      "hh:mm:ss.[png|jpg|mp4]`\n" +
                      "\n" +
                      "The month setting is optional. \n" +
                      "The flavor may be Instagram, Screenshots, Elins wedding or what folder " +
                      "you\n" +
                      "put your images in.\n" +
                      "A flavor is all sub folders from the root folder, except year/month.\n" +
                      "Example:\n" +
                      "`root-folder/2013/Wedding/TheKiss/imgA.png`\n" +
                      " will be sorted as: \n" +
                      "`root-folder/2013/04/Wedding/TheKiss/2013-04-22 13:17:00.png`\n" +
                      "\n" +
                      "The application will detect all duplicate files and merge them into one " +
                      "when possible. \n";

    System.out.println(helpText);
    formatter.printHelp("MainRenamer", options);
  }

}
