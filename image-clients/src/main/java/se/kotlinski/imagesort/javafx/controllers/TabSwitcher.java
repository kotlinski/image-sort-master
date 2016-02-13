package se.kotlinski.imagesort.javafx.controllers;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface TabSwitcher {

  void resetTabs();

  void setTabsInPreMoveMode();
  
  void setTabsInPreMoveModeDone(Map<List<File>, RelativeMediaFolderOutput> mediaFileDestinations);

  void setTabsInMoveMode();

  void setTabsInMoveModeDone();
}
