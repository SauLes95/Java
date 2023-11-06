package hr.java.production.model;

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
}
