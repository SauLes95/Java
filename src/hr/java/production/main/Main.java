package hr.java.production.main;

import hr.java.production.exception.*;
import hr.java.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final int NUM_CATEGORIES = 3;
    private static final int NUM_ITEMS = 5;
    private static final int NUM_FACTORIES = 2;
    private static final int NUM_STORES = 2;

    private static Scanner scanner = new Scanner(System.in);

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
    static Category[] setCategories() {
        Category[] categories = new Category[NUM_CATEGORIES];

        System.out.println("INSERT CATEGORIES");
        for (int i = 0; i < NUM_CATEGORIES; i++) {


            System.out.println("CATEGORY " + (i + 1));

            Category tmpCategory = new Category("tmpName", "tmpDescription");

            do{
                System.out.print("\tName: ");
                String tmpName = scanner.nextLine();

                System.out.print("\tDescription: ");
                String tmpDescription = scanner.nextLine();

                tmpCategory = new Category(tmpName, tmpDescription);

                try{
                    if(!categoryDuplicateCheck(categories, tmpCategory, i)){
                        categories[i]=tmpCategory;
                    }else{
                        throw new DuplicateCategoryException("Duplicate category was added by the user");
                    }
                }catch (DuplicateCategoryException e){
                    System.out.println("Error! Duplicate category was added. Enter new category name and description");
                    logger.info(e.getMessage(), e);
                }

            } while(categoryDuplicateCheck(categories, tmpCategory, i));


        }

        return categories;
    }

    /**
     * Unosi i postavlja artikle u niz artikala.
     *
     * @param categories Niz kategorija iz kojih korisnik bira kategorije za artikle.
     * @return Niz artikala koji su uneseni.
     */
    static Item[] setItems(Category[] categories) {
        Item[] items = new Item[NUM_ITEMS];

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
                    System.out.println("\t" + (j + 1) + ". " + categories[j].getName());
                }

                categoryNumber = intManipulation(scanner);

                if (categoryNumber < 1 || categoryNumber > NUM_CATEGORIES) {
                    System.out.println("\tWrong number of category, please enter correct number of category");
                }

            } while (categoryNumber < 1 || categoryNumber > NUM_CATEGORIES);


            Category tmpCategory = categories[categoryNumber - 1];

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
                                    items[i] = new Salty(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWeight);

                                } else if (tmpSweetSaltyCheck.equals("salty")) {
                                    items[i] = new Sweet(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWeight);
                                }else {
                                    throw new SweetSaltyException("User input was neither sweet nor salty");
                                }
                                if (items[i] instanceof Edible edible) {
                                    System.out.println("\tTotal number of calories " + edible.calculateKilocalories());
                                    System.out.println("\tTotal price of item " + edible.calculatePrice());
                                }
                            }catch(SweetSaltyException e){
                                System.out.println("You entered wrong input, type i either sweet or salty!");
                                logger.info(e.getMessage(), e);
                            }
                        }while(!tmpSweetSaltyCheck.equals("sweet") && !tmpSweetSaltyCheck.equals("salty"));


                    } else if (tmpEdible.equals("no")) {
                        items[i] = new Item(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount);
                    }
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

                            items[i] = new Laptop(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount, tmpWarranty);

                        } else if (tmpTechnical.equals("no")) {
                            items[i] = new Item(tmpName, tmpCategory, tmpWidth, tmpHeight, tmpLength, tmpProductionCost, tmpSellingPrice, tmpDiscount);
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


            do{
                try{
                    if(!stringNumberCheck(tmpPostalCode)){
                        tmpPostalCode=postalCodeCheck(tmpPostalCode);
                    }else{
                        throw new PostalCodeException("Error! postal code was not made from numbers, enter postal code made from numbers");
                    }
                }catch (PostalCodeException e){
                    System.out.println();
                    logger.error(e.getMessage());
                }

            }while(!stringNumberCheck(tmpPostalCode));


            Address tmpAddress = new Address.Builder()
                    .atStreet(tmpStreet)
                    .withHouseNumber(tmpHouseNumber)
                    .inCity(tmpCity)
                    .withPostalCode(tmpPostalCode)
                    .build();

            Item[] factoryItems = new Item[0];

            System.out.println("\tEnter the number in front of the item that is produced in the factory, if the input is complete, enter 0");

            int tmpItemNum;
            do {

                for (int j = 0; j < items.length; j++) {
                    System.out.println((j + 1) + ". " + items[j].getName());
                }

                tmpItemNum = intManipulation(scanner);

                try{
                    factoryItems = checkDuplicate(items, factoryItems, tmpItemNum, i);
                }catch (DuplicateItemException e){
                    System.out.println("Item can be added in the factory only one time, add item that was not previously added in the factory!");
                    logger.error(e.getMessage(), e);
                }

            } while (tmpItemNum != 0);


            factories[i] = new Factory(tmpName, tmpAddress, factoryItems);
        }

        return factories;
    }

    private static Item[] checkDuplicate(Item[] items, Item[] factoryItems, int tmpItemNum, int i) throws DuplicateItemException {
        if(!itemDuplicateCheck(factoryItems, items[tmpItemNum - 1], i)){
            factoryItems = addItem(items[tmpItemNum - 1], factoryItems);
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

            if (items.length > 0) {
                System.out.println("\tEnter the number in front of the thing that is sold in the store, if the input is complete, enter 0");

                int tmpItemNum = 0;
                do {

                    for (int j = 0; j < items.length; j++) {
                        System.out.println((j + 1) + ". " + items[j].getName());
                    }

                    tmpItemNum = intManipulation(scanner);

                    if (tmpItemNum != 0) {

                        try{
                            storeItems = checkDuplicate(items, storeItems, tmpItemNum, i);
                        }catch (DuplicateItemException e){
                            System.out.println("Error! Postal code needs to consist of numbers");
                            logger.error(e.getMessage(), e);
                        }

                    }


                } while (tmpItemNum != 0);
            } else {
                System.out.println("\tThere is no items left to place in a store");
            }


            stores[i] = new Store(tmpName, tmpEmailAddress, storeItems);
        }

        return stores;
    }


    /**
     * Pronalazi i vraća tvornicu koja proizvodi artikl najvećeg volumena.
     *
     * @param factories Niz tvornica za koje se traži tvornica s najvećim volumenom artikla.
     * @return Tvornica koja proizvodi artikl najvećeg volumena.
     */
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

    /**
     * Pronalazi i vraća trgovinu koja prodaje najjeftiniji artikl.
     *
     * @param stores Niz trgovina za koje se traži trgovina s najjeftinijim artiklom.
     * @return Trgovina koja prodaje najjeftiniji artikl.
     */
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

    /**
     * Pronalazi i vraća artikl s najvećim brojem kalorija među svim jestivim artiklima.
     *
     * @param items Niz artikala za koje se traži artikl s najvećim brojem kalorija.
     * @return Artikl s najvećim brojem kalorija ili null ako nema jestivih artikala.
     */
    static Item itemWithTheMostCalories(Item[] items) {
        Item tmpItem = items[0];
        int tmpMaxCalories = 0;

        for (Item i : items) {
            if (i instanceof Edible edible) {
                if (edible.calculateKilocalories() > tmpMaxCalories) {
                    tmpMaxCalories = edible.calculateKilocalories();
                    tmpItem = i;
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
    static Item theMostExpenciveItem(Item[] items) {
        Item tmpItem = items[0];
        int tmpMaxPrice = 0;

        for (Item i : items) {
            if (i instanceof Edible edible) {
                if (edible.calculatePrice() > tmpMaxPrice) {
                    tmpMaxPrice = edible.calculateKilocalories();
                    tmpItem = i;
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
    static Item findTheLaptopWithTheShortestWarranty(Item[] items) {
        Item tmpItem = items[0];
        int warranty = 0;

        for (Item i : items) {
            if (i instanceof Laptop laptop) {
                if (laptop.warrantyDuration() > warranty) {
                    warranty = laptop.warrantyDuration();
                    tmpItem = i;
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
    static boolean itemDuplicateCheck(Item[] items, Item item, int length){
        boolean check = false;

        for(int i = 0; i < length; i++){
            if(items[i].equals(item)){
                check = true;
                break;
            }
        }
        return check;
    }

    /**
     * Provjerava postojanje dupliciranih kategorija u nizu kategorija.
     *
     * @param categories Polje kategorija u kojem se provjerava duplikati.
     * @param category Kategorija koja se provjerava na duplikate.
     * @return True ako kategorija već postoji u nizu, inače false.
     */
    static boolean categoryDuplicateCheck(Category[] categories, Category category, int length){

        for(int i = 0 ; i < length; i++){
            if(categories[i].equals(category)){
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
        return string.matches("^[0-9]*$");
    }

    /**
     * Provjerava da li je niz znakova ispravan poštanski broj.
     *
     * @param tmpstring Niz znakova koji predstavlja poštanski broj.
     * @return Ispravan poštanski broj.
     * @throws PostalCodeException Ako poštanski broj nije ispravno formatiran.
     */
    static String postalCodeCheck(String tmpstring) throws PostalCodeException{
        if (!stringNumberCheck(tmpstring)) {
            throw new PostalCodeException("User input wrong, postal code was not made from numbers");
        }
        return tmpstring;
    }

}

