package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Konkretna klasa koja predstavlja slani proizvod.
 */
public class Salty extends Item implements Edible {

    /**
     * Broj kalorija po kilogramu.
     */
    public static final Integer caloriesPerKilo = 3250;

    private BigDecimal weight;

    /**
     * Konstruktor za stvaranje instance klase Salty.
     *
     * @param name          Naziv proizvoda.
     * @param category      Kategorija proizvoda.
     * @param width         Širina proizvoda.
     * @param height        Visina proizvoda.
     * @param length        Dužina proizvoda.
     * @param productionCost Trošak proizvodnje proizvoda.
     * @param sellingPrice  Cijena proizvoda.
     * @param discount      Popust na proizvod.
     * @param weight        Težina proizvoda.
     */
    public Salty(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * Računa ukupan broj kilokalorija za slani proizvod.
     *
     * @return Ukupan broj kilokalorija za slani proizvod.
     */
    @Override
    public Integer calculateKilocalories() {
        return weight.multiply(new BigDecimal(caloriesPerKilo)).intValue();
    }

    /**
     * Računa cijenu slanog proizvoda s uključenim popustom.
     *
     * @return Cijena slanog proizvoda s uključenim popustom.
     */
    @Override
    public Double calculatePrice() {
        Double price = weight.multiply(sellingPrice).doubleValue();
        Double tmpDiscount = price * discount.discountAmount() / 100;
        return (price - tmpDiscount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Salty salty = (Salty) o;

        return Objects.equals(getWeight(), salty.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWeight());
    }

}
