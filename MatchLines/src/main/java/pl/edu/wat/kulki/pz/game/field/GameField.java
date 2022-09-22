package pl.edu.wat.kulki.pz.game.field;

import javafx.event.Event;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pl.edu.wat.kulki.pz.game.Board;
import pl.edu.wat.kulki.pz.game.events.MatchLineEvent;
import pl.edu.wat.kulki.pz.game.events.NewTurnEvent;
import pl.edu.wat.kulki.pz.game.events.PlayerMoveBallEvent;
import pl.edu.wat.kulki.pz.game.events.handler.GameFieldClickHandler;
import pl.edu.wat.kulki.pz.game.logic.BallMovementLogic;
import pl.edu.wat.kulki.pz.game.logic.MatchLineLogic;
import pl.edu.wat.kulki.pz.Main;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

public class GameField extends StackPane {
    private FieldStateEnum state;
    private OnBoardCoordinates coordinates;
    private Circle ball;
    private Rectangle field;
    private Board board;
    private Boolean ableToMoveHere = false;
    private static final String fieldStyle = ResourceLoader.property("fS");
    private static final String colorStylePattern = ResourceLoader.property("cSP");
    private static final String ballColorStylePattern = ResourceLoader.property("bCSP");
    private static final String emptyColorStylePattern = ResourceLoader.property("eSP");

    public FieldStateEnum getState() {
        return state;
    }

    public void setState(FieldStateEnum state) {
        this.state = state;
        ball.setStyle(getColorStyle(state,true));
    }

    public GameField(Rectangle gameField, FieldStateEnum state, OnBoardCoordinates coordinates, Circle circle, Board board) {
        createField(gameField, state, coordinates, circle, board);
        addEventHandler(PlayerMoveBallEvent.PLAYER_MOVE_BALL, event -> {
            Main.logger.info("Gracz Wykonał Ruch");
            event.consume();
        });
        addEventHandler(MatchLineEvent.MATCH_LINE,event -> {
            Main.logger.info("Sprawdzam czy jest utworzona lina jednego koloru.");
            int count = MatchLineLogic.findLineOnBoard(this);
            board.addPlayerPoints(count*count);
            if(count<5)
                board.getMainContainer().fireEvent(new NewTurnEvent());
            event.consume();
        });
    }

    private void createField(Rectangle gameField, FieldStateEnum state, OnBoardCoordinates coordinates, Circle circle, Board board) {
        ball = circle;
        ball.setStyle(fieldStyle);
        field = gameField;
        field.setStyle(fieldStyle);
        this.getChildren().addAll(field, ball);
        this.state = state;
        this.coordinates = coordinates;
        this.board = board;
        this.onMouseClickedProperty().set(new GameFieldClickHandler() {
            @Override
            protected void performAction(Event event) {
               BallMovementLogic.ballMovement(board,getInstance());
            }
        });
    }

    private GameField getInstance(){
        return this;
    }

    public Rectangle getField() {
        return field;
    }

    public Circle getBall() {
        return ball;
    }

    public void setAbleToMoveHere(Boolean ableToMoveHere) {
        this.ableToMoveHere = ableToMoveHere;
    }

    public Boolean getAbleToMoveHere() {
        return ableToMoveHere;
    }

    public OnBoardCoordinates getCoordinates() {
        return coordinates;
    }

    public Board getBoard() {
        return board;
    }

    private String getColorStyle(FieldStateEnum state, boolean isBall) {
        StringBuilder sb = new StringBuilder();
        sb.append(colorStylePattern);
        if (state != FieldStateEnum.EMPTY){
            sb.append(state.toString());
            if(isBall == true)
                sb.append(ballColorStylePattern);
        }else {
            sb.append(emptyColorStylePattern);
        }
        return sb.toString();
    }

}
