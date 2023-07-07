<%-- @author Qinzhi Peng, qinzhip--%>
<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
<head>
    <title>Interesting Picture</title>
</head>
<body>
    <% if (request.getAttribute("pictureURL") != null) { %>
    <h1>Here is an interesting picture</h1>
    <img src="<%= request.getAttribute("pictureURL")%>" alt="random image">
    <% } %>
    <form action="getAnInterestingPicture" method="GET">
        <label for="letter">Type another word.</label>
        <input type="text" name="searchWord" id="letter" value="" /><br>
        <input type="submit" value="Submit" />
    </form>
</body>
</html>

