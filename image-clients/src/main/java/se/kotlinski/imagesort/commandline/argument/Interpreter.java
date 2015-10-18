package se.kotlinski.imagesort.commandline.argument;

import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.InvalidArgumentsException;
import se.kotlinski.imagesort.exception.InvalidMasterFolderException;
import se.kotlinski.imagesort.model.SortSettings;

public class Interpreter {

  private static final Logger LOGGER = LogManager.getLogger(Interpreter.class);
  private Transformer transformer;

  @Inject
  public Interpreter(final Transformer transformer) {
    this.transformer = transformer;
  }

  public SortSettings transformArguments(final String[] arguments) throws Exception {
    SortSettings sortSettings = null;

    try {
      sortSettings = getSortSettings(arguments);
    }
    catch (InvalidArgumentsException e) {
      LOGGER.error("Could not create input/output-folders, invalid arguments", e);
      throw new Exception("Invalid arguments");
    }
    catch (InvalidMasterFolderException e) {
      LOGGER.error(e);
      boolean masterFolderCreated = e.getMasterFolder() == null;
      if (masterFolderCreated) {
        transformArguments(arguments);
      }
      else {
        throw new Exception("Could not create master folder, " + e.getMasterFolder());
      }
    }
    return sortSettings;
  }


  public final SortSettings getSortSettings(final String[] arguments) throws
                                                                  Exception {
    CommandLine commandLine;
    try {
      commandLine = transformer.parseArgs(arguments);
    }
    catch (Exception e) {
      LOGGER.error("Could not parse arguments", e);
      throw e;
    }

    SortSettings sortSettings;
    try {
      sortSettings = transformer.transformCommandLineArguments(transformer.getOptions(),
                                                               commandLine);
      System.out.println(sortSettings.toString());
    }
    catch (InvalidArgumentsException e) {
      System.out.println("No input folder found, try again");
      LOGGER.error("No input folder found, try again", e);
      throw new InvalidArgumentsException("Invalid folder input parameters");
    }
    catch (InvalidMasterFolderException e) {
      LOGGER.error("Could not create output folder", e);
      System.out.println("No output folder found");
      throw e;
    }
    return sortSettings;
  }

}
