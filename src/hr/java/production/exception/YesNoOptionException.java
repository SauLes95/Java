package hr.java.production.exception;

public class YesNoOptionException extends Exception{
    public YesNoOptionException() {
        super();
    }

    /**
     * Konstruktor koji stvara izuzetak s porukom.
     *
     * @param message Poruka o izuzetku.
     */
    public YesNoOptionException(String message) {
        super(message);
    }

    /**
     * Konstruktor koji stvara izuzetak s uzrokom.
     *
     * @param cause Uzrok izuzetka.
     */
    public YesNoOptionException(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor koji stvara izuzetak s porukom i uzrokom.
     *
     * @param message Poruka o izuzetku.
     * @param cause   Uzrok izuzetka.
     */
    public YesNoOptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
