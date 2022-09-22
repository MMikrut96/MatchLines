package pl.edu.wat.kulki.pz.game.logic;

import pl.edu.wat.kulki.pz.game.Board;
import pl.edu.wat.kulki.pz.game.events.MatchLineEvent;
import pl.edu.wat.kulki.pz.game.events.PlayerMoveBallEvent;
import pl.edu.wat.kulki.pz.game.field.FieldStateEnum;
import pl.edu.wat.kulki.pz.game.field.GameField;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

public class BallMovementLogic {

    private static final String availableFieldColorPattern = ResourceLoader.property("aFS");
    private static final String fieldColorPattern = ResourceLoader.property("fS");

    public static void ballMovement(Board board, GameField gameField) {
        if (board.getClickedBall() == null) {
            if (gameField.getState() != FieldStateEnum.EMPTY) {
                board.setClickedBall(gameField);
                showAvailableMovements(gameField, board, true);
            }
        } else {
            if (gameField.getState() == FieldStateEnum.EMPTY && gameField.getAbleToMoveHere() == true) {
                resetGameBoard(board);
                GameField tmp = board.getClickedBall();
                gameField.setState(tmp.getState());
                tmp.setState(FieldStateEnum.EMPTY);
                board.setClickedBall(null);
                gameField.fireEvent(new PlayerMoveBallEvent());
                gameField.fireEvent(new MatchLineEvent());
            } else {
                if (board.getClickedBall().equals(gameField)) {
                    board.setClickedBall(null);
                    resetGameBoard(board);
                } else {
                    if (gameField.getState() != FieldStateEnum.EMPTY) {
                        resetGameBoard(board);
                        board.setClickedBall(gameField);
                        showAvailableMovements(gameField, board, true);
                    }
                }
            }
        }
    }

    private static void showAvailableMovements(GameField gameField, Board board, boolean firstStart) {
        if (gameField.getAbleToMoveHere() == true)
            return;
        int x = gameField.getCoordinates().getX();
        int y = gameField.getCoordinates().getY();
        if (firstStart || (gameField.getState() == FieldStateEnum.EMPTY)) {
            gameField.getField().setStyle(availableFieldColorPattern);
            if (!firstStart)
                gameField.getBall().setStyle(availableFieldColorPattern);
            gameField.setAbleToMoveHere(true);
            if (x - 1 >= 0)
                showAvailableMovements(board.getGameBoard()[x - 1][y], board, false);
            if (x + 1 < 9)
                showAvailableMovements(board.getGameBoard()[x + 1][y], board, false);
            if (y - 1 >= 0)
                showAvailableMovements(board.getGameBoard()[x][y - 1], board, false);
            if (y + 1 < 9)
                showAvailableMovements(board.getGameBoard()[x][y + 1], board, false);
        } else
            return;
    }

    private static void resetGameBoard(Board board) {
        for (GameField[] fields : board.getGameBoard())
            for (GameField field : fields) {
                if (field.getState() == FieldStateEnum.EMPTY) {
                    field.getField().setStyle(fieldColorPattern);
                    field.getBall().setStyle(fieldColorPattern);
                    field.setAbleToMoveHere(false);
                } else {
                    field.getField().setStyle(fieldColorPattern);
                    field.setAbleToMoveHere(false);
                }
            }
    }
}
