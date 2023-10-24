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

        Item mostCaloricItem = itemWithTheMostCalories(items);
        if(mostCaloricItem instanceof Edible edible){
            System.out.println("The item with the most calories is " + mostCaloricItem.getName() + " containing " + edible.calculateKilocalories() + " calories");
        }
        else{
            System.out.println("There were no edible items");
        }

        Item mostExpenciveItem = theMostExpenciveItem(items);
        if(mostCaloricItem instanceof Edible edible){
            System.out.println("The item with the biggest price is " + mostExpenciveItem.getName() + " costing " + edible.calculatePrice());
        }
        else{
            System.out.println("There were no edible items");
        }

        Item theShortestWarrantyLaptop = findTheLaptopWithTheShortestWarranty(items);
        if(theShortestWarrantyLaptop instanceof Laptop laptop){
            System.out.println("The item with the shortest warranty is " + theShortestWarrantyLaptop.getName());
        }
        else{
            System.out.println("There were no laptops in items");
        }


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

            System.out.print("\tSelling price: ");
            BigDecimal tmpSellingPrice = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.print("\tDiscount(%): ");
            Integer itemDiscount = scanner.nextInt();
            scanner.nextLine();
            Discount tmpDiscount = new Discount(itemDiscount);


            System.out.println("Is item edible, type yes for edible item or no for non edible item");
            String tmpEdible = scanner.nextLine();


            if(tmpEdible.equals("yes")){

                System.out.print("\tWeight: ");
                BigDecimal tmpWeight = scanner.nextBigDecimal();
                scanner.nextLine();

                System.out.println("If the item is  sweet type in sweet, or else type in salty");
                String tmpSweetSaltyCheck = scanner.nextLine();

                if (tmpSweetSaltyCheck.equals("sweet")){
                    items[i]= new Salty(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWeight);

                }else if(tmpSweetSaltyCheck.equals("salty")){
                    items[i]= new Sweet(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWeight);
                }

                if(items[i] instanceof Edible edible){
                    System.out.println("\tTotal number of calories " + edible.calculateKilocalories());
                    System.out.println("\tTotal price of item " + edible.calculatePrice());
                }

            } else if (tmpEdible.equals("no")) {
                items[i] = new Item(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount);
            }



            System.out.println("Is item technical, type yes for technical item or no for non technical item");
            String tmpTechnical = scanner.nextLine();

            if(tmpTechnical.equals("yes")){

                System.out.print("\tWarranty: ");
                Integer tmpWarranty = scanner.nextInt();
                scanner.nextLine();

                items[i]= new Laptop(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWarranty);

            } else if (tmpTechnical.equals("no")) {
                items[i] = new Item(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount);
            }

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

            Address tmpAddress = new Address.Builder()
                    .atStreet(tmpStreet)
                    .withHouseNumber(tmpHouseNumber)
                    .inCity(tmpCity)
                    .withPostalCode(tmpPostalCode)
                    .build();

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

    static Item itemWithTheMostCalories (Item[] items){
        Item tmpItem = items[0];
        int tmpMaxCalories = 0;

        for(Item i : items){
            if (i instanceof Edible edible){
                if(edible.calculateKilocalories() > tmpMaxCalories){
                    tmpMaxCalories = edible.calculateKilocalories();
                    tmpItem = i;
                }
            }
        }

        return tmpItem;
    }

    static Item theMostExpenciveItem (Item[] items){
        Item tmpItem = items[0];
        int tmpMaxPrice = 0;

        for(Item i : items){
            if (i instanceof Edible edible){
                if(edible.calculatePrice() > tmpMaxPrice){
                    tmpMaxPrice = edible.calculateKilocalories();
                    tmpItem = i;
                }
            }
        }

        return tmpItem;
    }

    static Item findTheLaptopWithTheShortestWarranty(Item[] items){
        Item tmpItem = items[0];
        int warranty = 0;

        for(Item i : items){
            if (i instanceof Laptop laptop){
                if(laptop.warrantyDuration() > warranty){
                    warranty = laptop.warrantyDuration();
                    tmpItem = i;
                }
            }
        }

        return tmpItem;
    }
}

