package hr.java.production.model;

/**
 * Predstavlja popust na cijenu proizvoda ili usluge.
 *
 * Ovaj zapis sadrži informaciju o iznosu popusta koji se primjenjuje na cijenu proizvoda ili usluge.
 *
 * @param discountAmount Iznos popusta izražen kao cjelobrojna vrijednost.
 */
public record Discount(Integer discountAmount) {
}
