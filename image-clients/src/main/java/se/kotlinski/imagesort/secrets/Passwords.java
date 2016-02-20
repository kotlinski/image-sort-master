package se.kotlinski.imagesort.secrets;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Passwords {


  public String getAnalytics() {
    String password = "";
    try {
      Path usrDir = Paths.get(System.getProperty("user.dir"));
      if (usrDir == null) {
        return "";
      }
      Path analyticsDir = usrDir.getParent();
      if (analyticsDir == null) {
        return "";
      }
      Path analytics = Paths.get(analyticsDir.toString() + File.separator + "analytics");
      List<String> lines = Files.readAllLines(analytics, Charset.defaultCharset());
      password = lines.get(0);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return password;
  }


  public String getMixpanelToken() {
    List<String> mixpanelStringArray = getMixpanelStringArray();
    if (mixpanelStringArray.size() > 0) {
      return mixpanelStringArray.get(0);
    }
    return "";
  }

  public String getMixpanelApiKey() {
    List<String> mixpanelStringArray = getMixpanelStringArray();
    if (mixpanelStringArray.size() > 1) {
      return mixpanelStringArray.get(1);
    }
    return "";
  }

  public String getMixpanelApiSecret() {
    List<String> mixpanelStringArray = getMixpanelStringArray();
    if (mixpanelStringArray.size() > 2) {
      return mixpanelStringArray.get(2);
    }
    return "";
  }

  public List<String> getMixpanelStringArray() {
    try {
      Path usrDir = Paths.get(System.getProperty("user.dir"));


      if (usrDir != null && usrDir.getParent() != null) {
        Path parent = usrDir.getParent();
        if (parent != null) {
          String path = parent.toString();
          Path analytics = Paths.get(path + File.separator + "mixpanel");
          return Files.readAllLines(analytics, Charset.defaultCharset());
        }

      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return new ArrayList<>();
  }


}
