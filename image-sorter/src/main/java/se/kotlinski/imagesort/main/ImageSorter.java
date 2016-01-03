package se.kotlinski.imagesort.main;


import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.executor.FileMover;
import se.kotlinski.imagesort.mapper.MediaFileDataMapper;
import se.kotlinski.imagesort.mapper.OutputMapper;
import se.kotlinski.imagesort.resolver.ConflictResolver;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSorter {
  private static final Logger LOGGER = LogManager.getLogger(ImageSorter.class);

  private final MediaFileUtil mediaFileUtil;
  private final ConflictResolver conflictResolver;
  private final FileMover fileMover;
  private final MediaFileDataMapper mediaFileDataMapper;
  private final OutputMapper mediaOutputCalculator;

  @Inject
  public ImageSorter(final MediaFileUtil mediaFileUtil,
                     final MediaFileDataMapper mediaFileDataMapper,
                     final OutputMapper mediaOutputCalculator,
                     final ConflictResolver conflictResolver,
                     final FileMover fileMover) {
    this.mediaFileUtil = mediaFileUtil;
    this.mediaFileDataMapper = mediaFileDataMapper;
    this.mediaOutputCalculator = mediaOutputCalculator;
    this.conflictResolver = conflictResolver;
    this.fileMover = fileMover;
  }

  public void sortImages(final ClientInterface clientInterface, SortSettings sortSettings) {
    if (!isValidateSortSettings(sortSettings)) {
      throw new IllegalArgumentException();
    }

    clientInterface.initiateMediaFileParsingPhase();
    List<File> mediaFiles = mediaFileUtil.getMediaFilesInFolder(clientInterface,
                                                                sortSettings.masterFolder);

    printMediaFileStatsInFolder(clientInterface, mediaFiles);

    clientInterface.startCalculatingOutputDirectories();
    Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations;
    mediaFileDestinations = mediaOutputCalculator.calculateOutputDestinations(sortSettings.masterFolder,
                                                                              mediaFiles);
    clientInterface.successfulCalculatedOutputDestinations(mediaFileDestinations);

    clientInterface.startResolvingConflicts();
    Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap;
    resolvedFilesToOutputMap = conflictResolver.resolveOutputConflicts(clientInterface,
                                                                       sortSettings.masterFolder,
                                                                       mediaFileDestinations);

    clientInterface.successfulResolvedOutputConflicts(resolvedFilesToOutputMap);


    if (resolvedFilesToOutputMap.size() > 0) {

      clientInterface.startMovingFiles();
      fileMover.moveFilesToNewDestination(clientInterface,
                                          sortSettings.masterFolder,
                                          resolvedFilesToOutputMap);

      printMediaFileStatsInFolder(clientInterface, mediaFiles);
    }

    // TODO: Clean up clientInterface.
    // TODO: Name after relative paths, alt store in files instead of strings.


    // Compare number of duplicates before and after....

    // Print link to btc.
  }

  private boolean isValidateSortSettings(final SortSettings sortSettings) {
    return mediaFileUtil.isValidFolder(sortSettings.masterFolder);
  }

  private void printMediaFileStatsInFolder(final ClientInterface clientInterface,
                                           final List<File> mediaFiles) {
    Map<MediaFileDataHash, List<File>> mediaFileHashDataListMap;
    mediaFileHashDataListMap = mediaFileDataMapper.mapOnMediaFileData(clientInterface, mediaFiles);
    clientInterface.masterFolderSuccessfulParsed(mediaFileHashDataListMap);
  }
}
