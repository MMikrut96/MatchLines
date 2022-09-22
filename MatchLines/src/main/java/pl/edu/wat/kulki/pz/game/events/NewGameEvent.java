package pl.edu.wat.kulki.pz.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class NewGameEvent extends Event {
    public static final EventType<NewGameEvent> NEW_GAME =
            new EventType<>(Event.ANY, "NEW_GAME");
    public NewGameEvent() {
        super(NEW_GAME);
    }
}
