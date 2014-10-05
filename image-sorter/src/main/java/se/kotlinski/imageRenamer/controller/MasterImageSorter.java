package se.kotlinski.imageRenamer.controller;

import java.io.File;
import java.util.ArrayList;

/**
 * Describe class/interface here.
 *
 * Date: 2014-10-05
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public interface MasterImageSorter {

	boolean runMasterImageSorter(ArrayList<File> inputFolders, File masterFolder);

}
