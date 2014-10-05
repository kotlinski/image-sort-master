package se.kotlinski.imageRenamer;

import se.kotlinski.imageRenamer.controller.ApplicationController;
import se.kotlinski.imageRenamer.controller.CmdController;

public class MainRenamer {

	private MainRenamer() {
	}

	public static void main(String[] args) {
		// No arguments, run FX gui application
		if (args.length == 0) {
			ApplicationController.startApplication(args);
		}
		// Else Run the comman line
		else {
			new CmdController().startCmd(args);
		}
	}
}