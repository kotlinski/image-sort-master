package se.kotlinski.imagesort.javafx.controllers.tabs;

import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONObject;
import se.kotlinski.imagesort.data.PixelHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.javafx.controllers.TabSwitcher;
import se.kotlinski.imagesort.statistics.MediaFilesInFolderCalculator;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FindDuplicatesTabController {

  private final TabSwitcher tabSwitcher;
  private final Tab tab;
  private final AnchorPane loadingScene;
  private final AnchorPane resultScene;
  private final Text tabLoadingText;
  private final ProgressBar tabProgressBar;
  private final TextArea folderTextArea;
  private final MessageBuilder messageBuilder;
  private final MixpanelAPI mixpanel;
  private final String sessionUniqueID;

  public FindDuplicatesTabController(final MixpanelAPI mixpanel,
                                     final String sessionUniqueID,
                                     final MessageBuilder messageBuilder,
                                     final TabSwitcher tabSwitcher,
                                     final Tab tab,
                                     final AnchorPane loadingScene,
                                     final AnchorPane resultScene,
                                     final Text tabLoadingText,
                                     final ProgressBar tabProgressBar,
                                     final TextArea folderTextArea) {
    this.mixpanel = mixpanel;
    this.sessionUniqueID = sessionUniqueID;
    this.messageBuilder = messageBuilder;
    this.tabSwitcher = tabSwitcher;
    this.tab = tab;
    this.loadingScene = loadingScene;
    this.resultScene = resultScene;
    this.tabLoadingText = tabLoadingText;
    this.tabProgressBar = tabProgressBar;
    this.folderTextArea = folderTextArea;

  }

  public void resetTab() {
    loadingScene.setVisible(true);
    resultScene.setVisible(false);
    tab.setDisable(true);

    tabLoadingText.setText("");
    tabProgressBar.setProgress(0.0);
    folderTextArea.setText("");
  }

  private void updateLoadingFromSeparateThread(String value) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        tabLoadingText.setText(value);
      }
    });
  }


  public void startGroupingFilesByContent() {
    updateLoadingFromSeparateThread("Initating...");
    tabProgressBar.setProgress(0.0);
  }

  public void groupFilesByContentProgress(final int total, final int progress) {
    tabProgressBar.setProgress((double) progress / (double) total);
    updateLoadingFromSeparateThread("Parsing files... (" + progress + " of " + total + ")");
  }

  public void parsedFilesInMasterFolderProgress(final int size) {
    tabProgressBar.setProgress(0.0);
    updateLoadingFromSeparateThread("Parsed " + size + " files in folder.");
  }

  public void masterFolderSuccessfulParsed(final Map<PixelHash, List<File>> mediaFilesInFolder) {
    tabProgressBar.setProgress(1.0);

    tabSwitcher.setTabsInFindDuplicatesModeDone();
    resultScene.setVisible(true);
    loadingScene.setVisible(false);

    MediaFilesInFolderCalculator mediaFilesInFolderCalculator = new MediaFilesInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution = mediaFilesInFolderCalculator.calculateMediaFileDataInFolder(
        mediaFilesInFolder);


    String output = mediaDataBeforeExecution.toString() + "\n\n" +
                    mediaDataBeforeExecution.printAllDuplicatedFiles();
    folderTextArea.setText(output);


    try {
      JSONObject props = new JSONObject();
      props.put("summary", mediaDataBeforeExecution.toString());
      props.put("duplicates", mediaDataBeforeExecution.printAllDuplicatedFiles());
      JSONObject sentEvent = messageBuilder.event(sessionUniqueID, "duplicates_result", props);
      mixpanel.sendMessage(sentEvent);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
