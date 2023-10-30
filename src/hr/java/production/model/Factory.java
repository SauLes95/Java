package hr.java.production.model;

/**
 * Predstavlja tvornicu u sustavu proizvodnje.
 * Svaka tvornica ima svoju adresu i proizvode koje proizvodi.
 */
public class Factory extends NamedEntity{

    private Address address;
    private Item[] items;


    /**
     * Konstruktor za stvaranje instance klase Factory.
     *
     * @param name    Ime tvornice.
     * @param address Adresa tvornice.
     * @param items   Proizvodi proizvedeni u tvornici.
     */
    public Factory(String name, Address address, Item[] items) {
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

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
