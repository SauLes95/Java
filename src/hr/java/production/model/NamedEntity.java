package hr.java.production.model;

/**
 * Apstraktna klasa koja predstavlja entitet s nazivom.
 */
public abstract class NamedEntity {
    private String name;

    /**
     * Konstruktor za stvaranje instance klase NamedEntity.
     *
     * @param name Naziv entiteta.
     */
    public NamedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
