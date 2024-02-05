package org.example.Service;

import org.example.Exception.CLIException;
import org.example.Exception.MovieException;
import org.example.Main;
import org.example.Model.*;

import java.util.*;

public class MovieService {
    List<MovieEntry> MovieList;
    List<DirectorEntry> DirectorList;

    public MovieService() {
        this.MovieList = new ArrayList<>();
        this.DirectorList = new ArrayList<>();

        DirectorList.add(new DirectorEntry(1, "Christopher", "Nolan"));
        DirectorList.add(new DirectorEntry(2, "Greta", "Gerwig"));
        DirectorList.add(new DirectorEntry(3, "Quentin", "Tarantino"));
        DirectorList.add(new DirectorEntry(4, "Wes", "Anderson"));
        DirectorList.add(new DirectorEntry(5, "Ava", "DuVernay"));
        DirectorList.add(new DirectorEntry(6, "Damien", "Chazelle"));

        MovieList.add(new MovieEntry(1, "Whiplash", 2014, getDirectorbyID(6), Arrays.asList("Drama")));
        MovieList.add(new MovieEntry(2, "Lady Bird", 2017, getDirectorbyID(2), Arrays.asList("Comedy", "Thriller")));
        MovieList.add(new MovieEntry(3, "Fantastic Mr. Fox", 2010, getDirectorbyID(4), Arrays.asList("Comedy", "Adventure")));
        MovieList.add(new MovieEntry(4, "Rushmore", 1998, getDirectorbyID(4), Arrays.asList("Comedy")));
        MovieList.add(new MovieEntry(5, "Selma", 2014, getDirectorbyID(5), Arrays.asList("Drama")));
    }

    public boolean isValidYear(int input) {
        return input >= 1895;
    }

    public int getMaxMovieID() {
        Main.log.info("Attempting to get max ID of Movie List.");
        int maxID = 0;
        for (int i = 0; i<this.MovieList.size(); i++) {
            if (this.MovieList.get(i).getID() > maxID){
                maxID = this.MovieList.get(i).getID();
            }
        }
        return maxID;
    }

    public void addMovie(String movieName, int releaseYear, DirectorEntry directorEntry, List<String> genreList) throws MovieException {
        Main.log.info("Attempting to add a new Movie.");
        int size = getMaxMovieID();
        if (movieName.isEmpty()) {
            Main.log.warn("MovieException: Throwing blank entry exception for Movie Name.");
            throw new MovieException("Movie Name is blank");
        }
        else if (!isValidYear(releaseYear)) {
            Main.log.warn("MovieException: Throwing exception due to incorrect Release Year input.");
            throw new MovieException("Release year must be after 1895.");
        }
        else if (genreList.get(0).isEmpty()) {
            Main.log.warn("MovieException: Throwing blank entry exception for genre.");
            throw new MovieException("Genre is blank");
        }
        else {
            this.MovieList.add(new MovieEntry(size + 1, movieName, releaseYear, directorEntry, genreList));
            System.out.println("Movie has been added! ");
        }
    }

    public List<MovieEntry> getMovies(){
        Main.log.info("Attempting to get all Movies.");
        return this.MovieList;
    }

    public MovieEntry getMoviebyID(int ID) throws MovieException{
        Main.log.info("Attempting to get movie by its ID.");
        MovieEntry movieEntry = null;
        try {
            for (MovieEntry entry : MovieList){
                if (entry.getID() == ID) {
                    movieEntry = entry;
                }
            }
        }
        catch (IndexOutOfBoundsException e){
            Main.log.warn("MovieException: ID not found.");
            e.getMessage();
            throw new MovieException("No movie was found with provided ID!");
        }
        return movieEntry;
    }

    public MovieEntry getMoviebyName(String movieName) throws MovieException{
        Main.log.info("Attempting to get movie by its name.");
        MovieEntry movieEntry = null;
        for (MovieEntry entry : MovieList){
            if (entry.getMovieName().equals(movieName)) {
                movieEntry = entry;
            }
        }
        if (movieEntry == null) {
            Main.log.warn("MovieException: Throwing no movie found exception.");
            throw new MovieException("Found no movies with provided name.");
        }
        return movieEntry;
    }

    public MovieEntry getMoviebyDirectorID(int directorID) {
        Main.log.info("Attempting to get movie by its name.");
        MovieEntry movieEntry = null;
        for (MovieEntry entry : MovieList){
            if (entry.getDirectorInfo().getID() == directorID) {
                movieEntry = entry;
            }
        }
        return movieEntry;    }

    public List<MovieEntry> searchMovie(String searchOption, String searchWord){
        Main.log.info("Attempting to search a Movie by name, director, or genre.");
        List<MovieEntry> ListMatch = new ArrayList<>();;
        if (searchOption.equals("name")) {
            for (MovieEntry entry : MovieList) {
                if (entry.getMovieName().equalsIgnoreCase(searchWord.toLowerCase())){
                    ListMatch.add(entry);
                }
            }
        }
        else if (searchOption.equals("director")) {
            for (MovieEntry entry : MovieList) {
                if (entry.getDirectorInfo().getFirstName().equalsIgnoreCase(searchWord) ||
                    entry.getDirectorInfo().getLastName().equalsIgnoreCase(searchWord)){
                    ListMatch.add(entry);
                }
            }
        }
        else if (searchOption.equals("genre")) {
            for (MovieEntry entry : MovieList) {
                for (int i = 0; i<entry.getGenreList().size(); i++){
                    if (entry.getGenreList().get(i).equalsIgnoreCase(searchWord.toLowerCase())) {
                        ListMatch.add(entry);
                    }
                }
            }
        }

        System.out.println("\nHere are the results of your search request: ");
        return ListMatch;
    }

    public void updateMovie(MovieEntry movieEntry, String newValue) throws MovieException{
        Main.log.info("Attempting to update Name of a Movie.");
        if (newValue.isEmpty()) {
            Main.log.warn("MovieException: Throwing blank entry exception for Movie Name.");
            throw new MovieException("Movie Name is blank");
        } else {
            movieEntry.setMovieName(newValue);
        }
    }

    public void updateMovie(MovieEntry movieEntry, int newValue) throws MovieException {
        Main.log.info("Attempting to update Release Year of a Movie.");
        if (!isValidYear(newValue)) {
            Main.log.warn("MovieException: Throwing exception due to incorrect Release Year input.");
            throw new MovieException("Release year must be after 1895.");
        }
        else {
            movieEntry.setReleaseYear(newValue);
        }
    }

    public void updateMovie(MovieEntry movieEntry, DirectorEntry newValue) throws MovieException{
        Main.log.info("Attempting to update Director of a Movie.");
        movieEntry.setDirectorInfo(newValue);

    }

    public void updateMovie(MovieEntry movieEntry, List<String> newValue) throws MovieException{
        Main.log.info("Attempting to update Genre(s) of a Movie.");
        if (newValue.get(0).isEmpty()) {
            Main.log.warn("MovieException: Throwing blank entry exception for genre.");
            throw new MovieException("Genre is blank");
        } else {
            movieEntry.setGenres(newValue);
        }
    }

    public void deleteMovie(int MovieID) throws MovieException {
        Main.log.info("Attempting to delete a Movie.");
        MovieEntry movieEntry = getMoviebyID(MovieID);
        if (movieEntry == null){
            Main.log.warn("MovieException: Throwing ID not found exception.");
            throw new MovieException("Found no movies with provided ID.");
        }
        else {
            System.out.println("Deleting movie... " + movieEntry.getMovieName());
            MovieList.remove(movieEntry);
        }
    }

    public int getMaxDirectorID() {
        Main.log.info("Attempting to get max ID of Director List.");
        int maxID = 0;
        for (int i = 0; i<this.DirectorList.size(); i++) {
            if (this.DirectorList.get(i).getID() > maxID){
                maxID = this.DirectorList.get(i).getID();
            }
        }
        return maxID;
    }

    public void addDirector(String firstName, String lastName) throws MovieException{
        Main.log.info("Attempting to add a Director.");
        int size = getMaxDirectorID();
        if (firstName.isEmpty()) {
            Main.log.warn("MovieException: Throwing blank entry exception for First Name.");
            throw new MovieException("First Name is blank");
        }
        else if (lastName.isEmpty()) {
            Main.log.warn("MovieException: Throwing blank entry exception for Last Name.");
            throw new MovieException("Last Name is blank");
        }
        else {
            this.DirectorList.add(new DirectorEntry(size+1, firstName, lastName));
        }

    }
    public List<DirectorEntry> getDirectors(){
        Main.log.info("Attempting to get all Directors.");
        return this.DirectorList;
    }

    public DirectorEntry getDirectorbyID(int ID) {
        Main.log.info("Attempting to get Director by their ID.");
        DirectorEntry directorEntry = null;
        try {
            for (DirectorEntry entry : DirectorList) {
                if (entry.getID() == ID) {
                    directorEntry = entry;
                }
            }
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return directorEntry;

    }


    public List<DirectorEntry> getDirectorbyFirstOrLastName(String searchWord){
        Main.log.info("Attempting to get Director by their First or Last Name.");
        List<DirectorEntry> ListMatch = new ArrayList<>();
        for (DirectorEntry entry : DirectorList) {
            if (entry.getFirstName().equalsIgnoreCase(searchWord) ||
                    entry.getLastName().equalsIgnoreCase(searchWord)){
                ListMatch.add(entry);
            }
        }
        return ListMatch;
    }

    public DirectorEntry getDirectorbyFullName(String searchFirstName, String searchLastName) throws MovieException{
        Main.log.info("Attempting to get Director by their First or Last Name.");
        DirectorEntry directorEntry = null;
        for (DirectorEntry entry : DirectorList) {
            if (entry.getFirstName().equalsIgnoreCase(searchFirstName) &&
                    entry.getLastName().equalsIgnoreCase(searchLastName)){
                directorEntry = entry;
            }
        }
        if (directorEntry == null) {
            Main.log.warn("MovieException: Throwing Director not found exception.");
            throw new MovieException("Found no directors with provided first and last name.");
        }
        else {
            return directorEntry;
        }
    }

    public void updateDirector(DirectorEntry directorEntry, String choice, String newValue) throws MovieException{
        Main.log.info("Attempting to update First or Last Name of a Director.");
        if (choice.equals("first")) {
            if (newValue.isEmpty()){
                Main.log.warn("MovieException: First Name can't be blank.");
                throw new MovieException("First Name can't be blank.");
            }
            else {
                directorEntry.setFirstName(newValue);
            }
        }
        else if (choice.equals("last")) {
            if (newValue.isEmpty()){
                Main.log.warn("MovieException: Last Name can't be blank.");
                throw new MovieException("Last Name can't be blank.");
            }
            else {
                directorEntry.setLastName(newValue);
            }
        }
    }

    public void deleteDirector(int DirectorID) throws MovieException{
        Main.log.info("Attempting to delete a Director.");
        DirectorEntry deleteDirector = getDirectorbyID(DirectorID);
        if (deleteDirector == null){
            Main.log.warn("MovieException: Throwing ID not found exception.");
            throw new MovieException("Found no directors with provided ID.");
        }
        else {
            System.out.println("Deleting director... " + deleteDirector.getFirstName() + " " + deleteDirector.getLastName());
            DirectorList.remove(getDirectorbyID(DirectorID));
        }
    }
}
