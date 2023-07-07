package ds.project1task3;

// @author Qinzhi Peng

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ClickerServlet", urlPatterns = {"/submit", "/getResults"})
public class ClickerServlet extends HttpServlet {
    // Create class variables for count
    int countA = 0;
    int countB = 0;
    int countC = 0;
    int countD = 0;

    ClickerModel cm = null;

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        cm = new ClickerModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nextView = "";
        if (request.getServletPath().equals("/submit")) {
            // get the choice parameter if it exists
            String currentChoice = request.getParameter("choice");
            System.out.println("currentChoice is " + currentChoice);
            if (currentChoice != null) {
                request.setAttribute("currentChoice", currentChoice);
                countA = cm.recordClicker(currentChoice, "A", countA);
                countB = cm.recordClicker(currentChoice, "B", countB);
                countC = cm.recordClicker(currentChoice, "C", countC);
                countD = cm.recordClicker(currentChoice, "D", countD);
            }
            // Choose the prompt view
            nextView = "prompt.jsp";
        } else if (request.getServletPath().equals("/getResults")) {
            // Set the count number if the count is not zero
            if (countA != 0) request.setAttribute("countA", countA);
            if (countB != 0) request.setAttribute("countB", countB);
            if (countC != 0) request.setAttribute("countC", countC);
            if (countD != 0) request.setAttribute("countD", countD);

            // Clear the count
            countA = 0;
            countB = 0;
            countC = 0;
            countD = 0;

            // Choose the result view
            nextView = "result.jsp";
        }

        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }

    public void destroy() {
    }

}