package se.kotlinski.imagesort.commandline;

import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.executor.FileMover;
import se.kotlinski.imagesort.forecaster.MediaFileOutputForecaster;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.mapper.MediaFileDataMapper;
import se.kotlinski.imagesort.mapper.MediaFileToOutputMapper;
import se.kotlinski.imagesort.mapper.OutputToMediaFileMapper;
import se.kotlinski.imagesort.resolver.ConflictResolver;
import se.kotlinski.imagesort.resolver.ExistingFilesResolver;
import se.kotlinski.imagesort.resolver.FileSkipper;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CommandLineInterfaceIntegrationTest {
  private CommandLineInterface commandLineInterface;
  private MediaFileTestUtil mediaFileTestUtil;

  @Before
  public void setUp() throws Exception {
    MediaFileUtil mediaFileUtil = spy(new MediaFileUtil());
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);
    MediaFileHashGenerator mediaFileHashGenerator = new MediaFileHashGenerator();

    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new GnuParser();
    MediaFileUtil fileUtil = new MediaFileUtil();
    Transformer transformer = new Transformer(formatter, parser, fileUtil);
    Interpreter interpreter = spy(new Interpreter(transformer));
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(new GregorianCalendar());
    MixpanelAPI mixpanel = mock(MixpanelAPI.class);

    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter(mixpanel, "", messageBuilder);

    MediaFileToOutputMapper mediaFileToOutputMapper;
    mediaFileToOutputMapper = new MediaFileToOutputMapper(mediaFileHashGenerator, mediaFileUtil);
    FileSkipper fileSkipper = new FileSkipper();
    ExistingFilesResolver existingFilesResolver = new ExistingFilesResolver(mediaFileUtil);
    ConflictResolver conflictResolver = new ConflictResolver(mediaFileToOutputMapper,
                                                             fileSkipper,
                                                             existingFilesResolver);
    FileMover fileMover = new FileMover();

    MediaFileDataMapper mediaFileDataMapper = new MediaFileDataMapper(mediaFileHashGenerator);
    MediaFileOutputForecaster mediaFileOutputForecaster;
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer,
                                                              fileDateInterpreter);
    OutputToMediaFileMapper outputToMediaFileMapper;
    outputToMediaFileMapper = new OutputToMediaFileMapper(mediaFileOutputForecaster);

    ImageSorter imageSorter = new ImageSorter(mediaFileUtil,
                                              outputToMediaFileMapper,
                                              mediaFileToOutputMapper,
                                              mediaFileDataMapper,
                                              conflictResolver,
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