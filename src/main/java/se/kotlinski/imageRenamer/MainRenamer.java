package se.kotlinski.imageRenamer;

import se.kotlinski.imageRenamer.Controllers.ApplicationController;
import se.kotlinski.imageRenamer.Controllers.CmdController;
import se.kotlinski.imageRenamer.utils.ImageIndex;

import java.io.File;

public class MainRenamer {

	public static void main(String[] args) {
		if(args.length == 0) {
			ApplicationController.startApplication(args);
		} else {
			new CmdController().startCmd(args);
		}
	}
}