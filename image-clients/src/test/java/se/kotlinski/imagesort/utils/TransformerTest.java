package se.kotlinski.imagesort.utils;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Transformer;

import static org.mockito.Mockito.*;

public class TransformerTest {

  private Transformer transformer;
  private HelpFormatter formatter;

  @Before
  public void setUp() throws Exception {
    SortMasterFileUtil sortMasterFileUtil = new SortMasterFileUtil();
    CommandLineParser parser = new GnuParser();
    formatter = spy(new HelpFormatter());
    transformer = spy(new Transformer(formatter, parser, sortMasterFileUtil));
  }

  @Test
  public void testPrintHelp() throws Exception {
    Options options = spy(new Options());
    transformer.printHelp(options);
    verify(formatter).printHelp(eq("MainRenamer"), eq(options));
  }

}