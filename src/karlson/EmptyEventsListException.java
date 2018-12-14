package karlson;

public class EmptyEventsListException extends Exception {
    public EmptyEventsListException(){
        super("Список событий пуст!");
    }
}
