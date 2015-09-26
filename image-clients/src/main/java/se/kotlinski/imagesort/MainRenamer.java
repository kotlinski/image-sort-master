package se.kotlinski.imagesort;

import com.google.inject.Guice;
import com.google.inject.Injector;
import se.kotlinski.imagesort.commandline.CommandLineInterface;
import se.kotlinski.imagesort.module.ImageModule;

/**
 * Entry point of the program, if any kind of arguments, run cmd-version, else application version
 */
final class MainRenamer {

	private MainRenamer() {
	}

	public static void main(String[] args) {
    Injector injector = Guice.createInjector(new ImageModule());

	  runCLI(args, injector);

  }

	private static void runCLI(final String[] args, final Injector injector) {
		CommandLineInterface commandLineInterface = injector.getInstance(CommandLineInterface.class);
		commandLineInterface.runCommandLine(args);
	}

/*
	if args.length == 0
	private static void runGUI(final String[] args, final Injector injector) {
		ApplicationController applicationController = injector.getInstance(ApplicationController.class);
		applicationController.startApplication(args);
	}*/
}