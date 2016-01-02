package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.executor.ClientInterface;
import se.kotlinski.imagesort.executor.FileMover;
import se.kotlinski.imagesort.forecaster.MediaFileForecaster;
import se.kotlinski.imagesort.forecaster.MediaFilesOutputForecaster;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.resolver.OutputConflictResolver;
import se.kotlinski.imagesort.transformer.MediaFileHashDataMapTransformer;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CommandLineInterfaceIntegrationTest {
  private CommandLineInterface commandLineInterface;
  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;
  private Interpreter interpreter;

  @Before
  public void setUp() throws Exception {
    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);
    MediaFileHashGenerator MediaFileHashGenerator = new MediaFileHashGenerator();

    MediaFileParser mediaFileParser = spy(new MediaFileParser(mediaFileUtil));

    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new GnuParser();
    MediaFileUtil fileUtil = new MediaFileUtil();
    Transformer transformer = new Transformer(formatter, parser, fileUtil);
    interpreter = spy(new Interpreter(transformer));
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(new GregorianCalendar());
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();

    OutputConflictResolver outputConflictResolver = new OutputConflictResolver(new MediaFileHashGenerator(),
                                                                               mediaFileUtil);
    FileMover fileMover = new FileMover(mediaFileUtil);
    ClientInterface clientInterface = mock(ClientInterface.class);

    MediaFileHashDataMapTransformer mediaFileHashMapTransformer = new MediaFileHashDataMapTransformer(MediaFileHashGenerator);
    MediaFileForecaster mediaFileForecaster = new MediaFileForecaster(dateToFileRenamer, fileDateInterpreter);
    MediaFilesOutputForecaster mediaOutputCalculator = new MediaFilesOutputForecaster(mediaFileForecaster);
    ImageSorter imageSorter = new ImageSorter(clientInterface,
                                              mediaFileParser,
                                              mediaFileHashMapTransformer,
                                              mediaOutputCalculator, outputConflictResolver,
                                              fileMover);

    commandLineInterface = new CommandLineInterface(interpreter, imageSorter);
  }

  @After
  public void tearDown() throws Exception {
    mediaFileTestUtil.cleanRestoreableMasterFolder();
  }

  @Test
  public void testRunCommandLine() throws Exception {
    mediaFileTestUtil.cleanRestoreableMasterFolder();
    mediaFileTestUtil.copyTestFilesToRestoreableDirectory();
    String[] arguments = new String[]{"-s", mediaFileTestUtil.getRestorableTestMasterPath()};

    commandLineInterface.runCommandLine(arguments);
  }
}