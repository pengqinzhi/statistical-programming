package ds;
/*
 * @author Qinzhi Peng, qinzhip
 * The servlet is acting as the controller.
 */

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


/*
 * It states that when the user browses to the URL path /getAnInterestingPicture
 * then the servlet with the name InterestingPictureServlet should be used.
 *
 */
@WebServlet(name = "InterestingPictureServlet",
        urlPatterns = {"/getAnInterestingPicture"})
public class InterestingPictureServlet extends HttpServlet {

    InterestingPictureModel ipm = null;  // The "business model" for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        ipm = new InterestingPictureModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // get the search parameter if it exists
        String search = request.getParameter("searchWord");
        // Debugging:
        // String search = "app";

        /*
         * Check if the search parameter is present.
         * If there is a search parameter, then do the search and return the result.
         */
        String nextView;
        if (search != null) {
            // use model to do the search and choose the result view
            String pictureURL = ipm.doPexelSearch(search);

            // set the picture tag and url
            request.setAttribute("pictureURL", pictureURL);
            
            // Pass the user search string (pictureTag) also to the view.
            nextView = "response.jsp";
        } else {
            // no search parameter so choose the prompt view
            nextView = "prompt.jsp";
        }
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}

