package se.kotlinski.imagesort;

import se.kotlinski.imagesort.data.DeprecatedParsedFileData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportPoints {
  //String, list of parsed file datas
  private Map<String, List<DeprecatedParsedFileData>> exportPoints;

  public ExportPoints() {
    exportPoints = new HashMap<>();
  }

  public void put(final String fullExportPath, final DeprecatedParsedFileData deprecatedParsedFileData) {
    if (exportPoints.containsKey(fullExportPath)) {
      List<DeprecatedParsedFileData> deprecatedParsedFileDatas = exportPoints.get(fullExportPath);
      deprecatedParsedFileDatas.add(deprecatedParsedFileData);
    }
    else {
      ArrayList<DeprecatedParsedFileData> deprecatedParsedFileDatas = new ArrayList<>();
      deprecatedParsedFileDatas.add(deprecatedParsedFileData);
      exportPoints.put(fullExportPath, deprecatedParsedFileDatas);
    }
  }

  public Set<String> getAllExportDestinations() {
    return exportPoints.keySet();
  }
}
