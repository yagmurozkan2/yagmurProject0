package org.example;

import org.example.Exception.CLIException;
import org.example.Exception.MovieException;
import org.example.Service.*;
import org.example.View.CLIParser;

import java.awt.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    public static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("Welcome to Movie Directory!");

        MovieService newMovieService = new MovieService();
        Scanner sc = new Scanner(System.in);
        CLIParser cliParser = new CLIParser();

        boolean flag = true;

        while(flag){
            System.out.println("\nWhat would you like work with: Please enter Movie, Director, or Exit to end the program: ");
            String userMovieDirectorChoice = sc.nextLine();

            try {
                flag = cliParser.parseCommandReturnOutput(userMovieDirectorChoice);
            }
            catch (CLIException exception) {
                System.out.println("You caused CLIException! Please make sure to enter valid input! For more information refer to Stack Trace. ");
                exception.printStackTrace();
            }
            catch (MovieException exception) {
                System.out.println("You caused MovieException! Please make sure to enter valid input! For more information refer to Stack Trace. ");
                exception.printStackTrace();
            }
            catch (NumberFormatException exception) {
                System.out.println("Cannot parse string into a number");
                exception.printStackTrace();
            }
            catch (NullPointerException exception) {
                System.out.println("Null Pointer Exception");
                exception.printStackTrace();
            }

        }
    }
}
