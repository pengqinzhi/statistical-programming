<%-- @author Qinzhi Peng, qinzhip--%>
<?xml version="1.0" encoding="UTF-8"?>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="ds.IPLog" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Dashboard</title>
    </head>
    <body>
        <h1>Here is an interesting picture</h1>
            <% if (request.getAttribute("pictureURL") != null) { %>
                <img src="<%= request.getAttribute("pictureURL")%>" alt="random image">
            <% } %>
        <form action="getAnInterestingPicture" method="GET">
            <label for="letter">Type another word.</label>
            <input type="text" name="searchWord" id="letter" value="" /><br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>

