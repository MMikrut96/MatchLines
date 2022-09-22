package pl.edu.wat.kulki.pz.exceptions;

public class LoadFromDatabaseException extends RuntimeException {
    public LoadFromDatabaseException(){
        super("Task of get data from database ends unsuccessful.");
    }
}
