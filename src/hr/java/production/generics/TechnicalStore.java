package hr.java.production.generics;

import hr.java.production.model.Item;
import hr.java.production.model.Store;
import hr.java.production.model.Technical;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class TechnicalStore <T extends Technical> extends Store implements Technical{

    private List<T> technicalStoreItems = new ArrayList<>();;

    public TechnicalStore(String name, String webAddress, Set<Item> items, List<T> technicalStoreItems) {
        super(name, webAddress, items);
        this.technicalStoreItems = technicalStoreItems;
    }

    public TechnicalStore(){}

    public List<T> getTechnicalStoreItems() {
        return technicalStoreItems;
    }

    public void setTechnicalStoreItems(List<T> technicalStoreItems) {
        this.technicalStoreItems = technicalStoreItems;
    }

    public void addItemsToStore(List<T> items) {
        technicalStoreItems.addAll(items);
    }

    public void addItem(T item){
        technicalStoreItems.add(item);
    }


    @Override
    public Integer warrantyDuration() {
        return null;
    }
}
