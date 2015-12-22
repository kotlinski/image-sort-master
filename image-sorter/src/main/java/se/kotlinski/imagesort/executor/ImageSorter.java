package se.kotlinski.imagesort.executor;


import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.forecaster.MediaFileForecaster;
import se.kotlinski.imagesort.forecaster.MediaFilesOutputForecaster;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.resolver.OutputConflictResolver;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSorter {
  private static final Logger LOGGER = LogManager.getLogger(ImageSorter.class);

  private final ClientInterface clientInterface;
  private final MediaFileParser mediaFileParser;
  private final DateToFileRenamer dateToFileRenamer;
  private final FileDateInterpreter fileDateInterpreter;
  private final OutputConflictResolver outputConflictResolver;
  private final FileMover fileMover;

  @Inject
  public ImageSorter(final ClientInterface clientInterface,
                     final MediaFileParser mediaFileParser,
                     final DateToFileRenamer dateToFileRenamer,
                     final FileDateInterpreter fileDateInterpreter,
                     final OutputConflictResolver outputConflictResolver,
                     final FileMover fileMover) {
    this.clientInterface = clientInterface;
    this.mediaFileParser = mediaFileParser;
    this.dateToFileRenamer = dateToFileRenamer;
    this.fileDateInterpreter = fileDateInterpreter;
    this.outputConflictResolver = outputConflictResolver;
    this.fileMover = fileMover;
  }

  public void sortImages(SortSettings sortSettings) {
    Map<String, List<File>> mediaFilesInFolder;
    String masterFolderPath = sortSettings.masterFolder.getAbsolutePath();

    mediaFilesInFolder = getMediaFilesInMasterFolder(sortSettings);

    clientInterface.masterFolderSuccessfulParsed(mediaFilesInFolder);

    Map<String, List<File>> mediaFileDestinations;
    mediaFileDestinations = calculateOutputDirectories(mediaFilesInFolder, masterFolderPath);
    clientInterface.successfulCalculatedOutputDestinations(mediaFileDestinations);

    Map<List<File>, String> resolvedFilesToOutputMap;
    resolvedFilesToOutputMap = outputConflictResolver.resolveOutputConflicts(mediaFileDestinations);

    //Print after move-action
    clientInterface.successfulResolvedOutputConflicts(resolvedFilesToOutputMap);

    fileMover.moveFilesToNewDestionation(resolvedFilesToOutputMap, masterFolderPath);

    //Print after move-action
    mediaFileDestinations = calculateOutputDirectories(mediaFilesInFolder, masterFolderPath);
    clientInterface.successfulCalculatedOutputDestinations(mediaFileDestinations);

  }

  private Map<String, List<File>> getMediaFilesInMasterFolder(final SortSettings sortSettings) {
    try {
      return mediaFileParser.getMediaFilesInFolder(sortSettings.masterFolder);
    }
    catch (InvalidInputFolders invalidInputFolders) {
      LOGGER.error("Invalid input folders, try again", invalidInputFolders);
      clientInterface.masterFolderFailedParsed();
    }
    catch (Exception e) {
      clientInterface.masterFolderFailedParsed();
    }
    return null;
  }


  private Map<String, List<File>> calculateOutputDirectories(final Map<String, List<File>> mediaFilesInFolder,
                                                             final String masterFolder) {
    MediaFileForecaster mediaFileForecaster;
    mediaFileForecaster = new MediaFileForecaster(dateToFileRenamer, fileDateInterpreter);
    MediaFilesOutputForecaster mediaOutputCalculator;
    mediaOutputCalculator = new MediaFilesOutputForecaster(mediaFileForecaster);

    return mediaOutputCalculator.calculateOutputDestinations(mediaFilesInFolder, masterFolder);
  }
}
