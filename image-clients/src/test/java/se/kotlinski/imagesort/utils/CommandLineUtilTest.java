package se.kotlinski.imagesort.utils;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommandLineUtilTest {

  private CommandLineUtil commandLineUtil;
  private HelpFormatter formatter;

  @Before
  public void setUp() throws Exception {
    ImageFileUtil imageFileUtil = new ImageFileUtil();
    CommandLineParser parser = new GnuParser();
    formatter = mock(HelpFormatter.class);
    commandLineUtil = new CommandLineUtil(formatter, parser, imageFileUtil);
  }

  @Test
  public void testPrintHelp() throws Exception {
    Options options = mock(Options.class);
    commandLineUtil.printHelp(options);
    verify(formatter).printHelp("MainRenamer", options);
  }
}