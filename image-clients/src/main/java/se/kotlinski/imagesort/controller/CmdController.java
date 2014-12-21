package se.kotlinski.imagesort.controller;

import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.exception.NoInputFolderException;
import se.kotlinski.imagesort.exception.NoMasterFolderException;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.CommandLineUtil;

import java.io.File;
import java.util.Scanner;

/**
 * Run the Image Renamer via command-line.
 *
 * @author Simon Kotlinski
 */
public class CmdController implements IController{
	private FolderIO folderIO;
	private ImageMapper imageMapper;
  private final FileExecutor fileExecutor;
  private final Scanner inScanner;
  private final CommandLineUtil commandLineUtil;
  private final FileIndexer fileIndexer;

  @Inject
  public CmdController(final FileExecutor fileExecutor,
                       final CommandLineUtil commandLineUtil,
                       final FileIndexer fileIndexer) {
    this.fileExecutor = fileExecutor;
    this.commandLineUtil = commandLineUtil;
    this.fileIndexer = fileIndexer;
    this.inScanner = new Scanner(System.in);
  }

  private void createMasterFolder(final File masterFolder) {
		System.out.println("Do you want to create " + masterFolder + "[y/n]");
		String answer = inScanner.nextLine().trim().toLowerCase();
		if ("y".equals(answer)) {
			System.out.println("Creating " + masterFolder.getName() + "...");
			boolean mkdirs = masterFolder.mkdirs();
			if (!mkdirs) {
				System.err.println("Couldn't create " + masterFolder.getName());
			}
		}
		else {
			System.out.println("Sorry, I didn't catch that. Please answer y/n");
			createMasterFolder(masterFolder);
		}
	}

	public Object getImageMapper() {
		return imageMapper;
	}

	public FolderIO getFolderIO() {
		return folderIO;
	}

	public void startCmd(String[] argv) {
    CommandLine commandLine = commandLineUtil.intepreterArgs(argv);
    try {
			try {
				folderIO = commandLineUtil.runCmd(commandLineUtil.getOptions(), commandLine);
			}
			catch (NoInputFolderException e) {
				e.printStackTrace();
			}
		}
		catch (NoMasterFolderException e) {
			createMasterFolder(e.getMasterFolder());
		}
		//Make static calls to an interface instead.
		System.out.println(folderIO);

    // We have created a folderIO
    // and now the image-sorter will do its magic.
    try {
      imageMapper = fileIndexer.runIndexing(folderIO);
      FileCopyReport fileCopyReport = fileExecutor.copyFiles(imageMapper, folderIO);

      System.out.println(fileCopyReport);
    }
    catch (InvalidInputFolders invalidInputFolders) {
      invalidInputFolders.printStackTrace();
    }
    //System.out.println(imageMapper);
	}
}
