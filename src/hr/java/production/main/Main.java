package hr.java.production.main;

import hr.java.production.exception.*;
import hr.java.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class Main {

    private static final int NUM_CATEGORIES = 3;
    private static final int NUM_ITEMS = 5;
    private static final int NUM_FACTORIES = 2;
    private static final int NUM_STORES = 2;

    private static Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application started");

        List<Category> categories = setCategories();
        List<Item> items = setItems(categories);
        List<Factory> factories = setFactories(items);
        List<Store> stores = setStores(items);

        Factory theBiggestVolumeFactory = factoryWithTheBiggestVolumeItem(factories);
        System.out.println("Factory with the item that has biggest volume is " + theBiggestVolumeFactory.getName());

        Store theCheapestStore = storeWithTheCheapestItem(stores);
        System.out.println("Store  with the cheapest item is " + theCheapestStore.getName());

        Item mostCaloricItem = itemWithTheMostCalories(items);
        if (mostCaloricItem instanceof Edible edible) {
            System.out.println("The item with the most calories is " + mostCaloricItem.getName() + " containing " + edible.calculateKilocalories() + " calories");
        } else {
            System.out.println("There were no edible items");
        }

        Item mostExpenciveItem = theMostExpenciveItem(items);
        if (mostCaloricItem instanceof Edible edible) {
            System.out.println("The item with the biggest price is " + mostExpenciveItem.getName() + " costing " + edible.calculatePrice());
        } else {
            System.out.println("There were no edible items");
        }

        Item theShortestWarrantyLaptop = findTheLaptopWithTheShortestWarranty(items);
        if (theShortestWarrantyLaptop instanceof Laptop laptop) {
            System.out.println("The item with the shortest warranty is " + theShortestWarrantyLaptop.getName());
        } else {
            System.out.println("There were no laptops in items");
        }

    }

    /**
     * Reducira veličinu niza predanih artikala za 1.
     *
     * @param items Niz artikala koji se trebaju smanjiti za 1 element.
     * @return Novi niz artikala smanjen za 1 element.
     */
    static Item[] reduceItemsSize(Item[] items) {

        return new Item[items.length - 1];
    }

    /**
     * Uklanja odabrani artikl iz niza artikala.
     *
     * @param items          Niz artikala iz kojeg treba ukloniti odabrani artikl.
     * @param tmpItemNumber  Broj odabranog artikla za uklanjanje.
     * @return Novi niz artikala koji ne uključuje odabrani artikl.
     */
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

    /**
     * Povećava veličinu niza artikala za jedan element.
     *
     * @param factoryItems  Niz artikala kojemu se treba povećati veličinu.
     * @return Novi niz artikala s povećanom veličinom koji uključuje sve elemente iz izvornog niza.
     */
    static Item[] increaseItemsSize(Item[] factoryItems) {

        Item[] newItems = new Item[factoryItems.length + 1];

        for (int i = 0; i < factoryItems.length; i++) {
            newItems[i] = factoryItems[i];
        }

        return newItems;
    }

    /**
     * Dodaje novi artikal na kraj niza artikala.
     *
     * @param item   Artikal koji treba dodati u niz.
     * @param items  Niz artikala u koji se dodaje novi artikal.
     * @return Novi niz artikala koji uključuje sve prethodne artikle i dodani artikal.
     */
    static Item[] addItem(Item item, Item[] items) {
        Item[] newItems = increaseItemsSize(items);
        newItems[items.length] = item;

        return newItems;
    }


    /**
     * Unosi i postavlja kategorije u niz kategorija.
     *
     * @return Niz kategorija koje su unesene.
     */
    static List<Category> setCategories() {
        ArrayList<Category> categories = new ArrayList<>();

        System.out.println("INSERT CATEGORIES");
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            System.out.println("CATEGORY " + (i + 1));

            Category tmpCategory;
            boolean check = true;
            do {
                System.out.print("\tName: ");
                String tmpName = scanner.nextLine();

                System.out.print("\tDescription: ");
                String tmpDescription = scanner.nextLine();

                tmpCategory = new Category(tmpName, tmpDescription);

                try {
                    if (!categoryDuplicateCheck(categories, tmpCategory)) {
                        categories.add(tmpCategory);
                        check = false;
                    } else {
                        throw new DuplicateCategoryException("Duplicate category was added by the user");
                    }
                } catch (DuplicateCategoryException e) {
                    System.out.println("Error! Duplicate category was added. Enter new category name and description");
                    logger.info(e.getMessage(), e);
                }
            } while (check);
        }

        return categories;
    }


    /**
     * Unosi i postavlja artikle u niz artikala.
     *
     * @param categories Niz kategorija iz kojih korisnik bira kategorije za artikle.
     * @return Niz artikala koji su uneseni.
     */
    static List<Item> setItems(List<Category> categories) {
        ArrayList<Item> items = new ArrayList<>();

        System.out.println("INSERT ITEMS");
        for (int i = 0; i < NUM_ITEMS; i++) {

            System.out.println("ITEM " + (i + 1));
            System.out.print("\tName: ");
            String tmpName = scanner.nextLine();

            int categoryNumber = -1;
            boolean error = false;
            do {

                System.out.println("\tEnter the number of the category to which the item belongs to: ");
                for (int j = 0; j < NUM_CATEGORIES; j++) {
                    System.out.println("\t" + (j + 1) + ". " + categories.get(j).getName());
                }

                categoryNumber = intManipulation(scanner);

                if (categoryNumber < 1 || categoryNumber > NUM_CATEGORIES) {
                    System.out.println("\tWrong number of category, please enter correct number of category");
                }

            } while (categoryNumber < 1 || categoryNumber > NUM_CATEGORIES);


            Category tmpCategory = categories.get(categoryNumber - 1);

            System.out.print("\tWidth: ");
            BigDecimal tmpWidth = bigDecimalManipulation(scanner);


            System.out.print("\tHeight: ");
            BigDecimal tmpHeight = bigDecimalManipulation(scanner);

            System.out.print("\tLength: ");
            BigDecimal tmpLength = bigDecimalManipulation(scanner);

            System.out.print("\tProduction cost: ");
            BigDecimal tmpProductionCost = bigDecimalManipulation(scanner);

            System.out.print("\tSelling price: ");
            BigDecimal tmpSellingPrice = bigDecimalManipulation(scanner);

            System.out.print("\tDiscount(%): ");
            Integer itemDiscount = intManipulation(scanner);

            Discount tmpDiscount = new Discount(itemDiscount);


            System.out.println("Is item edible, type yes for edible item or no for non edible item");
            String tmpEdible = "tmpEdible";


            do{
                tmpEdible = scanner.nextLine();

                try{
                    if (tmpEdible.equals("yes")) {

                        System.out.print("\tWeight: ");
                        BigDecimal tmpWeight = bigDecimalManipulation(scanner);

                        System.out.println("If the item is  sweet type in sweet, or else type in salty");
                        String tmpSweetSaltyCheck = "tmpSweetSaltyCheck";

                        do{

                            tmpSweetSaltyCheck = scanner.nextLine();

                            try{
                                if (tmpSweetSaltyCheck.equals("sweet")) {
                                    items.add(new Salty(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWeight));

                                } else if (tmpSweetSaltyCheck.equals("salty")) {
                                    items.add(new Sweet(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWeight));
                                }else {
                                    throw new SweetSaltyException("User input was neither sweet nor salty");
                                }
                                if (items.getLast() instanceof Edible edible) {
                                    System.out.println("\tTotal number of calories " + edible.calculateKilocalories());
                                    System.out.println("\tTotal price of item " + edible.calculatePrice());
                                }
                            }catch(SweetSaltyException e){
                                System.out.println("You entered wrong input, type i either sweet or salty!");
                                logger.info(e.getMessage(), e);
                            }
                        }while(!tmpSweetSaltyCheck.equals("sweet") && !tmpSweetSaltyCheck.equals("salty"));


                    } else if (tmpEdible.equals("no")) {}
                    else{
                        throw new YesNoOptionException("User input was neither yes nor no");
                    }

                }catch(YesNoOptionException e){
                    System.out.println("Error! Enter either 'yes' or 'no'");
                    logger.info(e.getMessage(), e);
                }

            }while(!tmpEdible.equals("yes") && !tmpEdible.equals("no"));



            if(tmpEdible.equals("no")){
                System.out.println("Is item technical, type yes for technical item or no for non technical item");
                String tmpTechnical = "tmpTechnical";

                do{

                    tmpTechnical = scanner.nextLine();

                    try{
                        if (tmpTechnical.equals("yes")) {

                            System.out.print("\tWarranty: ");
                            Integer tmpWarranty = intManipulation(scanner);

                            items.add( new Laptop(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWarranty));

                        } else if (tmpTechnical.equals("no")) {
                            items.add(new Item(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount));
                        }else{
                            throw new YesNoOptionException("User input was neither yes nor no");
                        }
                    }catch(YesNoOptionException e){
                        System.out.println("Error! Enter either 'yes' or 'no'");
                        logger.info(e.getMessage(), e);
                    }


                }while (!tmpTechnical.equals("yes") && !tmpTechnical.equals("no"));
            }


        }
        return items;
    }

    /**
     * Unosi i postavlja tvornice s pripadajućim artiklima.
     *
     * @param items Niz dostupnih artikala koji se mogu proizvoditi u tvornicama.
     * @return Niz tvornica koje su unesene.
     */
    static List<Factory> setFactories(List<Item> items) {

        ArrayList<Factory> factories = new ArrayList<>();

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


            boolean check = true;
            String tmpPostalCode = "tmppostalcode";
            do{
                System.out.print("\tPostal code: ");
                tmpPostalCode = scanner.nextLine();

                try{
                    if(stringNumberCheck(tmpPostalCode)){
                        tmpPostalCode=postalCodeCheck(tmpPostalCode);

                    }
                }catch (PostalCodeException e){
                    System.out.println("Enter postal code that is made from numbers!");
                    logger.error(e.getMessage());
                }

                check = stringNumberCheck(tmpPostalCode);
            }while(check);


            Address tmpAddress = new Address.Builder()
                    .atStreet(tmpStreet)
                    .withHouseNumber(tmpHouseNumber)
                    .inCity(tmpCity)
                    .withPostalCode(tmpPostalCode)
                    .build();

            Set<Item> factoryItems = new HashSet<>();

            System.out.println("\tEnter the number in front of the item that is produced in the factory, if the input is complete, enter 0");

            int tmpItemNum;
            do {

                for (int j = 0; j < items.size(); j++) {
                    System.out.println((j + 1) + ". " + items.get(j).getName());
                }

                tmpItemNum = intManipulation(scanner);

                try{
                    factoryItems = checkDuplicate(items, factoryItems, tmpItemNum);
                }catch (DuplicateItemException e){
                    System.out.println("Item can be added in the factory only one time, add item that was not previously added in the factory!");
                    logger.error(e.getMessage(), e);
                }

            } while (tmpItemNum != 0);


            factories.add(new Factory(tmpName, tmpAddress, factoryItems));
        }

        return factories;
    }

    private static Set<Item> checkDuplicate(List<Item> items, Set<Item> factoryItems, int tmpItemNum) throws DuplicateItemException {
        if(!factoryItemDuplicateCheck(factoryItems, items.get(tmpItemNum - 1))){
            factoryItems.add(items.get(tmpItemNum-1));
        }
        else{
            throw new DuplicateItemException("User added the same item twice");
        }
        return factoryItems;
    }


    /**
     * Unosi i postavlja prodavaonice s pripadajućim artiklima.
     *
     * @param items Niz dostupnih artikala koji se mogu prodavati u prodavaonicama.
     * @return Niz prodavaonica koje su unesene.
     */
    static List<Store> setStores(List<Item> items) {
        ArrayList<Store> stores= new ArrayList<>();

        System.out.println("INSERT STORES");

        for (int i = 0; i < NUM_STORES; i++) {

            System.out.println("STORE " + (i + 1));

            System.out.print("\tName: ");
            String tmpName = scanner.nextLine();

            System.out.print("\tE-mail address: ");
            String tmpEmailAddress = scanner.nextLine();

            Set<Item> storeItems = new HashSet<>();

            if (!items.isEmpty()) {
                System.out.println("\tEnter the number in front of the thing that is sold in the store, if the input is complete, enter 0");

                int tmpItemNum = 0;
                do {

                    for (int j = 0; j < items.size(); j++) {
                        System.out.println((j + 1) + ". " + items.get(j).getName());
                    }

                    tmpItemNum = intManipulation(scanner);

                    if (tmpItemNum != 0) {

                        try{
                            storeItems = checkDuplicate(items, storeItems, tmpItemNum);
                        }catch (DuplicateItemException e){
                            System.out.println("Error! Postal code needs to consist of numbers");
                            logger.error(e.getMessage(), e);
                        }

                    }


                } while (tmpItemNum != 0);
            } else {
                System.out.println("\tThere is no items left to place in a store");
            }


            stores.add(new Store(tmpName, tmpEmailAddress, storeItems));
        }

        return stores;
    }


    /**
     * Pronalazi i vraća tvornicu koja proizvodi artikl najvećeg volumena.
     *
     * @param factories Niz tvornica za koje se traži tvornica s najvećim volumenom artikla.
     * @return Tvornica koja proizvodi artikl najvećeg volumena.
     */
    static Factory factoryWithTheBiggestVolumeItem(List<Factory> factories) {

        Factory tmpFactory = factories.getFirst();
        BigDecimal tmpVolume = new BigDecimal(0);

        for (Factory factory: factories) {
            for (Item item: factory.getItems()) {

                BigDecimal itemVolume = item.getHeight().multiply(item.getLength().multiply(item.getWidth()));

                if (itemVolume.compareTo(tmpVolume) > 0) {
                    tmpVolume = itemVolume;
                    tmpFactory = factory;
                }
            }

        }

        return tmpFactory;
    }

    /**
     * Pronalazi i vraća trgovinu koja prodaje najjeftiniji artikl.
     *
     * @param stores Niz trgovina za koje se traži trgovina s najjeftinijim artiklom.
     * @return Trgovina koja prodaje najjeftiniji artikl.
     */
    static Store storeWithTheCheapestItem(List<Store> stores) {
        Store tmpStore = stores.getFirst();
        BigDecimal tmpPrice = new BigDecimal(100000);

        for (Store store: stores) {
            for (Item item: store.getItems()) {
                if (tmpPrice.compareTo(item.getSellingPrice()) < 0) {
                    tmpStore = store;
                    tmpPrice = item.getSellingPrice();
                }
            }
        }
        return tmpStore;
    }

    /**
     * Pronalazi i vraća artikl s najvećim brojem kalorija među svim jestivim artiklima.
     *
     * @param items Niz artikala za koje se traži artikl s najvećim brojem kalorija.
     * @return Artikl s najvećim brojem kalorija ili null ako nema jestivih artikala.
     */
    static Item itemWithTheMostCalories(List<Item> items) {
        Item tmpItem = items.getFirst();
        int tmpMaxCalories = 0;

        for (Item item : items) {
            if (item instanceof Edible edible) {
                if (edible.calculateKilocalories() > tmpMaxCalories) {
                    tmpMaxCalories = edible.calculateKilocalories();
                    tmpItem = item;
                }
            }
        }

        return tmpItem;
    }

    /**
     * Pronalazi i vraća artikl s najvećom cijenom među svim jestivim artiklima.
     *
     * @param items Niz artikala za koje se traži artikl s najvećom cijenom.
     * @return Artikl s najvećom cijenom ili null ako nema jestivih artikala.
     */
    static Item theMostExpenciveItem(List<Item> items) {
        Item tmpItem = items.getFirst();
        int tmpMaxPrice = 0;

        for (Item item : items) {
            if (item instanceof Edible edible) {
                if (edible.calculatePrice() > tmpMaxPrice) {
                    tmpMaxPrice = edible.calculateKilocalories();
                    tmpItem = item;
                }
            }
        }

        return tmpItem;
    }

    /**
     * Pronalazi i vraća laptop s najkraćim vijekom trajanja jamstva.
     *
     * @param items Niz artikala među kojima se traži laptop s najkraćim vijekom trajanja jamstva.
     * @return Laptop s najkraćim vijekom trajanja jamstva ili null ako nema laptopa u nizu.
     */
    static Item findTheLaptopWithTheShortestWarranty(List<Item> items) {
        Item tmpItem = items.getFirst();
        int warranty = 0;

        for (Item item : items) {
            if (item instanceof Laptop laptop) {
                if (laptop.warrantyDuration() > warranty) {
                    warranty = laptop.warrantyDuration();
                    tmpItem = item;
                }
            }
        }

        return tmpItem;
    }

    /**
     * Čita i obrađuje cijeli broj (int) iz korisničkog unosa uz provjeru na greške.
     *
     * @param scanner Scanner objekt koji se koristi za čitanje korisničkog unosa.
     * @return Uneseni cijeli broj (int).
     */
    static int intManipulation (Scanner scanner){

        boolean error;
        int intNum = -1;
        do{
            error = false;
            try{
                intNum = scanner.nextInt();

            }
            catch(InputMismatchException e){
                System.out.println("\tInput needs to be a number!");
                logger.error("Input was not an integer", e);
                error = true;
            }
            scanner.nextLine();
        }while(error);

        return intNum;
    }

    /**
     * Čita i obrađuje decimalni broj (BigDecimal) iz korisničkog unosa uz provjeru na greške.
     *
     * @param scanner Scanner objekt koji se koristi za čitanje korisničkog unosa.
     * @return Uneseni decimalni broj (BigDecimal).
     */
    static BigDecimal bigDecimalManipulation(Scanner scanner){
        boolean error;
        BigDecimal bigDecimalNum = new BigDecimal(-1);
        do{
            error = false;
            try{
                bigDecimalNum = scanner.nextBigDecimal();
            }
            catch(InputMismatchException e){
                System.out.println("\tInput needs to be a number!");
                logger.info("Input was not a big decimal", e);
                error = true;
            }
            scanner.nextLine();
        }while(error);

        return bigDecimalNum;
    }

    /**
     * Provjerava postojanje dupliciranih artikala u nizu artikala.
     *
     * @param items Polje artikala u kojem se provjerava duplikati.
     * @param item Artikal koji se provjerava na duplikate.
     * @return True ako artikal već postoji u nizu, inače false.
     */
    static boolean itemDuplicateCheck(List<Item> items, Item item){
         for(Item tmpItem : items){
            if(tmpItem.equals(item)){
                return true;
            }
        }
        return false;
    }

    static boolean factoryItemDuplicateCheck (Set<Item> items, Item item){
        for(Item tmpItem : items){
            if(tmpItem.equals(item)){
                return true;
            }
        }
        return false;
    }

    /**
     * Provjerava postojanje dupliciranih kategorija u nizu kategorija.
     *
     * @param categories Polje kategorija u kojem se provjerava duplikati.
     * @param category Kategorija koja se provjerava na duplikate.
     * @return True ako kategorija već postoji u nizu, inače false.
     */
    static boolean categoryDuplicateCheck(List<Category> categories, Category category){

        for(Category tmpCat : categories){
            if(tmpCat.equals(category)){
                return true;
            }
        }
        return false;
    }


    /**
     * Provjerava da li je niz znakova sastavljen isključivo od decimalnih brojeva.
     *
     * @param string Niz znakova koji se provjerava.
     * @return true ako je niz sastavljen isključivo od decimalnih brojeva, inače false.
     */
    static boolean stringNumberCheck(String string){
        return !string.matches("^[0-9]*$");
    }

    /**
     * Provjerava da li je niz znakova ispravan poštanski broj.
     *
     * @param tmpstring Niz znakova koji predstavlja poštanski broj.
     * @return Ispravan poštanski broj.
     * @throws PostalCodeException Ako poštanski broj nije ispravno formatiran.
     */
    static String postalCodeCheck(String tmpstring) throws PostalCodeException{
        if (stringNumberCheck(tmpstring)) {
            throw new PostalCodeException("User input wrong, postal code was not made from numbers");
        }
        return tmpstring;
    }

}

