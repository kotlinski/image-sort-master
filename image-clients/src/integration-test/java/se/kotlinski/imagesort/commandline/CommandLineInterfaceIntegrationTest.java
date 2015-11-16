package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.controller.ExportCollector;
import se.kotlinski.imagesort.controller.ExportForecaster;
import se.kotlinski.imagesort.controller.MediaFileParser;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateUniqueGenerator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CommandLineInterfaceIntegrationTest {
  private CommandLineInterface commandLineInterface;
  private SortMasterFileUtil sortMasterFileUtil;
  private Interpreter interpreter;
  FilePrinter filePrinter;

  @Before
  public void setUp() throws Exception {
    sortMasterFileUtil = new SortMasterFileUtil();
    Calendar calendar = new GregorianCalendar();
    FileDateUniqueGenerator fileDateUniqueGenerator = new FileDateUniqueGenerator();

    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    FileDescriptor fileDescriptor = new FileDescriptor();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    ExportForecaster exportForecaster = new ExportForecaster(dateToFileRenamer);
    MediaFileParser mediaFileParser = spy(new MediaFileParser(sortMasterFileUtil,
                                                              calendar,
                                                              fileDateUniqueGenerator,
                                                              fileDateInterpreter,
                                                              fileDescriptor,
                                                              dateToFileRenamer,
                                                              exportForecaster));
    filePrinter = spy(new FilePrinter());
    ExportCollector exportCollector = mock(ExportCollector.class);

    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new GnuParser();
    SortMasterFileUtil fileUtil = new SortMasterFileUtil();
    Transformer transformer = new Transformer(formatter, parser, fileUtil);
    interpreter = spy(new Interpreter(transformer));

    commandLineInterface = new CommandLineInterface(mediaFileParser,
                                                    filePrinter,
                                                    exportCollector,
                                                    interpreter);
  }

  @Test
  public void testRunCommandLine() throws Exception {
    String[] arguments = new String[]{
        "-s",
        sortMasterFileUtil.getTestInputPath()
    };

    commandLineInterface.runCommandLine(arguments);

    verify(filePrinter).printTotalNumberOfDuplicates(0,8,0);

  }
}