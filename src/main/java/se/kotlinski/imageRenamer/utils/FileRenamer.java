package se.kotlinski.imageRenamer.utils;

import java.io.File;

public class FileRenamer {
	private File[] _listOfFiles;
	private File _sourceFolder;
	private File _destinationFolder;


	public FileRenamer() {
	}


	private void refactorFile(File file) {
		// Do nothing with toType at the moment, always choose to dropbox-Format

		String fileName = file.getName();
		File f = new File(_sourceFolder.getPath() + "\\" + file.getName());
		renameFile(f, fileName);

	}

	private void renameFile(File f, String fileName) {
		try {
			f.renameTo(new File(_destinationFolder.getPath() + "\\" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't rename file: " + fileName);
		}
	}
}