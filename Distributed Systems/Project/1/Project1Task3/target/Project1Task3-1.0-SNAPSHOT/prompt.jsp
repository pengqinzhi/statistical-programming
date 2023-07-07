<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>DS Clicker</title>
    </head>
    <body>
     <h1>Distributed Systems Class Clicker</h1>
        <% if (request.getAttribute("currentChoice") == null) { %>
            <p>Submit your answer to the current question:</p>
        <% } else { %>
            <p>Your "<%= request.getAttribute("currentChoice")%>" response has been registered</p>
            <p>Submit your answer to the next question:</p>
        <% } %>
        <form action="submit" method="post">
            <input type="radio" name="choice" id="A" value="A"/><label for="A"> A </label><br/>
            <input type="radio" name="choice" id="B" value="B"/><label for="B"> B </label><br/>
            <input type="radio" name="choice" id="C" value="C"/><label for="C"> C </label><br/>
            <input type="radio" name="choice" id="D" value="D"/><label for="D"> D </label><br/><br/>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>