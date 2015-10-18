package se.kotlinski.imagesort.commandline.argument;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidArgumentsException;
import se.kotlinski.imagesort.model.SortSettings;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


public class TransformerIntegrationTest {

  private Transformer transformer;
  private HelpFormatter formatter;
  private SortMasterFileUtil sortMasterFileUtil;

  @Before
  public void setUp() throws Exception {
    sortMasterFileUtil = new SortMasterFileUtil();
    CommandLineParser parser = new GnuParser();
    formatter = new HelpFormatter();
    transformer = new Transformer(formatter, parser, sortMasterFileUtil);
  }

  @Test
  public void testParseArgsHelp() throws Exception {
    String[] args = {"-h"};
    CommandLine cmd = transformer.parseArgs(args);
    Option[] options = cmd.getOptions();
    MatcherAssert.assertThat(options.length, CoreMatchers.equalTo(1));

    args = new String[]{"--help"};
    cmd = transformer.parseArgs(args);
    options = cmd.getOptions();
    MatcherAssert.assertThat(options.length, CoreMatchers.equalTo(1));
  }

  @Test (expected = Exception.class)
  public void testParseArgsInvalid() throws Exception {
    String[] args = {"-k", "sourceValue"};
    transformer.parseArgs(args);
  }

  @Test
  public void testParseArgsSource() throws Exception {
    String testInputPath = sortMasterFileUtil.getTestInputPath();

    String[] args = {"-s", testInputPath};
    CommandLine cmd = transformer.parseArgs(args);

    Option[] options = cmd.getOptions();
    MatcherAssert.assertThat(options.length, CoreMatchers.equalTo(1));
    MatcherAssert.assertThat(options[0].getOpt(), CoreMatchers.equalTo("s"));
    MatcherAssert.assertThat(options[0].getValue(), CoreMatchers.equalTo(testInputPath));
  }

  @Test (expected = Exception.class)
  public void testTransformCommandLineArgumentsWithNoValues() throws Exception {
    String[] args = {};
    Options options = transformer.getOptions();
    CommandLine commandLine = transformer.parseArgs(args);
    SortSettings sortSettings = transformer.transformCommandLineArguments(commandLine);
    verify(transformer).printHelp(options);
    assertThat(sortSettings, is(notNullValue()));
  }

  @Test (expected = InvalidArgumentsException.class)
  public void testTransformCommandLineArgumentsWithHelpArgument() throws Exception {
    String[] args = {"-h"};
    Options options = transformer.getOptions();
    CommandLine commandLine = transformer.parseArgs(args);
    SortSettings sortSettings = transformer.transformCommandLineArguments(commandLine);
    verify(transformer).printHelp(options);
    assertThat(sortSettings, is(notNullValue()));
  }

  @Test (expected = Exception.class)
  public void testTransformCommandLineArgumentsWithSourceArguments() throws Exception {
    String[] args = {"-s"};
    CommandLine commandLine = transformer.parseArgs(args);
    transformer.transformCommandLineArguments(commandLine);
  }

}