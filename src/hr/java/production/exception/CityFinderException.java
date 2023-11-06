package hr.java.production.exception;

public class CityFinderException extends Exception{

        public CityFinderException(){
            super();
        }

        public CityFinderException(String message){
            super(message);
        }

        public CityFinderException(Throwable cause){
            super(cause);
        }

        public CityFinderException(String message, Throwable cause){
            super(message, cause);
        }
}
