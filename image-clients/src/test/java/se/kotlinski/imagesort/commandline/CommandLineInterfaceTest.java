package se.kotlinski.imagesort.commandline;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.argument.Transformer;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CommandLineInterfaceTest {

  @Before
  public void setUp() throws Exception {

    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new GnuParser();
    MediaFileUtil fileUtil = new MediaFileUtil();
    Transformer transformer = new Transformer(formatter, parser, fileUtil);
    Interpreter interpreter = spy(new Interpreter(transformer));

    ImageSorter imageSorter = mock(ImageSorter.class);


    new CommandLineInterface(interpreter, imageSorter);
  }

  @Test
  public void testStartCmd() throws Exception {


//    Mockito.verify(filePrinter).printFolderStructure(any(DeprecatedExportFileDataMap.class));
  }

}