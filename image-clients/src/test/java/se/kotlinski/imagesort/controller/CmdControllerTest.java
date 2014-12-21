package se.kotlinski.imagesort.controller;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CmdControllerTest {
	CmdController cmdController;
  private ImageFileUtil imageFileUtil;

  @Before
	public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();
    FileExecutor fileExecutor = mock(FileExecutor.class);
    cmdController = new CmdController(fileExecutor);
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