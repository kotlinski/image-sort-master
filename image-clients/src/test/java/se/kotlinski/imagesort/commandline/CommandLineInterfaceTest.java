package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.ExportCollector;
import se.kotlinski.imagesort.ExportForecaster;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.transformer.MediaFileTransformer;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CommandLineInterfaceTest {
  private CommandLineInterface commandLineInterface;
  private MediaFileUtil mediaFileUtil;
  private Interpreter interpreter;

  @Before
  public void setUp() throws Exception {
    mediaFileUtil = new MediaFileUtil();
    Calendar calendar = new GregorianCalendar();
    MD5Generator MD5Generator = new MD5Generator();

    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    FileDescriptor fileDescriptor = new FileDescriptor();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    ExportForecaster exportForecaster = new ExportForecaster(dateToFileRenamer);
    MediaFileTransformer mediaFileTransformer = new MediaFileTransformer();
    MediaFileParser mediaFileParser = spy(new MediaFileParser(mediaFileUtil,
                                                              calendar,
                                                              MD5Generator,
                                                              fileDateInterpreter,
                                                              fileDescriptor,
                                                              dateToFileRenamer,
                                                              exportForecaster,
                                                              mediaFileTransformer));
    FilePrinter filePrinter = spy(new FilePrinter());
    ExportCollector exportCollector = mock(ExportCollector.class);

    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new GnuParser();
    MediaFileUtil fileUtil = new MediaFileUtil();
    Transformer transformer = new Transformer(formatter, parser, fileUtil);
    interpreter = spy(new Interpreter(transformer));

    new CommandLineInterface(mediaFileParser,
                             filePrinter,
                             exportCollector,
                             interpreter);
  }

  @Test
  public void testStartCmd() throws Exception {


//    Mockito.verify(filePrinter).printFolderStructure(any(ExportFileDataMap.class));
  }

}