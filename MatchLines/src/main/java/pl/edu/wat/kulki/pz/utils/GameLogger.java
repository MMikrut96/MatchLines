package pl.edu.wat.kulki.pz.utils;

import javafx.concurrent.Task;
import pl.edu.wat.kulki.pz.Main;
import pl.edu.wat.kulki.pz.exceptions.CannotCreateGameLogger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameLogger {
    private static final Logger logger = Logger.getLogger(GameLogger.class.getName());

    public GameLogger(){
        try {
            FileHandler fileHandler = new FileHandler("GameLog.log",true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.setUseParentHandlers(false);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new CannotCreateGameLogger();
        }

    }
    public void info(String msg) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                logger.info(msg);
                return null;
            }
        };
        Main.getExecutorService().submit(task);
    }
}
