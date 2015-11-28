package se.kotlinski.imagesort;

import se.kotlinski.imagesort.data.ParsedFileData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportPoints {
  //String, list of parsed file datas
  private Map<String, List<ParsedFileData>> exportPoints;

  public ExportPoints() {
    exportPoints = new HashMap<>();
  }

  public void put(final String fullExportPath, final ParsedFileData parsedFileData) {
    if (exportPoints.containsKey(fullExportPath)) {
      List<ParsedFileData> parsedFileDatas = exportPoints.get(fullExportPath);
      parsedFileDatas.add(parsedFileData);
    }
    else {
      ArrayList<ParsedFileData> parsedFileDatas = new ArrayList<>();
      parsedFileDatas.add(parsedFileData);
      exportPoints.put(fullExportPath, parsedFileDatas);
    }
  }

  public Set<String> getAllExportDestinations() {
    return exportPoints.keySet();
  }
}
