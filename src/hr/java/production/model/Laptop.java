package hr.java.production.model;

import java.math.BigDecimal;

/**
 * Klasa koja predstavlja laptop.
 */
public non-sealed class Laptop extends Item implements Technical {
    private Integer warrantyDuration;

    /**
     * Konstruktor za stvaranje instance klase Laptop.
     *
     * @param name            Ime laptopa.
     * @param category        Kategorija laptopa.
     * @param width           Širina laptopa.
     * @param height          Visina laptopa.
     * @param length          Duljina laptopa.
     * @param productionCost  Trošak proizvodnje laptopa.
     * @param sellingPrice    Cijena prodaje laptopa.
     * @param discount        Popust na laptop.
     * @param warrantyDuration Trajanje jamstva za laptop.
     */
    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, Integer warrantyDuration) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warrantyDuration = warrantyDuration;
    }

    /**
     * Postavlja trajanje jamstva za laptop.
     *
     * @param warrantyDuration Trajanje jamstva za laptop.
     */
    public void setWarrantyDuration(Integer warrantyDuration) {
        this.warrantyDuration = warrantyDuration;
    }

    /**
     * Vraća trajanje jamstva za laptop.
     *
     * @return Trajanje jamstva za laptop.
     */
    @Override
    public Integer warrantyDuration() {
        return warrantyDuration;
    }
}

