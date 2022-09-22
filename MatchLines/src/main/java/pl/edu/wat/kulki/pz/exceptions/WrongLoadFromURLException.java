package pl.edu.wat.kulki.pz.exceptions;

public class WrongLoadFromURLException extends RuntimeException {
    public WrongLoadFromURLException(){
        super("Can't load data from adress.");
    }
}
