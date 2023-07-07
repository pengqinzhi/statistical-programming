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
<h1>Previously searched images (5)</h1>
<table>
    <tbody>
    <% ArrayList<String> pictureList = (ArrayList<String>) request.getAttribute("prevPicture"); %>
    <% for (int i = 0; i < pictureList.size(); i++) { %>
    <% if (i < 5) { %>
    <img src="<%= pictureList.get(pictureList.size() - 1 - i)%>" alt="random image"><br>
    <% } %>
    <% } %>
    </tbody>
</table>

<h1>Top 5 picture search terms(&counts)</h1>
<table>
    <% ArrayList<Map.Entry<String, Integer>> topSearchTerm = (ArrayList<Map.Entry<String, Integer>>) request.getAttribute("topSearchTerm"); %>
    <% for (int i = 0; i < topSearchTerm.size(); i++) { %>
    <% if (i < 5) { %>
    <a> <%= topSearchTerm.get(i) %></a><br>
    <% } %>
    <% } %>
</table>

<h1>Average search latency</h1>
<table>
    <a> Average search latency is <%= request.getAttribute("avgLatency") %> (seconds)</a><br>
</table>

<h1>Display the data logs</h1>
<table>
    <% ArrayList<IPLog> logList = (ArrayList<IPLog>) request.getAttribute("loggedImg"); %>
    <% for (int i = 0; i < logList.size(); i++) { %>
        <a>Search ID: <%= logList.get(i).id %>,</a><br>
        <a>Search term: <%= logList.get(i).searchTerm %>,</a><br>
        <a>Request from phone: <%= logList.get(i).requestFromPhone %>,</a><br>
        <a>Request to API: <%= logList.get(i).requestToAPI %>,</a><br>
        <a>Response from API: <%= logList.get(i).responseFromAPI %>, </a><br>
        <a>PictureURL: <%= logList.get(i).pictureURL %>,</a><br>
        <a>Search latency: <%= logList.get(i).latency %></a><br><br>
    <% } %>
</table>
</body>
</html>