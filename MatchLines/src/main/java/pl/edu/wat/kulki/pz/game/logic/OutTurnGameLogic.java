package pl.edu.wat.kulki.pz.game.logic;

import pl.edu.wat.kulki.pz.game.Board;
import pl.edu.wat.kulki.pz.game.field.FieldStateEnum;
import pl.edu.wat.kulki.pz.game.field.GameField;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

import java.util.Random;

public class OutTurnGameLogic {

    private static final int dimensionNo = ResourceLoader.getIntFromXML("dimension");

    public static void startNewTurn(Board board, int amountOfBallToAdd) {
        GameField[][] gameBoard = board.getGameBoard();
        Random r = new Random();
        int x = 0;
        int y = 0;
        for (int i = 0; i < amountOfBallToAdd; ) {
            x = r.nextInt(dimensionNo);
            y = r.nextInt(dimensionNo);
            if (gameBoard[x][y].getState() == FieldStateEnum.EMPTY) {
                gameBoard[x][y].setState(randomColor(r));
                int count = MatchLineLogic.findLineOnBoard(gameBoard[x][y]);
                board.addPlayerPoints(count*count);
                i++;
            }
        }
    }

    private static FieldStateEnum randomColor(Random r) {
        int pick = 0;
        while (pick == 0)
            pick = r.nextInt(FieldStateEnum.values().length);
        return FieldStateEnum.values()[pick];
    }
}
