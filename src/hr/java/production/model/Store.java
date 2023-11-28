package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Razred koji predstavlja trgovinu u sustavu proizvodnje.
 */
public class Store extends NamedEntity implements Serializable {
    private String webAddress;
    private Set<Item> items = new HashSet<>();


    /**
     * Konstruktor za inicijalizaciju trgovine.
     *
     * @param name       Ime trgovine.
     * @param webAddress Web adresa trgovine.
     * @param items      Polje proizvoda koji su dostupni u trgovini.
     */
    public Store(Long id, String name, String webAddress, Set<Item> items) {
        super(id, name);
        this.webAddress = webAddress;
        this.items = items;
    }

    public Store (){}

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


    public void sortStoreItems(){
        List<Item> itemList = new ArrayList<>(items);

        itemList.sort((item1, item2) -> item1.getVolume().compareTo(item2.getVolume()));

        Set<Item> setItems = new LinkedHashSet<>(itemList);

        this.items = setItems;
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
