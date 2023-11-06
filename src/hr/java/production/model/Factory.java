package hr.java.production.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Predstavlja tvornicu u sustavu proizvodnje.
 * Svaka tvornica ima svoju adresu i proizvode koje proizvodi.
 */
public class Factory extends NamedEntity{

    private Address address;
    private Set<Item> items;


    /**
     * Konstruktor za stvaranje instance klase Factory.
     *
     * @param name    Ime tvornice.
     * @param address Adresa tvornice.
     * @param items   Proizvodi proizvedeni u tvornici.
     */
    public Factory(String name, Address address, Set<Item> items) {
        super(name);
        this.address = address;
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
