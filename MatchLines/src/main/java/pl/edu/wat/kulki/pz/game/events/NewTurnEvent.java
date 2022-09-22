package pl.edu.wat.kulki.pz.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class NewTurnEvent extends Event {
    public static final EventType<NewTurnEvent> NEW_TURN =
            new EventType<>(Event.ANY, "NEW_TURN");

    public NewTurnEvent() {
        super(NEW_TURN);
    }
}
