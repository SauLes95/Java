package hr.java.production.exception;

/**
 * Izuzetak koji označava da je došlo do pokušaja dodavanja duplikata kategorije.
 */
public class DuplicateCategoryException extends RuntimeException {
    /**
     * Konstruktor koji prima poruku o izuzetku.
     *
     * @param message Poruka o izuzetku.
     */
    public DuplicateCategoryException(String message) {
        super(message);
    }

    /**
     * Konstruktor koji prima uzrok izuzetka.
     *
     * @param cause Uzrok izuzetka.
     */
    public DuplicateCategoryException(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor koji prima poruku i uzrok izuzetka.
     *
     * @param message Poruka o izuzetku.
     * @param cause   Uzrok izuzetka.
     */
    public DuplicateCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
