package hr.java.production.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Razred koji predstavlja trgovinu u sustavu proizvodnje.
 */
public class Store extends NamedEntity {
    private String webAddress;
    private Set<Item> items;


    /**
     * Konstruktor za inicijalizaciju trgovine.
     *
     * @param name       Ime trgovine.
     * @param webAddress Web adresa trgovine.
     * @param items      Polje proizvoda koji su dostupni u trgovini.
     */
    public Store(String name, String webAddress, Set<Item> items) {
        super(name);
        this.webAddress = webAddress;
        this.items = items;
    }


    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }


    @Override
    public boolean equals(Object tmpObject) {
        if (this == tmpObject) {
            return true;
        }
        if (tmpObject == null || getClass() != tmpObject.getClass()) {
            return false;
        }
        if (!super.equals(tmpObject)) {
            return false;
        }

        Store tmpStore = (Store) tmpObject;

        return Objects.equals(getWebAddress(), tmpStore.getWebAddress()) &&
                Objects.equals(getItems(), tmpStore.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWebAddress(), getItems());
    }
}
