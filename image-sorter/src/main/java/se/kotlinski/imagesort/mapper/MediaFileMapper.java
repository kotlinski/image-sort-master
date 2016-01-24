package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.main.ClientInterface;
import se.kotlinski.imagesort.mapper.mappers.MediaFileToOutputMapper;
import se.kotlinski.imagesort.mapper.mappers.OutputToMediaFileMapper;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MediaFileMapper {


  private final OutputToMediaFileMapper outputToMediaFileMapper;
  private final MediaFileToOutputMapper mediaFileToOutputMapper;

  @Inject
  public MediaFileMapper(final OutputToMediaFileMapper outputToMediaFileMapper,
                         final MediaFileToOutputMapper mediaFileToOutputMapper) {

    this.outputToMediaFileMapper = outputToMediaFileMapper;
    this.mediaFileToOutputMapper = mediaFileToOutputMapper;
  }

  public Map<List<File>, RelativeMediaFolderOutput> mapMediaFiles(final ClientInterface clientInterface,
                                                                  final List<File> mediaFiles,
                                                                  final File masterFolder) {

    clientInterface.startCalculatingOutputDirectories();
    Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations;
    long l1 = System.currentTimeMillis();

    mediaFileDestinations = outputToMediaFileMapper.calculateOutputDestinations(masterFolder,
                                                                                mediaFiles);
    System.out.println(System.currentTimeMillis() - l1 + " ms");
    clientInterface.successfulCalculatedOutputDestinations(mediaFileDestinations);

    return mediaFileToOutputMapper.mapRelativeOutputsToFiles(clientInterface,
                                                             mediaFileDestinations);
  }


}
