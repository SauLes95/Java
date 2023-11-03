package hr.java.production.exception;

public class PostalCodeException extends Exception{
    public PostalCodeException(){
        super();
    }

    public PostalCodeException(String message){
        super(message);
    }

    public PostalCodeException(Throwable cause){
        super(cause);
    }

    public PostalCodeException(String message, Throwable cause){
        super(message, cause);
    }

}


