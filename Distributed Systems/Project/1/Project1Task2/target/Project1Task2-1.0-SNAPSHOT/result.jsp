<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dog Information</title>
    </head>
    <body>
        <h1>Dog: <%= request.getParameter("dogBreed")%></h1><br>
        <h2>Friendly: <%= request.getAttribute("friendly")%></h2>
        <h2>Intelligence: <%= request.getAttribute("intelligence")%></h2>
        <h2>Height: <%= request.getAttribute("height")%></h2>
        <h2>Weight: <%= request.getAttribute("weight")%></h2>
        <h2>Lifespan: <%= request.getAttribute("lifeSpan")%></h2>
        <h3>Credit: https://dogtime.com/dog-breeds/profiles</h3><br>
        <% if (request.getAttribute("pictureURL") != null) { %>
            <img src="<%= request.getAttribute("pictureURL")%>"><br>
        <% } %>
        <h3>Credit: https://dog.ceo/dog-api/</h3><br><br>
        <form action="getDog" method="GET">
<%--            Return to the original page--%>
            <input type="submit" name = "submit" οnclick="javascript:history.back(-1);" value="Continue" />
        </form>
    </body>
</html>

