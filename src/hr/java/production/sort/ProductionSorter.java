package hr.java.production.sort;

import hr.java.production.model.Item;

import java.util.Comparator;

public class ProductionSorter implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.getSellingPrice().compareTo(item2.getSellingPrice());
    }
}
