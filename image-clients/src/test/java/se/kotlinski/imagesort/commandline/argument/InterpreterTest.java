package se.kotlinski.imagesort.commandline.argument;

import org.apache.commons.cli.CommandLine;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.SortSettings;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class InterpreterTest {

  private Interpreter cmdInterpreter;
  private Transformer transformer;

  @Before
  public void setUp() throws Exception {
    transformer = mock(Transformer.class);
    cmdInterpreter = new Interpreter(transformer);
  }

  @Test (expected = Exception.class)
  public void testTransformArgumentsWhenParsingArgsFails() throws Exception {
    when(transformer.parseArgs(any())).thenThrow(new Exception());
    String[] arguments = new String[]{"programName", "someCommand", "-s", ""};
    cmdInterpreter.transformArguments(arguments);
  }

  @Test (expected = Exception.class)
  public void testTransformArgumentsWithTransfomerThrowing() throws Exception {
    CommandLine commandLine = mock(CommandLine.class);
    when(transformer.parseArgs(any())).thenReturn(commandLine);
    when(transformer.transformCommandLineArguments(commandLine)).thenThrow(new Exception());
    String[] arguments = new String[]{"programName", "someCommand", "-s", null};
    cmdInterpreter.transformArguments(arguments);
  }

  @Test
  public void testTransformArgumentsSunshineScenario() throws Exception {
    CommandLine commandLine = mock(CommandLine.class);
    SortSettings sortSettings = mock(SortSettings.class);
    when(transformer.parseArgs(any())).thenReturn(commandLine);
    when(transformer.transformCommandLineArguments(commandLine)).thenReturn(sortSettings);
    SortSettings sortSettingsResponse = cmdInterpreter.transformArguments(any());
    assertThat(sortSettings, is(sortSettingsResponse));
  }

}