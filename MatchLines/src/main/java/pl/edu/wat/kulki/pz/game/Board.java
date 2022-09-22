package pl.edu.wat.kulki.pz.game;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pl.edu.wat.kulki.pz.controllers.GameBoardController;
import pl.edu.wat.kulki.pz.controllers.MainController;
import pl.edu.wat.kulki.pz.game.events.EndGameEvent;
import pl.edu.wat.kulki.pz.game.events.NewGameEvent;
import pl.edu.wat.kulki.pz.game.events.NewTurnEvent;
import pl.edu.wat.kulki.pz.game.field.FieldStateEnum;
import pl.edu.wat.kulki.pz.game.field.GameField;
import pl.edu.wat.kulki.pz.game.field.OnBoardCoordinates;
import pl.edu.wat.kulki.pz.game.logic.OutTurnGameLogic;
import pl.edu.wat.kulki.pz.Main;
import pl.edu.wat.kulki.pz.utils.database.GameResult;
import pl.edu.wat.kulki.pz.utils.dialogs.EndGameDialog;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

import java.io.IOException;

public class Board {

    private GameBoardController gameBoardController;
    private static final String amountOfBallPerTurn = "ballPerTurn";
    private static final String amountOfBallAtStart = "ballOnStart";
    private static final String boardDimensionNo = "dimension";
    private static final String fieldSize = "fieldSize";
    private BorderPane mainContainer;
    private GameField[][] gameBoard;
    private GameField clickedBall = null;
    private GridPane gameBoardPane;

    public Board(BorderPane mainContainer, GameBoardController controller) {
        this.mainContainer = mainContainer;
        this.gameBoardController = controller;
        createBoard();
        mainContainer.addEventHandler(NewGameEvent.NEW_GAME, event -> {
            Main.logger.info("Nowa Gra Się Rozpoczyna.");
            OutTurnGameLogic.startNewTurn(this, ResourceLoader.getIntFromXML(amountOfBallAtStart));
            event.consume();
        });
        mainContainer.addEventHandler(NewTurnEvent.NEW_TURN, event -> {
            Main.logger.info("Rozpoczynam nową turę.");
            if (countEmptyFields() <= ResourceLoader.getIntFromXML(amountOfBallPerTurn)) {
                OutTurnGameLogic.startNewTurn(this, countEmptyFields());
                mainContainer.fireEvent(new EndGameEvent());
            } else {
                OutTurnGameLogic.startNewTurn(this, ResourceLoader.getIntFromXML(amountOfBallPerTurn));
            }
            event.consume();
        });
        mainContainer.addEventHandler(EndGameEvent.END_GAME, event -> {
            Main.logger.info("Koniec Gry.\n Uzyskany wynik :" + gameBoardController.getPlayerPoints().getText());
            new GameResult().saveResult(EndGameDialog.getPlayerName(),gameBoardController.getPlayerPoints().getText());
            Stage gameBoardWindow = (Stage) gameBoardController.windowContainer.getScene().getWindow();
            gameBoardWindow.close();
            try {
                MainController.getInstance().loadMainMenu(gameBoardController);
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        });
    }

    private void createBoard() {
        GridPane pane = new GridPane();
        gameBoard = new GameField[ResourceLoader.getIntFromXML(boardDimensionNo)][ResourceLoader.getIntFromXML(boardDimensionNo)];
        pane.setHgap(2.0);
        pane.setVgap(2.0);

        for (int i = 0; i < ResourceLoader.getIntFromXML(boardDimensionNo); i++) {
            for (int j = 0; j < ResourceLoader.getIntFromXML(boardDimensionNo); j++) {
                GameField field = new GameField(new Rectangle(ResourceLoader.getDoubleFromXML(fieldSize), ResourceLoader.getDoubleFromXML(fieldSize))
                        , FieldStateEnum.EMPTY, new OnBoardCoordinates(i, j)
                        , new Circle(ResourceLoader.getDoubleFromXML(fieldSize) / 2), this);
                gameBoard[i][j] = field;
                pane.add(field, i, j);
            }
        }
        gameBoardPane = pane;
        mainContainer.setCenter(pane);
    }

    public GameField[][] getGameBoard() {
        return gameBoard;
    }

    public GameField getClickedBall() {
        return clickedBall;
    }

    public void setClickedBall(GameField clickedBall) {
        this.clickedBall = clickedBall;
    }

    public BorderPane getMainContainer() {
        return mainContainer;
    }

    public int countEmptyFields() {
        int count = 0;
        for (GameField[] row : gameBoard) {
            for (GameField field : row) {
                if (field.getState() == FieldStateEnum.EMPTY)
                    count++;
            }
        }
        return count;
    }

    public void resetGame() {
        for (GameField[] row : gameBoard) {
            for (GameField field : row) {
                field.setState(FieldStateEnum.EMPTY);
            }
        }
        gameBoardController.getPlayerPoints().setText("0");
    }

    public GridPane getGameBoardPane() {
        return gameBoardPane;
    }

    public void setGameBoardController(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    public void addPlayerPoints(int points) {
        int actualPoints = Integer.parseInt(gameBoardController.getPlayerPoints().getText());
        actualPoints += points;
        if (points > 0)
            Main.logger.info("Znaleziono linie jednego koloru. Przyznaje graczowi za to " + points + " punktow.");
        gameBoardController.getPlayerPoints().setText(String.valueOf(actualPoints));
    }
}
