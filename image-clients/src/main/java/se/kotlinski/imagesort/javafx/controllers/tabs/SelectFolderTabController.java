package se.kotlinski.imagesort.javafx.controllers.tabs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.javafx.controllers.TabSwitcher;
import se.kotlinski.imagesort.main.ClientPreMovePhaseInterface;
import se.kotlinski.imagesort.main.ClientReadFilesInFolderInterface;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.module.ImageModule;

import java.io.File;

public class SelectFolderTabController {

  public final Button selectFolderButton;
  public final Button continueButton;
  private final ClientPreMovePhaseInterface clientPreMovePhaseInterface;
  private final ClientReadFilesInFolderInterface clientReadFilesInFolderInterface;
  private final TabSwitcher tabSwitcher;
  private final Text selectedFolderPathText;
  private final ImageSorter imageSorter;
  private File selectedFolder;

  public SelectFolderTabController(final ClientPreMovePhaseInterface clientPreMovePhaseInterface,
                                   final ClientReadFilesInFolderInterface clientReadFilesInFolderInterface,
                                   final TabSwitcher tabSwitcher,
                                   final Button selectFolderButton,
                                   final Button continueButton,
                                   final Text selectedFolderPathText) {
    this.clientPreMovePhaseInterface = clientPreMovePhaseInterface;
    this.clientReadFilesInFolderInterface = clientReadFilesInFolderInterface;
    this.tabSwitcher = tabSwitcher;
    this.selectFolderButton = selectFolderButton;
    this.continueButton = continueButton;
    this.selectedFolderPathText = selectedFolderPathText;

    Injector injector = Guice.createInjector(new ImageModule());
    imageSorter = injector.getInstance(ImageSorter.class);

    setUpListeners();
  }

  private void setUpListeners() {
    continueButton.setOnAction((event) -> {
      if (selectedFolder != null) {
        tabSwitcher.setTabsInAnalyzeMode();
        imageSortFolder(selectedFolder);
      }
    });
  }

  public void setStageAndSetupListeners(final Stage primaryStage) {
    inputFolderSetup(primaryStage);
  }

  private void inputFolderSetup(final Stage primaryStage) {
    Injector injector = Guice.createInjector(new ImageModule());
    DirectoryChooser directoryChooser = injector.getInstance(DirectoryChooser.class);
    EventHandler<ActionEvent> selectInputEvent = e -> {
      tabSwitcher.resetTabs();
      File folder = directoryChooser.showDialog(primaryStage);
      selectedFolder = folder;
      if (folder != null) {
        selectedFolderPathText.setText(folder.getAbsolutePath());
        continueButton.setDisable(false);
      }
      else {
        selectedFolderPathText.setText("");
        continueButton.setDisable(true);
      }
    };
    selectFolderButton.setOnAction(selectInputEvent);
  }

  private void imageSortFolder(final File folder) {
    SortSettings sortSettings = new SortSettings();
    sortSettings.masterFolder = folder;

    Task<Integer> task = new Task<Integer>() {
      @Override
      protected Integer call() throws Exception {
        imageSorter.analyzeImages(clientReadFilesInFolderInterface, clientPreMovePhaseInterface, sortSettings);
        return 0;
      }
    };

    new Thread(task).start();

  }

}
