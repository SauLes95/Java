package hr.java.production.exception;

/**
 * Izuzetak koji označava pokušaj dodavanja duplikata predmeta.
 */
public class DuplicateItemException extends Exception {
    /**
     * Konstruktor koji stvara izuzetak bez poruke.
     */
    public DuplicateItemException() {
        super();
    }

    /**
     * Konstruktor koji stvara izuzetak s porukom.
     *
     * @param message Poruka o izuzetku.
     */
    public DuplicateItemException(String message) {
        super(message);
    }

    /**
     * Konstruktor koji stvara izuzetak s uzrokom.
     *
     * @param cause Uzrok izuzetka.
     */
    public DuplicateItemException(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor koji stvara izuzetak s porukom i uzrokom.
     *
     * @param message Poruka o izuzetku.
     * @param cause   Uzrok izuzetka.
     */
    public DuplicateItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
