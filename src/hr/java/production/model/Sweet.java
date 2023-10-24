package hr.java.production.model;

import java.math.BigDecimal;

public class Sweet extends Item implements Edible{
    public static final int caloriesPerKilo = 4800;
    private BigDecimal weight;

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

    @Override
    public int calculateKilocalories() {

        return weight.multiply(new BigDecimal(caloriesPerKilo)).intValue();
    }

    @Override
    public double calculatePrice() {
        double price = weight.multiply(sellingPrice).doubleValue();
        double tmpDiscount = price * discount.discountAmount() / 100;
        return (price - tmpDiscount);
    }
}
