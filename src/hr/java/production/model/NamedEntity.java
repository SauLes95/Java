package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Apstraktna klasa koja predstavlja entitet s nazivom.
 */
public abstract class NamedEntity implements Serializable {
    private String name;
    private Long id;


    /**
     * Konstruktor za stvaranje instance klase NamedEntity.
     *
     * @param name Naziv entiteta.
     */

    public NamedEntity( Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public NamedEntity(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
