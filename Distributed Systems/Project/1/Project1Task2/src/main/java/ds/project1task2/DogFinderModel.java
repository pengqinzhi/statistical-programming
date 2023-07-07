package ds.project1task2;

// @author Qinzhi Peng

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DogFinderModel {

    /**
     * Return a URL of a dog image
     *
     * @param searchTag The tag of the photo to be searched for.
     */
    public String doDogSearch(String searchTag) throws IOException {
        searchTag = URLEncoder.encode(searchTag, "UTF-8");
        String response = "";

        // Create a URL for the Json page to be screen scraped
        String dogJsonUrl = "https://dog.ceo/api/breed/" + searchTag + "/images";

        // Fetch the page
        response = fetch(dogJsonUrl);

        // Convert json record to java object
        DogPictures dogPictures = json2object(response);

        // Get a URL from  all URLs
        String dogPictureUrl = chooseUrl(dogPictures);
        System.out.println("pictureURL= " + dogPictureUrl);
        return dogPictureUrl;
    }

    /**
     * Return a list of a dog information
     *
     * @param searchTag The tag of the dog to be searched for.
     */
    public List<String> searchDogInfo(String searchTag) throws IOException {
        searchTag = URLEncoder.encode(searchTag, "UTF-8");

        // Create a String List to store information for selected dog
        List<String> dogInfo = new ArrayList<String>();

        // Create a URL for the dog information page to be screen scraped
        String dogInfoUrl = "https://dogtime.com/dog-breeds/" + searchTag;

        // Load a Document from a URL
        Document doc = Jsoup.connect(dogInfoUrl).get();

        // Get star ratings for Friendly and Intelligence
        String firStar = getStar(doc, "All Around Friendliness");
        String intelSubStar = getSubStar(doc, "Intelligence");
        System.out.println("Friendly: " + firStar);
        System.out.println("Intelligence: " + intelSubStar);

        // Get vital stat for height, weight, and lifespan
        String height = getStat(doc, "height");
        String weight = getStat(doc, "weight");
        String lifeSpan = getStat(doc, "life span");
        System.out.println("Height: " + height);
        System.out.println("Weight: " + weight);
        System.out.println("Life Span: " + lifeSpan);

        // Store information into dogInfo List
        dogInfo.add(firStar);
        dogInfo.add(intelSubStar);
        dogInfo.add(height);
        dogInfo.add(weight);
        dogInfo.add(lifeSpan);

        return dogInfo;
    }

    /*
     * Get dog star rating
     *
     * Arguments
     * @param Document
     * @return star string
     */
    private String getStar(Document doc, String searchWord) {
        //  Find all elements by class "characteristic-stars parent-characteristic"
        Elements contents = doc.getElementsByClass("characteristic-stars parent-characteristic");

        String starClass = "";
        //  Find star Tag by searchWord
        for (Element content : contents) {
            if (content.getElementsByClass("characteristic-title").text().equalsIgnoreCase(searchWord)) {
                Elements subContent = content.getElementsByClass("characteristic-star-block");
                // Extract the class names
                starClass = subContent.get(0).children().attr("class");
            }
        }
        // Format the star string
        String star = starClass.substring(starClass.length() - 1) + " Stars";
        return star;
    }

    /*
     * Get dog sub star rating
     *
     * Arguments
     * @param Document
     * @return star string
     */
    private String getSubStar(Document doc, String searchWord) {
        //  Find all elements by class "characteristic-title"
        Elements contents = doc.getElementsByClass("characteristic-title");

        String starClass = "";
        for (Element content : contents) {
            //  Find star Tag by searchWord
            if (content.text().equalsIgnoreCase(searchWord)) {
                Elements subContent = content.parent().getElementsByClass("characteristic-star-block");
                // Extract the class names
                starClass = subContent.get(0).children().attr("class");
            }
        }
        // Format the star string
        String star = starClass.substring(starClass.length() - 1) + " Stars";
        return star;
    }

    /*
     * Get dog vital stat
     *
     * Arguments
     * @param Document
     * @return stat string
     */
    private String getStat(Document doc, String searchWord) {
        String stat = "";
        Elements contents = doc.getElementsByClass("vital-stat-box");
        for (Element content : contents) {
            // Extract the text
            if (content.text().toLowerCase().contains(searchWord.toLowerCase())) {
                stat = content.text().substring(content.text().lastIndexOf(":") + 2);
            }
        }
        return stat;
    }

    // Create a DogPictures Object
    class DogPictures {
        List<String> message = new ArrayList<String>();
        String status = "";
    }

    /*
     * Convert json record to java object
     *
     * Arguments
     * @param dogJsonUrl The Json String
     * @return The dogPicture object
     */
    private DogPictures json2object(String response) throws IOException {
        // Creating a Gson Object
        Gson gson = new Gson();
        DogPictures dogPictures = gson.fromJson(response, DogPictures.class);
        return dogPictures;
    }

    /*
     * Randomly choose a URL from  all URLs for pictures of dogs
     *
     * Arguments
     * @param dogPicture The dogPictures object
     * @return A URL of a dog's picture chosen randomly
     */
    private String chooseUrl(DogPictures dogPictures) {
        Random rd = new Random();
        int randomIndex = rd.nextInt(dogPictures.message.size());
        String dogPictureUrl = dogPictures.message.get(randomIndex);
        return dogPictureUrl;
    }

    /*
     * Make an HTTP request to a given URL
     *
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("read mistake, an exception");
            // Do something reasonable.  This is left for students to do.
        }
        return response;
    }

}