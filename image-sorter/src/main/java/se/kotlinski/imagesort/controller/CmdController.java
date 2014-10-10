package se.kotlinski.imagesort.controller;

import org.apache.commons.cli.CommandLine;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.CommandLineUtil;
import se.kotlinski.imagesort.utils.ImageIndex;

/**
 * Run the Image Renamer via command-line.
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class CmdController {
	private FolderIO folderIO;
	private Object imageMapper;

	public CmdController() {
	}
	public Object getImageMapper() {
		return imageMapper;
	}

	public FolderIO getFolderIO() {
		return folderIO;
	}

	public void startCmd(String[] argv) {
		CommandLine cmd = CommandLineUtil.intepreterArgs(argv);
		folderIO = CommandLineUtil.runCmd(CommandLineUtil.getOptions(), cmd);

		ImageIndex imageIndex = new ImageIndex(folderIO);

		System.out.println(folderIO);
		imageMapper = imageIndex.runIndexing();
		//System.out.println(imageMapper);
	}
}