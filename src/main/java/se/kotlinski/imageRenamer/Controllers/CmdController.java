package se.kotlinski.imageRenamer.Controllers;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import se.kotlinski.imageRenamer.mappers.ImageMapper;
import se.kotlinski.imageRenamer.models.FolderIO;
import se.kotlinski.imageRenamer.utils.CommandLineUtil;
import se.kotlinski.imageRenamer.utils.ImageIndex;

import java.io.File;

/**
 * Run the Image Renamer via command-line.
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class CmdController {
	private FolderIO folderIO;
	private ImageIndex imageIndex;
	private Object imageMapper;

	public CmdController() {
		imageIndex = new ImageIndex();
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

		System.out.println(folderIO);
		imageMapper = imageIndex.runIndexing(folderIO);
		System.out.println(imageMapper);
	}
}
