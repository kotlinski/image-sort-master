package se.kotlinski.imagesort.resolver;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileSkipper {


  public void skipFilesAlreadyNamedAsOutput(final MoveFeedbackInterface moveFeedbackInterface,
                                            final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap,
                                            final File masterFolderFile) {
    int skippedFiles = 0;
    int filesToMove = 0;
    for (Map.Entry<List<File>, RelativeMediaFolderOutput> listStringEntry : resolvedFilesToOutputMap.entrySet()) {

      List<File> fileList = listStringEntry.getKey();

      for (Iterator<File> it = fileList.iterator(); it.hasNext(); ) {
        File file = it.next();

        String folderPath = masterFolderFile.getAbsolutePath() + listStringEntry.getValue();
        if (file.getAbsolutePath().equals(folderPath)) {
          skippedFiles++;
          it.remove();
        }
        else {
          filesToMove++;
        }
        moveFeedbackInterface.skippingFilesToMove(skippedFiles, filesToMove);
      }
    }
  }
}
