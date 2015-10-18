package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.controller.ExportCollector;
import se.kotlinski.imagesort.controller.FileAnalyzer;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.model.SortSettings;

import java.util.Set;

public class CommandLineInterface {
	private static final Logger LOGGER = LogManager.getLogger(CommandLineInterface.class);
	private final FileAnalyzer fileAnalyzer;
	private final FilePrinter filePrinter;
	private final ExportCollector exportCollector;
	private final Interpreter interpreter;

	@Inject
	public CommandLineInterface(final FileAnalyzer fileAnalyzer,
			final FilePrinter filePrinter,
			final ExportCollector exportCollector,
			final Interpreter interpreter) {
		this.fileAnalyzer = fileAnalyzer;
		this.filePrinter = filePrinter;
		this.exportCollector = exportCollector;
		this.interpreter = interpreter;
	}

	public final void runCommandLine(String[] arguments) {
		SortSettings sortSettings;
		ExportFileDataMap exportFileDataMap;

		try {
			sortSettings = interpreter.transformArguments(arguments);
		}
		catch (Exception e) {
			System.out.println("Check your parameters...");
			LOGGER.error("Invalid parameters");
			return;
		}

		try {
			exportFileDataMap = this.fileAnalyzer.createParsedFileMap(sortSettings);
		}
		catch (InvalidInputFolders invalidInputFolders) {
			System.out.println("Invalid input folders, try again");
			LOGGER.error("Invalid input folders, try again", invalidInputFolders);
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}

		//Print all images and videos read from inputFolders + master folder
		filePrinter.printTotalNumberOfFiles(exportFileDataMap.totalNumberOfFiles());

		//Some files may be the same, but with different flavours
		exportCollector.tagUniqueFilesWithSameName(exportFileDataMap);

		printExportFolders(exportFileDataMap);

		filePrinter.printTotalNumberOfDuplicates(exportFileDataMap.totalNumberOfFiles(),
				exportFileDataMap.getNumberOfUniqueImages(),
				exportFileDataMap.getNumberOfRemovableFiles());


	}

	private void printExportFolders(final ExportFileDataMap exportFileDataMap) {
		Set<String> foldersToExport = exportCollector.collectFoldersToExport(exportFileDataMap);
		filePrinter.printExportPaths(foldersToExport);
	}

	private void printExportPoints(final ExportFileDataMap exportFileDataMap) {
		Set<String> finalExportPoints = exportCollector.collectFilesToExport(exportFileDataMap);
		filePrinter.printExportDestinations(finalExportPoints);
	}


}
