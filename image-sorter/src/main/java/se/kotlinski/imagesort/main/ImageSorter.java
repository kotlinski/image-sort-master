package se.kotlinski.imagesort.main;


import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.executor.FileMover;
import se.kotlinski.imagesort.feedback.FindDuplicatesFeedbackInterface;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;
import se.kotlinski.imagesort.feedback.PreMoveFeedbackInterface;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;
import se.kotlinski.imagesort.mapper.MediaFileDataMapper;
import se.kotlinski.imagesort.mapper.MediaFileToOutputMapper;
import se.kotlinski.imagesort.mapper.OutputToMediaFileMapper;
import se.kotlinski.imagesort.resolver.ConflictResolver;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSorter {
  private static final Logger LOGGER = LogManager.getLogger(ImageSorter.class);

  private final MediaFileUtil mediaFileUtil;
  private final OutputToMediaFileMapper outputToMediaFileMapper;
  private final MediaFileToOutputMapper mediaFileToOutputMapper;
  private final ConflictResolver conflictResolver;
  private final FileMover fileMover;
  private final MediaFileDataMapper mediaFileDataMapper;

  @Inject
  public ImageSorter(final MediaFileUtil mediaFileUtil,
                     final OutputToMediaFileMapper outputToMediaFileMapper,
                     final MediaFileToOutputMapper mediaFileToOutputMapper,
                     final MediaFileDataMapper mediaFileDataMapper,
                     final ConflictResolver conflictResolver,
                     final FileMover fileMover) {
    this.mediaFileUtil = mediaFileUtil;
    this.outputToMediaFileMapper = outputToMediaFileMapper;
    this.mediaFileToOutputMapper = mediaFileToOutputMapper;
    this.mediaFileDataMapper = mediaFileDataMapper;
    this.conflictResolver = conflictResolver;
    this.fileMover = fileMover;
  }


  // TODO: Make 3 types of client callbacks,
  /*
   * one for analyze phase
   * one for move phase
   * one for count unique images in folder (Maybe do this in the move - phase instead)
   */

  public Map<List<File>, RelativeMediaFolderOutput> analyzeImages(final ReadFilesFeedbackInterface readFilesFeedback,
                                                                  final PreMoveFeedbackInterface preMoveFeedback,
                                                                  SortSettings sortSettings) {
    if (!isValidateSortSettings(sortSettings)) {
      throw new IllegalArgumentException();
    }
    preMoveFeedback.initiatePreMovePhase();


    List<File> mediaFiles = mediaFileUtil.getMediaFilesInFolder(readFilesFeedback,
                                                                sortSettings.masterFolder);

    Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations;
    mediaFileDestinations = outputToMediaFileMapper.calculateOutputDestinations(preMoveFeedback,
                                                                                sortSettings.masterFolder,
                                                                                mediaFiles);
    preMoveFeedback.calculatedDestinationForEachFile(mediaFileDestinations);



    Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent;
    filesGroupedByContent = mediaFileToOutputMapper.mapRelativeOutputsToFiles(preMoveFeedback,
                                                                              mediaFileDestinations);


    preMoveFeedback.fileGroupedByContent(filesGroupedByContent);

    return filesGroupedByContent;
  }

  public void moveImages(final MoveFeedbackInterface moveFeedbackInterface,
                         SortSettings sortSettings,
                         Map<List<File>, RelativeMediaFolderOutput> mappedMediaFiles) {


    moveFeedbackInterface.startResolvingConflicts();
    conflictResolver.resolveOutputConflicts(moveFeedbackInterface,
                                            sortSettings.masterFolder,
                                            mappedMediaFiles);

    moveFeedbackInterface.successfulResolvedOutputConflicts(mappedMediaFiles);


    if (mappedMediaFiles.size() > 0) {
      moveFeedbackInterface.startMovingFiles();
      fileMover.moveFilesToNewDestination(moveFeedbackInterface,
                                          sortSettings.masterFolder,
                                          mappedMediaFiles);
    }

    // Compare number of duplicates before and after....

    // Print link to btc.
  }

  private boolean isValidateSortSettings(final SortSettings sortSettings) {
    return mediaFileUtil.isValidFolder(sortSettings.masterFolder);
  }


  public void printMediaFileStatsInFolder(final SortSettings sortSettings,
                                          final ReadFilesFeedbackInterface readFilesFeedbackInterface,
                                          final FindDuplicatesFeedbackInterface findDuplicatesFeedbackInterface) {

    ///
    List<File> mediaFilesListAfterMovePhase;
    mediaFilesListAfterMovePhase = mediaFileUtil.getMediaFilesInFolder(readFilesFeedbackInterface,
                                                                       sortSettings.masterFolder);

    ///

    Map<MediaFileDataHash, List<File>> mediaFileHashDataListMap;
    mediaFileHashDataListMap = mediaFileDataMapper.mapOnMediaFileData(
        findDuplicatesFeedbackInterface,
        mediaFilesListAfterMovePhase);

    findDuplicatesFeedbackInterface.masterFolderSuccessfulParsed(mediaFileHashDataListMap);
  }

}
