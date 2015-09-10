package se.kotlinski.imagesort;

import com.google.inject.Guice;
import com.google.inject.Injector;
import se.kotlinski.imagesort.javafx.ApplicationController;
import se.kotlinski.imagesort.commandline.CommandLineInterface;
import se.kotlinski.imagesort.module.ImageModule;

/**
 * Entry point of the program, if any kind of arguments, run cmd-version, else application version
 */
class MainRenamer {

  private MainRenamer() {
  }

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new ImageModule());

    // No arguments, run FX gui application
    if (args.length == 0) {
      ApplicationController applicationController = injector.getInstance(ApplicationController.class);
      applicationController.startApplication(args);
    }
    // Else Run the command line
    else {
      CommandLineInterface commandLineInterface = injector.getInstance(CommandLineInterface.class);
      commandLineInterface.runCommandLine(args);
    }
  }
}