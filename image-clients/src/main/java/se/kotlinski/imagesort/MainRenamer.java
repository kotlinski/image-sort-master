package se.kotlinski.imagesort;

import com.google.inject.Guice;
import com.google.inject.Injector;
import se.kotlinski.imagesort.controller.ApplicationController;
import se.kotlinski.imagesort.controller.CmdController;
import se.kotlinski.imagesort.module.ImageModule;

/**
 * Entry point of the program, if any kind of arguments, run cmd-version, else application version
 */
public class MainRenamer {

  private MainRenamer() {
  }

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new ImageModule());

    // No arguments, run FX gui application
    if (args.length == 0) {
      ApplicationController applicationController = injector.getInstance(ApplicationController.class);
      applicationController.startApplication(args);
    }
    // Else Run the comman line
    else {
      CmdController cmdController = injector.getInstance(CmdController.class);
      cmdController.startCmd(args);
    }
  }
}