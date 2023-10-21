package hr.java.production.main;

import hr.java.production.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    private static final int NUM_CATEGORIES = 3;
    private static final int NUM_ITEMS = 5;
    private static final int NUM_FACTORIES = 2;
    private static final int NUM_STORES = 2;

    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Category[] categories = setCategories();
        Item[] items = setItems(categories);
        Factory[] factories = setFactories(items);
        Store[] stores = setStores(items);

        Factory theBiggestVolumeFactory = factoryWithTheBiggestVolumeItem(factories);
        System.out.println("Factory with the item that has biggest volume is " + theBiggestVolumeFactory.getName());

        Store theCheapestStore = storeWithTheCheapestItem(stores);
        System.out.println("Store  with the cheapest item is " + theCheapestStore.getName());


    }

    static Item[] reduceItemsSize(Item[] items) {

        return new Item[items.length - 1];
    }

    static Item[] removeItem(Item[] items, int tmpItemNumber) {
        Item[] newItems = reduceItemsSize(items);
        int tmpCounter = 0;
        for (int i = 0; i < items.length; i++) {
            if (i != tmpItemNumber - 1) {
                newItems[tmpCounter] = items[i];
                tmpCounter++;
            }
        }

        return newItems;
    }

    static Item[] increaseItemsSize(Item[] factoryItems) {

        Item[] newItems = new Item[factoryItems.length + 1];

        for (int i = 0; i < factoryItems.length; i++) {
            newItems[i] = factoryItems[i];
        }

        return newItems;
    }

    static Item[] addItem(Item item, Item[] items) {
        Item[] newItems = increaseItemsSize(items);
        newItems[items.length] = item;

        return newItems;
    }


    static Category[] setCategories() {
        Category[] categories = new Category[NUM_CATEGORIES];

        System.out.println("INSERT CATEGORIES");
        for (int i = 0; i < NUM_CATEGORIES; i++) {

            System.out.println("CATEGORY " + (i + 1));
            System.out.print("\tName: ");
            String tmpName = scanner.nextLine();

            System.out.print("\tDescription: ");
            String tmpDescription = scanner.nextLine();

            categories[i] = new Category(tmpName, tmpDescription);
        }

        return categories;
    }

    static Item[] setItems(Category[] categories) {
        Item[] items = new Item[NUM_ITEMS];

        System.out.println("INSERT ITEMS");
        for (int i = 0; i < NUM_ITEMS; i++) {

            System.out.println("ITEM " + (i + 1));
            System.out.print("\tName: ");
            String tmpName = scanner.nextLine();

            int categoryNumber;

            do {
                System.out.println("\tEnter the number of the category to which the item belongs to: ");
                for (int j = 0; j < NUM_CATEGORIES; j++) {
                    System.out.println("\t" + (j + 1) + ". " + categories[j].getName());
                }

                categoryNumber = scanner.nextInt();
                scanner.nextLine();

                if (categoryNumber < 1 || categoryNumber > NUM_CATEGORIES) {
                    System.out.println("\tWrong number of category, please enter correct number of category");
                }

            } while (categoryNumber < 1 || categoryNumber > NUM_CATEGORIES);


            Category tmpCategory = categories[categoryNumber - 1];

            System.out.print("\tWidth: ");
            BigDecimal tmpWidth = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.print("\tHeight: ");
            BigDecimal tmpHeight = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.print("\tLength: ");
            BigDecimal tmpLength = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.print("\tProduction cost: ");
            BigDecimal tmpProductionCost = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.print("\tselling price: ");
            BigDecimal tmpSellingPrice = scanner.nextBigDecimal();
            scanner.nextLine();

            items[i] = new Item(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice);
        }
        return items;
    }

    static Factory[] setFactories(Item[] items) {

        Factory[] factories = new Factory[NUM_FACTORIES];

        System.out.println("INSERT FACTORIES");

        for (int i = 0; i < NUM_FACTORIES; i++) {

            System.out.println("FACTORY " + (i + 1));

            System.out.print("\tName: ");
            String tmpName = scanner.nextLine();

            System.out.print("\tStreet: ");
            String tmpStreet = scanner.nextLine();

            System.out.print("\tHouse number: ");
            String tmpHouseNumber = scanner.nextLine();

            System.out.print("\tCity: ");
            String tmpCity = scanner.nextLine();

            System.out.print("\tPostal code: ");
            String tmpPostalCode = scanner.nextLine();

            Address tmpAddress = new Address(tmpStreet, tmpHouseNumber, tmpCity, tmpPostalCode);

            Item[] factoryItems = new Item[0];
            if(items.length > 0){
                System.out.println("\tEnter the number in front of the item that is produced in the factory, if the input is complete, enter 0");

                int tmpItemNum;
                do {

                    for (int j = 0; j < items.length; j++) {
                        System.out.println((j + 1) + ". " + items[j].getName());
                    }

                    tmpItemNum = scanner.nextInt();
                    scanner.nextLine();

                    if (tmpItemNum != 0 && items.length > 0) {

                        factoryItems = addItem(items[tmpItemNum - 1], factoryItems);
                        items = removeItem(items, tmpItemNum);
                    }

                } while (tmpItemNum != 0 && items.length > 0);
            }
            else{
                System.out.println("\tThere is no items left to place in a factory");
            }


            factories[i] = new Factory(tmpName, tmpAddress, factoryItems);
        }

        return factories;
    }


    static Store[] setStores(Item[] items) {
        Store[] stores = new Store[NUM_STORES];

        System.out.println("INSERT STORES");

        for (int i = 0; i < NUM_STORES; i++) {

            System.out.println("STORE " + (i + 1));

            System.out.print("\tName: ");
            String tmpName = scanner.nextLine();

            System.out.print("\tE-mail address: ");
            String tmpEmailAddress = scanner.nextLine();

            Item[] storeItems = new Item[0];

            if(items.length > 0){
                System.out.println("\tEnter the number in front of the thing that is sold in the store, if the input is complete, enter 0");

                int tmpItemNum = 0;
                do {

                    for (int j = 0; j < items.length; j++) {
                        System.out.println((j + 1) + ". " + items[j].getName());
                    }

                    tmpItemNum = scanner.nextInt();
                    scanner.nextLine();

                    if (tmpItemNum != 0 && items.length > 0) {

                        storeItems = addItem(items[tmpItemNum - 1], storeItems);
                        items = removeItem(items, tmpItemNum);
                    }


                } while (tmpItemNum != 0 && items.length > 0);
            }
            else{
                System.out.println("\tThere is no items left to place in a store");
            }


            stores[i] = new Store(tmpName, tmpEmailAddress, storeItems);
        }

        return stores;
    }

    static Factory factoryWithTheBiggestVolumeItem(Factory[] factories) {

        Factory tmpFactory = factories[0];
        BigDecimal tmpVolume = new BigDecimal(0);

        for (int i = 0; i < NUM_FACTORIES; i++) {
            for (int j = 0; j < factories[i].getItems().length; j++) {

                BigDecimal itemVolume = factories[i].getItems()[j].getHeight().multiply(factories[i].getItems()[j].getWidth().multiply(factories[i].getItems()[j].getLength()));

                if (itemVolume.compareTo(tmpVolume) > 0) {
                    tmpVolume = itemVolume;
                    tmpFactory = factories[i];
                }
            }

        }

        return tmpFactory;
    }

    static Store storeWithTheCheapestItem(Store[] stores) {
        Store tmpStore = stores[0];
        BigDecimal tmpPrice = new BigDecimal(100000);

        for (int i = 0; i < NUM_STORES; i++) {
            for (int j = 0; j < stores[i].getItems().length; j++) {
                if (tmpPrice.compareTo(stores[i].getItems()[j].getSellingPrice()) < 0) {
                    tmpStore = stores[i];
                    tmpPrice = stores[i].getItems()[j].getSellingPrice();
                }
            }
        }
        return tmpStore;
    }
}

