package se.kotlinski.imagesort.controller;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.CommandLineInterface;
import se.kotlinski.imagesort.commandline.FilePrinter;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateUniqueGenerator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CommandLineInterfaceTest {
  private CommandLineInterface commandLineInterface;
  private SortMasterFileUtil sortMasterFileUtil;
  private Interpreter interpreter;

  @Before
  public void setUp() throws Exception {
    sortMasterFileUtil = new SortMasterFileUtil();
    Calendar calendar = new GregorianCalendar();
    FileDateUniqueGenerator fileDateUniqueGenerator = new FileDateUniqueGenerator();

    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    FileDescriptor fileDescriptor = new FileDescriptor();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    ExportForecaster exportForecaster = new ExportForecaster(dateToFileRenamer);
    FileAnalyzer fileAnalyzer = spy(new FileAnalyzer(sortMasterFileUtil,
                                                     calendar,
                                                     fileDateUniqueGenerator,
                                                     fileDateInterpreter,
                                                     fileDescriptor,
                                                     dateToFileRenamer, exportForecaster));
    FilePrinter filePrinter = spy(new FilePrinter());
    ExportCollector exportCollector = mock(ExportCollector.class);
    interpreter = mock(Interpreter.class);

    commandLineInterface = new CommandLineInterface(fileAnalyzer, filePrinter,
                                                    exportCollector, interpreter);
  }

  @Test
  public void testStartCmd() throws Exception {
    String[] arguments = new String[]{"programName", "someCommand", "-s", sortMasterFileUtil
        .getTestInputPath(), "-o", sortMasterFileUtil.getTestOutputPath()};

    commandLineInterface.runCommandLine(arguments);
    Mockito.verify(interpreter).transformArguments(arguments);
//    Mockito.verify(filePrinter).printFolderStructure(any(ExportFileDataMap.class));
  }

}