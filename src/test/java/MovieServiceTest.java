import org.example.Model.MovieEntry;
import org.example.Service.MovieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertTrue(movieList.size() != 0);
    }

    /**
     * happy path, everything works
     */
}
