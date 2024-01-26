package org.example.Exception;

/*
INCORRECT FORMAT FOR YEAR
 */
public class MovieException extends Exception{
    public MovieException(String message){
        super(message);
    }
}
