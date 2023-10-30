package hr.java.production.model;

/**
 * Predstavlja kategoriju predmeta u sustavu proizvodnje.
 * Svaka kategorija ima svoje ime i opis.
 */
public class Category extends NamedEntity{

    private String description;


    /**
     * Konstruktor za stvaranje instance klase Category.
     *
     * @param name        Ime kategorije.
     * @param description Opis kategorije.
     */
    public Category(String name, String description) {
        super(name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
