package hr.java.production.model;

import java.math.BigDecimal;

/**
 * Predstavlja proizvod u sustavu proizvodnje.
 * Svaki proizvod ima svoje svojstva, uključujući cijenu, dimenzije i kategoriju.
 */
public class Item extends NamedEntity {
    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    protected BigDecimal sellingPrice;
    protected Discount discount;


    /**
     * Konstruktor za stvaranje instance klase Item.
     *
     * @param name            Ime predmeta.
     * @param category        Kategorija predmeta.
     * @param width           Širina predmeta.
     * @param height          Visina predmeta.
     * @param length          Duljina predmeta.
     * @param productionCost  Trošak proizvodnje predmeta.
     * @param sellingPrice    Cijena prodaje predmeta.
     * @param discount        Popust na predmet.
     */
    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount) {
        super(name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }


}
