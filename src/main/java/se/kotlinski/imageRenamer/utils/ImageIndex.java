package se.kotlinski.imageRenamer.utils;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imageRenamer.mapper.ImageMapper;
import se.kotlinski.imageRenamer.model.FolderIO;
import se.kotlinski.imageRenamer.model.ImageDescriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: Simon Kotlinski Date: 2014-02-10 Time:
 * 21:40 To change this template use File | Settings | File Templates.
 */
public class ImageIndex {
	private ImageMapper imageMapper;
	private FolderIO folderIO;

	public ImageIndex(FolderIO folderIO) {
		imageMapper = new ImageMapper();
		this.folderIO = folderIO;
	}

	private void printRedundantFiles(final ArrayList<ImageDescriber> duplicates) {
		System.out.println("Redundant Files: ");
		for (ImageDescriber imageDescriber : duplicates) {
			System.out.println(imageDescriber.getFile().getAbsoluteFile());
		}
	}

	private void printNewFileList(final ArrayList<ImageDescriber> uniqueImageDescribers) {
		System.out.println("New files: ");
		for (ImageDescriber imageDescriber : uniqueImageDescribers) {
			System.out.println(imageDescriber.getRenamedFilePath() + "-" + imageDescriber.getMd5());
		}
	}

	public ImageMapper runIndexing() {

		imageMapper.populateWithImages(folderIO.inputFolder);

		return imageMapper;
	}

	public void printIndexedFiles() {
		ArrayList<ImageDescriber> uniqueImageDescribers = imageMapper.getUniqueImageDescribers();
		printNewFileList(uniqueImageDescribers);
		ArrayList<ImageDescriber> duplicates = imageMapper.getRedundantFiles();
		printRedundantFiles(duplicates);
	}

	public void copyFiles() {
		ArrayList<ImageDescriber> uniqueImageDescribers = imageMapper.getUniqueImageDescribers();
		for (ImageDescriber uniqueImageDescriber : uniqueImageDescribers) {
			String newImageFolder = folderIO.outputFolder.getAbsolutePath() + "\\" + uniqueImageDescriber.getRenamedFilePath();
			FileUtils.mkdir(newImageFolder);
			File file;
			try {
				file = new File(newImageFolder+"\\"+uniqueImageDescriber.getRenamedFile());
				file.createNewFile();
				FileUtils.copyFile(uniqueImageDescriber.getFile(), file);

			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
