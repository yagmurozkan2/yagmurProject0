package org.example.Model;

import java.util.*;

public class MovieEntry {

    private int ID;
    private String MovieName;
    private int ReleaseYear;
    private DirectorEntry DirectorInfo;
    private List<String> GenreList;

    public MovieEntry(int ID, String MovieName, int ReleaseYear, DirectorEntry DirectorInfo, List<String> GenreList){
        this.ID = ID;
        this.MovieName = MovieName;
        this.ReleaseYear = ReleaseYear;
        this.DirectorInfo = DirectorInfo;
        this.GenreList = GenreList;
    }

    public int getID() { return this.ID; }

    public String getMovieName() { return this.MovieName; }

    public int getReleaseYear() { return this.ReleaseYear; }

    public DirectorEntry getDirectorInfo() { return this.DirectorInfo; }

    public List<String> getGenreList() { return this.GenreList; }

    public void setMovieName(String MovieName) {
        this.MovieName = MovieName;
    }

    public void setReleaseYear(int ReleaseYear) {
        this.ReleaseYear = ReleaseYear;
    }

    public void setDirectorInfo(DirectorEntry DirectorInfo) {
        this.DirectorInfo = DirectorInfo;
    }

    public void setGenres(List<String> GenreList){
        this.GenreList = GenreList;
    }

    public String getGenreString(){
        String GenreString = "";
        for (int i = 0; i < this.GenreList.size(); i++){
            if (i == this.GenreList.size() - 1) {
                GenreString = GenreString + GenreList.get(i);
            }
            else {
                GenreString = GenreString + GenreList.get(i) + ", ";
            }
        }
        return GenreString;
    }

    @Override
    public String toString(){
        return "Movie ID: " + this.ID
                + " - Name: " + this.MovieName
                + ", Release Year: " + this.ReleaseYear
                + ", Director: " + this.DirectorInfo.getFirstName() + " " + this.DirectorInfo.getLastName()
                + ", Genre(s): " + getGenreString();
    }

}