package hr.java.production.model;

import hr.java.production.generics.TechnicalStore;

/**
 * Sučelje koje označava tehničke proizvode u sustavu proizvodnje.
 * Implementira metodu za dobivanje trajanja jamstva.
 */
public interface Technical{
    /**
     * Vraća trajanje jamstva za tehnički proizvod.
     *
     * @return Trajanje jamstva u mjesecima.
     */
    Integer warrantyDuration();
}

