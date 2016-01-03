package se.kotlinski.imagesort.resolver;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.main.ClientInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ConflictResolver {
  private final UniqueFileOutputResolver uniqueFileOutputResolver;
  private final FileSkipper fileSkipper;
  private final ExistingFilesResolver existingFilesResolver;


  @Inject
  public ConflictResolver(final UniqueFileOutputResolver uniqueFileOutputResolver,
                          final FileSkipper fileSkipper,
                          final ExistingFilesResolver existingFilesResolver) {
    this.uniqueFileOutputResolver = uniqueFileOutputResolver;
    this.fileSkipper = fileSkipper;
    this.existingFilesResolver = existingFilesResolver;
  }

  public Map<List<File>, RelativeMediaFolderOutput> resolveOutputConflicts(final ClientInterface clientInterface,
                                                                           final File masterFolderFile,
                                                                           final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {

    Map<List<File>, RelativeMediaFolderOutput> fileMapWithResolvedConflicts;
    fileMapWithResolvedConflicts = uniqueFileOutputResolver.resolve(clientInterface,
                                                                    mediaFileDestinations);

    fileSkipper.skipFilesAlreadyNamedAsOutput(clientInterface,
                                              fileMapWithResolvedConflicts,
                                              masterFolderFile);


    //TODO: run recusivley, Move to Conflict resolver.
    boolean foundConflicts = true;
    while (foundConflicts) {
      foundConflicts = existingFilesResolver.resolveOutputConflictsWithOldFiles(masterFolderFile,
                                                                                fileMapWithResolvedConflicts);
    }


    return fileMapWithResolvedConflicts;

  }



}
