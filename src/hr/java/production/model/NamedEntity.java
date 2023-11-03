package hr.java.production.model;

import java.util.Objects;

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

        NamedEntity tmpNamedEntity = (NamedEntity) tmpObject;
        return Objects.equals(getName(), tmpNamedEntity.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

}
