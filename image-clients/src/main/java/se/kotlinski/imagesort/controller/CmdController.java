package se.kotlinski.imagesort.controller;

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
public class CmdController {
	private FolderIO folderIO;
	private ImageMapper imageMapper;

	private static void createMasterFolder(final File masterFolder) {
		System.out.println("Do you want to create " + masterFolder + "[y/n]");
		Scanner in = new Scanner(System.in);
		String answer = in.nextLine().trim().toLowerCase();
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
		CommandLine cmd = CommandLineUtil.intepreterArgs(argv);
		try {
			try {
				folderIO = CommandLineUtil.runCmd(CommandLineUtil.getOptions(), cmd);
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
    FileIndexer fileIndexer = new FileIndexer(folderIO);
    try {
      imageMapper = fileIndexer.runIndexing();
      FileExecutor copyFiles = new FileExecutor();
      FileCopyReport fileCopyReport = copyFiles.copyFiles(imageMapper, folderIO);

      System.out.println(fileCopyReport);
    }
    catch (InvalidInputFolders invalidInputFolders) {
      invalidInputFolders.printStackTrace();
    }
    //System.out.println(imageMapper);
	}
}
