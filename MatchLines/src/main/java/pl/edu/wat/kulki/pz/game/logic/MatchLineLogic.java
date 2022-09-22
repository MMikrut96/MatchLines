package pl.edu.wat.kulki.pz.game.logic;

import pl.edu.wat.kulki.pz.game.field.FieldStateEnum;
import pl.edu.wat.kulki.pz.game.field.GameField;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

public class MatchLineLogic {

    private static final int dimensionNo = ResourceLoader.getIntFromXML("dimension");

    public static int findLineOnBoard(GameField field) {
        int count = 0;
        count = lookTopLeftToBottomRight(field.getCoordinates().getX(), field.getCoordinates().getY(), field);
        if (count != 0)
            return count;
        count = lookTopRightToBottomLeft(field.getCoordinates().getX(), field.getCoordinates().getY(), field);
        if (count != 0)
            return count;
        count = lookVertical(field.getCoordinates().getX(), field);
        if (count != 0)
            return count;
        count = lookHorizontal(field.getCoordinates().getY(), field);
        if (count != 0)
            return count;
        return count;
    }

    private static int lookTopLeftToBottomRight(int x, int y, GameField field) {
        int count = 0;
        FieldStateEnum pattern = field.getState();
        GameField[][] gameBoard = field.getBoard().getGameBoard();
        while (x != 0 && y != 0) {
            x--;
            y--;
        }
        for (int i = 0; i < dimensionNo; i++)
            if (x + i == dimensionNo || y + i == dimensionNo) {
                break;
            } else {
                if (gameBoard[x + i][y + i].getState().equals(pattern)) {
                    count++;
                    if (count > 4) {
                        for (int k = 0; k < count; k++) {
                            gameBoard[x + i - k][y + i - k].setState(FieldStateEnum.EMPTY);
                        }
                    }
                } else {
                    if (count > 4) {
                        break;
                    }
                    count = 0;
                }
            }
        if (count < 5)
            count = 0;
        return count;
    }

    private static int lookTopRightToBottomLeft(int x, int y, GameField field) {
        int count = 0;
        FieldStateEnum pattern = field.getState();
        GameField[][] gameBoard = field.getBoard().getGameBoard();
        while (x != dimensionNo - 1 && y != 0) {
            x++;
            y--;
        }
        for (int i = 0; i < dimensionNo; i++)
            if (x - i == 0 || y + i == dimensionNo) {
                break;
            } else {
                if (gameBoard[x - i][y + i].getState().equals(pattern)) {
                    count++;
                    if (count > 4) {
                        for (int k = 0; k < count; k++) {
                            gameBoard[x - i + k][y + i - k].setState(FieldStateEnum.EMPTY);
                        }
                    }
                } else {
                    if (count > 4) {
                        break;
                    }
                    count = 0;
                }
            }
        if (count < 5)
            count = 0;
        return count;
    }

    private static int lookHorizontal(int y, GameField field) {
        int count = 0;
        FieldStateEnum pattern = field.getState();
        GameField[][] gameBoard = field.getBoard().getGameBoard();
        for (int i = 0; i < dimensionNo; i++) {
            if (gameBoard[i][y].getState().equals(pattern)) {
                count++;
                if (count > 4) {
                    for (int k = 0; k < count; k++) {
                        gameBoard[i - k][y].setState(FieldStateEnum.EMPTY);
                    }
                }
            } else {
                if (count > 4) {
                    break;
                }
                count = 0;
            }
        }
        if (count < 5)
            count = 0;
        return count;
    }

    private static int lookVertical(int x, GameField field) {
        int count = 0;
        FieldStateEnum pattern = field.getState();
        GameField[][] gameBoard = field.getBoard().getGameBoard();
        int i = 0;
        while (i < dimensionNo) {
            if (gameBoard[x][i].getState().equals(pattern)) {
                count++;
                if (count > 4) {
                    for (int k = 0; k < count; k++) {
                        gameBoard[x][i - k].setState(FieldStateEnum.EMPTY);
                    }
                }
            } else {
                if (count > 4) {
                    break;
                }
                count = 0;
            }
            i++;
        }
        if (count < 5)
            count = 0;
        return count;
    }
}
