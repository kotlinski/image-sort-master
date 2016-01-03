package se.kotlinski.imagesort.resolver;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.main.ClientInterface;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniqueFileOutputResolver {
  private final MediaFileHashGenerator mediaFileHashGenerator;
  private final MediaFileUtil mediaFileUtil;

  @Inject
  public UniqueFileOutputResolver(final MediaFileHashGenerator mediaFileHashGenerator,
                                  final MediaFileUtil mediaFileUtil) {
    this.mediaFileHashGenerator = mediaFileHashGenerator;
    this.mediaFileUtil = mediaFileUtil;
  }


  public Map<List<File>, RelativeMediaFolderOutput> resolve(final ClientInterface clientInterface,
                                                            final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {

    Map<List<File>, RelativeMediaFolderOutput> filesToOutputDestination = new HashMap<>();

    int progress = 0;
    for (RelativeMediaFolderOutput outputDirectory : mediaFileDestinations.keySet()) {
      clientInterface.searchingForConflictsProgress(mediaFileDestinations.size(), ++progress);

      resolvConflictForMd5FileList(clientInterface,
                                   mediaFileDestinations,
                                   filesToOutputDestination,
                                   outputDirectory);
    }

    return filesToOutputDestination;
  }


  private void resolvConflictForMd5FileList(final ClientInterface clientInterface,
                                            final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations,
                                            final Map<List<File>, RelativeMediaFolderOutput> filesToOutputDestination,
                                            final RelativeMediaFolderOutput outputDirectory) {
    List<File> files = mediaFileDestinations.get(outputDirectory);

    Map<MediaFileDataHash, List<File>> md5Groups = new HashMap<>();
    groupFilesByMd5(files, md5Groups);

    renameOutputsWhenConflicts(clientInterface,
                               filesToOutputDestination,
                               outputDirectory,
                               files,
                               md5Groups);
  }

  private void renameOutputsWhenConflicts(final ClientInterface clientInterface,
                                          final Map<List<File>, RelativeMediaFolderOutput> filesToOutputDestination,
                                          final RelativeMediaFolderOutput outputDirectory,
                                          final List<File> files,
                                          final Map<MediaFileDataHash, List<File>> md5Groups) {

    if (severalGroupsWithSameOutput(md5Groups)) {
      clientInterface.conflictFound(outputDirectory);
      int append = 1;
      for (List<File> fileList : md5Groups.values()) {
        String outputWithAppendedIdentifier = mediaFileUtil.appendToFileName(outputDirectory.relativePath, "_" + append);
        RelativeMediaFolderOutput outputWithAppendedValue = new RelativeMediaFolderOutput(outputWithAppendedIdentifier);

        filesToOutputDestination.put(fileList, outputWithAppendedValue);
        append++;
      }
    }
    else {
      filesToOutputDestination.put(files, outputDirectory);
    }
  }

  private void groupFilesByMd5(final List<File> files,
                               final Map<MediaFileDataHash, List<File>> md5Groups) {
    for (File file : files) {
      MediaFileDataHash hashDataValue = mediaFileHashGenerator.generateMediaFileDataHash(file);
      if (!md5Groups.containsKey(hashDataValue)) {
        md5Groups.put(hashDataValue, new ArrayList<>());
      }
      md5Groups.get(hashDataValue).add(file);
    }
  }

  private boolean severalGroupsWithSameOutput(final Map<MediaFileDataHash, List<File>> md5Groups) {
    return md5Groups.size() > 1;
  }

}
