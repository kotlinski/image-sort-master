package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.main.ClientInterface;
import se.kotlinski.imagesort.main.ImageSorter;

public class CommandLineInterface {
  private static final Logger LOGGER = LogManager.getLogger(CommandLineInterface.class);
  private final Interpreter interpreter;
  private final ImageSorter imageSorter;

  @Inject
  public CommandLineInterface(final Interpreter interpreter, final ImageSorter imageSorter) {
    this.interpreter = interpreter;
    this.imageSorter = imageSorter;
  }

  public final void runCommandLine(String[] arguments) {
    SortSettings sortSettings;

    try {
      sortSettings = interpreter.transformArguments(arguments);
    }
    catch (Exception e) {
      System.out.println("Check your parameters...");
      LOGGER.error("Invalid parameters");
      return;
    }

    ClientInterface clientInterface = new ImageSortProgressFeedback(new FileSystemPrettyPrinter());
    imageSorter.analyzeImages(clientInterface, sortSettings);

  }
}





