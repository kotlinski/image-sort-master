package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.exception.InvalidFolderArgumentsException;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import static org.mockito.Mockito.mock;

public class CmdInterpreterIntegerTest {

	private Interpreter cmdInterpreter;
	private SortMasterFileUtil sortMasterFileUtil;

	@Before
	public void setUp() {

		//Mock this up instead of running the real thing?
		sortMasterFileUtil = new SortMasterFileUtil();
		CommandLineParser parser = new GnuParser();
		HelpFormatter formatter = mock(HelpFormatter.class);
		Transformer transformer = new Transformer(formatter, parser, sortMasterFileUtil);

		cmdInterpreter = new Interpreter(transformer);
	}


	@Test (expected = Exception.class)
	public void testGetFolderInputInvalidIntegration() throws Exception {
		String[] arguments = new String[]{"programName", "someCommand", "-s", ""};
		cmdInterpreter.getFolderIO(arguments);
	}

	@Test (expected = Exception.class)
	public void testGetFolderInputNull() throws Exception {
		String[] arguments = new String[]{"programName", "someCommand", "-s", null};
		cmdInterpreter.getFolderIO(arguments);
	}

}