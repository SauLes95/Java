package hr.java.production.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja popust na cijenu proizvoda ili usluge.
 *
 * Ovaj zapis sadrži informaciju o iznosu popusta koji se primjenjuje na cijenu proizvoda ili usluge.
 *
 * @param discountAmount Iznos popusta izražen kao cjelobrojna vrijednost.
 */
public record Discount(Integer discountAmount) implements Serializable {

    @Override
    public boolean equals(Object tmpObject) {
        if (this == tmpObject) {
            return true;
        }
        if (tmpObject == null || getClass() != tmpObject.getClass()) {
            return false;
        }

        Discount tmpDiscount = (Discount) tmpObject;

        return Objects.equals(discountAmount, tmpDiscount.discountAmount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(discountAmount);
    }
}
