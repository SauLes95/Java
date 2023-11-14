package hr.java.production.generics;

import hr.java.production.model.Edible;
import hr.java.production.model.Item;
import hr.java.production.model.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FoodStore <T extends Edible> extends Store implements Edible{
    private List<T> foodStoreItems = new ArrayList<>();;

    public FoodStore(String name, String webAddress, Set<Item> items, List<T> foodStoreItems) {
        super(name, webAddress, items);
        this.foodStoreItems = foodStoreItems;
    }

    public FoodStore(){}
    public List<T> getFoodStoreItems() {
        return foodStoreItems;
    }

    public void setFoodStoreItems(List<T> foodStoreItems) {
        this.foodStoreItems = foodStoreItems;
    }

    public void addItemsToStore(List<T> items) {
        foodStoreItems.addAll(items);
    }

    public void addItem(T item){
        foodStoreItems.add(item);
    }

    @Override
    public Integer calculateKilocalories() {
        return null;
    }

    @Override
    public Double calculatePrice() {
        return null;
    }
}
