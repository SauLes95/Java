package hr.java.production.exception;

public class SweetSaltyException extends Exception{
    public SweetSaltyException(){
        super();
    }

    public SweetSaltyException(String message){
        super(message);
    }

    public SweetSaltyException(Throwable cause){
        super(cause);
    }

    public SweetSaltyException(String message, Throwable cause){
        super(message, cause);
    }
}
