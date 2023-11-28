package hr.java.production.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja kategoriju predmeta u sustavu proizvodnje.
 * Svaka kategorija ima svoje ime i opis.
 */
public class Category extends NamedEntity implements Serializable {

    private String description;


    /**
     * Konstruktor za stvaranje instance klase Category.
     *
     * @param name        Ime kategorije.
     * @param description Opis kategorije.
     */
    public Category( Long id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object tmpObject) {
        if (this == tmpObject) {
            return true;
        }
        if (tmpObject == null || getClass() != tmpObject.getClass()) {
            return false;
        }

        Category tmpCategory = (Category) tmpObject;

        return Objects.equals(getDescription(), tmpCategory.getDescription());
    }


    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by {@code HashMap}.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription());
    }

}
