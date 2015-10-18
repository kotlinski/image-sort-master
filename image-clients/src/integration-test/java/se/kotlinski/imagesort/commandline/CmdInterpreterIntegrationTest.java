package se.kotlinski.imagesort.commandline;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CmdInterpreterIntegrationTest {

	private Interpreter cmdInterpreter;
	private SortMasterFileUtil sortMasterFileUtil;

	@Before
	public void setUp() {
		sortMasterFileUtil = new SortMasterFileUtil();
		CommandLineParser parser = spy(new GnuParser());
		HelpFormatter formatter = mock(HelpFormatter.class);
		Transformer transformer = new Transformer(formatter, parser, sortMasterFileUtil);

		cmdInterpreter = new Interpreter(transformer);
	}


	@Test (expected = Exception.class)
	public void testGetFolderInputInvalidIntegration() throws Exception {
		String[] arguments = new String[]{"programName", "someCommand", "-s", ""};
		cmdInterpreter.getSortSettings(arguments);
	}

	@Test (expected = Exception.class)
	public void testGetFolderInputNull() throws Exception {
		String[] arguments = new String[]{"programName", "someCommand", "-s", null};
		cmdInterpreter.getSortSettings(arguments);
	}

	@Test
	public void testGetFilesFromResources() throws Exception {
		String testIntputPath = sortMasterFileUtil.getTestInputPath();
		String[] arguments = new String[]{"programName", "someCommand", "-s", testIntputPath};

		SortSettings sortSettings = cmdInterpreter.getSortSettings(arguments);
		File masterFolder = sortSettings.masterFolder;
		assertThat(masterFolder.toString(), is(testIntputPath));
		assertThat(masterFolder.isDirectory(), is(true));
	}

}