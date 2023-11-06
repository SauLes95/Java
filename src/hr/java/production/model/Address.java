package hr.java.production.model;

import java.util.Objects;

/**
 * Predstavlja adresu u sustavu proizvodnje. Adresa se sastoji od naziva ulice, kućnog broja, grada i poštanskog broja.
 */
public class Address {

    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;

    private Address(){}

    /**
     * Unutarnja klasa koja omogućava izgradnju objekta klase {@link Address}.
     * Ova klasa omogućava postavljanje vrijednosti za adrese koristeći različite metode.
     */
    public static class Builder {
        private String street, houseNumber, city, postalCode;

        /**
         * Postavlja naziv ulice adrese.
         *
         * @param street Naziv ulice.
         * @return Trenutna instanca Builder objekta.
         */
        public Builder atStreet(String street) {
            this.street = street;
            return this;
        }

        /**
         * Postavlja kućni broj adrese.
         *
         * @param houseNumber Kućni broj.
         * @return Trenutna instanca Builder objekta.
         */
        public Builder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        /**
         * Postavlja grad adrese.
         *
         * @param city Grad.
         * @return Trenutna instanca Builder objekta.
         */
        public Builder inCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * Postavlja poštanski broj adrese.
         *
         * @param postalCode Poštanski broj.
         * @return Trenutna instanca Builder objekta.
         */
        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * Izgrađuje i vraća instancu objekta klase {@link Address} s postavljenim vrijednostima.
         *
         * @return Instanca objekta klase {@link Address} s postavljenim vrijednostima.
         */
        public Address build() {
            Address address = new Address();
            address.street = this.street;
            address.houseNumber = this.houseNumber;
            address.city = this.city;
            address.postalCode = this.postalCode;
            return address;
        }
    }



    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Vraća niz koji predstavlja ovaj objekt adrese.
     *
     * @return Niz koji sadrži ulicu, kućni broj, grad i poštanski broj adrese.
     */
    @Override
    public String toString(){
        return "Address : " +
                "street : " + street + ", " +
                "house number : " + houseNumber + ", " +
                "city : " + city + ", " +
                "zip code : " + postalCode;
    }

    @Override
    public boolean equals(Object tmpObject){
        if(this == tmpObject){
            return true;
        }

        if (tmpObject == null || getClass() != tmpObject.getClass()){
            return false;
        }

        Address tmpAddress = (Address) tmpObject;

        return Objects.equals(getStreet(), tmpAddress.getStreet()) &&
                Objects.equals(getHouseNumber(), tmpAddress.getHouseNumber()) &&
                Objects.equals(getCity(), tmpAddress.getCity()) &&
                Objects.equals(getPostalCode(), tmpAddress.getPostalCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getHouseNumber(), getCity(), getPostalCode());
    }

}
