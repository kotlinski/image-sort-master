package se.kotlinski.imagesort.javafx.controllers.tabs;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.javafx.controllers.TabSwitcher;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MoveTabController {
  private final TabSwitcher tabSwitcher;
  private final Tab moveTab;
  private final AnchorPane moveLoadingScene;
  private final AnchorPane moveResultScene;
  private final Text moveTabLoadingText;
  private final ProgressBar moveTabProgressBar;

  public MoveTabController(final Tab moveTab,
                           final TabSwitcher tabSwitcher,
                           final AnchorPane moveLoadingScene,
                           final AnchorPane moveResultScene,
                           final Text moveTabLoadingText,
                           final ProgressBar moveTabProgressBar) {
    this.tabSwitcher = tabSwitcher;
    this.moveTab = moveTab;
    this.moveLoadingScene = moveLoadingScene;
    this.moveResultScene = moveResultScene;
    this.moveTabLoadingText = moveTabLoadingText;
    this.moveTabProgressBar = moveTabProgressBar;
  }

  public void resetTab() {
    moveLoadingScene.setVisible(true);
    moveResultScene.setVisible(false);
    moveTab.setDisable(true);

    moveTabLoadingText.setText("");
    moveTabProgressBar.setProgress(0.0);
  }

  public void startResolvingConflicts() {
    moveTabLoadingText.setText("Resolving conflicts...");
    moveTabProgressBar.setProgress(0);
  }

  public void successfulResolvedOutputConflicts(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {
    moveTabLoadingText.setText("Resolving conflicts...");
    moveTabProgressBar.setProgress(0.0);
  }

  public void startMovingFiles() {
    moveTabLoadingText.setText("Moving files");
    moveTabProgressBar.setProgress(0.0);
  }

  public void copyingFile(final int numberOfCopiedFiles, final int size) {
    moveTabLoadingText.setText("Moving files");
    moveTabProgressBar.setProgress((numberOfCopiedFiles * 1.0 / size * 1.0));
  }

  public void movePhaseComplete() {
    moveLoadingScene.setVisible(false);
    moveResultScene.setVisible(true);
    tabSwitcher.setTabsInMoveModeDone();
  }

  public void deletingFile(final int filesDeleted, final int size) {
    moveTabLoadingText.setText("Removing duplicates...");
    moveTabProgressBar.setProgress((filesDeleted * 1.0 / size * 1.0));
  }
}
