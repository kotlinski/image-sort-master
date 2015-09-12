package se.kotlinski.imagesort.commandline.argument;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.commandline.ScannerWrapper;
import se.kotlinski.imagesort.exception.CouldNotCreateMasterFolderException;
import se.kotlinski.imagesort.exception.InvalidFolderArgumentsException;
import se.kotlinski.imagesort.exception.InvalidInputFolderException;
import se.kotlinski.imagesort.exception.InvalidMasterFolderException;
import se.kotlinski.imagesort.model.SortSettings;

import java.io.File;

public class Interpreter {

  private static final Logger logger = LogManager.getLogger(Interpreter.class);
  private Transformer transformer;


  public Interpreter(final Transformer transformer) {
    this.transformer = transformer;
  }

  public SortSettings transformArguments(final String[] arguments) throws
                                                                   CouldNotCreateMasterFolderException {
    SortSettings sortSettings = null;

    try {
      sortSettings = getFolderIO(arguments);
    }
    catch (InvalidFolderArgumentsException e) {
      System.out.println("Wrong input/output folder arguments, try again");
      logger.error("Could not create input/output-folders, invalid arguments", e);
    }
    catch (InvalidMasterFolderException e) {
      logger.error(e);
      boolean masterFolderCreated = e.getMasterFolder() == null;
      if (masterFolderCreated) {
        transformArguments(arguments);
      }
      else {
        throw new CouldNotCreateMasterFolderException("Could not create master folder, " +
                                                      e.getMasterFolder());
      }
    }
    return sortSettings;
  }


  public SortSettings getFolderIO(final String[] arguments) throws
                                                            InvalidFolderArgumentsException,
                                                            InvalidMasterFolderException {
    CommandLine commandLine;
    try {
      commandLine = transformer.interpreterArgs(arguments);
    }
    catch (Exception e) {
      logger.debug("Could not parse arguments", e);
      System.out.println("Could not parse arguments");
      throw new InvalidFolderArgumentsException("Could not parse arguments");
    }

    SortSettings sortSettings;
    try {
      sortSettings = transformer.transformCommandLineArguments(transformer.getOptions(),
                                                               commandLine);
      System.out.println(sortSettings.toString());
    }
    catch (InvalidInputFolderException e) {
      System.out.println("No input folder found, try again");
      logger.error("No input folder found, try again", e);
      throw new InvalidFolderArgumentsException("Invalid folder input parameters");
    }
    catch (InvalidMasterFolderException e) {
      logger.error("Could not create output folder", e);
      System.out.println("No output folder found");
      throw new InvalidMasterFolderException("Invalid folder output parameter",
                                             e.getMasterFolder());
    }
    return sortSettings;
  }

}
