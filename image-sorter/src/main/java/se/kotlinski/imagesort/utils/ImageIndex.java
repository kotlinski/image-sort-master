package se.kotlinski.imagesort.utils;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.model.ImageDescriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: Simon Kotlinski Date: 2014-02-10 Time: 21:40 To change this
 * template use File | Settings | File Templates.
 */
public class ImageIndex {
	private ImageMapper imageMapper;
	private FolderIO folderIO;

	public ImageIndex(FolderIO folderIO) {
		imageMapper = new ImageMapper();
		this.folderIO = folderIO;
	}

	private static boolean validInputFolders(final ArrayList<File> inputFolders) {
		for (File inputFolder : inputFolders) {
			if (!FileUtil.isValidFolder(inputFolder)) {
				return false;
			}
		}
		return true;
	}

	public ImageMapper runIndexing() {
		if (folderIO == null || folderIO.masterFolder == null || folderIO.inputFolders == null) {
			return null;
		}
		if (validInputFolders(folderIO.inputFolders)) {
			imageMapper.populateWithImages(folderIO.inputFolders);
			return imageMapper;
		}
		else {
			return null;
		}
	}

	public boolean copyFiles() {
		ArrayList<ImageDescriber> uniqueImageDescribers = imageMapper.getUniqueImageDescribers();
		for (ImageDescriber uniqueImageDescriber : uniqueImageDescribers) {
			String newImageFolder = folderIO.masterFolder.getAbsolutePath() + "\\" +
															uniqueImageDescriber.getRenamedFilePath();
			FileUtils.mkdir(newImageFolder);
			try {
				createImageFile(uniqueImageDescriber, newImageFolder);
			}
			catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}


	protected void createImageFile(final ImageDescriber uniqueImageDescriber,
																 final String newImageFolder) throws IOException {
		File file;
		file = new File(newImageFolder + "\\" + uniqueImageDescriber.getRenamedFile());
		boolean fileCreated = file.createNewFile();
		if (fileCreated) {
			FileUtils.copyFile(uniqueImageDescriber.getFile(), file);
		}
		else {
			System.err.println("couldn't move image: " + uniqueImageDescriber.getFile().getName());
		}
	}


}
