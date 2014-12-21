package se.kotlinski.imagesort.controller;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.CommandLineUtil;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CmdControllerTest {
	CmdController cmdController;
  private ImageFileUtil imageFileUtil;

  @Before
	public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();
    FileExecutor fileExecutor = mock(FileExecutor.class);
    CommandLineParser parser = new GnuParser();
    CommandLineUtil commandLineUtil = new CommandLineUtil(parser);
    FileIndexer fileIndexer = mock(FileIndexer.class);
    cmdController = new CmdController(fileExecutor, commandLineUtil, fileIndexer);
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