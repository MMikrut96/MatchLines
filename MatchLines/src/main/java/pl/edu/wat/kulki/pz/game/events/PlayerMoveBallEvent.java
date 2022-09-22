package pl.edu.wat.kulki.pz.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class PlayerMoveBallEvent extends Event {
    public static final EventType<PlayerMoveBallEvent> PLAYER_MOVE_BALL =
            new EventType<>(Event.ANY,"PLAYER_MOVE_BALL");

    public PlayerMoveBallEvent() {
        super(PLAYER_MOVE_BALL);
    }
}
