package ds;

/*
 * @author Qinzhi Peng, qinzhip
 */

import com.google.gson.Gson;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;

@WebServlet(name = "InterestingPictureServlet",
        urlPatterns = {"/getAnInterestingPicture", "/getDashboard"})
public class InterestingPictureServlet extends HttpServlet {
    InterestingPictureModel ipm = null;
    int id;
    double latency;
    String pictureURL;

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        ipm = new InterestingPictureModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // previous images
        ArrayList<String> pictureURLList = new ArrayList<>();

        // top 10 picture search terms
        ArrayList<String> pictureTagList = new ArrayList<>();

        // average search latency

        ArrayList<Double> latencyList = new ArrayList<>();

        // initial log List
        ArrayList<IPLog> logList = new ArrayList<>();

        String search = request.getParameter("searchWord");

        String nextView = "";

        // connect to MongoDB
        String uri = "mongodb://qinzhip:pqz12311@cluster0-shard-00-00.lhwqj.mongodb.net:27017,cluster0-shard-00-01.lhwqj.mongodb.net:27017,cluster0-shard-00-02.lhwqj.mongodb.net:27017/InterestingPictureDB?w=majority&retryWrites=true&tls=true&authMechanism=SCRAM-SHA-1";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("InterestingPictureDB");
        MongoCollection<Document> collection = database.getCollection("logs");

        if (request.getServletPath().equals("/getAnInterestingPicture")) {
            if (search != null) {
                id++;

                long start = System.currentTimeMillis();
                // use model to do the search and choose the result view
                pictureURL = ipm.doPexelSearch(search);
                long end = System.currentTimeMillis();

                latency = (end - start) / 1000d;

                // set the picture url
                request.setAttribute("pictureURL", pictureURL);

                // insert data
                // Source: https://www.mongodb.com/docs/drivers/java/sync/v4.3/usage-examples/findOne/
                InsertOneResult log = collection.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("id", id)
                        .append("searchTerm", search)
                        .append("requestFromPhone", "https://damp-fortress-92242.herokuapp.com/getAnInterestingPicture?searchWord=" + search)
                        .append("requestToAPI", ipm.request.toString())
                        .append("responseFromAPI", ipm.IPresponse.toString())
                        .append("pictureURL", pictureURL)
                        .append("latency", latency));
                System.out.println("Success! Inserted document id: " + log.getInsertedId());

                nextView ="result.jsp";

            } else {
                // no search parameter so choose the prompt view
                nextView = "prompt.jsp";
            }
        } else if (request.getServletPath().equals("/getDashboard")) {
            // retrieve data
            List<Document> logDoc = collection.find(gte("id", 1)).into(new ArrayList<>());
            System.out.println("log list: ");
            for (Document eachLog : logDoc) {
                System.out.println(eachLog.toJson());
                Gson gson = new Gson();
                IPLog ipLog = gson.fromJson(eachLog.toJson(), IPLog.class);
                //System.out.println(ipLog.id);
                //System.out.println(ipLog.requestFromPhone);

                // add new logged information to logList
                logList.add(ipLog);

                // add data to related list
                pictureURLList.add(ipLog.pictureURL);
                pictureTagList.add(ipLog.searchTerm);
                latencyList.add(ipLog.latency);
            }

            request.setAttribute("pictureURL", pictureURL);
            request.setAttribute("loggedImg", logList);
            request.setAttribute("prevPicture", pictureURLList);
            request.setAttribute("topSearchTerm", ipm.getTopSearchTerm(pictureTagList));
            //System.out.println(ipm.getTopSearchTerm(pictureTagList).get(0));
            request.setAttribute("avgLatency", ipm.getAvgLatency(latencyList));
            //System.out.println("avgLatency: "+ipm.getAvgLatency(latencyList));

            // choose dashboard view
            nextView = "dashboard.jsp";
        }

        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}

