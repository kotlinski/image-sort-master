package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.exception.InvalidFolderArgumentsException;
import se.kotlinski.imagesort.exception.InvalidMasterFolderException;
import se.kotlinski.imagesort.model.SortSettings;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CmdInterpreterTest {

  private Interpreter cmdInterpreter;
  private SortMasterFileUtil sortMasterFileUtil;

  @Before
  public void setUp() throws Exception {

    //Mock this up instead of running the real thing?
    sortMasterFileUtil = new SortMasterFileUtil();
    CommandLineParser parser = new GnuParser();
    HelpFormatter formatter = mock(HelpFormatter.class);
    Transformer transformer = new Transformer(formatter, parser, sortMasterFileUtil);

    cmdInterpreter = new Interpreter(transformer);
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

}