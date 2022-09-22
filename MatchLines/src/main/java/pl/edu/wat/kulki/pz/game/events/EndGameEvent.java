package pl.edu.wat.kulki.pz.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class EndGameEvent extends Event {
    public static final EventType<EndGameEvent> END_GAME =
            new EventType<>(Event.ANY, "END_GAME");
    public EndGameEvent() {
        super(END_GAME);
    }
}
