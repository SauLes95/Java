package hr.java.production.model;

/**
 * Sučelje koje označava tehničke proizvode u sustavu proizvodnje.
 * Implementira metodu za dobivanje trajanja jamstva.
 */
public sealed interface Technical permits Laptop {
    /**
     * Vraća trajanje jamstva za tehnički proizvod.
     *
     * @return Trajanje jamstva u mjesecima.
     */
    Integer warrantyDuration();
}

