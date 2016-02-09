package se.kotlinski.imagesort.resolver;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.main.ClientMovePhaseInterface;
import se.kotlinski.imagesort.mapper.mappers.MediaFileToOutputMapper;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ConflictResolver {
  private final MediaFileToOutputMapper mediaFileToOutputMapper;
  private final FileSkipper fileSkipper;
  private final ExistingFilesResolver existingFilesResolver;


  @Inject
  public ConflictResolver(final MediaFileToOutputMapper mediaFileToOutputMapper,
                          final FileSkipper fileSkipper,
                          final ExistingFilesResolver existingFilesResolver) {
    this.mediaFileToOutputMapper = mediaFileToOutputMapper;
    this.fileSkipper = fileSkipper;
    this.existingFilesResolver = existingFilesResolver;
  }

  public void resolveOutputConflicts(final ClientMovePhaseInterface clientMovePhaseInterface,
                                     final File masterFolderFile,
                                     final Map<List<File>, RelativeMediaFolderOutput> mediaFileDestinations) {


    //TODO: run recusivley, Move to Conflict resolver.
    boolean foundConflicts = true;
    while (foundConflicts) {
      foundConflicts = existingFilesResolver.resolveOutputConflictsWithOldFiles(masterFolderFile,
                                                                                mediaFileDestinations);
    }


    fileSkipper.skipFilesAlreadyNamedAsOutput(clientMovePhaseInterface,
                                              mediaFileDestinations,
                                              masterFolderFile);

  }


}
