package se.kotlinski.imageRenamer.Controllers;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import se.kotlinski.imageRenamer.models.FolderIO;
import se.kotlinski.imageRenamer.utils.CommandLineUtil;

import java.io.File;

/**
 * Run the Image Renamer via command-line.
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class CmdController {
	private FolderIO folderIO;

	public CmdController() {
	}


	public FolderIO getFolderIO() {
		return folderIO;
	}

	public void startCmd(String[] argv) {
		CommandLine cmd = CommandLineUtil.intepreterArgs(argv);
		folderIO = CommandLineUtil.runCmd(CommandLineUtil.getOptions(), cmd);

		System.out.println(folderIO);
	}


}
