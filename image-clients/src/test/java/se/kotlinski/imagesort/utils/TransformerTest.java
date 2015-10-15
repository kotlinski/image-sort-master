package se.kotlinski.imagesort.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.model.SortSettings;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;


public class TransformerTest {

  private Transformer transformer;
  private HelpFormatter formatter;
  private SortMasterFileUtil sortMasterFileUtil;

  @Before
  public void setUp() throws Exception {
    sortMasterFileUtil = spy(new SortMasterFileUtil());
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

  @Test
  public void testParseArgsHelp() throws Exception {
    String[] args = {"-h"};
    CommandLine cmd = transformer.parseArgs(args);
    Option[] options = cmd.getOptions();
    assertThat(options.length, equalTo(1));

    args = new String[]{"--help"};
    cmd = transformer.parseArgs(args);
    options = cmd.getOptions();
    assertThat(options.length, equalTo(1));
  }

  @Test
  public void testParseArgsSource() throws Exception {
    String[] args = {"-s", "sourceValue"};
    CommandLine cmd = transformer.parseArgs(args);
    Option[] options = cmd.getOptions();
    assertThat(options.length, equalTo(1));
    assertThat(options[0].getOpt(), equalTo("s"));
    assertThat(options[0].getValue(), equalTo("sourceValue"));
  }

  @Test (expected = Exception.class)
  public void testParseArgsInvalid() throws Exception {
    String[] args = {"-k", "sourceValue"};
    transformer.parseArgs(args);
  }

  @Test
  public void testGetOptions() throws Exception {
    Options options = transformer.getOptions();
    Collection optionsCollection = options.getOptions();
    assertThat(optionsCollection.size(), equalTo(2));
  }

  @Test (expected = Exception.class)
  public void testTransformCommandLineArgumentsWithNoValues() throws Exception {
    //With no arguments,
    //Should print help and not return any sortSettings
    String[] args = {};
    Options options = transformer.getOptions();
    CommandLine commandLine = transformer.parseArgs(args);
    SortSettings sortSettings = transformer.transformCommandLineArguments(options, commandLine);
    verify(transformer).printHelp(options);
    assertThat(sortSettings, is(notNullValue()));
  }

  @Test (expected = Exception.class)
  public void testTransformCommandLineArgumentsWithHelpArgument() throws Exception {
    //With help argument,
    //Should print help and not return any sortSettings
    String[] args = {"-h"};
    Options options = transformer.getOptions();
    CommandLine commandLine = transformer.parseArgs(args);
    SortSettings sortSettings = transformer.transformCommandLineArguments(options, commandLine);
    verify(transformer).printHelp(options);
    assertThat(sortSettings, is(notNullValue()));
  }

  @Test (expected = Exception.class)
  public void testTransformCommandLineArgumentsWithSourceArguments() throws Exception {
    //With empty source argument,
    //Should not pass parsing
    String[] args = {"-s"};
    Options options = transformer.getOptions();
    CommandLine commandLine = transformer.parseArgs(args);
    transformer.transformCommandLineArguments(options, commandLine);
  }

  @Test
  public void testTransformCommandLineArgumentsWithValidSourceArguments() throws Exception {
    //With empty source argument,
    //Should pass parsing
    String[] args = {"-s", "validPath"};
    Options options = transformer.getOptions();
    CommandLine commandLine = transformer.parseArgs(args);
    when(sortMasterFileUtil.isValidFolder(any())).thenReturn(true);

    SortSettings sortSettings = transformer.transformCommandLineArguments(options, commandLine);
    assertThat(sortSettings.masterFolder.getName(), is("validPath"));
  }
}