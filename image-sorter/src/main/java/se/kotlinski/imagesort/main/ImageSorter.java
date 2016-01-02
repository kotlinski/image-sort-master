package se.kotlinski.imagesort.main;


import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.executor.ClientInterface;
import se.kotlinski.imagesort.executor.FileMover;
import se.kotlinski.imagesort.forecaster.MediaFilesOutputForecaster;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.resolver.OutputConflictResolver;
import se.kotlinski.imagesort.transformer.MediaFileHashDataMapTransformer;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSorter {
  private static final Logger LOGGER = LogManager.getLogger(ImageSorter.class);

  private final ClientInterface clientInterface;
  private final MediaFileParser mediaFileParser;
  private final OutputConflictResolver outputConflictResolver;
  private final FileMover fileMover;
  private final MediaFileHashDataMapTransformer mediaFileHashDataMapTransformer;
  private final MediaFilesOutputForecaster mediaOutputCalculator;

  @Inject
  public ImageSorter(final ClientInterface clientInterface,
                     final MediaFileParser mediaFileParser,
                     final MediaFileHashDataMapTransformer mediaFileHashDataMapTransformer,
                     final MediaFilesOutputForecaster mediaOutputCalculator, final OutputConflictResolver outputConflictResolver,
                     final FileMover fileMover) {
    this.clientInterface = clientInterface;
    this.mediaFileParser = mediaFileParser;
    this.mediaFileHashDataMapTransformer = mediaFileHashDataMapTransformer;
    this.mediaOutputCalculator = mediaOutputCalculator;
    this.outputConflictResolver = outputConflictResolver;
    this.fileMover = fileMover;
  }

  public void sortImages(SortSettings sortSettings) {
    String masterFolderPath = sortSettings.masterFolder.getAbsolutePath();

    clientInterface.initiateMediaFileParsingPhase();

    List<File> mediaFiles;
    mediaFiles = mediaFileParser.getMediaFilesInFolder(clientInterface, sortSettings.masterFolder);

    printMediaFileStatsInFolder(mediaFiles);

    clientInterface.startCalculatingOutputDirectories();
    Map<String, List<File>> mediaFileDestinations;
    mediaFileDestinations = mediaOutputCalculator.calculateOutputDestinations(mediaFiles, masterFolderPath);
    clientInterface.successfulCalculatedOutputDestinations(mediaFileDestinations);

    clientInterface.startResolvingConflicts();
    Map<List<File>, String> resolvedFilesToOutputMap;
    resolvedFilesToOutputMap = outputConflictResolver.resolveOutputConflicts(clientInterface, mediaFileDestinations);
    clientInterface.successfulResolvedOutputConflicts(resolvedFilesToOutputMap);

    clientInterface.startMovingFiles();
    fileMover.moveFilesToNewDestionation(clientInterface, resolvedFilesToOutputMap, masterFolderPath);

    printMediaFileStatsInFolder(mediaFiles);

    // TODO: Clean up clientInterface.
    // TODO: Name after relative paths, alt store in files instead of strings. 


    // Compare number of duplicates before and after....

    // Print link to btc.
  }

  private void printMediaFileStatsInFolder(final List<File> mediaFiles) {
    Map<MediaFileDataHash, List<File>> mediaFileHashDataListMap;
    mediaFileHashDataListMap = mediaFileHashDataMapTransformer.transform(clientInterface, mediaFiles);
    clientInterface.masterFolderSuccessfulParsed(mediaFileHashDataListMap);
  }
}
