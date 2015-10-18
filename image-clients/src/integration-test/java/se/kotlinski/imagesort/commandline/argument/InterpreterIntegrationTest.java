package se.kotlinski.imagesort.commandline.argument;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.model.SortSettings;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class InterpreterIntegrationTest {

	private Interpreter cmdInterpreter;
	private SortMasterFileUtil sortMasterFileUtil;

	@Before
	public void setUp() {
		sortMasterFileUtil = new SortMasterFileUtil();
		CommandLineParser parser = spy(new GnuParser());
		HelpFormatter formatter = new HelpFormatter();
		Transformer transformer = new Transformer(formatter, parser, sortMasterFileUtil);

		cmdInterpreter = new Interpreter(transformer);
	}

	@Test
	public void testTransformArgumentsSunchineScenario() throws Exception {
		String testInputPath = sortMasterFileUtil.getTestInputPath();
		String[] arguments = new String[]{"-s", testInputPath};
    SortSettings sortSettings = cmdInterpreter.transformArguments(arguments);
    assertThat(sortSettings.masterFolder.toString(), is(testInputPath));
  }

  @Test (expected = Exception.class)
  public void testTransformArgumentsWithInvalidArguments() throws Exception {
    String testInputPath = sortMasterFileUtil.getTestInputPath();
    String[] arguments = new String[]{"-asdf", "-s", testInputPath};
    cmdInterpreter.transformArguments(arguments);
  }

  @Test (expected = Exception.class)
  public void testTransformArgumentsWithInvalidPath() throws Exception {
    String[] arguments = new String[]{"-s", "invalid path"};
    cmdInterpreter.transformArguments(arguments);
  }

}