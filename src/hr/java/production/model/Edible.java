package hr.java.production.model;

/**
 * Sučelje koje označava da je objekt moguće konzumirati i pruža metode za izračun kalorija i cijene.
 */
public interface Edible {
    /**
     * Izračunava ukupan broj kilokalorija (kalorija) koje objekt pruža.
     *
     * @return Ukupan broj kilokalorija.
     */
    int calculateKilocalories();

    /**
     * Izračunava cijenu objekta koji se može konzumirati.
     *
     * @return Cijena objekta.
     */
    double calculatePrice();
}

