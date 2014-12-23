package se.kotlinski.imagesort.controller;

import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.exception.NoInputFolderException;
import se.kotlinski.imagesort.exception.NoMasterFolderException;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.CommandLineUtil;

import java.io.File;
import java.util.Scanner;

public class CmdController implements IController {
  private static final Logger logger = LogManager.getLogger(CmdController.class);
  private final FileExecutor fileExecutor;
  private final Scanner inScanner;
  private final CommandLineUtil commandLineUtil;
  private final FileIndexer fileIndexer;
  private FolderIO folderIO;
  private ImageMapper imageMapper;


  @Inject
  public CmdController(final FileExecutor fileExecutor,
                       final CommandLineUtil commandLineUtil,
                       final FileIndexer fileIndexer) {
    this.fileExecutor = fileExecutor;
    this.commandLineUtil = commandLineUtil;
    this.fileIndexer = fileIndexer;
    inScanner = new Scanner(System.in);
  }

  private void createMasterFolder(final File masterFolder) {
    System.out.println("Do you want to create " + masterFolder + "[y/n]");

    String answer = inScanner.nextLine().trim().toLowerCase();
    if ("y".equals(answer)) {
      System.out.println("Creating " + masterFolder.getName() + "...");
      logger.info("Creating " + masterFolder.getName() + "...");

      boolean success = masterFolder.mkdirs();
      if (!success) {
        System.out.println("Couldn't create " + masterFolder.getName() + ", try again");
        logger.error("Couldn't create " + masterFolder.getName());
      }
    }
    else {
      System.out.println("Sorry, I didn't catch that. Please answer y/n");
      createMasterFolder(masterFolder);
    }
  }

  public ImageMapper getImageMapper() {
    return imageMapper;
  }

  public FolderIO getFolderIO() {
    return folderIO;
  }

  public void startCmd(String[] arguments) {
    CommandLine commandLine = commandLineUtil.interpreterArgs(arguments);
    try {
      try {
        folderIO = commandLineUtil.runCmd(commandLineUtil.getOptions(), commandLine);
      }
      catch (NoInputFolderException e) {
        System.out.println("No input folder found, try again");
        logger.error("No input folder found, try again", e);
      }
    }
    catch (NoMasterFolderException e) {
      createMasterFolder(e.getMasterFolder());
    }
    System.out.println(folderIO.toString());
    try {
      imageMapper = fileIndexer.runIndexing(folderIO);
      fileExecutor.copyFiles(imageMapper, folderIO);
    }
    catch (InvalidInputFolders invalidInputFolders) {
      System.out.print("Invalid input folders, try again");
      logger.error("Invalid input folders, try again", invalidInputFolders);
    }
  }
}
