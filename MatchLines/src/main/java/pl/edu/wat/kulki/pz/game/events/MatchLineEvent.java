package pl.edu.wat.kulki.pz.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class MatchLineEvent extends Event {
    public static final EventType<MatchLineEvent> MATCH_LINE =
            new EventType<>(Event.ANY,"MATCH_LINE");

    public MatchLineEvent() {
        super(MATCH_LINE);
    }
}
