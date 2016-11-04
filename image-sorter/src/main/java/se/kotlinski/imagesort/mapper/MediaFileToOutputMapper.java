package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.PixelHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.PreMoveFeedbackInterface;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFileToOutputMapper {
  private final MediaFileHashGenerator mediaFileHashGenerator;
  private final MediaFileUtil mediaFileUtil;

  @Inject
  public MediaFileToOutputMapper(final MediaFileHashGenerator mediaFileHashGenerator,
                                 final MediaFileUtil mediaFileUtil) {
    this.mediaFileHashGenerator = mediaFileHashGenerator;
    this.mediaFileUtil = mediaFileUtil;
  }


  public Map<List<File>, RelativeMediaFolderOutput> mapRelativeOutputsToFiles(final PreMoveFeedbackInterface preMoveFeedback,
                                                                              final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {

    Map<List<File>, RelativeMediaFolderOutput> filesToOutputDestination = new HashMap<>();

    int progress = 0;
    for (RelativeMediaFolderOutput outputDirectory : mediaFileDestinations.keySet()) {
      preMoveFeedback.groupingFilesByContentProgress(mediaFileDestinations.size(), ++progress);

      resolvConflictForMd5FileList(preMoveFeedback,
                                   mediaFileDestinations,
                                   filesToOutputDestination,
                                   outputDirectory);
    }

    return filesToOutputDestination;
  }


  private void resolvConflictForMd5FileList(final PreMoveFeedbackInterface preMoveFeedback,
                                            final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations,
                                            final Map<List<File>, RelativeMediaFolderOutput> filesToOutputDestination,
                                            final RelativeMediaFolderOutput outputDirectory) {
    List<File> files = mediaFileDestinations.get(outputDirectory);

    Map<PixelHash, List<File>> md5Groups = new HashMap<>();
    groupFilesByMd5(files, md5Groups);

    renameOutputsWhenConflicts(preMoveFeedback,
                               filesToOutputDestination,
                               outputDirectory,
                               files,
                               md5Groups);
  }

  private void renameOutputsWhenConflicts(final PreMoveFeedbackInterface preMoveFeedback,
                                          final Map<List<File>, RelativeMediaFolderOutput> filesToOutputDestination,
                                          final RelativeMediaFolderOutput outputDirectory,
                                          final List<File> files,
                                          final Map<PixelHash, List<File>> md5Groups) {

    if (severalGroupsWithSameOutput(md5Groups)) {
      preMoveFeedback.conflictFound(outputDirectory);
      int append = 1;
      for (List<File> fileList : md5Groups.values()) {
        String outputWithAppendedIdentifier = mediaFileUtil.appendToFileName(outputDirectory.relativePath,
                                                                             "_" + append);
        RelativeMediaFolderOutput outputWithAppendedValue = new RelativeMediaFolderOutput(
            outputWithAppendedIdentifier);

        filesToOutputDestination.put(fileList, outputWithAppendedValue);
        append++;
      }
    }
    else {
      filesToOutputDestination.put(files, outputDirectory);
    }
  }

  private void groupFilesByMd5(final List<File> files,
                               final Map<PixelHash, List<File>> md5Groups) {
    for (File file : files) {
      PixelHash pixelHash = mediaFileHashGenerator.generatePixelDataHash(file);
      if (!md5Groups.containsKey(pixelHash)) {
        md5Groups.put(pixelHash, new ArrayList<>());
      }
      md5Groups.get(pixelHash).add(file);
    }
  }

  private boolean severalGroupsWithSameOutput(final Map<PixelHash, List<File>> md5Groups) {
    return md5Groups.size() > 1;
  }

}
