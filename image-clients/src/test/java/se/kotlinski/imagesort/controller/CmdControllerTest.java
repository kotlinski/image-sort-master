package se.kotlinski.imagesort.controller;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import static org.junit.Assert.*;

public class CmdControllerTest {
	CmdController cmdController;
  private ImageFileUtil imageFileUtil;

  @Before
	public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();
    cmdController = new CmdController();
  }

	@Test
	public void testStartCmd() throws Exception {
		String[] argv = new String[]{"programname",
				"somecommand", "-s",
        imageFileUtil.getTestInputPath(),
				"-o",
        imageFileUtil.getTestOutputPath()};
		cmdController.startCmd(argv);
		assertNotNull(cmdController.getFolderIO());
		assertNotNull(cmdController.getFolderIO().inputFolders);
		assertNotNull(cmdController.getFolderIO().masterFolder);
		cmdController.getImageMapper();
	}
}