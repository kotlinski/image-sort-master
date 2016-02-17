package se.kotlinski.imagesort.secrets;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Passwords {


  public String getAnalyticsSecret() {
    String password = "";
    try {
      Path analytics = Paths.get(System.getProperty("user.dir") + File.separator + "analytics");
      List<String> lines = Files.readAllLines(analytics, Charset.defaultCharset());
      password = lines.get(0);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return password;
  }


}
