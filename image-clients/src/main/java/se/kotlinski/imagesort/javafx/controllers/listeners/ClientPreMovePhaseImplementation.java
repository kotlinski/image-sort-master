package se.kotlinski.imagesort.javafx.controllers.listeners;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.javafx.controllers.TabGroupController;
import se.kotlinski.imagesort.javafx.controllers.tabs.AnalyzeTabController;
import se.kotlinski.imagesort.main.ClientPreMovePhaseInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ClientPreMovePhaseImplementation implements ClientPreMovePhaseInterface {

  private final TabGroupController tabSwitcher;
  private final AnalyzeTabController analyzeTabController;

  public ClientPreMovePhaseImplementation(final TabGroupController tabSwitcher,
                                          final AnalyzeTabController analyzeTabController) {
    this.tabSwitcher = tabSwitcher;
    this.analyzeTabController = analyzeTabController;
  }

  @Override
  public void initiateMediaFileParsingPhase() {
    analyzeTabController.initiateMediaFileParsingPhase();
  }

  @Override
  public void startCalculatingOutputDirectories() {
    analyzeTabController.startCalculatingOutputDirectories();
  }

  @Override
  public void successfulCalculatedOutputDestinations(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    tabSwitcher.setTabsInAnalyzeModeDone(mediaFileDestinations);
  }

  @Override
  public void searchingForConflictsProgress(final int total, final int progress) {

  }

  @Override
  public void conflictFound(final RelativeMediaFolderOutput outputDirectory) {

  }
}
