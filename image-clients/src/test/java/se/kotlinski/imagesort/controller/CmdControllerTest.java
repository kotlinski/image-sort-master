package se.kotlinski.imagesort.controller;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.utils.CommandLineUtil;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class CmdControllerTest {
  private CmdController cmdController;
  private ImageFileUtil imageFileUtil;

  @Before
  public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();
    FileExecutor fileExecutor = mock(FileExecutor.class);
    CommandLineParser parser = new GnuParser();
    HelpFormatter formatter = mock(HelpFormatter.class);
    CommandLineUtil commandLineUtil = new CommandLineUtil(formatter, parser, imageFileUtil);
    FileIndexer fileIndexer = mock(FileIndexer.class);
    cmdController = new CmdController(fileExecutor, commandLineUtil, fileIndexer);
  }

  @Test
  public void testStartCmd() throws Exception {
    String[] arguments = new String[]{"programName", "someCommand", "-s", imageFileUtil
        .getTestInputPath(), "-o", imageFileUtil.getTestOutputPath()};
    cmdController.startCmd(arguments);
    assertNotNull(cmdController.getFolderIO());
    assertNotNull(cmdController.getFolderIO().inputFolders);
    assertNotNull(cmdController.getFolderIO().masterFolder);
    ImageMapper imageMapper = cmdController.getImageMapper();
    assertThat(imageMapper, is(nullValue()));
  }

}