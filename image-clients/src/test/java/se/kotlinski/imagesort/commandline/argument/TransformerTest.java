package se.kotlinski.imagesort.commandline.argument;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;


public class TransformerTest {

  private Transformer transformer;
  private HelpFormatter formatter;
  private SortMasterFileUtil sortMasterFileUtil;
  private CommandLineParser parser;

  @Before
  public void setUp() throws Exception {
    sortMasterFileUtil = spy(new SortMasterFileUtil());
    parser = mock(GnuParser.class);
    formatter = spy(new HelpFormatter());
    transformer = spy(new Transformer(formatter, parser, sortMasterFileUtil));
  }

  @Test
  public void testParseWithValidArgs() throws Exception {
    CommandLine cmd = mock(CommandLine.class);
    when(parser.parse(any(), any())).thenReturn(cmd);
    String[] args = {};
    CommandLine commandLine = transformer.parseArgs(args);
    assertThat(commandLine, is(cmd));
  }

  @Test (expected = Exception.class)
  public void testParseWithInvalidArgs() throws Exception {
    when(parser.parse(any(), any())).thenThrow(new Exception());
    String[] args = {};
    transformer.parseArgs(args);
  }

  @Test
  public void testPrintHelp() throws Exception {
    Options options = spy(new Options());
    transformer.printHelp(options);
    verify(formatter).printHelp(eq("MainRenamer"), eq(options));
  }

  @Test
  public void testGetOptions() throws Exception {
    Options options = transformer.getOptions();
    Collection optionsCollection = options.getOptions();
    assertThat(optionsCollection.size(), equalTo(2));
  }

}