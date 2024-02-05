import org.example.Exception.MovieException;
import org.example.Model.DirectorEntry;
import org.example.Model.MovieEntry;
import org.example.Service.MovieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MovieServiceTest {

    MovieService movieService;

    /**
     * Before every single test, we should reset the movie service to a newly-inst
     */
    @Before
    public void setUp(){
        movieService = new MovieService();
    }

    @Test
    public void movieServiceEmptyAtStart(){
        List<MovieEntry> movieList = movieService.getMovies();
        // ensure that at the start, there are no movies
        Assert.assertTrue(!movieList.isEmpty());
    }

    @Test
    /** happy path to add Movie **/
    public void movieServiceAddMovie() throws MovieException {
        String testName = "Asteroid City";
        int testReleaseYear = 2023;
        String testDirectorFirstName = "Wes";
        String testDirectorLastName = "Anderson";
        List<String> testGenre = Arrays.asList("Romance", "Comedy");

        try {
            DirectorEntry testDirector = movieService.getDirectorbyFullName(testDirectorFirstName, testDirectorLastName);
            movieService.addMovie(testName, testReleaseYear, testDirector, testGenre);
        }
        catch (MovieException e) {
            e.printStackTrace();
            Assert.fail("Movie Exception incorrectly thrown");
        }

        List<MovieEntry> movieList = movieService.getMovies();
        MovieEntry movieEntry = movieService.getMoviebyID(movieService.getMaxMovieID());
        Assert.assertEquals(testName, movieEntry.getMovieName());
        Assert.assertEquals(testReleaseYear, movieEntry.getReleaseYear());
        Assert.assertEquals(testDirectorFirstName, movieEntry.getDirectorInfo().getFirstName());
        Assert.assertEquals(testDirectorLastName, movieEntry.getDirectorInfo().getLastName());
        Assert.assertEquals(testGenre, movieEntry.getGenreList());
    }

    @Test
    /** happy path to add Director **/
    public void movieServiceAddDirector() throws MovieException {
        String testDirectorFirstName = "Martin";
        String testDirectorLastName = "Scorsese";

        try {
            movieService.addDirector(testDirectorFirstName, testDirectorLastName);
        }
        catch (MovieException e){
            e.printStackTrace();
            Assert.fail("Movie Exception incorrectly thrown");
        }

        List<DirectorEntry> movieList = movieService.getDirectors();
        DirectorEntry directorEntry = movieService.getDirectorbyID(movieService.getMaxDirectorID());
        Assert.assertEquals(testDirectorFirstName, directorEntry.getFirstName());
        Assert.assertEquals(testDirectorLastName, directorEntry.getLastName());
    }

    /** Invalid Release Year, should throw error **/
    @Test
    public void movieServiceAddMovieInvalidYear() throws MovieException{
        String testName = "Asteroid City";
        int testReleaseYear = 1823;
        String testDirectorFirstName = "Wes";
        String testDirectorLastName = "Anderson";
        List<String> testGenre = Arrays.asList("Romance", "Comedy");

        try{
            DirectorEntry testDirector = movieService.getDirectorbyFullName(testDirectorFirstName, testDirectorLastName);
            movieService.addMovie(testName, testReleaseYear, testDirector, testGenre);
            Assert.fail();
        }catch(MovieException e){
            e.printStackTrace();
        }
    }


    @Test
    public void movieServiceUpdateMovieInvalidYear() throws MovieException{
        String testName = "Asteroid City";
        int testReleaseYear = 1823;

        try{
            MovieEntry movieEntry = movieService.getMoviebyName(testName);
            movieService.updateMovie(movieEntry, testReleaseYear);
            Assert.fail();
        }catch(MovieException e){
            e.printStackTrace();
        }
    }

    /** No Movie found, should throw exception **/
    @Test
    public void movieServiceFoundNoMovietoUpdate() throws MovieException{
        String testName = "Asteroid City";
        String testNewName = "Asteroid City: Welcome Aliens!";

        List<MovieEntry> movieEntry = movieService.searchMovie("name", testName);
        Assert.assertTrue(movieEntry.isEmpty());
    }

    @Test
    public void movieServiceFoundNoMovietoDelete() throws MovieException {
        int testMovieID = 0;

        try{
            movieService.deleteMovie(testMovieID);
            Assert.fail();
        }
        catch(MovieException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void movieServiceUpdateMovieWithBlankName() throws MovieException{
        String testName = "Whiplash";
        List<String> testGenre = Arrays.asList("");
        try{
            List<MovieEntry> movieEntry = movieService.searchMovie("name", testName);
            MovieEntry fistMovieEntry = movieEntry.get(0);
            movieService.updateMovie(fistMovieEntry, testGenre);
            Assert.fail();
        }catch(MovieException e){
            e.printStackTrace();
        }
    }

    /** No Director found, should throw exception **/
    @Test
    public void movieServiceFoundNoDirector() throws MovieException{
        String testDirectorFirstName = "Martin";
        String testDirectorLastName = "Scorsese";

        try{
            movieService.getDirectorbyFullName(testDirectorFirstName, testDirectorLastName);
            Assert.fail();
        }catch(MovieException e){
            e.printStackTrace();
        }
    }

    @Test
    public void movieServiceFoundNoDirectorWithName() {
        String testSearchWord = "Martin";
        List<DirectorEntry> directorList = movieService.getDirectorbyFirstOrLastName(testSearchWord);
        Assert.assertTrue(directorList.isEmpty());
    }

    @Test
    public void movieServiceDeleteDirector() throws MovieException {
        int directorID = 1;
        movieService.deleteDirector(directorID);
        MovieEntry deletedMovie = movieService.getMoviebyDirectorID(directorID);
        Assert.assertNull(deletedMovie);
    }

    @Test
    public void movieServiceDeleteInvalidDirector() throws MovieException {
        int directorID = 9;
        try {
            movieService.deleteDirector(directorID);
            Assert.fail();
        }
        catch (MovieException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void movieServiceUpdateDirectorFirstName() {
        String testDirectorFirstName = "Wes";
        int directorID = 1;

        try {
            DirectorEntry testDirector = movieService.getDirectorbyID(directorID);
            movieService.updateDirector(testDirector, "first", testDirectorFirstName);
        }
        catch (MovieException e) {
            e.printStackTrace();
            Assert.fail("Movie Exception incorrectly thrown");
        }
        String updatedDirectorFistName = movieService.getDirectorbyID(directorID).getFirstName();
        Assert.assertEquals(testDirectorFirstName, updatedDirectorFistName);
    }

    @Test
    public void movieServiceUpdateDirectorFirstNamewithBlank() {
        String testDirectorFirstName = "";
        int directorID = 1;

        try {
            DirectorEntry testDirector = movieService.getDirectorbyID(directorID);
            movieService.updateDirector(testDirector, "first", testDirectorFirstName);
            Assert.fail();
        }
        catch (MovieException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void movieServiceUpdateDirectorLastName() {
        String testDirectorLastName = "Wes";
        int directorID = 1;

        try {
            DirectorEntry testDirector = movieService.getDirectorbyID(directorID);
            movieService.updateDirector(testDirector, "last", testDirectorLastName);
        }
        catch (MovieException e) {
            e.printStackTrace();
            Assert.fail("Movie Exception incorrectly thrown");
        }
        String updatedDirectorLastName = movieService.getDirectorbyID(directorID).getLastName();
        Assert.assertEquals(testDirectorLastName, updatedDirectorLastName);
    }

    @Test
    public void movieServiceUpdateDirectorLastNamewithBlank() {
        String testDirectorLastName = "";
        int directorID = 1;

        try {
            DirectorEntry testDirector = movieService.getDirectorbyID(directorID);
            movieService.updateDirector(testDirector, "last", testDirectorLastName);
            Assert.fail();
        }
        catch (MovieException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void movieServiceAddDirectorwithBlankFirstName(){
        String testDirectorFirstName = "";
        String testDirectorLastName = "temp";

        try {
            movieService.addDirector(testDirectorFirstName, testDirectorFirstName);
            Assert.fail();
        }
        catch (MovieException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void movieServiceUpdateWithInvalidReleaseYear() throws MovieException {
        int testReleaseYear = 1880;
        int movieID = 1;

        try {
            MovieEntry movieEntry = movieService.getMoviebyID(movieID);
            movieService.updateMovie(movieEntry, testReleaseYear);
            Assert.fail();
        }
        catch (MovieException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void movieServiceUpdateReleaseYear() throws MovieException {
        int testReleaseYear = 2024;
        int movieID = 1;
        try {
            MovieEntry movieEntry = movieService.getMoviebyID(movieID);
            movieService.updateMovie(movieEntry, testReleaseYear);
        }
        catch (MovieException e) {
            e.printStackTrace();
            Assert.fail("Movie Exception incorrectly thrown");
        }
        int updatedReleaseYear = movieService.getMoviebyID(movieID).getReleaseYear();
        Assert.assertEquals(testReleaseYear, updatedReleaseYear);
    }

    @Test
    public void movieServiceUpdateDirector() throws MovieException {
        int testDirectorID = 1;
        int testMovieID = 1;
        try {
            MovieEntry movieEntry = movieService.getMoviebyID(testMovieID);
            DirectorEntry directorEntry = movieService.getDirectorbyID(testDirectorID);
            movieService.updateMovie(movieEntry, directorEntry);
        }
        catch (MovieException e) {
            e.printStackTrace();
            Assert.fail("Movie Exception incorrectly thrown");
        }
        int updatedDirectorID = movieService.getMoviebyID(testMovieID).getDirectorInfo().getID();
        Assert.assertEquals(testDirectorID, updatedDirectorID);
    }


    @Test
    public void movieServiceUpdateName() throws MovieException {
        String testName = "temp";
        int testMovieID = 1;
        try {
            MovieEntry movieEntry = movieService.getMoviebyID(testMovieID);
            movieService.updateMovie(movieEntry, testName);
        }
        catch (MovieException e) {
            e.printStackTrace();
            Assert.fail("Movie Exception incorrectly thrown");
        }
        String updatedName = movieService.getMoviebyID(testMovieID).getMovieName();
        Assert.assertEquals(testName, updatedName);
    }
}
