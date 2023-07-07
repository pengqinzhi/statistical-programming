package ds.project1task2;

// @author Qinzhi Peng

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DogFinderServlet", urlPatterns = {"/getDog"})
public class DogFinderServlet extends HttpServlet {

    DogFinderModel dfm = null;

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        dfm = new DogFinderModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // get the search parameter if it exists
        String dogBreed = request.getParameter("dogBreed");

        String nextView;
        /*
         * Check if the dogBreed parameter is present.
         * If not, then give the user instructions and prompt for choosing a dog Breed.
         * If there is a dogBreed parameter, then do the search and return the result.
         */
        if (dogBreed != null) {
            // use model to do the search and choose the result view
            String dogPictureUrl = dfm.doDogSearch(dogBreed.toLowerCase());
            request.setAttribute("pictureURL", dogPictureUrl);

            List<String> dogInfo = dfm.searchDogInfo(dogBreed.toLowerCase());
            request.setAttribute("friendly", dogInfo.get(0));
            request.setAttribute("intelligence", dogInfo.get(1));
            request.setAttribute("height", dogInfo.get(2));
            request.setAttribute("weight", dogInfo.get(3));
            request.setAttribute("lifeSpan", dogInfo.get(4));

            nextView = "result.jsp";
        } else {
            // no dogBreed parameter so choose the prompt view
            nextView = "prompt.jsp";
        }
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}