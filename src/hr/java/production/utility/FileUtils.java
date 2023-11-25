package hr.java.production.utility;

import hr.java.production.enumeration.City;
import hr.java.production.exception.CityFinderException;
import hr.java.production.main.Main;
import hr.java.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String CATEGORIES_TEXT_FILE_NAME = "dat/categories.txt";
    private static final String ITEMS_TEXT_FILE_NAME = "dat/items.txt";
    private static final String ADDRESSES_TEXT_FILE_NAME = "dat/addresses.txt";
    private static final String FACTORIES_TEXT_FILE_NAME = "dat/factories.txt";
    private static final String STORES_TEXT_FILE_NAME = "dat/stores.txt";

    public static List<Category> getCategoryFromFile(){
        List<Category> categoryList = new ArrayList<>();

        File categoriesFile = new File(CATEGORIES_TEXT_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(categoriesFile))){
            Optional<String> categoriesOptional = Optional.empty();

            while((categoriesOptional = Optional.ofNullable(reader.readLine())).isPresent()){

                Optional<Category> newCategoryOptional = Optional.empty();

                Long categoryID = Long.parseLong(categoriesOptional.get());
                String name = reader.readLine();
                String description = reader.readLine();

                newCategoryOptional = Optional.of(new Category(categoryID, name, description));

                if(newCategoryOptional.isPresent()) {
                    categoryList.add((newCategoryOptional.get()));
                }
            }

        }
        catch (FileNotFoundException ex){
            String message="File could not be found";
            logger.error(message, ex);
            System.out.println(message);
        }
        catch(IOException ex){
            String message="Error while reading a file";
            logger.error(message, ex);
            System.out.println(message);
        }

        return categoryList;
    }

    public static List<Item> getItemsFromFile(List<Category> categories){
        List<Item> itemList = new ArrayList<>();
        File itemsFile = new File(ITEMS_TEXT_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(itemsFile))){
            Optional<String> itemsOptional = Optional.empty();

            while((itemsOptional = Optional.ofNullable(reader.readLine())).isPresent()){

                Optional<Item> newItemOptional = Optional.empty();

                Long itemID = Long.parseLong(itemsOptional.get());
                String name = reader.readLine();
                Long categoryID = Long.parseLong(reader.readLine());
                Category category = categories.get(categoryID.intValue() - 1);
                BigDecimal width = new BigDecimal(reader.readLine());
                BigDecimal height = new BigDecimal(reader.readLine());
                BigDecimal length = new BigDecimal(reader.readLine());
                BigDecimal productionCost = new BigDecimal(reader.readLine());
                BigDecimal sellingPrice = new BigDecimal(reader.readLine());
                Discount discount = new Discount(Integer.parseInt(reader.readLine()));
                String categoryType = reader.readLine();

                if(categoryType.compareTo("EDIBLE") == 0){

                    String edibleType = reader.readLine();
                    BigDecimal weight = new BigDecimal(reader.readLine());

                    if(edibleType.compareTo("SWEET") == 0) {
                        newItemOptional = Optional.of(new Sweet(itemID, name, category, width, height, length, productionCost, sellingPrice, discount, weight));
                    } else if (edibleType.compareTo("SALTY") == 0) {
                        newItemOptional = Optional.of(new Salty(itemID, name, category, width, height, length, productionCost, sellingPrice, discount, weight));
                    }
                }
                else if(categoryType.compareTo("TECHNICAL") == 0){
                    Integer warranty = Integer.parseInt(reader.readLine());
                    newItemOptional  = Optional.of(new Laptop(itemID, name, category, width, height, length, productionCost, sellingPrice, discount, warranty));
                }
                else {
                    newItemOptional = Optional.of(new Item(itemID, name, category, width, height, length, productionCost, sellingPrice, discount));
                }

                if(newItemOptional.isPresent()){
                    itemList.add(newItemOptional.get());
                }

            }

        }catch (FileNotFoundException ex){
            String message="File could not be found";
            logger.error(message, ex);
            System.out.println(message);
        }
        catch(IOException ex){
            String message="Error while reading a file";
            logger.error(message, ex);
            System.out.println(message);
        }

        return itemList;
    }

    public static List<Factory> getFactoriesFromFile(List<Item> items) {

        List<Address> addressList = new ArrayList<>();
        File addressFile = new File(ADDRESSES_TEXT_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(addressFile))){

            Optional<String> addressOptional = Optional.empty();

            while((addressOptional = Optional.ofNullable(reader.readLine())).isPresent()){
                Optional<Address> newAddressOptional = Optional.empty();

                String street = addressOptional.get();
                String houseNumber = reader.readLine();
                String cityString = reader.readLine();
                try{
                City city = convertStringToCity(cityString);
                newAddressOptional = Optional.of(new Address.Builder().atStreet(street).withHouseNumber(houseNumber).inCity(city).build());
                }catch (CityFinderException ex){
                    String message="City could not be found";
                    logger.error(message, ex);
                    System.out.println(message);
                }

                if(newAddressOptional.isPresent()){
                    addressList.add(newAddressOptional.get());
                }
            }
        }catch (FileNotFoundException ex){
            String message="File could not be found";
            logger.error(message, ex);
            System.out.println(message);
        }
        catch(IOException ex){
            String message="Error while reading a file";
            logger.error(message, ex);
            System.out.println(message);
        }


        List<Factory> factoryList = new ArrayList<>();
        File factoryFile = new File(FACTORIES_TEXT_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(factoryFile))){

            Optional<String> factoriesOptional = Optional.empty();

            while((factoriesOptional = Optional.ofNullable(reader.readLine())).isPresent()){
                Optional<Factory> newFactoryOptional = Optional.empty();

                Long factoryID = Long.parseLong(factoriesOptional.get());
                String name = reader.readLine();
                Address address = addressList.get(Integer.parseInt(reader.readLine()) - 1);

                Set<Item> factoryItems = new HashSet<>();
                String[] stringItemIDXs = reader.readLine().split(",");

                for (String stringItemIDX : stringItemIDXs) {
                    int itemIdx = Integer.parseInt(stringItemIDX) - 1;
                    factoryItems.add(items.get(itemIdx));
                }

                newFactoryOptional = Optional.of(new Factory(factoryID, name, address, factoryItems));

                if(newFactoryOptional.isPresent()){
                    factoryList.add(newFactoryOptional.get());
                }

            }
        }catch (FileNotFoundException ex){
            String message="File could not be found";
            logger.error(message, ex);
            System.out.println(message);
        }
        catch(IOException ex){
            String message="Error while reading a file";
            logger.error(message, ex);
            System.out.println(message);
        }

        return factoryList;
    }

    public static List<Store> getStoresFromFile (List<Item> items){
        List<Store> storeList = new ArrayList<>();
        File storeFile = new File(STORES_TEXT_FILE_NAME);


        try(BufferedReader reader = new BufferedReader(new FileReader(storeFile))){

            Optional<String> storesOptional = Optional.empty();

            while((storesOptional = Optional.ofNullable(reader.readLine())).isPresent()){
                Optional<Store> newStoreOptional = Optional.empty();

                Long storeID = Long.parseLong(storesOptional.get());
                String name = reader.readLine();
                String email = reader.readLine();

                Set<Item> storeItems = new HashSet<>();
                String[] stringItemIDXs = reader.readLine().split(",");

                for (String stringItemIDX : stringItemIDXs) {
                    int itemIdx = Integer.parseInt(stringItemIDX) - 1;
                    storeItems.add(items.get(itemIdx));
                }

                newStoreOptional = Optional.of(new Store(storeID, name, email, storeItems));

                if(newStoreOptional.isPresent()){
                    storeList.add(newStoreOptional.get());
                }

            }
        }catch (FileNotFoundException ex){
            String message="File could not be found";
            logger.error(message, ex);
            System.out.println(message);
        }
        catch(IOException ex){
            String message="Error while reading a file";
            logger.error(message, ex);
            System.out.println(message);
        }

        return storeList;
    }

    private static City convertStringToCity(String cityName) throws CityFinderException {
        return switch (cityName) {
            case "Zagreb" -> City.ZAGREB;
            case "Split" -> City.SPLIT;
            case "Rijeka" -> City.RIJEKA;
            case "Osijek" -> City.OSIJEK;
            case "Zadar" -> City.ZADAR;
            default ->
                    throw new CityFinderException("City not in the database: [" + cityName + "]. Cities in enums: [" + Arrays.stream(City.values()).map(City::getName).collect(Collectors.joining(", ")) + "]");
        };
    }

}
