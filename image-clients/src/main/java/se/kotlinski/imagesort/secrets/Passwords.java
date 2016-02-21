package se.kotlinski.imagesort.secrets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Passwords {


  public String getAnalyticsToken() {
    InputStream inputStream = Passwords.class.getResourceAsStream("analytics");
    Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader br = new BufferedReader(reader);
    String analyticsKey;
    try {
      analyticsKey = br.readLine();
      br.close();
      return analyticsKey;
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }


  public String getMixpanelToken() {
    InputStream inputStream = Passwords.class.getResourceAsStream("mixpanel");
    Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader br = new BufferedReader(reader);
    String mixpanelKey;
    try {
      mixpanelKey = br.readLine();
      br.close();
      return mixpanelKey;
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

}
