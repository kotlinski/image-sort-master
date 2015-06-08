package se.kotlinski.imagesort.controller;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import se.kotlinski.imagesort.commandline.CommandLineArgumentsInterpreter;
import se.kotlinski.imagesort.commandline.CmdController;
import se.kotlinski.imagesort.commandline.FilePrinter;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateUniqueGenerator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CmdControllerTest {
  private CmdController cmdController;
  private SortMasterFileUtil sortMasterFileUtil;
  private FilePrinter filePrinter;
  private ExportCollector exportCollector;
  private CommandLineArgumentsInterpreter commandLineArgumentsInterpreter;

  @Before
  public void setUp() throws Exception {
    sortMasterFileUtil = new SortMasterFileUtil();
    FileExecutor fileExecutor = mock(FileExecutor.class);
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
    filePrinter = spy(new FilePrinter());
    exportCollector = mock(ExportCollector.class);
    commandLineArgumentsInterpreter = mock(CommandLineArgumentsInterpreter.class);

    cmdController = new CmdController(fileAnalyzer,
                                      filePrinter,
                                      exportCollector, commandLineArgumentsInterpreter);
  }

  @Test
  public void testStartCmd() throws Exception {
    String[] arguments = new String[]{"programName", "someCommand", "-s", sortMasterFileUtil
        .getTestInputPath(), "-o", sortMasterFileUtil.getTestOutputPath()};

    cmdController.startCmd(arguments);
    Mockito.verify(commandLineArgumentsInterpreter).transformArguments(arguments);
//    Mockito.verify(filePrinter).printFolderStructure(any(ExportFileDataMap.class));
  }

}