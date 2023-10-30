package hr.java.production.model;

import java.math.BigDecimal;

/**
 * Klasa koja predstavlja slatki proizvod u sustavu proizvodnje.
 */
public class Sweet extends Item implements Edible {
    public static final int caloriesPerKilo = 4800;
    private BigDecimal weight;

    /**
     * Konstruktor za inicijalizaciju slatkog proizvoda.
     *
     * @param name           Ime proizvoda.
     * @param category       Kategorija proizvoda.
     * @param width          Širina proizvoda.
     * @param height         Visina proizvoda.
     * @param length         Duljina proizvoda.
     * @param productionCost Trošak proizvodnje proizvoda.
     * @param sellingPrice   Cijena prodaje proizvoda.
     * @param discount       Popust na proizvod.
     * @param weight         Težina proizvoda.
     */
    public Sweet(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
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
     * Računa ukupan broj kilokalorija za slatki proizvod na temelju težine.
     *
     * @return Ukupan broj kilokalorija za proizvod.
     */
    @Override
    public int calculateKilocalories() {
        return weight.multiply(new BigDecimal(caloriesPerKilo)).intValue();
    }

    /**
     * Računa ukupnu cijenu slatkog proizvoda uzimajući u obzir težinu i popust.
     *
     * @return Ukupna cijena proizvoda s popustom.
     */
    @Override
    public double calculatePrice() {
        double price = weight.multiply(sellingPrice).doubleValue();
        double tmpDiscount = price * discount.discountAmount() / 100;
        return (price - tmpDiscount);
    }
}
