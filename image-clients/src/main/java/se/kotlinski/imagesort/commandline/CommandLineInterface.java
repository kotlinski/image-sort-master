package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.commandline.listeners.ImageSortPreMoveProgressFeedback;
import se.kotlinski.imagesort.commandline.listeners.ImageSortReadFilesInFolderFeedback;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.feedback.PreMoveFeedbackInterface;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;
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

    PreMoveFeedbackInterface clientPreMovePhaseFeedback = new ImageSortPreMoveProgressFeedback(new FileSystemPrettyPrinter());
    ReadFilesFeedbackInterface imageSortReadFilesInFolderFeedback = new ImageSortReadFilesInFolderFeedback();
    imageSorter.analyzeImages(imageSortReadFilesInFolderFeedback, clientPreMovePhaseFeedback, sortSettings);

  }
}





