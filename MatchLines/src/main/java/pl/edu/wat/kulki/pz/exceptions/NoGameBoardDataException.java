package pl.edu.wat.kulki.pz.exceptions;

import pl.edu.wat.kulki.pz.Main;

public class NoGameBoardDataException extends RuntimeException {
    public NoGameBoardDataException(){
        super("Can't find game Board Data");
        Main.logger.info("Wyjatek - Nie mozna znalezc danych planszy");
    }
}
