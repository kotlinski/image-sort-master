package se.kotlinski.imageRenamer;

import se.kotlinski.imageRenamer.controller.ApplicationController;
import se.kotlinski.imageRenamer.controller.CmdController;

public class MainRenamer {

	private MainRenamer() {
	}

	public static void main(String[] args) {
		if(args.length == 0) {
			ApplicationController.startApplication(args);
		} else {
			new CmdController().startCmd(args);
		}
	}
}