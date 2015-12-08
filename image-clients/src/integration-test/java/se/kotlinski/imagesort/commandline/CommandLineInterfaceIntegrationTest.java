package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.DeprecatedExportCollector;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CommandLineInterfaceIntegrationTest {
  private CommandLineInterface commandLineInterface;
  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;
  private Interpreter interpreter;
  FilePrinter filePrinter;

  @Before
  public void setUp() throws Exception {
    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);
    Calendar calendar = new GregorianCalendar();
    MD5Generator MD5Generator = new MD5Generator();

    MediaFileParser mediaFileParser = spy(new MediaFileParser(mediaFileUtil, MD5Generator));
    filePrinter = spy(new FilePrinter());
    DeprecatedExportCollector deprecatedExportCollector = mock(DeprecatedExportCollector.class);

    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new GnuParser();
    MediaFileUtil fileUtil = new MediaFileUtil();
    Transformer transformer = new Transformer(formatter, parser, fileUtil);
    interpreter = spy(new Interpreter(transformer));
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(new GregorianCalendar());
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    FileSystemPrettyPrinter fileSystemPrettyPrinter = new FileSystemPrettyPrinter();

    commandLineInterface = new CommandLineInterface(mediaFileParser,
                                                    filePrinter,
                                                    deprecatedExportCollector,
                                                    interpreter,
                                                    dateToFileRenamer,
                                                    fileDateInterpreter,
                                                    fileSystemPrettyPrinter);
  }

  @Test
  public void testRunCommandLine() throws Exception {
    String[] arguments = new String[]{"-s", mediaFileTestUtil.getTestInputPath()};

    commandLineInterface.runCommandLine(arguments);
  }
}