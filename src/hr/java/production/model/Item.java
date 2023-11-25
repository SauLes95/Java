package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Comparator;

/**
 * Predstavlja proizvod u sustavu proizvodnje.
 * Svaki proizvod ima svoje svojstva, uključujući cijenu, dimenzije i kategoriju.
 */
public class Item extends NamedEntity{
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
    public Item(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount) {
        super(id, name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.discount = discount;

        if (sellingPrice.compareTo(BigDecimal.ZERO) == 0) {
            this.sellingPrice = null;
        } else {
            this.sellingPrice = sellingPrice;
        }

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

    public BigDecimal getVolume(){
        return width.multiply(length.multiply(height));
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

        Item tmpItem = (Item) tmpObject;

        return Objects.equals(category, tmpItem.category) &&
                Objects.equals(width, tmpItem.width) &&
                Objects.equals(height, tmpItem.height) &&
                Objects.equals(length, tmpItem.length) &&
                Objects.equals(productionCost, tmpItem.productionCost) &&
                Objects.equals(sellingPrice, tmpItem.sellingPrice) &&
                Objects.equals(discount, tmpItem.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, width, height, length, productionCost, sellingPrice, discount);
    }

    @Override
    public String toString() {
        return "Item name - "+ super.getName()
                + ", category - " + category.getName()
                + ", width - " + width
                + ", height - " + height
                + ", length - " + length
                + ", productionCost - " + productionCost
                + ", sellingPrice - " +  Optional.ofNullable(sellingPrice)
                                        .map(price -> price.compareTo(BigDecimal.ZERO) == 0 ? "potrebno definirati cijenu" : price.toString())
                                        .orElse("No selling price")
                + ", discount - " + (discount != null ? discount.discountAmount() : "No discount") ;
    }

}
