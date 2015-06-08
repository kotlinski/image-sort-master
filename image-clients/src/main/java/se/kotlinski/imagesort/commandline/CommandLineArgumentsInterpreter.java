package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.CouldNotCreateMasterFolderException;
import se.kotlinski.imagesort.exception.InvalidFolderArgumentsException;
import se.kotlinski.imagesort.exception.InvalidInputFolderException;
import se.kotlinski.imagesort.exception.InvalidMasterFolderException;
import se.kotlinski.imagesort.model.FolderIO;

import java.io.File;

public class CommandLineArgumentsInterpreter {

  private static final Logger logger = LogManager.getLogger(CommandLineArgumentsInterpreter.class);
  private CommandLineArgumentsTransformer commandLineArgumentsTransformer;
  private ScannerWrapper inScanner;


  public CommandLineArgumentsInterpreter(final CommandLineArgumentsTransformer commandLineArgumentsTransformer,
                                         ScannerWrapper inScanner) {
    this.commandLineArgumentsTransformer = commandLineArgumentsTransformer;
    this.inScanner = inScanner;
  }

  public FolderIO transformArguments(final String[] arguments) throws
                                                               CouldNotCreateMasterFolderException {



    FolderIO folderIO = null;
    try {
      folderIO = getFolderIO(arguments);
    }
    catch (InvalidFolderArgumentsException e) {
      System.out.println("Wrong input/output folder arguments, try again");
      logger.error("Could not create input/output-folders, invalid arguments", e);
    }
    catch (InvalidMasterFolderException e) {
      logger.error(e);
      boolean masterFolderCreated = createMasterFolder(e.getMasterFolder());
      if (masterFolderCreated) {
        //lets try again
        transformArguments(arguments);
      }
      else {
        // epic fail
        throw new CouldNotCreateMasterFolderException("Could not create master folder, " +
                                                      e.getMasterFolder());
      }
    }
    return folderIO;
  }


  public FolderIO getFolderIO(final String[] arguments) throws
                                                        InvalidFolderArgumentsException,
                                                        InvalidMasterFolderException {
    CommandLine commandLine;
    try {
      commandLine = commandLineArgumentsTransformer.interpreterArgs(arguments);
    }
    catch (Exception e) {
      logger.debug("Could not parse argumnets", e);
      System.out.println("Could not parse arguments");
      throw new InvalidFolderArgumentsException("Could not parse arguments");
    }

    FolderIO folderIO;
    try {
      folderIO = commandLineArgumentsTransformer.transformCommandLineArguments(
          commandLineArgumentsTransformer.getOptions(),
          commandLine);
      System.out.println(folderIO.toString());
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
    return folderIO;
  }


  public boolean createMasterFolder(final File masterFolder) {
    if(masterFolder == null) {
      return false;
    }

    System.out.println("Do you want to create " + masterFolder + "[y/n]");
    String answer = inScanner.nextLine().trim().toLowerCase();

    if ("y".equals(answer)) {
      System.out.println("Creating " + masterFolder.getName() + "...");
      logger.info("Creating " + masterFolder.getName() + "...");

      boolean success = masterFolder.mkdirs();
      if (!success) {
        System.out.println("Couldn't create " + masterFolder.getName() + ", try again");
        logger.error("Couldn't create " + masterFolder.getName());
        return false;
      } else {
        System.out.println(masterFolder.getName() + " created ");
        return true;
      }

    }
    else {
      System.out.println("Sorry, I didn't catch that. Please answer y/n");
      return createMasterFolder(masterFolder);
    }
  }



}
