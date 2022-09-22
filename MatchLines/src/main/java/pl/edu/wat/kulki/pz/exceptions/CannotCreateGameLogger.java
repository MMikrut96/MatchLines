package pl.edu.wat.kulki.pz.exceptions;

public class CannotCreateGameLogger extends RuntimeException {
    public CannotCreateGameLogger(){
        super("Can't create log file for file handler");
    }
}
