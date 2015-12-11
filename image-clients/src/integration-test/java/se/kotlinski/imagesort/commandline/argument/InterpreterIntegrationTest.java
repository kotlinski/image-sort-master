package se.kotlinski.imagesort.commandline.argument;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.spy;

public class InterpreterIntegrationTest {

	private Interpreter cmdInterpreter;
  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;

	@Before
	public void setUp() {
    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

		CommandLineParser parser = spy(new GnuParser());
		HelpFormatter formatter = new HelpFormatter();
		Transformer transformer = new Transformer(formatter, parser, mediaFileUtil);

		cmdInterpreter = new Interpreter(transformer);
	}

	@Test
	public void testTransformArgumentsSunchineScenario() throws Exception {
		String testInputPath = mediaFileTestUtil.getTestInputPath();
		String[] arguments = new String[]{"-s", testInputPath};
    SortSettings sortSettings = cmdInterpreter.transformArguments(arguments);
    assertThat(sortSettings.masterFolder.toString(), is(testInputPath));
  }

  @Test (expected = Exception.class)
  public void testTransformArgumentsWithInvalidArguments() throws Exception {
    String testInputPath = mediaFileTestUtil.getTestInputPath();
    String[] arguments = new String[]{"-asdf", "-s", testInputPath};
    cmdInterpreter.transformArguments(arguments);
  }

  @Test (expected = Exception.class)
  public void testTransformArgumentsWithInvalidPath() throws Exception {
    String[] arguments = new String[]{"-s", "invalid path"};
    cmdInterpreter.transformArguments(arguments);
  }

}