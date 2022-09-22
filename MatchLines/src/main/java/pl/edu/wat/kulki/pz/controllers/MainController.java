package pl.edu.wat.kulki.pz.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.wat.kulki.pz.game.events.NewGameEvent;
import pl.edu.wat.kulki.pz.Main;
import pl.edu.wat.kulki.pz.utils.BackgroundManager;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;
import pl.edu.wat.kulki.pz.utils.StatisticsTable;
import pl.edu.wat.kulki.pz.utils.dialogs.CuriosityDialog;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Button startButton;
    @FXML
    RadioMenuItem plRMI;
    @FXML
    RadioMenuItem enRMI;
    @FXML
    RadioMenuItem background1;
    @FXML
    RadioMenuItem background2;
    @FXML
    RadioMenuItem background3;
    @FXML
    public VBox mainContainer;

    private static MainController mainController;
    private ResourceBundle resourceBundle;
    private static String fxmlGameBoard = "gameBoardFXML";
    private static String fxmlMainMenu = "mainFXML";
    private static String windowWidth = "window.width";
    private static String windowHeight = "window.height";
    private static final ToggleGroup language = new ToggleGroup();
    private static final ToggleGroup background = new ToggleGroup();

    public void startClicked() throws IOException {
        Parent game = ResourceLoader.getParentForFXML(resourceBundle,ResourceLoader.property(fxmlGameBoard));
        Stage loadBorder = (Stage) startButton.getScene().getWindow();
        loadBorder.close();
        loadBorder.setScene(new Scene(game,Integer.parseInt(ResourceLoader.property(windowWidth)), Integer.parseInt(ResourceLoader.property(windowHeight))));
        loadBorder.setResizable(false);
        GameBoardController.getInstance().windowContainer.getScene().getWindow().setOnCloseRequest(e -> GameBoardController.getInstance().exitGame());
        loadBorder.show();
        GameBoardController.getInstance().mainContainer.fireEvent(new NewGameEvent());
    }

    public void enClicked(){
        Locale.setDefault(new Locale("en"));
        reloadStage();
        mainController.enRMI.setSelected(true);
        BackgroundManager.getSelectedInPreviousWindow(mainController.background1,mainController.background2,mainController.background3);
        mainController.setBackground();
    }

    public void plClicked(){
        Locale.setDefault(new Locale("pl"));
        reloadStage();
        mainController.plRMI.setSelected(true);
        BackgroundManager.getSelectedInPreviousWindow(mainController.background1,mainController.background2,mainController.background3);
        mainController.setBackground();
    }

    public void reloadStage(){
        try {
            mainContainer.getScene().setRoot(ResourceLoader.getParentForFXML(resourceBundle,ResourceLoader.property(fxmlMainMenu)));
        } catch (IOException e) {
            e.printStackTrace();
            Main.logger.info("Wystapil blad przy przeladowaniu okna");
        }
        ((Stage)mainController.mainContainer.getScene().getWindow()).setTitle(ResourceLoader.localeProperty("GameName",Locale.getDefault().getLanguage()));
    }

    public void loadMainMenu(GameBoardController gameBoardController) throws IOException {
        Stage menuStage = new Stage();
        Parent mainMenu = ResourceLoader.getParentForFXML(resourceBundle,ResourceLoader.property(fxmlMainMenu));
        menuStage.setScene(new Scene(mainMenu,Integer.parseInt(ResourceLoader.property(windowWidth)), Integer.parseInt(ResourceLoader.property(windowHeight))));
        menuStage.setOnCloseRequest(e -> closeGameItem());
        menuStage.show();
        if(gameBoardController.plRMI.isSelected())
            mainController.plClicked();
        else
            mainController.enClicked();
        BackgroundManager.getSelectedInPreviousWindow(mainController.background1,mainController.background2,mainController.background3);
        mainController.setBackground();
    }

    public void closeGameItem(){
        Main.logger.info("Koniec dzialania programu");
        Main.getExecutorService().shutdown();
        Platform.exit();
    }

    public void showStatistics(){
        new StatisticsTable();
        mainContainer.getScene().getWindow().setOnCloseRequest(e->closeGameItem());
    }

    public void showCuriosity(){
        CuriosityDialog.showCuriosity();
    }

    public static MainController getInstance(){
        return mainController;
    }

    public void setBackground(){
        BackgroundManager.setBackgroundStyle(background.getSelectedToggle().getUserData().toString(),mainContainer,background1,background2,background3);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enRMI.setToggleGroup(language);
        plRMI.setToggleGroup(language);
        background1.setToggleGroup(background);
        background2.setToggleGroup(background);
        background3.setToggleGroup(background);
        background1.setSelected(true);
        plRMI.setSelected(true);
        mainController=this;
        resourceBundle=resources;
    }
}
