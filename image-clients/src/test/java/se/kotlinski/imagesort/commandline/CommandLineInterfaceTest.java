package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.executor.FileMover;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.resolver.OutputConflictResolver;
import se.kotlinski.imagesort.transformer.MediaFileTransformer;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.FileDescriptor;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CommandLineInterfaceTest {
  private MediaFileUtil mediaFileUtil;
  private Interpreter interpreter;

  @Before
  public void setUp() throws Exception {
    mediaFileUtil = new MediaFileUtil();
    Calendar calendar = new GregorianCalendar();
    MD5Generator MD5Generator = new MD5Generator();

    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();

    MediaFileParser mediaFileParser = spy(new MediaFileParser(mediaFileUtil, MD5Generator));
    FilePrinter filePrinter = spy(new FilePrinter());


    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new GnuParser();
    MediaFileUtil fileUtil = new MediaFileUtil();
    Transformer transformer = new Transformer(formatter, parser, fileUtil);
    interpreter = spy(new Interpreter(transformer));

    new CommandLineInterface(mediaFileParser,
                             filePrinter,
                             interpreter,
                             new DateToFileRenamer(calendar),
                             fileDateInterpreter,
                             new FileSystemPrettyPrinter(),
                             new OutputConflictResolver(new MD5Generator(), mediaFileUtil),
                             new FileMover(mediaFileUtil));
  }

  @Test
  public void testStartCmd() throws Exception {


//    Mockito.verify(filePrinter).printFolderStructure(any(DeprecatedExportFileDataMap.class));
  }

}