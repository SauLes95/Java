package hr.java.production.model;

import java.math.BigDecimal;

public class Salty extends Item implements Edible{

    public static final int caloriesPerKilo = 3250;
    private BigDecimal weight;
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
