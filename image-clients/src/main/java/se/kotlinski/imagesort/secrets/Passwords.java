package se.kotlinski.imagesort.secrets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
      URL resource = getClass().getResource("/secrets/analytics");
      Path analytics = Paths.get(resource.toURI());
      List<String> lines = Files.readAllLines(analytics, Charset.defaultCharset());
      password = lines.get(0);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (URISyntaxException e) {
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
      URL resource = getClass().getResource("/secrets/mixpanel");
      Path analytics = Paths.get(resource.toURI());
      return Files.readAllLines(analytics, Charset.defaultCharset());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }

    return new ArrayList<>();
  }


}
