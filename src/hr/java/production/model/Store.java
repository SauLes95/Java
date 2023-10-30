package hr.java.production.model;

/**
 * Razred koji predstavlja trgovinu u sustavu proizvodnje.
 */
public class Store extends NamedEntity {
    private String webAddress;
    private Item[] items;

    /**
     * Konstruktor za inicijalizaciju trgovine.
     *
     * @param name       Ime trgovine.
     * @param webAddress Web adresa trgovine.
     * @param items      Polje proizvoda koji su dostupni u trgovini.
     */
    public Store(String name, String webAddress, Item[] items) {
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

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
