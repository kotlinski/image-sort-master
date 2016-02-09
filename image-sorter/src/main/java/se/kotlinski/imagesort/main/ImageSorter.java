package se.kotlinski.imagesort.main;


import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.executor.FileMover;
import se.kotlinski.imagesort.mapper.MediaFileMapper;
import se.kotlinski.imagesort.mapper.mappers.MediaFileDataMapper;
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
  private final MediaFileMapper mediaFileMapper;
  private final MediaFileDataMapper mediaFileDataMapper;

  @Inject
  public ImageSorter(final MediaFileUtil mediaFileUtil,
                     final MediaFileDataMapper mediaFileDataMapper,
                     final MediaFileMapper mediaFileMapper,
                     final ConflictResolver conflictResolver,
                     final FileMover fileMover) {
    this.mediaFileUtil = mediaFileUtil;
    this.mediaFileDataMapper = mediaFileDataMapper;
    this.conflictResolver = conflictResolver;
    this.fileMover = fileMover;
    this.mediaFileMapper = mediaFileMapper;
  }


  // TODO: Make 3 types of client callbacks,
  /*
   * one for analyze phase
   * one for move phase
   * one for count unique images in folder (Maybe do this in the move - phase instead)
   */

  public Map<List<File>, RelativeMediaFolderOutput> analyzeImages(final ClientReadFilesInFolderInterface clientReadFilesInFolderInterface,
                                                                  final ClientPreMovePhaseInterface clientPreMovePhaseInterface,
                                                                  SortSettings sortSettings) {
    if (!isValidateSortSettings(sortSettings)) {
      throw new IllegalArgumentException();
    }

    clientPreMovePhaseInterface.initiateMediaFileParsingPhase();
    List<File> mediaFiles = mediaFileUtil.getMediaFilesInFolder(clientReadFilesInFolderInterface,
                                                                sortSettings.masterFolder);

    Map<List<File>, RelativeMediaFolderOutput> mappedMediaFiles;
    mappedMediaFiles = mediaFileMapper.mapMediaFiles(clientPreMovePhaseInterface,
                                                     mediaFiles,
                                                     sortSettings.masterFolder);
    return mappedMediaFiles;
  }

  public void moveImages(final ClientMovePhaseInterface clientMovePhaseInterface,
                         SortSettings sortSettings,
                         Map<List<File>, RelativeMediaFolderOutput> mappedMediaFiles) {


    clientMovePhaseInterface.startResolvingConflicts();
    conflictResolver.resolveOutputConflicts(clientMovePhaseInterface,
                                            sortSettings.masterFolder,
                                            mappedMediaFiles);

    clientMovePhaseInterface.successfulResolvedOutputConflicts(mappedMediaFiles);


    if (mappedMediaFiles.size() > 0) {
      clientMovePhaseInterface.startMovingFiles();
      fileMover.moveFilesToNewDestination(clientMovePhaseInterface,
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
                                          final ClientReadFilesInFolderInterface clientReadFilesInFolderInterface,
                                          final ClientAnalyzeFilesInFolderInterface clientAnalyzeFilesInFolderInterface) {

    ///
    List<File> mediaFilesListAfterMovePhase;
    mediaFilesListAfterMovePhase = mediaFileUtil.getMediaFilesInFolder(
        clientReadFilesInFolderInterface,
        sortSettings.masterFolder);

    ///

    Map<MediaFileDataHash, List<File>> mediaFileHashDataListMap;
    mediaFileHashDataListMap = mediaFileDataMapper.mapOnMediaFileData(
        clientAnalyzeFilesInFolderInterface,
        mediaFilesListAfterMovePhase);

    clientAnalyzeFilesInFolderInterface.masterFolderSuccessfulParsed(mediaFileHashDataListMap);
  }

}
