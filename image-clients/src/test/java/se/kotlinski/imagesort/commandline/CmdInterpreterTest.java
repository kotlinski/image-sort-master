package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidFolderArgumentsException;
import se.kotlinski.imagesort.exception.InvalidMasterFolderException;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CmdInterpreterTest {

  private CmdInterpreter cmdInterpreter;
  private SortMasterFileUtil sortMasterFileUtil;

  @Before
  public void setUp() throws Exception {

    //Mock this up instead of running the real thing?
    sortMasterFileUtil = new SortMasterFileUtil();
    CommandLineParser parser = new GnuParser();
    HelpFormatter formatter = mock(HelpFormatter.class);
    CmdUtils cmdUtils = new CmdUtils(formatter, parser, sortMasterFileUtil);

    ScannerWrapper inScanner = mock(ScannerWrapper.class);
    when(inScanner.nextLine()).thenReturn("y");

    cmdInterpreter = new CmdInterpreter(cmdUtils, inScanner, sortMasterFileUtil);
  }

  @Test
  public void testGetFolderIO() throws Exception {
    String[] arguments = new String[]{"programName", "someCommand", "-s", sortMasterFileUtil
        .getTestInputPath(), "-o", sortMasterFileUtil.getTestOutputPath()};
    FolderIO folderIO = cmdInterpreter.getFolderIO(arguments);

    assertThat(folderIO, is(notNullValue()));
    assertNotNull(folderIO.inputFolders);
    assertNotNull(folderIO.masterFolder);
  }

  @Test (expected = InvalidMasterFolderException.class)
  public void testGetFolderOutputInvalid() throws Exception {
    String[] arguments = new String[]{"programName", "someCommand", "-s", sortMasterFileUtil
        .getTestInputPath(), "-o", "invalidpath"};
    cmdInterpreter.getFolderIO(arguments);
  }

  @Test (expected = InvalidFolderArgumentsException.class)
  public void testGetFolderInputInvalid() throws Exception {
    String[] arguments = new String[]{"programName", "someCommand", "-s", "", "-o", sortMasterFileUtil
        .getTestOutputPath()};
    cmdInterpreter.getFolderIO(arguments);
  }

  @Test (expected = InvalidFolderArgumentsException.class)
  public void testGetFolderInputNull() throws Exception {
    String[] arguments = new String[]{"programName", "someCommand", "-s", null, "-o",
                                      sortMasterFileUtil.getTestOutputPath()};
    cmdInterpreter.getFolderIO(arguments);
  }

  @Test
  public void testMakeMasterFolder() throws Exception {
    boolean masterFolderCreated = cmdInterpreter.createMasterFolder(null);
    assertThat(masterFolderCreated, is(false));
  }
}