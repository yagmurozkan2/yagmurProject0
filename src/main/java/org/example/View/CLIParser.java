package org.example.View;

import org.example.Exception.*;
import org.example.Main;
import org.example.Model.*;
import org.example.Service.MovieService;

import java.text.*;
import java.util.*;

public class CLIParser {

    MovieService newMovieService;
    Scanner sc = new Scanner(System.in);

    public CLIParser() { newMovieService = new MovieService(); }

    public boolean printList(List<?> list) {
        if (!list.isEmpty()) {
            for (Object entry : list) {
                System.out.println(entry.toString());
            }
            return true;
        } else {
            System.out.println("Found nothing! ");
            return false;
        }
    }

    public int validateIDInput(String choice) throws CLIException{
        int choiceInt;
        if (choice.isEmpty()) {
            Main.log.warn("CLIException: Empty command.");
            throw new CLIException("Incorrect selection! Please choose a valid ID.");
        }
        else {
            choiceInt = Integer.parseInt(choice);
        }
        return choiceInt;
    }

    public String requestMovieName() {
        System.out.println("Please enter the Name of the Movie: ");
        return sc.nextLine();
    }

    public int requestReleaseYear() throws NumberFormatException {
        System.out.println("Please enter the Release Year of the Movie: ");
        String ReleaseYear = sc.nextLine();
        return Integer.parseInt(ReleaseYear);
    }

    public String requestNewDirectorFirstName() {
        System.out.println("Please enter the First Name of Director: ");
        return sc.nextLine();
    }

    public String requestNewDirectorLastName() {
        System.out.println("Please enter the Last Name of Director: ");
        return sc.nextLine();
    }

    public DirectorEntry requestDirector() throws CLIException, MovieException {
        System.out.println("Here is the available list of Directors: Please enter either Director ID too add a new movie or enter 'new' to add a new Director");

        printList(newMovieService.getDirectors());
        String DirectorChoice = sc.nextLine();

        if (DirectorChoice.equalsIgnoreCase("new")) {
            newMovieService.addDirector(requestNewDirectorFirstName(), requestNewDirectorLastName());
            return newMovieService.getDirectorbyID(newMovieService.getDirectors().size());
        }
        else if (DirectorChoice.isEmpty()){
            Main.log.warn("CLIException: Empty command.");
            throw new CLIException("Incorrect selection! Please enter either Director ID too add a new movie or enter 'new' to add a new Director");
        }
        else {
            int DirectorID = Integer.parseInt(DirectorChoice);
            if (newMovieService.getDirectorbyID(DirectorID) == null) {
                Main.log.warn("MovieException: Throwing ID not found exception.");
                throw new MovieException("Found no directors with provided ID.");
            }
            else {
                return newMovieService.getDirectorbyID(DirectorID);
            }
        }
    }

    public List<String> requestGenre() {
        System.out.println("Please enter the Genre(s) of the Movie (separate by comma): ");
        return Arrays.asList(sc.nextLine().replace(" ", "").split(","));
    }

    public boolean interpretMovieAddAction() throws MovieException, CLIException {
        newMovieService.addMovie(requestMovieName(), requestReleaseYear(), requestDirector(), requestGenre());
        return true;
    }

    public boolean interpretMovieViewAction() {
        printList(newMovieService.getMovies());
        return true;
    }

    public boolean interpretMovieDeleteAction() throws CLIException, MovieException {
        System.out.println("Here are the movies eligible for delete operation: ");
        boolean flag = printList(newMovieService.getMovies());
        if (flag) {
            System.out.println("Which movie would you like to Delete? Please choose its ID: ");
            newMovieService.deleteMovie(validateIDInput(sc.nextLine()));
        }
        return true;
    }

    public boolean interpretMovieUpdateAction() throws MovieException, CLIException {
        System.out.println("Here are the movies eligible for update operation: ");
        boolean flag = printList(newMovieService.getMovies());

        if (flag) {
            System.out.println("Please enter the ID of the movie to be updated");
            int movieID = validateIDInput(sc.nextLine());
            MovieEntry movieEntry = newMovieService.getMoviebyID(movieID);

            System.out.println("What attribute would you like to update? Please Name, Year, Director, or Genre: ");
            String UpdateChoice = sc.nextLine();

            if (UpdateChoice.equalsIgnoreCase("name")) {
                newMovieService.updateMovie(movieEntry, requestMovieName());
            }
            else if (UpdateChoice.equalsIgnoreCase("year")) {
                newMovieService.updateMovie(movieEntry, requestReleaseYear());
            }
            else if (UpdateChoice.equalsIgnoreCase("director")) {
                newMovieService.updateMovie(movieEntry, requestDirector());
            }
            else if (UpdateChoice.equalsIgnoreCase("genre")) {
                newMovieService.updateMovie(movieEntry, requestGenre());
            }
            else {
                Main.log.info("CLIException: Throwing invalid choice exception.");
                throw new CLIException("Incorrect selection! Please choose from: Name, Year, Director, or Genre: ");
            }
        }
        return true;
    }

    public boolean interpretMovieSearchAction() throws CLIException{
        System.out.println("What would you like to use as Movie Search criteria; Name, Director, or Genre: ");
        String searchChoice = sc.nextLine();
        if (searchChoice.equalsIgnoreCase("name")) {
            System.out.println("Please enter the Movie Name you would like to search for: ");
            printList(newMovieService.searchMovie("name", sc.nextLine()));
        }
        else if (searchChoice.equalsIgnoreCase("director")) {
            System.out.println("Please enter Director First or Last Name you would like to search for: ");
            printList(newMovieService.searchMovie("director", sc.nextLine()));
        }
        else if (searchChoice.equalsIgnoreCase("genre")) {
            System.out.println("Please enter the Movie Genre you would like to search for: ");
            printList(newMovieService.searchMovie("genre", sc.nextLine()));
        }
        else {
            Main.log.warn("CLIException: Throwing exception due to incorrect selection.");
            throw new CLIException("Incorrect selection! Please choose from: Name, Director, or Genre: ");
        }
        return true;
    }

    public boolean interpretDirectorAddAction() throws CLIException, MovieException {
        newMovieService.addDirector(requestNewDirectorFirstName(), requestNewDirectorLastName());
        return true;
    }

    public boolean interpretDirectorViewAction() {
        printList(newMovieService.getDirectors());
        return true;
    }

    public boolean interpretDirectorDeleteAction() throws CLIException, MovieException {
        System.out.println("Here are the directors eligible for delete operation: ");
        boolean flag = printList(newMovieService.getDirectors());
        if (flag) {
            System.out.println("Which director would you like to Delete? Please choose its ID: ");
            int directorID = validateIDInput(sc.nextLine());
            newMovieService.deleteMovie(newMovieService.getMoviebyDirectorID(directorID).getID());
            newMovieService.deleteDirector(directorID);
        }
        return true;
    }

    public boolean interpretDirectorUpdateAction() throws CLIException, MovieException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Here are the directors eligible for update operation: ");
        boolean flag = printList(newMovieService.getDirectors());
        if (flag) {
            System.out.println("Please enter the ID of the director to be updated");
            int directorID = validateIDInput(sc.nextLine());
            DirectorEntry directorChosen = newMovieService.getDirectorbyID(directorID);

            if(directorChosen != null) {
                System.out.println("What attribute would you like to update? Please enter First, or Last Name: ");
                String UpdateChoice = sc.nextLine();
                if (UpdateChoice.equalsIgnoreCase("first")) {
                    newMovieService.updateDirector(directorChosen, "first", requestNewDirectorFirstName());
                } else if (UpdateChoice.equalsIgnoreCase("last")) {
                    newMovieService.updateDirector(directorChosen, "last", requestNewDirectorLastName());
                } else {
                    Main.log.warn("CLIException: Throwing exception due to incorrect selection.");
                    throw new CLIException("Incorrect selection! Please choose from: First, or Last Name: ");
                }
                System.out.println(directorChosen.toString());
            }
            else {
                Main.log.warn("MovieException: ID not found.");
                throw new MovieException("No director was found with provided ID!");
            }
        }
        return true;
    }

    public boolean interpretDirectorSearchAction() throws CLIException {
        System.out.println("Please enter Director First or Last Name you would like to search for: ");
        String searchWord = sc.nextLine();
        if (searchWord.isEmpty()) {
            Main.log.warn("CLIException: Empty command.");
            throw new CLIException("Incorrect Selection! Please enter Director First or Last Name you would like to search for: ");
        }
        printList(newMovieService.getDirectorbyFirstOrLastName(searchWord));
        return true;
    }

    public boolean parseMovieCommandReturnOutput(String command) throws MovieException, CLIException {
        if (command.equalsIgnoreCase("add")) {
            return interpretMovieAddAction();
        } else if (command.equalsIgnoreCase("view")) {
            return interpretMovieViewAction();
        } else if (command.equalsIgnoreCase("delete")) {
            return interpretMovieDeleteAction();
        } else if (command.equalsIgnoreCase("update")) {
            return interpretMovieUpdateAction();
        } else if (command.equalsIgnoreCase("search")) {
            return interpretMovieSearchAction();
        } else if (command.equalsIgnoreCase("exit")) {
            return false;
        } else {
            Main.log.warn("CLIException: Throwing exception due to incorrect selection.");
            throw new CLIException("Incorrect selection! Please choose from: Add, View, Delete, Update, Search, or Exit to end the program.");
        }
    }

    public Boolean parseDirectorCommandReturnOutput(String command) throws MovieException, CLIException {
        if (command.equalsIgnoreCase("add")) {
            return interpretDirectorAddAction();
        } else if (command.equalsIgnoreCase("view")) {
            return interpretDirectorViewAction();
        } else if (command.equalsIgnoreCase("delete")) {
            return interpretDirectorDeleteAction();
        } else if (command.equalsIgnoreCase("update")) {
            return interpretDirectorUpdateAction();
        } else if (command.equalsIgnoreCase("search")) {
            return interpretDirectorSearchAction();
        } else if (command.equalsIgnoreCase("done")) {
            return false;
        } else {
            Main.log.warn("CLIException: Throwing exception due to incorrect selection.");
            throw new CLIException("Incorrect selection! Please choose from: Add, View, Delete, Update, Search, or Exit to end the program.");
        }
    }

    public boolean parseCommandReturnOutput(String command) throws CLIException, MovieException {
        if (command.equalsIgnoreCase("movie")) {
            System.out.println("\nMovie Menu Options: Add, View, Delete, Update, Search, or Exit to end the program.");
            String userChoice = sc.nextLine();
            return parseMovieCommandReturnOutput(userChoice);
        }
        else if (command.equalsIgnoreCase("director")) {
            System.out.println("\nDirector Menu Options: Add, View, Delete, Update, Search, or Exit to end the program.");
            String userChoice = sc.nextLine();
            return parseDirectorCommandReturnOutput(userChoice);
        }
        else if (command.equalsIgnoreCase("exit")) {
            return false;
        }
        else {
            Main.log.warn("CLIException: Throwing exception due to incorrect selection.");
            throw new CLIException("Incorrect selection! Please choose from: Movie, Director, or Exit.");
        }
    }
}
