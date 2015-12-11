package se.kotlinski.imagesort.commandline.argument;

import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.exception.InvalidArgumentsException;

public class Interpreter {

  private static final Logger LOGGER = LogManager.getLogger(Interpreter.class);
  private Transformer transformer;

  @Inject
  public Interpreter(final Transformer transformer) {
    this.transformer = transformer;
  }

  public final SortSettings transformArguments(final String[] arguments) throws
      Exception {
    CommandLine commandLine = getCommandLine(arguments);

    return getSortSettings(commandLine);
  }

  private CommandLine getCommandLine(final String[] arguments) throws Exception {
    CommandLine commandLine;
    try {
      commandLine = transformer.parseArgs(arguments);
    }
    catch (Exception e) {
      LOGGER.error("Could not parse arguments", e);
      throw e;
    }
    return commandLine;
  }

  private SortSettings getSortSettings(final CommandLine commandLine) throws Exception {
    try {
      SortSettings sortSettings = transformer.transformCommandLineArguments(commandLine);
      System.out.println(sortSettings.toString());
      return sortSettings;
    }
    catch (InvalidArgumentsException e) {
      System.out.println("No input folder found, try again");
      LOGGER.error("No input folder found, try again", e);
      throw new InvalidArgumentsException("Invalid folder input parameters");
    }
  }
}
