package fhku.leanlabapp.classes.exceptions;

/**
 * Created by kevin on 14.11.2017.
 */

public class JsonToObjectMapper_Exception extends Exception {
    // Parameterless Constructor
    public JsonToObjectMapper_Exception() {}

    // Constructor that accepts a message
    public JsonToObjectMapper_Exception(String message)
    {
        super(message);
    }
}
