package ds.project1task1;

// @author Qinzhi Peng

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "computeHashes", value = "/computeHashes")
public class ComputeHashes extends HttpServlet {

    ComputeHasesModel chm = null;

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        chm = new ComputeHasesModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // get the search parameter if it exists
        String text = request.getParameter("text");
        String hashChoice = request.getParameter("hashChoice");

        /*
         * Check if the search parameter is present.
         * If not, then keep the page
         * If there is a text parameter, then return the result.
         */
        System.out.println(text);
        if (!text.equals("")) {
            // Use model to compute hash
            String hash_hex = chm.computeHashesHex(text, hashChoice);
            String hash_base = chm.computeHashesBase64(text, hashChoice);
            request.setAttribute("oriText", text);
            request.setAttribute("hashChoice", hashChoice);
            request.setAttribute("hash_hex", hash_hex);
            request.setAttribute("hash_base", hash_base);

            // Text parameter exists so choose the result view
            RequestDispatcher view = request.getRequestDispatcher("result.jsp");
            view.forward(request, response);

        } else {
            // no text parameter so choose the index view
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request, response);
        }
    }

    public void destroy() {
    }

}