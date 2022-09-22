package pl.edu.wat.kulki.pz.exceptions;

import pl.edu.wat.kulki.pz.Main;

public class NoPropertyInFileException extends RuntimeException {
    public NoPropertyInFileException(String key){
        super("Can't find property at key: " + key);
        Main.logger.info("Wyjatek - Nie mozna znalezc parametru o kluczu "+ key);
    }
}
