package se.kotlinski.imageRenamer.utils;

import se.kotlinski.imageRenamer.Constants;

import java.io.File;

public class FileRenamer {
	private File[] _listOfFiles;
	private File _sourceFolder;
	private File _destinationFolder;


	public FileRenamer() {
		/*System.out.println("Working Directory = " + System.getProperty("user.dir"));
		System.getProperty("user.dir");*/
	}

	public void setSourceFolder(String folderName) {
		System.out.print(folderName);
		_sourceFolder = new File(folderName);
	}

	public void setDestinationFolder(String folderName) {
		_destinationFolder = new File(folderName);
	}

	public void renameFiles(Constants.FORMAT fromType, Constants.FORMAT toType) {
		File[] listOfFiles = _sourceFolder.listFiles();

		assert listOfFiles != null;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				refactorFile(file, fromType, toType);
			}
		}
	}

	private void refactorFile(File file, Constants.FORMAT fromType, Constants.FORMAT toType) {
		// Do nothing with toType at the moment, always choose to dropbox-Format

		String fileName = file.getName();
		File f = new File(_sourceFolder.getPath() + "\\" + file.getName());

		if (fromType == Constants.FORMAT.SAMSUNG) {
//			if (isSamsungFormat(fileName)) {
			fileName = samsungToDropboxFormat(fileName);
			renameFile(f, fileName);
//			} else {
			System.out.println("Not Samsung File Format: " + file.getName());
			System.out.println();
//			}
		} else if (fromType == Constants.FORMAT.DROPBOX) {
//			if (isDropboxFormat(fileName)) {
			//Just move the file. From Dropbox to Dropbox.
			renameFile(f, fileName);
//			} else {
			System.out.println("Not DropBox File Format: " + file.getName());
			System.out.println();
//			}
		}
	}

	private void renameFile(File f, String fileName) {
		try {
			f.renameTo(new File(_destinationFolder.getPath() + "\\" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't rename file: " + fileName);
		}
	}

	public String dropboxToSamsungFormat(String filename) {
		String year = filename.substring(0, 4);
		String month = filename.substring(5, 7);
		String day = filename.substring(8, 10);
		String time = filename.substring(11, 19).replace(".", "");
		return year + month + day + "_" + time + getExtension(filename);
	}

	public String samsungToDropboxFormat(String filename) {
		String year = filename.substring(0, 4);
		String month = filename.substring(4, 6);
		String day = filename.substring(6, 8);
		String time = filename.substring(9, 11);
		time += "." + filename.substring(11, 13);
		time += "." + filename.substring(13, 15);
		return year + '-' + month + '-' + day + " " + time + getExtension(filename);
		//return filename;
	}

	public String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf('.'), filename.length());
	}


}