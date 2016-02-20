package se.kotlinski.imagesort.javafx.controllers.tabs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONObject;
import se.kotlinski.imagesort.commandline.FileSystemPrettyPrinter;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;
import se.kotlinski.imagesort.javafx.controllers.TabSwitcher;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.module.ImageModule;

import java.io.File;
import java.util.List;
import java.util.Map;

public class PreMoveTabController {

  private final MoveFeedbackInterface moveFeedback;
  private final TabSwitcher tabSwitcher;
  private final Tab preMoveTab;
  private final AnchorPane preMoveLoadingScene;
  private final AnchorPane preMoveResultScene;
  private final Text preMoveTabLoadingText;
  private final ProgressBar preMoveTabProgressBar;
  private final TextArea preMoveFolderTextArea;
  private final ImageSorter imageSorter;
  private final FileSystemPrettyPrinter fileSystemPrettyPrinter;
  private final ButtonBase moveImagesButton;
  private final MessageBuilder messageBuilder;
  private final MixpanelAPI mixpanel;
  private final String sessionUniqueID;
  private Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent;
  private SortSettings sortSettings;

  public PreMoveTabController(final MixpanelAPI mixpanel,
                              final String sessionUniqueID,
                              final MessageBuilder messageBuilder,
                              final MoveFeedbackInterface moveFeedback,
                              final TabSwitcher tabSwitcher,
                              final Tab preMoveTab,
                              final AnchorPane preMoveLoadingScene,
                              final AnchorPane preMoveResultScene,
                              final Text preMoveTabLoadingText,
                              final ProgressBar preMoveTabProgressBar,
                              final TextArea preMoveFolderTextArea,
                              final ButtonBase moveImagesButton) {
    this.mixpanel = mixpanel;
    this.sessionUniqueID = sessionUniqueID;
    this.messageBuilder = messageBuilder;
    this.moveFeedback = moveFeedback;
    this.tabSwitcher = tabSwitcher;
    this.preMoveTab = preMoveTab;
    this.preMoveLoadingScene = preMoveLoadingScene;
    this.preMoveResultScene = preMoveResultScene;
    this.preMoveTabLoadingText = preMoveTabLoadingText;
    this.preMoveTabProgressBar = preMoveTabProgressBar;
    this.preMoveFolderTextArea = preMoveFolderTextArea;
    this.moveImagesButton = moveImagesButton;

    Injector injector = Guice.createInjector(new ImageModule());
    imageSorter = injector.getInstance(ImageSorter.class);
    fileSystemPrettyPrinter = injector.getInstance(FileSystemPrettyPrinter.class);

    setUpListeners();


  }

  public void resetTab() {
    preMoveLoadingScene.setVisible(true);
    preMoveResultScene.setVisible(false);
    preMoveTab.setDisable(true);

    preMoveTabLoadingText.setText("");
    preMoveTabProgressBar.setProgress(0.0);
    preMoveFolderTextArea.setText("");
  }

  private void setUpListeners() {
    moveImagesButton.setOnAction((event) -> {
      if (filesGroupedByContent != null) {
        tabSwitcher.setTabsInMoveMode();
        runMovePhase(filesGroupedByContent);
      }
    });
  }

  private void runMovePhase(final Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent) {

    Task<Integer> task = new Task<Integer>() {

      @Override
      protected Integer call() throws Exception {
        try {
          imageSorter.moveImages(moveFeedback, sortSettings, filesGroupedByContent);
        }
        catch (Exception e) {
          try {
            JSONObject props = new JSONObject();
            props.put("phase", "move_images");
            props.put("stacktrace", e.toString());
            JSONObject sentEvent = messageBuilder.event(sessionUniqueID,
                                                        "error",
                                                        props);
            mixpanel.sendMessage(sentEvent);
          }
          catch (Exception error) {
            error.printStackTrace();
          }
          e.printStackTrace();
        }


        moveFeedback.movePhaseComplete();
        return 0;
      }
    };

    new Thread(task).start();

  }


  private void updateLoadingFromSeparateThread(String value) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        preMoveTabLoadingText.setText(value);
      }
    });
  }

  public void startCalculatingOutputDirectories() {
    updateLoadingFromSeparateThread("Calculating output directories...");
  }

  public void preMoveFeedbackInitiated() {
    updateLoadingFromSeparateThread("Initating...");
    preMoveTabProgressBar.setProgress(0.0);
  }


  public void readFilesProgressFeedback(final int size) {
    preMoveTabProgressBar.setProgress(0.0);
    updateLoadingFromSeparateThread("Parsed " + size + " files in folder.");
  }


  public void doneCalculatingDestinationEveryFile(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    //DO somethinge?
  }

  public void groupFilesByContentProgress(final int total, final int progress) {
    preMoveTabProgressBar.setProgress((double) progress / (double) total);
    updateLoadingFromSeparateThread("Parsing files... (" + progress + " of " + total + ")");
  }


  public void successfulCalculatedOutputDestinations(final Map<List<File>, RelativeMediaFolderOutput> mediaFileDestinations) {
    String folderStructureString;
    folderStructureString = fileSystemPrettyPrinter.convertFolderStructureToString(
        mediaFileDestinations,
        false);
    preMoveFolderTextArea.setText(folderStructureString);
  }

  public void preMovePhaseCompleted(final Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent,
                                    final SortSettings sortSettings) {
    this.sortSettings = sortSettings;
    this.filesGroupedByContent = filesGroupedByContent;
    tabSwitcher.setTabsInPreMoveModeDone(filesGroupedByContent);
    preMoveResultScene.setVisible(true);
    preMoveLoadingScene.setVisible(false);
  }


}
