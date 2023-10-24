package hr.java.production.model;

import java.math.BigDecimal;

public non-sealed class Laptop extends Item implements Technical{
    private Integer warrantyDuration;

    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, Integer warrantyDuration) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warrantyDuration = warrantyDuration;
    }

    public void setWarrantyDuration(Integer warrantyDuration) {
        this.warrantyDuration = warrantyDuration;
    }

    @Override
    public Integer warrantyDuration(){
        return warrantyDuration;
    }

}
