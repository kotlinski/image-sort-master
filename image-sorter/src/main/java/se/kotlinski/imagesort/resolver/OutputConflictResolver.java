package se.kotlinski.imagesort.resolver;

import com.google.inject.Inject;
import se.kotlinski.imagesort.executor.ClientInterface;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputConflictResolver {
  private final MD5Generator md5Generator;
  private final MediaFileUtil mediaFileUtil;

  @Inject
  public OutputConflictResolver(final MD5Generator md5Generator,
                                final MediaFileUtil mediaFileUtil) {
    this.md5Generator = md5Generator;
    this.mediaFileUtil = mediaFileUtil;
  }

  public Map<List<File>, String> resolveOutputConflicts(final ClientInterface clientInterface,
                                                        final Map<String, List<File>> mediaFileDestinations) {
    Map<List<File>, String> filesToOutputDestination = new HashMap<>();

    int progress = 0;
    for (String outputDirectory : mediaFileDestinations.keySet()) {
      clientInterface.searchingForConflictsProgress(mediaFileDestinations.size(), ++progress);

      resolvConflictForMd5FileList(clientInterface,
                                   mediaFileDestinations,
                                   filesToOutputDestination,
                                   outputDirectory);
    }

    return filesToOutputDestination;

  }

  private void resolvConflictForMd5FileList(final ClientInterface clientInterface,
                                            final Map<String, List<File>> mediaFileDestinations,
                                            final Map<List<File>, String> filesToOutputDestination,
                                            final String outputDirectory) {
    List<File> files = mediaFileDestinations.get(outputDirectory);

    Map<String, List<File>> md5Groups = new HashMap<>();
    groupFilesByMd5(files, md5Groups);

    renameOutputsWhenConflicts(clientInterface,
                               filesToOutputDestination,
                               outputDirectory,
                               files,
                               md5Groups);
  }

  private void renameOutputsWhenConflicts(final ClientInterface clientInterface,
                                          final Map<List<File>, String> filesToOutputDestination,
                                          final String outputDirectory,
                                          final List<File> files,
                                          final Map<String, List<File>> md5Groups) {

    if (severalGroupsWithSameOutput(md5Groups)) {
      clientInterface.conflictFound(outputDirectory);
      int append = 1;
      for (List<File> fileList : md5Groups.values()) {
        String outputWithAppendedValue = mediaFileUtil.appendToFileName(outputDirectory,
                                                                        "_" + append);
        filesToOutputDestination.put(fileList, outputWithAppendedValue);
        append++;
      }
    }
    else {
      filesToOutputDestination.put(files, outputDirectory);
    }
  }

  private void groupFilesByMd5(final List<File> files, final Map<String, List<File>> md5Groups) {
    for (File file : files) {
      String fileMd5 = md5Generator.generateMd5(file);
      if (!md5Groups.containsKey(fileMd5)) {
        md5Groups.put(fileMd5, new ArrayList<>());
      }
      md5Groups.get(fileMd5).add(file);
    }
  }

  private boolean severalGroupsWithSameOutput(final Map<String, List<File>> md5Groups) {
    return md5Groups.size() > 1;
  }

}
