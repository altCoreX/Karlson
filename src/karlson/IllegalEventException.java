package karlson;

public class IllegalEventException extends RuntimeException{
    IllegalEventException(){
        super("Заданы недопустимые параметры для события!");
    }
}
