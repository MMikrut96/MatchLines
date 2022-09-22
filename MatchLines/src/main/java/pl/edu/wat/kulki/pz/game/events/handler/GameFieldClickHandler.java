package pl.edu.wat.kulki.pz.game.events.handler;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class GameFieldClickHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        performAction(event);
    }

    protected abstract void performAction(Event event);
}
