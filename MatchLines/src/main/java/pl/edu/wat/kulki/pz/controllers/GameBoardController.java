package pl.edu.wat.kulki.pz.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.wat.kulki.pz.game.Board;
import pl.edu.wat.kulki.pz.game.events.EndGameEvent;
import pl.edu.wat.kulki.pz.game.events.NewGameEvent;
import pl.edu.wat.kulki.pz.Main;
import pl.edu.wat.kulki.pz.utils.BackgroundManager;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {

    @FXML
    public VBox windowContainer;
    @FXML
    BorderPane mainContainer;
    @FXML
    Label playerPoints;
    @FXML
    Button retreatButton;
    @FXML
    RadioMenuItem background1;
    @FXML
    RadioMenuItem background2;
    @FXML
    RadioMenuItem background3;
    @FXML
    RadioMenuItem plRMI;
    @FXML
    RadioMenuItem enRMI;

    private Board gameBoard;
    private static GameBoardController gameBoardController;
    private ResourceBundle resourceBundle;
    private static final ToggleGroup language = new ToggleGroup();
    private static final ToggleGroup background = new ToggleGroup();
    private static final String fxmlGameBoard = "gameBoardFXML";

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        gameBoardController = this;
        gameBoard = new Board(mainContainer,this);
        resourceBundle = resource;
        retreatButton.setAlignment(Pos.BOTTOM_CENTER);
        retreatButton.setOnAction(e -> actionForRetreat());
        enRMI.setToggleGroup(language);
        plRMI.setToggleGroup(language);
        background1.setToggleGroup(background);
        background2.setToggleGroup(background);
        background3.setToggleGroup(background);
        BackgroundManager.getSelectedInPreviousWindow(background1,background2,background3);
        setBackground();
        if(MainController.getInstance().plRMI.isSelected())
            plRMI.setSelected(true);
        else
            enRMI.setSelected(true);
    }

    public void backToMenu(){
        mainContainer.fireEvent(new EndGameEvent());
    }

    public void exitGame(){
        mainContainer.fireEvent(new EndGameEvent());
        Main.logger.info("Koniec dziania programu");
        Main.getExecutorService().shutdown();
        Platform.exit();
    }

    public void newGame(){
        gameBoard.resetGame();
        mainContainer.fireEvent(new NewGameEvent());
    }

    public void enClicked(){
        Locale.setDefault(new Locale("en"));
        reloadStage();
        gameBoardController.enRMI.setSelected(true);
    }

    public void plClicked(){
        Locale.setDefault(new Locale("pl"));
        reloadStage();
        gameBoardController.plRMI.setSelected(true);
    }

    public void reloadStage(){
        try {
            windowContainer.getScene().setRoot(ResourceLoader.getParentForFXML(resourceBundle,ResourceLoader.property(fxmlGameBoard)));
        } catch (IOException e) {
            Main.logger.info("Wystapil blad przy przeladowaniu okna");
            e.printStackTrace();
        }
        BackgroundManager.getSelectedInPreviousWindow(gameBoardController.background1,gameBoardController.background2,gameBoardController.background3);
        gameBoardController.setBackground();
        gameBoardController.gameBoard=this.gameBoard;
        gameBoardController.gameBoard.setGameBoardController(gameBoardController);
        gameBoardController.mainContainer.setCenter(gameBoardController.gameBoard.getGameBoardPane());
        gameBoardController.playerPoints.setText(this.playerPoints.getText());
        gameBoardController.mainContainer=this.mainContainer;
        ((Stage)gameBoardController.windowContainer.getScene().getWindow()).setTitle(ResourceLoader.localeProperty("GameName",Locale.getDefault().getLanguage()));
        gameBoardController.windowContainer.getScene().getWindow().setOnCloseRequest(e->gameBoardController.exitGame());
    }

    public void setBackground(){
        BackgroundManager.setBackgroundStyle(background.getSelectedToggle().getUserData().toString(),windowContainer,background1,background2,background3);
    }

    public static GameBoardController getInstance(){
        return gameBoardController;
    }

    public void actionForRetreat(){
        mainContainer.fireEvent(new EndGameEvent());
    }

    public Label getPlayerPoints() {
        return playerPoints;
    }
}
