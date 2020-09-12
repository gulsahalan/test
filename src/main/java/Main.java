import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    private String url = "http://www.omdbapi.com/?apikey=91d193d3&";
    private String byName = "s=harry+potter";
    private String byId = "i=";
    private String movieName = "Harry Potter and the Sorcerer's Stone";

    @Test
    public void harryPotterTest() {
        Response response = given().get(url + byName);
        Assertions.assertEquals(200, response.statusCode());
        List<Map<String, String>> movies = new ArrayList<>();
        movies = response.jsonPath().getList("Search");
//        System.out.println(movies);
        Map<String, String> movie = getMovieByName(movieName, movies);
        Assertions.assertNotNull(movie);
        byId = byId + movie.get("imdbID");


        Response movieResponse = given().get(url + byId);
//        System.out.println("new url: " + url+byId);
//        System.out.println(movieResponse.jsonPath().prettyPrint());
        Map<String, String> movieMap = movieResponse.jsonPath().getMap("");
        System.out.println(movieMap);

        Assertions.assertNotNull(movieMap.get("Title"));
        System.out.println("Movie Title : " + movieMap.get("Title"));
        Assertions.assertNotNull(movieMap.get("Released"));
        System.out.println("Movie Year : " + movieMap.get("Released"));
        Assertions.assertNotNull(movieMap.get("Year"));
        System.out.println("Movie Year : " + movieMap.get("Year"));
        Assertions.assertEquals(200, movieResponse.statusCode());

    }

    public Map<String, String> getMovieByName (String movieName, List<Map<String, String>> movies){
        for (Map<String, String> movie : movies) {
            if (movieName.equals(movie.get("Title"))) {
                return movie;
            }
        }
        return null;
    }


}
