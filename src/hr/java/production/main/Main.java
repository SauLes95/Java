package hr.java.production.main;

import hr.java.production.enumeration.City;
import hr.java.production.exception.*;
import hr.java.production.generics.FoodStore;
import hr.java.production.generics.TechnicalStore;
import hr.java.production.model.*;
import hr.java.production.sort.ProductionSorter;
import hr.java.production.utility.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final int NUM_CATEGORIES = 3;
    private static final int NUM_ITEMS = 5;
    private static final int NUM_FACTORIES = 2;
    private static final int NUM_STORES = 2;

    private static Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application started");

        List<Category> categories = FileUtils.getCategoryFromFile();
        List<Item> items = FileUtils.getItemsFromFile(categories);
        List<Factory> factories = FileUtils.getFactoriesFromFile(items);
        List<Store> stores = FileUtils.getStoresFromFile(items);

        Map<Category, List<Item>> itemsByCategory = new HashMap<>();

        for(Item item : items){
            if(!itemsByCategory.containsKey(item.getCategory())){
                itemsByCategory.put(item.getCategory(), new ArrayList<>());
            }
            itemsByCategory.get(item.getCategory()).add(item);
        }

        for (Map.Entry<Category, List<Item>> entry : itemsByCategory.entrySet()) {
            Category category = entry.getKey();
            List<Item> tmpItems = entry.getValue();

            items.sort(new ProductionSorter());

            System.out.println("Kategorija: " + category.getName());
            System.out.println("Najjeftiniji artikl: " + tmpItems.get(0));
            System.out.println("Najskuplji artikl: " + tmpItems.get(tmpItems.size() - 1));
            System.out.println();
        }

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

        TechnicalStore<Technical> technicalStore = new TechnicalStore<>();
        FoodStore<Edible> foodStore = new FoodStore<>();

        for(Item item : items){
            if(item instanceof Edible edible){
                foodStore.addItem(edible);
            }
            if(item instanceof Technical technical){
                technicalStore.addItem(technical);
            }
        }

        for(Store store : stores){
            store.sortStoreItems();
        }

        technicalStore.sortStoreItems();
        foodStore.sortStoreItems();

        BigDecimal avgPriceOfBiggerItems = avgPriceOfBigItems(items);

        List<Store> storesWithALotOfItems = calculateStoresWithALotOfItems(stores);

        Optional<Item> itemsWithDiscount = findItemsWithDiscount(items);

        if (itemsWithDiscount.isPresent()) {
            System.out.println("Pronađeni su objekti s popstom.");
        } else {
            System.out.println("Nije pronađen nijedan objekt s popustom.");
        }


        for(Store store : stores){
            printStoreItems(store);
        }

        printStoreItems(technicalStore);
        printStoreItems(foodStore);


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



    /**
     * Collects user input to determine the city.
     * <p>
     * Prompts the user to enter the name of a city. It checks if the entered city is in the list of supported cities.
     * If the city is not supported, a {@code CityNotSupportedException} is thrown.
     *
     * @param scanner A Scanner object for reading user input.
     * @return A {@code Cities} enum value representing the entered city.
     * @throws CityFinderException If the entered city is not supported.
     */
    static City inputCity(Scanner scanner) throws CityFinderException {
        System.out.print("City: ");
        String name = scanner.nextLine();

        return switch (name) {
            case "Zagreb" -> City.ZAGREB;
            case "Split" -> City.SPLIT;
            case "Rijeka" -> City.RIJEKA;
            case "Osijek" -> City.OSIJEK;
            case "Zadar" -> City.ZADAR;
            default ->
                    throw new CityFinderException("User entered city that is not listed in enumeration city base");
        };
    }

    static BigDecimal avgPriceOfBigItems(List<Item> items){

        try{
            if(BigDecimal.valueOf(items.size()).equals(0)){
                BigDecimal averageVolume = items.stream()
                        .map(Item::getVolume)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(items.size()), 5, BigDecimal.ROUND_HALF_UP);

                List<BigDecimal> pricesOfAboveAverageVolumeItems = items.stream()
                        .filter(item -> item.getVolume().compareTo(averageVolume) > 0)
                        .map(Item::getSellingPrice)
                        .toList();


                BigDecimal averagePrice = pricesOfAboveAverageVolumeItems.stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(pricesOfAboveAverageVolumeItems.size()), 5, BigDecimal.ROUND_HALF_UP);

                return averagePrice;
            }else{
                throw new ArithmeticException("Dividing by 0") ;
            }

        }catch(ArithmeticException e){
            System.out.println("Cant divide by 0");
            logger.error(e.getMessage(), e);
        }

        return new BigDecimal(0);
    }

    static List<Store> calculateStoresWithALotOfItems(List<Store> stores){
        List<Store> storesWithAboveAverageItems = stores.stream()
                .filter(store -> {
                    double averageNumberOfItems = stores.stream()
                            .mapToInt(s -> s.getItems().size())
                            .average()
                            .orElse(0.0);
                    return store.getItems().size() > averageNumberOfItems;
                })
                .collect(Collectors.toList());

        return storesWithAboveAverageItems;
    }

    static Optional<Item> findItemsWithDiscount(List<Item> items){
        return items.stream()
                .filter(item -> item.getDiscount().discountAmount() > 0)
                .findFirst();

    }

    static void printStoreItems(Store store){
        store.getItems().stream()
                .map(Item::toString)
                .forEach(System.out::println);
    }
}


