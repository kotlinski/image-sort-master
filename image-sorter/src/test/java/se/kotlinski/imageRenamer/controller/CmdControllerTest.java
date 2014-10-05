package se.kotlinski.imageRenamer.controller;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imageRenamer.utils.Constants;

import static org.junit.Assert.*;

public class CmdControllerTest {
	CmdController cmdController;

	@Before
	public void setUp() throws Exception {
		cmdController = new CmdController();

	}

	@Test
	public void testStartCmd() throws Exception {
		String[] argv = new String[]{"programname", "somecommand", "-s", Constants.PATH_INPUT_TEST, "-o", Constants.PATH_OUTPUT_TEST};
		cmdController.startCmd(argv);
		assertNotNull(cmdController.getFolderIO());
		assertNotNull(cmdController.getFolderIO().inputFolders);
		assertNotNull(cmdController.getFolderIO().masterFolder);
		cmdController.getImageMapper();
	}
}