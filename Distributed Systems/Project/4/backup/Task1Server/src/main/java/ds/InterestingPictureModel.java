package ds;

/*
 * @author Qinzhi Peng, qinzhip
 *
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * making a request to Web Search API and then parse the result in order to fabricate an image URL.
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InterestingPictureModel {

    /**
     * @param searchTag The tag of the photo to be searched for.
     */
    public String doPexelSearch(String searchTag)
            throws UnsupportedEncodingException {

        // URL encode the searchTag
        searchTag = URLEncoder.encode(searchTag, "UTF-8");

        String pictureURL = fetch(searchTag);
        System.out.println("pictureURL= " + pictureURL);

        return pictureURL;
    }

    /*
     * Make an HTTP request to a given URL
     *
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://pexelsdimasv1.p.rapidapi.com/v1/search?query=" + urlString + "&locale=en-US&per_page=1&page=1"))
                    .header("Authorization", "563492ad6f917000010000013ad7ebbed0e540b9b5df8699603da568")
                    .header("X-RapidAPI-Host", "PexelsdimasV1.p.rapidapi.com")
                    .header("X-RapidAPI-Key", "221879cb08msh568eb9c3c4b0281p1c582ajsn0ad4adf6c64b")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> IPresponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONParser parse = new JSONParser();

            try {
                JSONObject data_obj = (JSONObject) parse.parse(IPresponse.body());
                JSONArray photos = (JSONArray) data_obj.get("photos");
                response = parsePhotoObject((JSONObject) photos.get(0));
                System.out.println("response: " + response);
            } catch (ParseException e) {
                System.out.println("photos broken " + e);
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            System.out.println("read mistake, an exception");
            // Do something reasonable.  This is left for students to do.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private static String parsePhotoObject(JSONObject pic) {
        JSONObject picObject = (JSONObject) pic.get("src");

        String original = (String) picObject.get("original");
        System.out.println(original);

        String large = (String) picObject.get("large");
        System.out.println(large);

        String small = (String) picObject.get("small");
        System.out.println(small);

        return small;
    }

    /*
     * Return a URL of an image of appropriate size
     *
     * Arguments
     * @param picSize The string "mobile" or "desktop" indicating the size of
     * photo requested.
     * @return The URL an image of appropriate size.
     */
    private String interestingPictureSize(String pictureURL, String picSize) {
        int finalDot = pictureURL.lastIndexOf(".");
        /*
         * From the flickr online documentation, an underscore and a letter
         * before the final "." and file extension is a size indicator.
         * "_m" for small and "-z" for big.
         */
        String sizeLetter = (picSize.equals("mobile")) ? "m" : "z";
        if (pictureURL.indexOf("_", finalDot-2) == -1) {
            // If the URL currently did not have a _? size indicator, add it.
            return (pictureURL.substring(0, finalDot) + "_" + sizeLetter
                    + pictureURL.substring(finalDot));
        } else {
            // Else just change it
            return (pictureURL.substring(0, finalDot - 1) + sizeLetter
                    + pictureURL.substring(finalDot));
        }
    }
}
