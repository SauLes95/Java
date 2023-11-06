package hr.java.production.enumeration;

public enum City {
        ZAGREB("Zagreb", "10000"),
        SPLIT("Split", "21000"),
        RIJEKA("Rijeka", "51000"),
        OSIJEK("Osijek", "31000"),
        ZADAR("Zadar", "23000");

        private String name;
        private String postalCode;

        /**
         * Constructs a new city with the specified name and postal code.
         *
         * @param name       The name of the city.
         * @param postalCode The postal code of the city.
         */
        City(String name, String postalCode) {
            this.name = name;
            this.postalCode = postalCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
}
